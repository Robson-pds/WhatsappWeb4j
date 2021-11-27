package it.auties.whatsapp.protobuf.model;

import it.auties.whatsapp.utils.Nodes;
import lombok.NonNull;

import java.util.*;

/**
 * An immutable model class that represents the primary unit used by WhatsappWeb's WebSocket to communicate with the client.
 *
 * @param description a non-null String that describes the data that this object holds in its {@code attributes} and {@code content}
 * @param attributes       a non-null Map of strings that describe additional information related to the content of this object or an encoded object when sending a message a protobuf object is not optimal
 * @param content     a nullable object, usually a List of {@link Node}, a {@link String} or a {@link Number}
 */
public record Node(@NonNull String description,
                   @NonNull Map<String, Object> attributes, Object content) {

    /**
     * Constructs a Node that only provides a non-null tag
     *
     * @param description a non-null String that describes the data that this object holds
     */
    public Node(@NonNull String description){
        this(description, Map.of(), null);
    }

    /**
     * Constructs a Node that only provides a non-null tag
     *
     * @param description a non-null String that describes the data that this object holds
     * @param content     a nullable object, usually a List of {@link Node}, a {@link String} or a {@link Number}
     */
    public Node(@NonNull String description, Object content) {
        this(description, Map.of(), content);
    }

    /**
     * Returns a list of child WhatsappNodes
     *
     * @return a non-null list containing WhatsappNodes extracted from this body's content
     */
    public @NonNull LinkedList<Node> childNodes() {
        if (!hasContent()) {
            return new LinkedList<>();
        }

        if (!(content instanceof List<?> listContent)) {
            return new LinkedList<>();
        }

        return Nodes.filter(listContent);
    }

    /**
     * Returns a body that matches the nullable description provided
     *
     * @return an optional body, present if a result was found
     */
    public @NonNull Optional<Node> findNodeByDescription(String description) {
        return childNodes().stream()
                .filter(node -> Objects.equals(node.description(), description))
                .findFirst();
    }

    /**
     * Returns a body that matches the nullable description provided
     *
     * @return an optional body, present if a result was found
     */
    public @NonNull List<Node> findNodesByDescription(String description) {
        return childNodes().stream()
                .filter(node -> Objects.equals(node.description(), description))
                .toList();
    }

    /**
     * Returns whether this object's content is non-null
     *
     * @return true if this object has a content
     */
    public boolean hasContent(){
        return Objects.nonNull(content);
    }

    /**
     * Returns whether this object's content is non-null
     *
     * @return true if this object has a content
     */
    public int size(){
        var descriptionSize = 1;
        var attributesSize = 2 * attributes.size();
        var contentSize = hasContent() ? 1 : 0;
        return descriptionSize + attributesSize + contentSize;
    }
}
