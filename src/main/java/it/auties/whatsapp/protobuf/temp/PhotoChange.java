package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class PhotoChange {
  @JsonProperty(value = "3")
  @JsonPropertyDescription("uint32")
  private int newPhotoId;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("bytes")
  private byte[] newPhoto;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] oldPhoto;
}
