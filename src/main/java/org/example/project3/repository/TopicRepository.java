package org.example.project3.repository;

import org.example.project3.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

    List<TopicEntity> findByTitleContainingIgnoreCase(String title);
}
