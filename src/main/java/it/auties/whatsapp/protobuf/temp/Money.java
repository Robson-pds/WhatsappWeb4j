package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class Money {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String currencyCode;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("uint32")
  private int offset;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("int64")
  private long value;
}
