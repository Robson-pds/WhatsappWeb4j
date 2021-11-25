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
public class WebInfo {
  @JsonProperty(value = "4")
  @JsonPropertyDescription("WebInfoWebSubPlatform")
  private WebInfoWebSubPlatform webSubPlatform;

  @JsonProperty(value = "3")
  @JsonPropertyDescription("WebdPayload")
  private WebdPayload webdPayload;

  @JsonProperty(value = "2")
  @JsonPropertyDescription("string")
  private String version;

  @JsonProperty(value = "1")
  @JsonPropertyDescription("string")
  private String refToken;

  @Accessors(fluent = true)
  public enum WebInfoWebSubPlatform {
    WEB_BROWSER(0),
    APP_STORE(1),
    WIN_STORE(2),
    DARWIN(3),
    WIN32(4);

    private final @Getter int index;

    WebInfoWebSubPlatform(int index) {
      this.index = index;
    }

    @JsonCreator
    public static WebInfoWebSubPlatform forIndex(int index) {
      return Arrays.stream(values())
          .filter(entry -> entry.index() == index)
          .findFirst()
          .orElse(null);
    }
  }
}
