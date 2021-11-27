package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class CompanionRegData {
  @JsonProperty(value = "8")
  @JsonPropertyDescription("bytes")
  private byte[] companionProps;

  @JsonProperty(value = "7")
  @JsonPropertyDescription("bytes")
  private byte[] buildHash;

  @JsonProperty(value = "6")
  @JsonPropertyDescription("bytes")
  private byte[] eSkeySig;

  @JsonProperty(value = "5")
  @JsonPropertyDescription("bytes")
  private byte[] eSkeyVal;

  @JsonProperty(value = "4")
  @JsonPropertyDescription("bytes")
  private byte[] eSkeyId;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("bytes")
  private byte[] eIdent;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("bytes")
  private byte[] eKeytype;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("bytes")
  private byte[] eRegid;
}
