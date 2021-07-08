package com.dnstth.simplenotes.model.exception;

import java.util.UUID;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(UUID id) {
        super("could not find task: " + id.toString());
    }

}
