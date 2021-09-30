package it.auties.whatsapp4j.common.protobuf.model.hsm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class HSMCurrency {
  @JsonProperty(value = "2")
  private long amount1000;

  @JsonProperty(value = "1")
  private String currencyCode;
}