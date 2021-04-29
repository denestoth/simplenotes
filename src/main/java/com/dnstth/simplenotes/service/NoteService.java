package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.repository.NoteRepository;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public Note create(CreateNoteView view) {
        Note note = Note.builder()
                .text(view.getText())
                .createdAt(LocalDateTime.now())
                .build();

        return repository.save(note);
    }
}
