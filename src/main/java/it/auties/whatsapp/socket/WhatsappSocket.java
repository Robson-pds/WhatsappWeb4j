package it.auties.whatsapp.socket;

import it.auties.protobuf.decoder.ProtobufDecoder;
import it.auties.protobuf.encoder.ProtobufEncoder;
import it.auties.whatsapp.api.WhatsappConfiguration;
import it.auties.whatsapp.binary.BinaryArray;
import it.auties.whatsapp.binary.BinaryMessage;
import it.auties.whatsapp.cipher.Cipher;
import it.auties.whatsapp.cipher.NoiseHandshake;
import it.auties.whatsapp.cipher.Request;
import it.auties.whatsapp.manager.WhatsappKeys;
import it.auties.whatsapp.manager.WhatsappStore;
import it.auties.whatsapp.protobuf.contact.ContactId;
import it.auties.whatsapp.protobuf.message.server.HandshakeMessage;
import it.auties.whatsapp.protobuf.model.Node;
import it.auties.whatsapp.protobuf.model.PreKey;
import it.auties.whatsapp.protobuf.temp.*;
import it.auties.whatsapp.utils.Qr;
import it.auties.whatsapp.utils.Validate;
import it.auties.whatsapp.utils.WhatsappUtils;
import jakarta.websocket.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import org.whispersystems.curve25519.Curve25519;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.util.Map.of;

@RequiredArgsConstructor
@Data
@Accessors(fluent = true)
@ClientEndpoint(configurator = WhatsappSocketConfiguration.class)
@Log
public class WhatsappSocket {
    private @Getter(onMethod = @__(@NonNull)) Session session;
    private boolean loggedIn;
    private final NoiseHandshake handshake;
    private final @NonNull WebSocketContainer container;
    private final @NonNull WhatsappConfiguration options;
    private final @NonNull WhatsappStore store;
    private final @NonNull WhatsappKeys keys;
    private final @NonNull Digester digester;
    private final @NonNull Qr generator;
    private CountDownLatch lock;
    private long readCounter;
    private long writeCounter;

    public WhatsappSocket(@NonNull WhatsappConfiguration options, @NonNull WhatsappStore store, @NonNull WhatsappKeys keys) {
        this.handshake = new NoiseHandshake();
        this.container = ContainerProvider.getWebSocketContainer();
        this.options = options;
        this.store = store;
        this.keys = keys;
        this.digester = new Digester();
        this.generator = new Qr();
        this.lock = new CountDownLatch(1);
    }

    @OnOpen
    public void onOpen(@NonNull Session session) {
        session(session);
        if(loggedIn){
            return;
        }

        handshake.start(keys());
        handshake.updateHash(keys.ephemeralKeyPair().publicKey());
        var clientHello = new ClientHello(null, null, keys.ephemeralKeyPair().publicKey());
        Request.of(new HandshakeMessage(clientHello))
                .send(session(), keys(), store());
    }

    @OnMessage
    @SneakyThrows
    public void onBinary(byte @NonNull [] raw) {
        var message = new BinaryMessage(raw).decoded().data();
        if(!loggedIn){
            authenticate(message);
            lock.countDown();
            return;
        }

        var deciphered = Cipher.decipherMessage(message, keys.readKey(), store.readCounter().getAndIncrement());
        digester.digest(deciphered);
    }

    @SneakyThrows
    private void authenticate(byte[] message) {
        var serverHello = ProtobufDecoder.forType(HandshakeMessage.class).decode(message).serverHello();
        var ephemeralPrivate = Cipher.toPKCS8Encoded(keys.ephemeralKeyPair().privateKey());
        handshake.updateHash(serverHello.ephemeral());
        var sharedEphemeral = Cipher.calculateSharedSecret(Cipher.toX509Encoded(serverHello.ephemeral()), ephemeralPrivate);
        handshake.mixIntoKey(sharedEphemeral.data());

        var decodedStaticText = handshake.cypher(serverHello._static(), false);
        var sharedStatic = Cipher.calculateSharedSecret(Cipher.toX509Encoded(decodedStaticText), ephemeralPrivate);
        handshake.mixIntoKey(sharedStatic.data());
        handshake.cypher(serverHello.payload(), false);

        var encodedKey = handshake.cypher(Cipher.raw(keys.keyPair().getPublic()), true);
        var sharedPrivate = Cipher.calculateSharedSecret(Cipher.toX509Encoded(serverHello.ephemeral()), keys.keyPair().getPrivate());
        handshake.mixIntoKey(sharedPrivate.data());

        var encodedPayload = handshake.cypher(createPayload(), true);
        var clientFinish = new ClientFinish(encodedPayload, encodedKey);
        Request.of(new HandshakeMessage(clientFinish))
                .send(session(), keys(), store());
        changeState(true);
        handshake.finish();
    }

    @SneakyThrows
    private byte[] createPayload() {
        var payload = createClientPayload(keys().hasUser());
        if (!keys().hasUser()) {
            payload.regData(createRegisterData());
        }else {
            payload.username(Long.parseLong(keys().user().user()))
                    .device(keys().user().device());
        }

        return ProtobufEncoder.encode(payload.build());
    }

    private ClientPayload.ClientPayloadBuilder createClientPayload(boolean passive) {
        return ClientPayload.builder()
                .connectReason(ClientPayload.ClientPayloadConnectReason.USER_ACTIVATED)
                .connectType(ClientPayload.ClientPayloadConnectType.WIFI_UNKNOWN)
                .userAgent(createUserAgent())
                .passive(passive)
                .webInfo(WebInfo.builder().webSubPlatform(WebInfo.WebInfoWebSubPlatform.WEB_BROWSER).build());
    }

    private UserAgent createUserAgent() {
        return UserAgent.builder()
                .appVersion(new AppVersion(0, 0, 14, 2126, 2))
                .platform(UserAgent.UserAgentPlatform.WEB)
                .releaseChannel(UserAgent.UserAgentReleaseChannel.RELEASE)
                .mcc("000")
                .mnc("000")
                .osVersion("0.1")
                .manufacturer("")
                .device("Desktop")
                .osBuildNumber("0.1")
                .localeLanguageIso6391("en")
                .localeCountryIso31661Alpha2("en")
                .build();
    }

    @SneakyThrows
    private CompanionRegData createRegisterData() {
        return CompanionRegData.builder()
                .buildHash(Base64.getDecoder().decode("S9Kdc4pc4EJryo21snc5cg=="))
                .companionProps(ProtobufEncoder.encode(createCompanionProps()))
                .eRegid(BinaryArray.of(keys().id(), 4).data())
                .eKeytype(BinaryArray.of(5, 1).data())
                .eIdent(keys().signedIdentityKey().publicKey())
                .eSkeyId(keys().signedPreKey().id())
                .eSkeyVal(keys().signedPreKey().keyPair().publicKey())
                .eSkeySig(keys().signedPreKey().signature())
                .build();
    }

    private CompanionProps createCompanionProps() {
        return CompanionProps.builder()
                .os("Windows")
                .version(new AppVersion(0, 0, 0, 0, 10))
                .platformType(CompanionProps.CompanionPropsPlatformType.CHROME)
                .requireFullSync(false)
                .build();
    }

    public void connect() {
        try{
            container.connectToServer(this, URI.create(options.whatsappUrlBeta()));
            lock.await();
        }catch (IOException | DeploymentException | InterruptedException exception){
            throw new RuntimeException("Cannot connect to WhatsappWeb's WebServer", exception);
        }
    }

    @SneakyThrows
    private void reconnect(){
        changeState(false);
        session().close();
        connect();
    }

    public void disconnect(){
        try{
            session.close();
        }catch (IOException exception){
            throw new RuntimeException("Cannot close connection to WhatsappWeb's WebServer", exception);
        }
    }

    private void changeState(boolean loggedIn){
        this.loggedIn = loggedIn;
        this.readCounter = 0;
        this.writeCounter = 0;
        this.lock = new CountDownLatch(1);
        keys().clear();
    }

    @OnClose
    public void onClose(){
        System.out.println("Closed");
    }

    @OnError
    public void onError(Throwable throwable){
        throwable.printStackTrace();
    }

    private class Digester {
        private void digest(@NonNull Node node) {
            System.out.printf("Received: %s%n", node);
            switch (node.description()){
                case "iq" -> digestIq(node);
                case "success" -> digestSuccess();
                case "stream:error" -> digestError(node);
                case "failure" -> digestFailure(node);
                case "xmlstreamend" -> disconnect();
                default -> System.err.println("Unhandled");
            }
        }

        private void digestFailure(Node node) {
            Validate.isTrue(node.attributes().get("reason").equals(401),
                    "WhatsappWeb failure at %s, status code: %s",
                    node.attributes().get("location"), node.attributes().get("reason"));
            reconnect();
        }

        private void digestError(Node node) {
            var code = (Integer) node.attributes().get("code");
            if(code != 515){
                return;
            }

            reconnect();
        }

        private void digestSuccess() {
            sendPreKeys();
            confirmConnection();
        }

        private void digestIq(Node node) {
            var children = node.childNodes();
            if(children.isEmpty()){
                return;
            }

            var container = children.getFirst();
            switch (container.description()){
                case "pair-device" -> generateQrCode(node, container);
                case "pair-success" -> confirmQrCode(node, container);
                case "active" -> store().callListeners(listener -> listener.onLoggedIn(keys().user()));
                default -> throw new IllegalArgumentException("Cannot handle iq request, unknown description. %s%n".formatted(container.description()));
            }
        }

        private void confirmConnection() {
            Node.of("iq", of("id", WhatsappUtils.buildRequestTag(options()), "to", ContactId.WHATSAPP_SERVER, "xmlns", "passive", "type", "set"), Node.of("active"))
                    .toRequest()
                    .send(session(), keys(), store());
        }

        private void sendPreKeys() {
            if(keys().preKeys()){
                return;
            }

            keys().preKeys(true);
            Node.of("iq", of("id", WhatsappUtils.buildRequestTag(options()), "xmlns", "encrypt", "type", "set", "to", ContactId.WHATSAPP_SERVER), createPreKeysContent())
                    .toRequest()
                    .send(session(), keys(), store());
        }

        private Node[] createPreKeysContent() {
            return new Node[]{createPreKeysRegistration(), createPreKeysType(),
                    createPreKeysIdentity(), createPreKeys(), keys().signedPreKey().encode()};
        }

        private Node createPreKeysIdentity() {
            return Node.of("identity", keys().signedIdentityKey().publicKey());
        }

        private Node createPreKeysType() {
            return Node.of("type", "");
        }

        private Node createPreKeysRegistration() {
            return Node.of("registration", BinaryArray.of(keys().id(), 4).data());
        }

        private Node createPreKeys(){
            var nodes = IntStream.range(0, 30)
                    .mapToObj(PreKey::fromIndex)
                    .map(PreKey::encode)
                    .toList();
            return Node.of("list", nodes);
        }

        private void generateQrCode(Node node, Node container) {
            var qr = decodeQrCode(container);
            var matrix = generator.generate(qr, Cipher.raw(keys().keyPair().getPublic()), keys().signedIdentityKey().publicKey(), keys().advSecretKey());
            store().callListeners(listener -> listener.onQRCode(matrix));
            sendConfirmNode(node, null);
            Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate(this::ping, 0L, 30L, TimeUnit.SECONDS);
        }

        private void ping() {
            Node.of("iq", of("id", WhatsappUtils.buildRequestTag(options()), "to", ContactId.WHATSAPP_SERVER, "type", "get", "xmlns", "w:p"), Node.of("ping"))
                    .toRequest()
                    .send(session(), keys(), store());
        }

        private String decodeQrCode(Node container) {
            return container.findNodeByDescription("ref")
                    .filter(ref -> ref.content() instanceof byte[])
                    .map(ref -> (byte[]) ref.content())
                    .map(String::new)
                    .orElseThrow(() -> new NoSuchElementException("Pairing error: missing qr code reference"));
        }


        private void confirmQrCode(Node node, Node container) {
           try {
               keys().user(fetchJid(container));

               var curve = Curve25519.getInstance(Curve25519.BEST);
               var deviceIdentity = fetchDeviceIdentity(container);

               var advIdentity = ProtobufDecoder.forType(ADVSignedDeviceIdentityHMAC.class).decode(deviceIdentity);
               var advSecret = Base64.getDecoder().decode(keys().advSecretKey());
               var advSign = Cipher.hmacSha256(BinaryArray.of(advIdentity.details()), BinaryArray.of(advSecret));
               Validate.isTrue(Arrays.equals(advIdentity.hmac(), advSign.data()), "Cannot login: Hmac validation failed!", SecurityException.class);

               var account = ProtobufDecoder.forType(ADVSignedDeviceIdentity.class).decode(advIdentity.details());
               var message = BinaryArray.of(new byte[]{6, 0})
                       .append(account.details())
                       .append(keys().signedIdentityKey().publicKey())
                       .data();
               Validate.isTrue(curve.verifySignature(account.accountSignatureKey(), message, account.accountSignature()), "Cannot login: Hmac validation failed!", SecurityException.class);

               var deviceSignatureMessage = BinaryArray.of(new byte[]{6, 1})
                       .append(account.details())
                       .append(keys().signedIdentityKey().publicKey())
                       .append(account.accountSignature())
                       .data();
               var deviceSignature = curve.calculateSignature(keys().signedIdentityKey().privateKey(), deviceSignatureMessage);

               var keyIndex = ProtobufDecoder.forType(ADVDeviceIdentity.class).decode(account.details()).keyIndex();
               var identity = ProtobufEncoder.encode(account.deviceSignature(deviceSignature).accountSignature(null));
               var identityNode = Node.of("device-identity", of("key-index", keyIndex), identity);
               var content = Node.of("pair-device-sign", identityNode);

               sendConfirmNode(node, content);
           }catch (IOException exception){
               throw new IllegalArgumentException("Cannot confirm qr code", exception);
           }
        }

        private void sendConfirmNode(Node node, Node content) {
            Node.of("iq", of("id", Objects.requireNonNull(node.attributes().get("id"), "Missing id"), "to", ContactId.WHATSAPP_SERVER, "type", "result"), content)
                    .toRequest()
                    .send(session(), keys(), store());
        }

        private byte[] fetchDeviceIdentity(Node container) {
            return container.findNodeByDescription("device-identity")
                    .map(Node::content)
                    .filter(data -> data instanceof byte[])
                    .map(data -> (byte[]) data)
                    .orElseThrow(() -> new NoSuchElementException("Cannot find device identity body for authentication in %s".formatted(container)));
        }

        private ContactId fetchJid(Node container) {
            return container.findNodeByDescription("device")
                    .map(node -> (ContactId) node.attributes().get("jid"))
                    .orElseThrow(() -> new NoSuchElementException("Cannot find jid attribute in %s".formatted(container)));
        }
    }
}
