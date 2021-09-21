package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.Status;
import com.dnstth.simplenotes.model.exception.NoteNotFoundException;
import com.dnstth.simplenotes.model.exception.UpdatingOldVersionException;
import com.dnstth.simplenotes.repository.NoteRepository;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import com.dnstth.simplenotes.view.note.UpdateNoteView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    @Autowired
    private NoteRepository repository;

    public Note getOne(UUID noteId) {
        return repository.findById(noteId).orElseThrow(() -> new NoteNotFoundException(noteId));
    }

    public List<Note> getAll(boolean newestOnly) {
        return newestOnly
            ? repository.findAll().stream().filter(Note::getNewestVersion).collect(Collectors.toList())
            : repository.findAll();
    }

    public List<Note> getAllNotesByTag(String tag, boolean newestOnly) {
        return getAll(newestOnly)
                    .stream()
                    .filter(note -> note.getTags().stream().map(t -> t.getText()).collect(Collectors.toList()).contains(tag))
                    .collect(Collectors.toList());
    }

    public Note create(CreateNoteView view) {
        Note note = Note.builder()
                        .title(view.getTitle())
                        .text(view.getText())
                        .status(Status.CREATED)
                        .createdAt(LocalDateTime.now())
                        .modifiedAt(LocalDateTime.now())
                        .newestVersion(true)
                        .build();

        return repository.save(note);
    }

    public Note update(UUID noteId, UpdateNoteView view) {
        Note oldNote = updateOldNote(noteId);

        Note newNote = Note.builder()
                           .text(view.getText())
                           .title(view.getTitle())
                           .status(Status.UPDATED)
                           .previousVersion(oldNote)
                           .createdAt(oldNote.getCreatedAt())
                           .modifiedAt(LocalDateTime.now())
                           .newestVersion(true)
                           .build();

        oldNote.getTasks().forEach(task -> {
            task.getNotes().remove(oldNote);
            task.getNotes().add(newNote);
        });

        repository.save(newNote);

        return newNote;
    }

    public void delete(UUID noteId) {
        throw new RuntimeException("not implemented yet");
    }

    private Note updateOldNote(UUID id) {
        Note oldNote = repository.findById(id).orElseThrow(() -> new NoteNotFoundException(id));

        if (!oldNote.getNewestVersion()) {
            throw new UpdatingOldVersionException(id);
        }

        oldNote.setNewestVersion(false);
        return repository.save(oldNote);
    }
}
