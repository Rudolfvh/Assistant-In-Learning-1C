package org.example.project3.repository;

import org.example.project3.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {
    Optional<LessonEntity> findByTopicId(Long topicId);
}

