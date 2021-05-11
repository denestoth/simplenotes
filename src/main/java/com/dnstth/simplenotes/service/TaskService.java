package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Status;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.model.exception.TaskNotFoundException;
import com.dnstth.simplenotes.model.exception.UpdatingOldVersionException;
import com.dnstth.simplenotes.repository.NoteRepository;
import com.dnstth.simplenotes.repository.TaskRepository;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.TaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTransformer taskTransformer;

    @Autowired
    private NoteRepository noteRepository;

    public TaskView get(UUID id) {
        return taskTransformer.transform(taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    public List<TaskView> getAll(boolean newestOnly) {
        return newestOnly
                ? taskRepository.findAll().stream().filter(Task::getNewestVersion).map(task -> taskTransformer.transform(task)).collect(Collectors.toList())
                : taskRepository.findAll().stream().map(task -> taskTransformer.transform(task)).collect(Collectors.toList());
    }

    public TaskView create(CreateTaskView view) {
        Task task = Task.builder()
                .title(view.getTitle())
                .text(view.getText())
                .status(Status.CREATED)
                .createdAt(LocalDateTime.now())
                .newestVersion(true)
                .build();

        return taskTransformer.transform(taskRepository.save(task));
    }

    public TaskView update(UUID id, UpdateTaskView view) {
        Task oldTask = updateOldTask(id);

        Task newTask = Task.builder()
                .title(view.getTitle())
                .text(view.getText())
                .status(Status.UPDATED)
                .createdAt(LocalDateTime.now())
                .newestVersion(true)
                .previousVersion(oldTask)
                .build();

        cloneAllNotes(oldTask, newTask);
        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transform(newTask);
    }

    public TaskView close(UUID id) {
        Task oldTask = updateOldTask(id);

        Task newTask = cloneTask(oldTask, Status.CLOSED);

        cloneAllNotes(oldTask, newTask);
        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transform(newTask);
    }

    public TaskView reOpen(UUID id) {
        Task oldTask = updateOldTask(id);

        Task newTask = cloneTask(oldTask, Status.REOPENED);

        cloneAllNotes(oldTask, newTask);
        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transform(newTask);
    }

    public TaskView addNote(UUID id, Note note) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTask(oldTask, Status.UPDATED);

        cloneAllNotes(oldTask, newTask);

        if (newTask.getNotes() == null) {
            newTask.setNotes(Collections.singletonList(note));
        } else {
            newTask.getNotes().add(note);
        }

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transform(newTask);
    }

    private Task updateOldTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getNewestVersion()) {
            throw new UpdatingOldVersionException(id);
        }

        task.setNewestVersion(false);
        return task;
    }

    private Task cloneTask(Task oldTask, Status status) {
        return Task.builder()
                .title(oldTask.getTitle())
                .text(oldTask.getText())
                .status(status)
                .createdAt(LocalDateTime.now())
                .newestVersion(true)
                .previousVersion(oldTask)
                .build();
    }

    private void cloneAllNotes(Task oldTask, Task newTask) {
        oldTask.getNotes().forEach(oldNote -> oldNote.setNewestVersion(false));
        List<Note> notes = oldTask.getNotes().stream().map(oldNote -> cloneNote(oldNote, newTask)).collect(Collectors.toList());
        noteRepository.saveAll(notes);
        newTask.setNotes(notes);
    }

    private Note cloneNote(Note oldNote, Task newTask) {
        return Note.builder()
                .title(oldNote.getTitle())
                .text(oldNote.getText())
                .createdAt(LocalDateTime.now())
                .previousVersion(oldNote)
                .newestVersion(true)
                .tasks(Collections.singleton(newTask))
                .build();
    }
}
