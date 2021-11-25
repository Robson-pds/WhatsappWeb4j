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
public class DeviceListMetadata {
  @JsonProperty(value = "10")
  @JsonPropertyDescription("uint32")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<Integer> recipientKeyIndexes;

  @JsonProperty(value = "9")
  @JsonPropertyDescription("uint64")
  private long recipientTimestamp;

  @JsonProperty(value = "8")
  @JsonPropertyDescription("bytes")
  private byte[] recipientKeyHash;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("uint32")
  @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
  private List<Integer> senderKeyIndexes;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("uint64")
  private long senderTimestamp;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] senderKeyHash;
}
