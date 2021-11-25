package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class ExitCode {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String text;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("uint64")
  private long code;
}
