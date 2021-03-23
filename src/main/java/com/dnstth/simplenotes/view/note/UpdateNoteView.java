package com.dnstth.simplenotes.view.note;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateNoteView {

  private UUID id;
  private String text;

}
