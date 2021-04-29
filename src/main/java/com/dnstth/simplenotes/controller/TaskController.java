package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.service.TaskService;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.TaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{id}")
    public TaskView get(@PathVariable UUID id) {
        return taskService.get(id);
    }

    @GetMapping
    public List<TaskView> getAll() {
        return taskService.getAll(false);
    }

    @PostMapping
    public TaskView create(@RequestBody CreateTaskView view) {
        return taskService.create(view);
    }

    @PutMapping("/{id}")
    public TaskView update(@PathVariable UUID id, @RequestBody UpdateTaskView view) {
        return taskService.update(id, view);
    }

    @PatchMapping("/{id}/close")
    public TaskView close(@PathVariable UUID id) {
        return taskService.close(id);
    }

    @PatchMapping("/{id}/open")
    public TaskView open(@PathVariable UUID id) {
        return taskService.reOpen(id);
    }
}
