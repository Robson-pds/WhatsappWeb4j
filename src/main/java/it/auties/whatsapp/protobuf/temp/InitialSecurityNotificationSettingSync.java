package it.auties.whatsapp.protobuf.temp;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Accessors(fluent = true)
public class InitialSecurityNotificationSettingSync {
  @JsonProperty(value = "1")
  @JsonPropertyDescription("bool")
  private boolean securityNotificationEnabled;
}
