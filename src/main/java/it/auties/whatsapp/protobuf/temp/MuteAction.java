package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class MuteAction {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("int64")
  private long muteEndTimestamp;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bool")
  private boolean muted;
}
