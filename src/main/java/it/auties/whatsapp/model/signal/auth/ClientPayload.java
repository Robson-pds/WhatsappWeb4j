package it.auties.whatsapp.model.signal.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import it.auties.protobuf.api.model.ProtobufMessage;
import it.auties.protobuf.api.model.ProtobufProperty;
import it.auties.whatsapp.model.signal.sender.SenderKeyRecord;
import it.auties.whatsapp.model.signal.sender.SenderKeyState;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.auties.protobuf.api.model.ProtobufProperty.Type.*;

@AllArgsConstructor
@Data
@Builder
@Jacksonized
@Accessors(fluent = true)
public class ClientPayload implements ProtobufMessage {
  @ProtobufProperty(index = 1, type = UINT64)
  private Long username;

  @ProtobufProperty(index = 3, type = BOOLEAN)
  private boolean passive;

  @ProtobufProperty(index = 4, type = MESSAGE,
          concreteType = ClientPayloadClientFeature.class, repeated = true)
  private List<ClientPayloadClientFeature> clientFeatures;

  @ProtobufProperty(index = 5, type = MESSAGE, concreteType = UserAgent.class)
  private UserAgent userAgent;

  @ProtobufProperty(index = 6, type = MESSAGE, concreteType = WebInfo.class)
  private WebInfo webInfo;

  @ProtobufProperty(index = 7, type = STRING)
  private String pushName;

  @ProtobufProperty(index = 9, type = SFIXED32)
  private Integer sessionId;

  @ProtobufProperty(index = 10, type = BOOLEAN)
  private boolean shortConnect;

  @ProtobufProperty(index = 12, type = MESSAGE, concreteType = ClientPayloadConnectType.class)
  private ClientPayloadConnectType connectType;

  @ProtobufProperty(index = 13, type = MESSAGE, concreteType = ClientPayloadConnectReason.class)
  private ClientPayloadConnectReason connectReason;

  @ProtobufProperty(index = 14, type = INT32, repeated = true)
  private List<Integer> shards;

  @ProtobufProperty(index = 15, type = MESSAGE, concreteType = DNSSource.class)
  private DNSSource dnsSource;

  @ProtobufProperty(index = 16, type = UINT32)
  private Integer connectAttemptCount;

  @ProtobufProperty(index = 17, type = UINT32)
  private Integer agent;

  @ProtobufProperty(index = 18, type = UINT32)
  private Integer device;

  @ProtobufProperty(index = 19, type = MESSAGE, concreteType = CompanionData.class)
  private CompanionData regData;

  @ProtobufProperty(index = 20, type = MESSAGE, concreteType = ClientPayloadProduct.class)
  private ClientPayloadProduct product;

  @ProtobufProperty(index = 21, type = BYTES)
  private byte[] fbCat;

  @ProtobufProperty(index = 22, type = BYTES)
  private byte[] fbUserAgent;

  @ProtobufProperty(index = 23, type = BOOLEAN)
  private boolean oc;

  @ProtobufProperty(index = 30, type = MESSAGE, concreteType = ClientPayloadIOSAppExtension.class)
  private ClientPayloadIOSAppExtension iosAppExtension;

  @AllArgsConstructor
  @Accessors(fluent = true)
  public enum ClientPayloadClientFeature implements ProtobufMessage {
    NONE(0);

    @Getter
    private final int index;

    @JsonCreator
    public static ClientPayloadClientFeature forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }

  @AllArgsConstructor
  @Accessors(fluent = true)
  public enum ClientPayloadIOSAppExtension implements ProtobufMessage {
    SHARE_EXTENSION(0),
    SERVICE_EXTENSION(1),
    INTENTS_EXTENSION(2);

    @Getter
    private final int index;

    public static ClientPayloadIOSAppExtension forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }

  @AllArgsConstructor
  @Accessors(fluent = true)
  public enum ClientPayloadConnectType implements ProtobufMessage{
    CELLULAR_UNKNOWN(0),
    WIFI_UNKNOWN(1),
    CELLULAR_EDGE(100),
    CELLULAR_IDEN(101),
    CELLULAR_UMTS(102),
    CELLULAR_EVDO(103),
    CELLULAR_GPRS(104),
    CELLULAR_HSDPA(105),
    CELLULAR_HSUPA(106),
    CELLULAR_HSPA(107),
    CELLULAR_CDMA(108),
    CELLULAR_1XRTT(109),
    CELLULAR_EHRPD(110),
    CELLULAR_LTE(111),
    CELLULAR_HSPAP(112);

    @Getter
    private final int index;

    public static ClientPayloadConnectType forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }

  @AllArgsConstructor
  @Accessors(fluent = true)
  public enum ClientPayloadConnectReason implements ProtobufMessage {
    PUSH(0),
    USER_ACTIVATED(1),
    SCHEDULED(2),
    ERROR_RECONNECT(3),
    NETWORK_SWITCH(4),
    PING_RECONNECT(5);

    @Getter
    private final int index;

    public static ClientPayloadConnectReason forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }

  @AllArgsConstructor
  @Accessors(fluent = true)
  public enum ClientPayloadProduct implements ProtobufMessage{
    WHATSAPP(0),
    MESSENGER(1);

    @Getter
    private final int index;

    public static ClientPayloadProduct forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }

  public static class ClientPayloadBuilder {
    public ClientPayloadBuilder clientFeatures(List<ClientPayloadClientFeature> clientFeatures) {
      if(this.clientFeatures == null) this.clientFeatures = new ArrayList<>();
      this.clientFeatures.addAll(clientFeatures);
      return this;
    }

    public ClientPayloadBuilder shards(List<Integer> shards) {
      if(this.shards == null) this.shards = new ArrayList<>();
      this.shards.addAll(shards);
      return this;
    }
  }
}
