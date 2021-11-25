package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import java.util.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ProductListInfo {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String businessOwnerJid;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("ProductListHeaderImage")
  private ProductListHeaderImage headerImage;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("ProductSection")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<ProductSection> productSections;
}
