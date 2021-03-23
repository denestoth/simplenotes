package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.service.TaskService;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @PostMapping
  public Task create(@RequestBody CreateTaskView view) {
    return taskService.create(view);
  }

  @GetMapping
  public List<Task> getAll() {
    return taskService.getAll();
  }

  @GetMapping("/{id}")
  public Task get(@PathVariable UUID id) {
    return taskService.get(id);
  }

  @PatchMapping("/{id}/close")
  public Task close(@PathVariable UUID id) {
    return taskService.close(id);
  }

  @PatchMapping("/{id}/open")
  public Task open(@PathVariable UUID id) {
    return taskService.reOpen(id);
  }

  @PostMapping("/{id}/note")
  public Task addNote(@PathVariable UUID id, @RequestBody String taskId) {
    return taskService.addNote(id, taskId);
  }
}
