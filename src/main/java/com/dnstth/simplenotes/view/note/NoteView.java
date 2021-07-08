package com.dnstth.simplenotes.view.note;

import com.dnstth.simplenotes.model.Status;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteView {
    private UUID id;
    private String title;
    private String text;
    private Status status;
    private UUID previousVersion;
    private Set<UUID> tasks;
    private Set<UUID> tags;
}
