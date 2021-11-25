package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class VerifiedNameCertificate {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("bytes")
  private byte[] serverSignature;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("bytes")
  private byte[] signature;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] details;
}
