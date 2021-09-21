package com.dnstth.simplenotes.view.task;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskView {

    private String title;
    private String text;
    private List<String> tags;
}
