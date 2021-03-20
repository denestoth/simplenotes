package com.dnstth.simplenotes.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
  @Column
  private UUID id;

  @Column
  private String text;

  @Column
  private LocalDateTime createdTime;

  @Column
  private LocalDateTime closedTime;

  @Column
  private boolean closed;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "task_id")
  private List<TaskHistoryEntry> taskHistoryEntries;

//  @OneToMany
//  private List<Note> notes;
}
