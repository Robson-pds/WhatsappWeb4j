package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
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
public class AppVersion {
  @JsonProperty(value = "5")
  @JsonPropertyDescription("uint32")
  private int quinary;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("uint32")
  private int quaternary;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("uint32")
  private int tertiary;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("uint32")
  private int secondary;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("uint32")
  private int primary;
}
