package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class MessageContextInfo {
  @JsonProperty(value = "2")
  @JsonPropertyDescription("int32")
  private int deviceListMetadataVersion;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("DeviceListMetadata")
  private DeviceListMetadata deviceListMetadata;
}
