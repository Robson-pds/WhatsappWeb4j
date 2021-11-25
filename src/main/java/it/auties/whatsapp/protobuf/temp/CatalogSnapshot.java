package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class CatalogSnapshot {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String description;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String title;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("ImageMessage")
  private ImageMessage catalogImage;
}
