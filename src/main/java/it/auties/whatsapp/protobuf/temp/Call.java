package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class Call {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("uint32")
  private int conversionDelaySeconds;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("bytes")
  private byte[] conversionData;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String conversionSource;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] callKey;
}
