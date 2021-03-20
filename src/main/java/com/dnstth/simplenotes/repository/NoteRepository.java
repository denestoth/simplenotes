package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Note;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, UUID> {
}
