package com.dnstth.simplenotes.view.task;

import com.dnstth.simplenotes.model.Status;

import java.time.LocalDateTime;
import java.util.List;
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
public class TaskView {
    private UUID id;
    private String title;
    private String text;
    private Status status;
    private UUID previousVersion;
    private List<String> notes;
    private Set<String> tags;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
