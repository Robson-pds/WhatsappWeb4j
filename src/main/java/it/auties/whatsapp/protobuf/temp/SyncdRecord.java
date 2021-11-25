package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class SyncdRecord {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("KeyId")
  private KeyId keyId;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("SyncdValue")
  private SyncdValue value;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("SyncdIndex")
  private SyncdIndex index;
}
