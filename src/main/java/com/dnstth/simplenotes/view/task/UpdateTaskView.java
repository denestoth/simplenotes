package com.dnstth.simplenotes.view.task;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskView {
    private String title;
    private String text;
}
