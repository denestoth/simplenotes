package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
}
