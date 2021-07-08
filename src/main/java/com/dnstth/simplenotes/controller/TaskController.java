package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.service.NoteService;
import com.dnstth.simplenotes.service.TaskService;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.TaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private NoteService noteService;

    @GetMapping
    public List<TaskView> getAll() {
        return taskService.getAllTasks(false);
    }

    @GetMapping("/{taskId}")
    public TaskView get(@PathVariable UUID taskId) {
        return taskService.getTaskById(taskId);
    }

    @PostMapping
    public TaskView create(@RequestBody CreateTaskView view) {
        return taskService.createNewTask(view);
    }

    @PutMapping("/{taskId}")
    public TaskView update(@PathVariable UUID taskId, @RequestBody UpdateTaskView view) {
        return taskService.updateTask(taskId, view);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
    }

    @PatchMapping("/{taskId}/close")
    public TaskView close(@PathVariable UUID taskId) {
        return taskService.closeTask(taskId);
    }

    @PatchMapping("/{taskId}/open")
    public TaskView reOpen(@PathVariable UUID taskId) {
        return taskService.reOpenClosedTask(taskId);
    }

    @PostMapping("/{taskId}/notes")
    public TaskView addNewNote(@PathVariable UUID taskId, @RequestBody CreateNoteView view) {
        Note note = noteService.create(view);
        return taskService.addNewNoteToTask(taskId, note);
    }

    @PostMapping("/{taskId}/notes/{noteId}")
    public TaskView addExistingNote(@PathVariable UUID taskId, @PathVariable UUID noteId) {
        return taskService.addExistingNoteToTask(taskId, noteId);
    }

    @DeleteMapping("/{taskId}/notes/{noteId}")
    public TaskView removeNote(@PathVariable UUID taskId, @PathVariable UUID noteId) {
        return taskService.removeNoteFromTask(taskId, noteId);
    }

    @PostMapping("/{taskId}/tag")
    public TaskView addTag(@PathVariable UUID taskId, @RequestBody String tag) {
        return taskService.addTagToTask(taskId, tag);
    }

    @DeleteMapping("/{taskId}/tag")
    public TaskView removeTag(@PathVariable UUID taskId, @RequestBody String tag) {
        return taskService.removeTagFromTask(taskId, tag);
    }
}
