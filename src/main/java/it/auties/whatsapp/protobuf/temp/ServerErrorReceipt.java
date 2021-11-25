package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ServerErrorReceipt {
  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String stanzaId;
}
