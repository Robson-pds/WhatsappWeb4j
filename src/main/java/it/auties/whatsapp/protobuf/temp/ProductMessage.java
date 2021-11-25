package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ProductMessage {
  @JsonProperty(value = "17")
  @JsonPropertyDescription("ContextInfo")
  private ContextInfo contextInfo;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("CatalogSnapshot")
  private CatalogSnapshot catalog;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String businessOwnerJid;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("ProductSnapshot")
  private ProductSnapshot product;
}
