package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class Location {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String name;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("double")
  private double degreesLongitude;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("double")
  private double degreesLatitude;
}
