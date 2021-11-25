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
public class ADVKeyIndexList {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("uint32")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<Integer> validIndexes;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("uint32")
  private int currentIndex;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("uint64")
  private long timestamp;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("uint32")
  private int rawId;
}
