package com.dnstth.simplenotes.model.exception;

import java.util.UUID;

public class UpdatingOldVersionException extends RuntimeException {

    public UpdatingOldVersionException(UUID id) {
        super("this is not the newest version: " + id.toString());
    }

}
