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
public class ProductSection {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("Product")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<Product> products;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String title;
}
