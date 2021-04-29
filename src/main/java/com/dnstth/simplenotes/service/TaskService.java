package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Status;
import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.repository.TaskRepository;
import com.dnstth.simplenotes.view.task.CreateTaskView;
import com.dnstth.simplenotes.view.task.TaskView;
import com.dnstth.simplenotes.view.task.UpdateTaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskTransformer taskTransformer;

    public TaskView get(UUID id) {
        return taskTransformer.transform(taskRepository.findById(id).orElseThrow(() -> new RuntimeException("no task like with id of " + id)));
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

        taskRepository.save(oldTask);
        taskRepository.save(newTask);
        cloneAllTasks();

        return taskTransformer.transform(newTask);
    }

    public TaskView close(UUID id) {
        Task oldTask = updateOldTask(id);

        Task newTask = Task.builder()
                .title(oldTask.getTitle())
                .text(oldTask.getText())
                .status(Status.CLOSED)
                .createdAt(LocalDateTime.now())
                .newestVersion(true)
                .previousVersion(oldTask)
                .build();

        taskRepository.save(oldTask);
        taskRepository.save(newTask);
        cloneAllTasks();

        return taskTransformer.transform(newTask);
    }

    public TaskView reOpen(UUID id) {
        Task oldTask = updateOldTask(id);

        Task newTask = Task.builder()
                .title(oldTask.getTitle())
                .text(oldTask.getText())
                .status(Status.REOPENED)
                .createdAt(LocalDateTime.now())
                .newestVersion(true)
                .previousVersion(oldTask)
                .build();

        taskRepository.save(oldTask);
        taskRepository.save(newTask);
        cloneAllTasks();

        return taskTransformer.transform(newTask);
    }

    private Task updateOldTask(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("no task like with id of " + id));
        if (!task.getNewestVersion()) {
            throw new RuntimeException("this is not an up-to-date task");
        }
        task.setNewestVersion(false);
        return task;
    }


    public Task addNote(Task task, Note note) {
        task.getNotes().add(note);
        return task;
    }

    private void cloneAllTasks() {
    }
}
