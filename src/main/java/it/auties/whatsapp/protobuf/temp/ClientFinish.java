package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ClientFinish {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("bytes")
  private byte[] payload;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] _static;
}
