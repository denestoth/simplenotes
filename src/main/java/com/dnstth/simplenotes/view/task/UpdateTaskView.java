package com.dnstth.simplenotes.view.task;

import com.dnstth.simplenotes.model.Note;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateTaskView {
  private UUID id;
  private String text;
  private boolean closed;
  private List<Note> notes;
}
