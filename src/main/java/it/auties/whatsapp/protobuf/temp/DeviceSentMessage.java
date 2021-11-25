package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class DeviceSentMessage {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String phash;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("Message")
  private Message message;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String destinationJid;
}
