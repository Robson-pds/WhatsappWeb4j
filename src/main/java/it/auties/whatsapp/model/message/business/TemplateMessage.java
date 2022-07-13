package it.auties.whatsapp.model.message.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.auties.protobuf.api.model.ProtobufProperty;
import it.auties.whatsapp.model.button.FourRowTemplate;
import it.auties.whatsapp.model.button.HydratedFourRowTemplate;
import it.auties.whatsapp.model.info.ContextInfo;
import it.auties.whatsapp.model.message.model.BusinessMessage;
import it.auties.whatsapp.model.message.model.ContextualMessage;
import lombok.*;
import lombok.Builder.Default;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;

import static it.auties.protobuf.api.model.ProtobufProperty.Type.MESSAGE;
import static java.util.Objects.requireNonNullElseGet;

/**
 * A model class that represents a message sent in a WhatsappBusiness chat that provides a list of buttons to choose from.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(builderMethodName = "newRawTemplateMessageBuilder")
@Accessors(fluent = true)
public final class TemplateMessage extends ContextualMessage implements BusinessMessage {
    /**
     * Four row template.
     * This property is defined only if {@link TemplateMessage#formatType()} == {@link Format#FOUR_ROW_TEMPLATE}.
     */
    @ProtobufProperty(index = 1, type = MESSAGE, concreteType = FourRowTemplate.class)
    private FourRowTemplate fourRowTemplate;

    /**
     * Hydrated four row template.
     * This property is defined only if {@link TemplateMessage#formatType()} == {@link Format#HYDRATED_FOUR_ROW_TEMPLATE}.
     */
    @ProtobufProperty(index = 2, type = MESSAGE, concreteType = HydratedFourRowTemplate.class)
    private HydratedFourRowTemplate hydratedFourRowTemplate;

    /**
     * The context info of this message
     */
    @ProtobufProperty(index = 3, type = MESSAGE, concreteType = ContextInfo.class)
    @Default
    private ContextInfo contextInfo = new ContextInfo();  // Overrides ContextualMessage's context info

    /**
     * Hydrated template
     */
    @ProtobufProperty(index = 4, type = MESSAGE, concreteType = HydratedFourRowTemplate.class)
    private HydratedFourRowTemplate hydratedTemplate;


    /**
     * Constructs a new template message
     *
     * @param template the non-null template
     * @return a non-null template message
     */
    public static TemplateMessage newFourRowTemplateMessage(@NonNull FourRowTemplate template) {
        return newFourRowTemplateMessage(template, null);
    }

    /**
     * Constructs a new template message
     *
     * @param template    the non-null template
     * @param contextInfo the nullable context info
     * @return a non-null template message
     */
    public static TemplateMessage newFourRowTemplateMessage(@NonNull FourRowTemplate template,
                                                            ContextInfo contextInfo) {
        return TemplateMessage.newRawTemplateMessageBuilder()
                .fourRowTemplate(template)
                .contextInfo(requireNonNullElseGet(contextInfo, ContextInfo::new))
                .build();
    }

    /**
     * Constructs a new template message
     *
     * @param template the non-null template
     * @return a non-null template message
     */
    public static TemplateMessage newHydratedFourRowTemplateMessage(@NonNull HydratedFourRowTemplate template) {
        return newHydratedFourRowTemplateMessage(template, null);
    }

    /**
     * Constructs a new template message
     *
     * @param template    the non-null template
     * @param contextInfo the nullable context info
     * @return a non-null template message
     */
    public static TemplateMessage newHydratedFourRowTemplateMessage(@NonNull HydratedFourRowTemplate template,
                                                                    ContextInfo contextInfo) {
        return TemplateMessage.newRawTemplateMessageBuilder()
                .hydratedFourRowTemplate(template)
                .contextInfo(requireNonNullElseGet(contextInfo, ContextInfo::new))
                .build();
    }

    /**
     * Constructs a new template message
     *
     * @param template the non-null template
     * @return a non-null template message
     */
    public static TemplateMessage newHydratedTemplateMessage(@NonNull HydratedFourRowTemplate template) {
        return newHydratedFourRowTemplateMessage(template, null);
    }

    /**
     * Constructs a new template message
     *
     * @param template    the non-null template
     * @param contextInfo the nullable context info
     * @return a non-null template message
     */
    public static TemplateMessage newHydratedTemplateMessage(@NonNull HydratedFourRowTemplate template,
                                                             ContextInfo contextInfo) {
        return TemplateMessage.newRawTemplateMessageBuilder()
                .hydratedTemplate(template)
                .contextInfo(requireNonNullElseGet(contextInfo, ContextInfo::new))
                .build();
    }

    /**
     * Returns the type of format of this message
     *
     * @return a non-null {@link Format}
     */
    public Format formatType() {
        if (fourRowTemplate != null)
            return Format.FOUR_ROW_TEMPLATE;
        if (hydratedFourRowTemplate != null)
            return Format.HYDRATED_FOUR_ROW_TEMPLATE;
        return Format.NONE;
    }

    /**
     * The constant of this enumerated type define the various of types of visual formats for a {@link TemplateMessage}
     */
    @AllArgsConstructor
    @Accessors(fluent = true)
    public enum Format {
        /**
         * No format
         */
        NONE(0),

        /**
         * Four row template
         */
        FOUR_ROW_TEMPLATE(1),

        /**
         * Hydrated four row template
         */
        HYDRATED_FOUR_ROW_TEMPLATE(2);

        @Getter
        private final int index;

        @JsonCreator
        public static Format forIndex(int index) {
            return Arrays.stream(values())
                    .filter(entry -> entry.index() == index)
                    .findFirst()
                    .orElse(Format.NONE);
        }
    }
}
