package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ExternalBlobReference {
  @JsonProperty(value = "6")
  @JsonPropertyDescription("bytes")
  private byte[] fileEncSha256;

  @JsonProperty(value = "5")
  @JsonPropertyDescription("bytes")
  private byte[] fileSha256;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("uint64")
  private long fileSizeBytes;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String handle;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String directPath;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] mediaKey;
}
