package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class MarkChatAsReadAction {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("SyncActionMessageRange")
  private SyncActionMessageRange messageRange;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bool")
  private boolean read;
}
