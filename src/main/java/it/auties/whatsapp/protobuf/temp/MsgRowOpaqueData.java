package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class MsgRowOpaqueData {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("MsgOpaqueData")
  private MsgOpaqueData quotedMsg;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("MsgOpaqueData")
  private MsgOpaqueData currentMsg;
}
