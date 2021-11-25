package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import it.auties.whatsapp.protobuf.info.MessageInfo;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class HistorySyncMsg {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("uint64")
  private long msgOrderId;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("WebMessageInfo")
  private MessageInfo message;
}
