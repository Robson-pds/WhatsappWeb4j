package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class MessageKey {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("string")
  private String participant;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String id;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("bool")
  private boolean fromMe;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String remoteJid;
}
