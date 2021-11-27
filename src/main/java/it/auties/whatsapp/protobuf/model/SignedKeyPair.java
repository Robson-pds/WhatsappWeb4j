package it.auties.whatsapp.protobuf.model;

import lombok.NonNull;

public record SignedKeyPair(byte @NonNull [] id, @NonNull IdentityKeyPair keyPair, byte @NonNull [] signature) {
    public Node encode(){
        return Node.of("skey", Node.of("id", id), Node.of("value", keyPair.publicKey()), Node.of("signature", signature));
    }
}
