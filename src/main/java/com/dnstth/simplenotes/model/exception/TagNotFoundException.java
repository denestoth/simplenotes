package com.dnstth.simplenotes.model.exception;

import java.util.UUID;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(UUID id) {
        super("could not find tag: " + id.toString());
    }

}
