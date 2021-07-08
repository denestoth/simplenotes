package com.dnstth.simplenotes.repository;

import com.dnstth.simplenotes.model.Tag;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByTextIgnoreCase(String text);
}
