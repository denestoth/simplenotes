package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Tag;
import com.dnstth.simplenotes.repository.TagRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag findByTextOrCreateTag(String text) {
        return tagRepository.findByTextIgnoreCase(text).orElseGet(() -> tagRepository.save(Tag.builder().text(text).build()));
    }
}
