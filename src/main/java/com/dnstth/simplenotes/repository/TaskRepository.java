package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Task;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
