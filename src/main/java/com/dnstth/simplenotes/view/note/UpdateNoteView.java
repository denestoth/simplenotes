package com.dnstth.simplenotes.view.note;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateNoteView {
    private String title;
    private String text;
}
