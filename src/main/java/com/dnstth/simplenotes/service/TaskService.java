package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.model.TaskEvent;
import com.dnstth.simplenotes.model.TaskHistoryEntry;
import com.dnstth.simplenotes.repository.TaskRepository;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  public Task create(CreateTaskView view) {
    Task task = Task.builder()
        .text(view.getText())
        .createdTime(LocalDateTime.now())
        .closedTime(null)
        .closed(false)
        .taskHistoryEntries(new ArrayList<>())
        .build();

    addTaskHistoryEntry(task, TaskEvent.CREATED);

    return taskRepository.save(task);
  }

  public List<Task> getAll() {
    return taskRepository.findAll();
  }

  public Task get(UUID id) {
    return taskRepository.findById(id).orElse(null);
  }

  public Task close(UUID id) {
    Optional<Task> taskOptional = taskRepository.findById(id);

    if (taskOptional.isPresent()) {
      Task task = taskOptional.get();
      task.setClosed(true);
      task.setClosedTime(LocalDateTime.now());
      addTaskHistoryEntry(task, TaskEvent.CLOSED);
      return taskRepository.save(task);
    }

    return null;
  }

  public Task reOpen(UUID id) {
    Optional<Task> taskOptional = taskRepository.findById(id);

    if (taskOptional.isPresent()) {
      Task task = taskOptional.get();
      task.setClosed(false);
      addTaskHistoryEntry(task, TaskEvent.REOPENED);
      return taskRepository.save(task);
    }

    return null;
  }

  public Task updateTask(UpdateTaskView view) {
    Optional<Task> taskOptional = taskRepository.findById(view.getId());

    if (taskOptional.isPresent()) {
      Task task = taskOptional.get();
      task.setText(view.getText());
      updateNotes(view.getNotes());
      task.setClosed(view.isClosed());
      return taskRepository.save(task);
    }

    return null;
  }

  private void updateNotes(List<Note> notes) {
  }

  private void addTaskHistoryEntry(Task task, TaskEvent taskEvent) {
    task.getTaskHistoryEntries().add(TaskHistoryEntry.builder().taskEvent(taskEvent).dateTime(LocalDateTime.now()).build());
  }

}
