package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class Point {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("double")
  private double y;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("double")
  private double x;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("int32")
  private int yDeprecated;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("int32")
  private int xDeprecated;
}
