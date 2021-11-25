package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class URLButton {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("HighlyStructuredMessage")
  private HighlyStructuredMessage url;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("HighlyStructuredMessage")
  private HighlyStructuredMessage displayText;
}
