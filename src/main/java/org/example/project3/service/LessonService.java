package org.example.project3.service;

import org.example.project3.entity.LessonEntity;
import org.example.project3.entity.TopicEntity;
import org.example.project3.repository.LessonRepository;
import org.example.project3.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TopicRepository topicRepository;

    public LessonService(LessonRepository lessonRepository,
                         TopicRepository topicRepository) {
        this.lessonRepository = lessonRepository;
        this.topicRepository = topicRepository;
    }

    public LessonEntity getByTopicId(Long topicId) {
        return lessonRepository.findByTopicId(topicId)
                .orElseThrow(() -> new RuntimeException("Урок не найден"));
    }

    public LessonEntity create(LessonEntity lesson) {
        Long topicId = lesson.getTopic().getId();

        TopicEntity topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Тема не найдена"));

        lesson.setTopic(topic);
        return lessonRepository.save(lesson);
    }
}

