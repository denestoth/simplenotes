package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.model.Note;
import com.dnstth.simplenotes.service.NoteService;
import com.dnstth.simplenotes.view.note.CreateNoteView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService service;

    @PostMapping
    public Note create(@RequestBody CreateNoteView view) {
        return service.create(view);
    }
}
