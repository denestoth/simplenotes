package com.dnstth.simplenotes.model.exception;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(UUID id) {
        super("could not find note: " + id.toString());
    }

}
