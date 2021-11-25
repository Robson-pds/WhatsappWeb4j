package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class GroupInviteMessage {
  @JsonProperty(value = "7")
  @JsonPropertyDescription("ContextInfo")
  private ContextInfo contextInfo;

  @JsonProperty(value = "6")
  @JsonPropertyDescription("string")
  private String caption;

  @JsonProperty(value = "5")
  @JsonPropertyDescription("bytes")
  private byte[] jpegThumbnail;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("string")
  private String groupName;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("int64")
  private long inviteExpiration;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String inviteCode;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String groupJid;
}
