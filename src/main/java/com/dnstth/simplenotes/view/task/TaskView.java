package com.dnstth.simplenotes.view.task;

import com.dnstth.simplenotes.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskView {

    private UUID id;

    private String title;

    private String text;

    private Status status;

    private LocalDateTime createdAt;

    private UUID previousVersion;

    private List<UUID> notes;
}
