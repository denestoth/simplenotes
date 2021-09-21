package com.dnstth.simplenotes.controller;

import com.dnstth.simplenotes.service.TagService;
import com.dnstth.simplenotes.service.transformer.TagTransformer;
import com.dnstth.simplenotes.view.tag.TagView;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:3000")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private TagTransformer tagTransformer;

    @GetMapping("/")
    public List<TagView> getAll() {
        return tagService.findAll().stream().map(tagTransformer::transformTagToTagView).collect(Collectors.toList());
    }
}
