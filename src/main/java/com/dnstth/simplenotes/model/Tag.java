package com.dnstth.simplenotes.model;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString(exclude = "notes")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class Tag {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    private UUID id;

    @Column
    private String text;

    @ManyToMany(mappedBy = "tags")
    Set<Note> notes;

    @ManyToMany(mappedBy = "tags")
    Set<Note> tasks;

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        return (o instanceof Tag) && this.text.equalsIgnoreCase(((Tag) o).text);
    }

    @Override
    public int hashCode() {
        return text.hashCode();
    }
}
