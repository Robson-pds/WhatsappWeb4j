package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ContactAction {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String firstName;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String fullName;
}
