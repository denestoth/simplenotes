package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
