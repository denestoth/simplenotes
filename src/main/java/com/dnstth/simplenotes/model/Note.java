package com.dnstth.simplenotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(exclude = "tasks")
@ToString(exclude = "tasks")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class Note {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    private UUID id;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private LocalDateTime createdAt;

    @OneToOne
    private Note previousVersion;

    @Column
    private Boolean newestVersion;

    @ManyToMany(mappedBy = "notes")
    Set<Task> tasks;
}
