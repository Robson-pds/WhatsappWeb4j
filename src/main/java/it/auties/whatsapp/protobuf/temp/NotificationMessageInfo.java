package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class NotificationMessageInfo {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("string")
  private String participant;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("uint64")
  private long messageTimestamp;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("Message")
  private Message message;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("MessageKey")
  private MessageKey key;
}
