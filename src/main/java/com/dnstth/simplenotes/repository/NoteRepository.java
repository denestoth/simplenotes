package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Note;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
}
