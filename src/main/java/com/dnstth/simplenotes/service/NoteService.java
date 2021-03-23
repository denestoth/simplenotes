package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.repository.NoteRepository;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import com.dnstth.simplenotes.view.note.UpdateNoteView;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

  @Autowired
  private NoteRepository repository;

  public Note create(CreateNoteView view) {
    Note note = Note.builder()
        .text(view.getText())
        .createdTime(LocalDateTime.now())
        .build();
    return repository.save(note);
  }

  public Note update(UpdateNoteView view) {
    Optional<Note> noteOptional = repository.findById(view.getId());

    if (noteOptional.isPresent()) {
      Note note = noteOptional.get();
      note.setText(view.getText());
      return repository.save(note);
    }

    return null;
  }
}
