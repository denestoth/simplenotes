package com.dnstth.simplenotes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TaskServiceTest {

    private static final String taskText = "task";

    @Autowired
    private TaskService underTest;
}
