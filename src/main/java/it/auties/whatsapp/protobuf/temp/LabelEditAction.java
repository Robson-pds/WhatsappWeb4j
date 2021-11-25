package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class LabelEditAction {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("bool")
  private boolean deleted;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("int32")
  private int predefinedId;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("int32")
  private int color;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String name;
}
