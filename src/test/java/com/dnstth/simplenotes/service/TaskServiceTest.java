package com.dnstth.simplenotes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dnstth.simplenotes.model.Task;
import com.dnstth.simplenotes.view.task.CreateTaskView;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskServiceTest {

    private static final String taskText = "task";

    @Autowired
    private TaskService underTest;
}
