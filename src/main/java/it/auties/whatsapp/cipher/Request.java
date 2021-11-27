package it.auties.whatsapp.cipher;

import it.auties.protobuf.encoder.ProtobufEncoder;
import it.auties.whatsapp.binary.BinaryEncoder;
import it.auties.whatsapp.manager.WhatsappKeys;
import it.auties.whatsapp.manager.WhatsappStore;
import it.auties.whatsapp.protobuf.model.Node;
import it.auties.whatsapp.utils.WhatsappUtils;
import jakarta.websocket.SendResult;
import jakarta.websocket.Session;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

/**
 * An abstract model class that represents a request made from the client to the server.
 */
@RequiredArgsConstructor
@Accessors(fluent = true )
public class Request {
    /**
     * The binary encoder, used to encode requests that take as a parameter a node
     */
    private static final BinaryEncoder NODE_ENCODER = new BinaryEncoder();

    /**
     * The id of this request
     */
    @Getter
    private final String id;

    /**
     * The body of the request
     */
    private final @NonNull Object body;

    /**
     * A future completed when Whatsapp sends a response or immediately after this request is sent if no response is expected
     */
    private final @NonNull CompletableFuture<Node> future = new CompletableFuture<>();

    /**
     * Whether this request requires a response
     */
    private final boolean response; // FIXME: 27/11/2021 Check if this field is needed

    /**
     * Constructs a new request with the provided body expecting a response
     */
    public static Request of(@NonNull Node body){
        return new Request(WhatsappUtils.readNullableId(body), NODE_ENCODER.encode(body), true);
    }

    /**
     * Constructs a new request with the provided body expecting a response
     */
    public static <R> Request of(@NonNull Object body){
        return new Request(null, ProtobufEncoder.encode(body), true);
    }

    /**
     * Sends a request to the WebSocket linked to {@code session}.
     *
     * @param store the store
     * @param session the WhatsappWeb's WebSocket session
     * @return this request
     */
    public @NonNull CompletableFuture<Node> send(@NonNull Session session, @NonNull WhatsappKeys keys, @NonNull WhatsappStore store){
        var encryptedBody = Cipher.cipherMessage(parseBodyOrThrow(), keys.writeKey(), store.writeCounter().getAndIncrement());
        session.getAsyncRemote()
                .sendBinary(encryptedBody.toBuffer(), result -> handleSendResult(store, result));
        return future;
    }

    /**
     * Completes this request using {@code response}
     *
     * @param response the response used to complete {@link Request#future}
     */
    public void complete(@NonNull Node response){
        future.complete(response);
    }

    private byte[] parseBodyOrThrow() {
        return switch (body){
            case byte[] bytes -> bytes;
            case Node node -> NODE_ENCODER.encode(node);
            default -> throw new IllegalStateException("Illegal body: " + body);
        };
    }

    private void handleSendResult(WhatsappStore store, SendResult result) {
        if (!result.isOK()) {
            throw new IllegalStateException("Cannot send request", result.getException());
        }

        if (response) {
            store.pendingRequests().add(this);
            return;
        }

        future.complete(null);
    }
}
