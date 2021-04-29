package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.view.task.TaskView;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskTransformer {

    public TaskView transform(Task task) {
        return TaskView.builder()
                .id(task.getId())
                .title(task.getTitle())
                .text(task.getText())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .previousVersion(task.getPreviousVersion() != null ? task.getPreviousVersion().getId() : null)
                .notes(task.getNotes() != null ? task.getNotes().stream().map(Note::getId).collect(Collectors.toList()) : null)
                .build();
    }
}
