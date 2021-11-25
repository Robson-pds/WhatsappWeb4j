package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class SendPaymentMessage {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("PaymentBackground")
  private PaymentBackground background;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("MessageKey")
  private MessageKey requestMessageKey;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("Message")
  private Message noteMessage;
}
