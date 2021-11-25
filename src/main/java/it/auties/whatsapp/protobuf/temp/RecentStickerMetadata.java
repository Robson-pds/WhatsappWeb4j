package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class RecentStickerMetadata {
  @JsonProperty(value = "7")
  @JsonPropertyDescription("bool")
  private boolean isSentByMe;

  @JsonProperty(value = "6")
  @JsonPropertyDescription("string")
  private String participant;

  @JsonProperty(value = "5")
  @JsonPropertyDescription("string")
  private String chatJid;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("string")
  private String stanzaId;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String mediaKey;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String encFilehash;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String directPath;
}
