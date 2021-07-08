package com.dnstth.simplenotes.service.transformer;

import com.dnstth.simplenotes.model.Tag;
import com.dnstth.simplenotes.view.tag.TagView;

import org.springframework.stereotype.Component;

@Component
public class TagTransformer {

    public TagView transformTagToTagView(Tag tag) {
        return TagView.builder()
                      .id(tag.getId())
                      .text(tag.getText())
                      .build();
    }
}
