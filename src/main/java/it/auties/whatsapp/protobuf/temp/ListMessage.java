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
public class ListMessage {
  @JsonProperty(value = "8")
  @JsonPropertyDescription("ContextInfo")
  private ContextInfo contextInfo;

  @JsonProperty(value = "7")
  @JsonPropertyDescription("string")
  private String footerText;

  @JsonProperty(value = "6")
  @JsonPropertyDescription("ProductListInfo")
  private ProductListInfo productListInfo;

  @JsonProperty(value = "5")
  @JsonPropertyDescription("Section")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<Section> sections;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("ListMessageListType")
  private ListMessageListType listType;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("string")
  private String buttonText;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String description;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String title;

  @Accessors(fluent = true)
  public enum ListMessageListType {
    UNKNOWN(0),
    SINGLE_SELECT(1),
    PRODUCT_LIST(2);

    private final @Getter int index;

    ListMessageListType(int index) {
      this.index = index;
    }

    @JsonCreator
    public static ListMessageListType forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }
}
