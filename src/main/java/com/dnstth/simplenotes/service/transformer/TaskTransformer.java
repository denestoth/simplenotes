package com.dnstth.simplenotes.service.transformer;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Tag;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.view.task.TaskView;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TaskTransformer {

    public TaskView transformTaskToTaskView(Task task) {
        return TaskView.builder()
                       .id(task.getId())
                       .title(task.getTitle())
                       .text(task.getText())
                       .status(task.getStatus())
                       .previousVersion(task.getPreviousVersion() != null ? task.getPreviousVersion().getId() : null)
                       .notes(task.getNotes() != null ? task.getNotes().stream().map(Note::getTitle).collect(Collectors.toList()) : null)
                       .tags(task.getTags() != null ? task.getTags().stream().map(Tag::getText).collect(Collectors.toSet()) : null)
                       .createdAt(task.getCreatedAt())
                       .modifiedAt(task.getModifiedAt())
                       .build();
    }
}
