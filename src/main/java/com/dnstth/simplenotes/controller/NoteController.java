package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.model.exception.NotImplementedException;
import com.dnstth.simplenotes.service.NoteService;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import com.dnstth.simplenotes.view.note.UpdateNoteView;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @GetMapping("/{noteId}")
    public Note getOne(@PathVariable UUID noteId) {
        return service.getOne(noteId);
    }

    @GetMapping()
    public List<Note> getAll() {
        return service.getAll(false);
    }

    @PostMapping
    public Note create(@RequestBody CreateNoteView view) {
        return service.create(view);
    }

    @PutMapping("/{noteId}")
    public Note edit(@PathVariable UUID noteId, @RequestBody UpdateNoteView view) {
        return service.update(noteId, view);
    }

    @DeleteMapping("/{noteId}")
    public void delete(@PathVariable UUID noteId) {
        service.delete(noteId);
    }

    @PostMapping("/{noteId}/tag")
    public Note addTag(@PathVariable UUID noteId, @RequestBody String tag) {
        throw new NotImplementedException();
    }

    @DeleteMapping("/{noteId}/tag")
    public Note removeTag(@PathVariable UUID noteId, @RequestBody String tag) {
        throw new NotImplementedException();
    }
}
