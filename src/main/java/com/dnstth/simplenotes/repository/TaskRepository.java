package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
