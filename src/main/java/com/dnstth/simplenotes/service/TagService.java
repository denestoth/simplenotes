package com.dnstth.simplenotes.service;

import com.dnstth.simplenotes.model.Tag;
import com.dnstth.simplenotes.repository.TagRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findByTextOrCreateTag(String text) {
        return tagRepository.findByTextIgnoreCase(text).orElseGet(() -> tagRepository.save(Tag.builder().text(text).build()));
    }
}
