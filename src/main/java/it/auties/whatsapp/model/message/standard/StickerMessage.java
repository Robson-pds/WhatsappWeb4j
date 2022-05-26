package it.auties.whatsapp.model.message.standard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import it.auties.protobuf.api.model.ProtobufProperty;
import it.auties.whatsapp.api.Whatsapp;
import it.auties.whatsapp.model.info.ContextInfo;
import it.auties.whatsapp.model.info.MessageInfo;
import it.auties.whatsapp.model.message.model.MediaMessage;
import it.auties.whatsapp.model.message.model.MediaMessageType;
import it.auties.whatsapp.model.product.ProductCatalog;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import static it.auties.protobuf.api.model.ProtobufProperty.Type.*;

/**
 * A model class that represents a WhatsappMessage sent by a contact and that holds a sticker inside.
 * This class is only a model, this means that changing its values will have no real effect on WhatsappWeb's servers.
 * Instead, methods inside {@link Whatsapp} should be used.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "newRawStickerMessage", buildMethodName = "create")
@Jacksonized
@Accessors(fluent = true)
public final class StickerMessage extends MediaMessage {
  /**
   * The upload url of the encoded sticker that this object wraps
   */
  @ProtobufProperty(index = 1, type = STRING)
  private String url;

  /**
   * The sha256 of the decoded sticker that this object wraps
   */
  @ProtobufProperty(index = 2, type = BYTES)
  private byte[] fileSha256;

  /**
   * The sha256 of the encoded sticker that this object wraps
   */
  @ProtobufProperty(index = 3, type = BYTES)
  private byte[] fileEncSha256;

  /**
   * The media key of the sticker that this object wraps
   */
  @ProtobufProperty(index = 4, type = BYTES)
  private byte[] key; 

  /**
   * The mime type of the sticker that this object wraps.
   * Most of the endTimeStamp this is {@link MediaMessageType#defaultMimeType()}
   */
  @ProtobufProperty(index = 5, type = STRING)
  private String mimetype;

  /**
   * The unsigned height of the decoded sticker that this object wraps
   */
  @ProtobufProperty(index = 6, type = UINT32)
  private Integer height;

  /**
   * The unsigned width of the decoded sticker that this object wraps
   */
  @ProtobufProperty(index = 7, type = UINT32)
  private Integer width;

  /**
   * The direct path to the encoded sticker that this object wraps
   */
  @ProtobufProperty(index = 8, type = STRING)
  private String directPath;

  /**
   * The unsigned size of the decoded sticker that this object wraps
   */
  @ProtobufProperty(index = 9, type = UINT64)
  private Long fileLength;

  /**
   * The timestamp, that is the seconds elapsed since {@link java.time.Instant#EPOCH}, for {@link StickerMessage#key()}
   */
  @ProtobufProperty(index = 10, type = UINT64)
  private Long mediaKeyTimestamp;

  /**
   * The length of the first frame
   */
  @ProtobufProperty(index = 11, type = UINT32)
  private Integer firstFrameLength;

  /**
   * The sidecar for the first frame
   */
  @ProtobufProperty(index = 12, type = BYTES)
  private byte[] firstFrameSidecar;

  /**
   * Determines whether this sticker message is animated
   */
  @ProtobufProperty(index = 13, type = BOOLEAN)
  private boolean animated;

  /**
   * The thumbnail for this sticker message encoded as png in an array of bytes
   */
  @ProtobufProperty(index = 16, type = BYTES)
  private byte[] thumbnail;

  /**
   * Constructs a new builder to create a StickerMessage.
   * The result can be later sent using {@link Whatsapp#sendMessage(MessageInfo)}
   *
   * @param media        the non-null sticker that the new message wraps
   * @param mimeType     the mime type of the new message, by default {@link MediaMessageType#defaultMimeType()}
   * @param pngThumbnail the thumbnail of the sticker that the new message wraps as a png
   * @param isAnimated   whether the sticker that the new message wraps is animated
   * @param contextInfo  the context info that the new message wraps
   *
   * @return a non-null new message
   */
  @Builder(builderClassName = "SimpleStickerMessageBuilder", builderMethodName = "newStickerMessage", buildMethodName = "create")
  private static StickerMessage builder(byte @NonNull [] media, String mimeType, byte[] pngThumbnail, boolean isAnimated, ContextInfo contextInfo) {
    /*
    var upload = CypherUtils.mediaEncrypt(media, MediaMessageType.STICKER);
    return StickerMessage.builder()
            .fileSha256(upload.fileSha256())
            .fileEncSha256(upload.fileEncSha256())
            .mediaKey(upload.mediaKey().toByteArray())
            .mediaKeyTimestamp(ZonedDateTime.now().toEpochSecond())
            .url(upload.url())
            .directPath(upload.directPath())
            .fileLength(media.length)
            .mimetype(Optional.ofNullable(mimeType).orElse(MediaMessageType.STICKER.defaultMimeType()))
            .firstFrameSidecar(upload.sidecar())
            .firstFrameLength(upload.sidecar().length)
            .isAnimated(isAnimated)
            .pngThumbnail(pngThumbnail)
            .contextInfo(contextInfo)
            .create();
     */
    throw new UnsupportedOperationException("Work in progress");
  }
  
  /**
   * Returns the media type of the sticker that this object wraps
   *
   * @return {@link MediaMessageType#STICKER}
   */
  @Override
  public MediaMessageType type() {
    return MediaMessageType.STICKER;
  }
}
