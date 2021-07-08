package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Status;
import com.dnstth.simplenotes.model.Tag;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.model.exception.TaskNotFoundException;
import com.dnstth.simplenotes.model.exception.UpdatingOldVersionException;
import com.dnstth.simplenotes.repository.TaskRepository;
import com.dnstth.simplenotes.service.transformer.TaskTransformer;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.TaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTransformer taskTransformer;

    @Autowired
    private NoteService noteService;

    @Autowired
    private TagService tagService;

    public TaskView getTaskById(UUID id) {
        return taskTransformer.transformTaskToTaskView(taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id)));
    }

    public List<TaskView> getAllTasks(boolean newestOnly) {
        return newestOnly
            ?
            taskRepository.findAll().stream().filter(Task::getNewestVersion).map(task -> taskTransformer.transformTaskToTaskView(task))
                          .collect(Collectors.toList())
            : taskRepository.findAll().stream().map(task -> taskTransformer.transformTaskToTaskView(task)).collect(Collectors.toList());
    }

    public TaskView createNewTask(CreateTaskView view) {
        Task task = Task.builder()
                        .title(view.getTitle())
                        .text(view.getText())
                        .status(Status.CREATED)
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .newestVersion(true)
                        .build();

        return taskTransformer.transformTaskToTaskView(taskRepository.save(task));
    }

    public TaskView updateTask(UUID id, UpdateTaskView view) {
        Task oldTask = updateOldTask(id);

        Task newTask = Task.builder()
                           .title(view.getTitle())
                           .text(view.getText())
                           .status(Status.UPDATED)
                           .createdAt(oldTask.getCreatedAt())
                           .modifiedAt(LocalDateTime.now())
                           .previousVersion(oldTask)
                           .newestVersion(true)
                           .notes(List.copyOf(oldTask.getNotes()))
                           .tags(List.copyOf(oldTask.getTags()))
                           .build();

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    public TaskView closeTask(UUID id) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.CLOSED);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    public TaskView reOpenClosedTask(UUID id) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.REOPENED);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    public void deleteTask(UUID id) {
        Task oldTask = updateOldTask(id);

        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.DELETED);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);
    }

    public TaskView addNewNoteToTask(UUID id, Note note) {
        return addNoteToTask(id, note);
    }

    public TaskView addExistingNoteToTask(UUID id, UUID noteId) {
        Note note = noteService.getOne(noteId);

        return addNoteToTask(id, note);
    }

    public TaskView removeNoteFromTask(UUID id, UUID noteId) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.UPDATED);

        Note note = noteService.getOne(noteId);

        newTask.getNotes().remove(note);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    public TaskView addTagToTask(UUID id, String tagText) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.UPDATED);

        Tag tag = tagService.findByTextOrCreateTag(tagText);
        newTask.getTags().add(tag);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    public TaskView removeTagFromTask(UUID id, String tagText) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.UPDATED);

        Tag tag = tagService.findByTextOrCreateTag(tagText);
        newTask.getTags().remove(tag);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    private Task updateOldTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        if (!task.getNewestVersion()) {
            throw new UpdatingOldVersionException(id);
        }

        task.setNewestVersion(false);
        return task;
    }

    private TaskView addNoteToTask(UUID id, Note note) {
        Task oldTask = updateOldTask(id);
        Task newTask = cloneTaskAndUpdateStatus(oldTask, Status.UPDATED);

        newTask.getNotes().add(note);

        taskRepository.save(oldTask);
        taskRepository.save(newTask);

        return taskTransformer.transformTaskToTaskView(newTask);
    }

    private Task cloneTaskAndUpdateStatus(Task oldTask, Status status) {
        return Task.builder()
                   .title(oldTask.getTitle())
                   .text(oldTask.getText())
                   .status(status)
                   .createdAt(oldTask.getCreatedAt())
                   .modifiedAt(LocalDateTime.now())
                   .previousVersion(oldTask)
                   .newestVersion(true)
                   .notes(new ArrayList<>(oldTask.getNotes()))
                   .tags(new ArrayList<>(oldTask.getTags()))
                   .build();
    }
}
