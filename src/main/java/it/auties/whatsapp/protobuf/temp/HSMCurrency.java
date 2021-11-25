package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class HSMCurrency {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("int64")
  private long amount1000;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String currencyCode;
}
