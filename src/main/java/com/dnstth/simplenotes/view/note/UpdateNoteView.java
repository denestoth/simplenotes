package com.dnstth.simplenotes.view.note;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateNoteView {

    private UUID id;
    private String title;
    private String text;
}
