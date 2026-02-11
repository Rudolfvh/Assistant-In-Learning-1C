package org.example.project3.service;

import lombok.RequiredArgsConstructor;
import org.example.project3.dto.TopicPageDto;
import org.example.project3.entity.LessonEntity;
import org.example.project3.entity.TopicEntity;
import org.example.project3.repository.LessonRepository;
import org.example.project3.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TopicPageService {

    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;

    public TopicPageDto getTopicPage(Long topicId) {

        TopicEntity topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new RuntimeException("Тема не найдена"));

        LessonEntity lesson = lessonRepository.findByTopicId(topicId)
                .orElseThrow(() -> new RuntimeException("Урок не найден"));

        return TopicPageDto.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .description(topic.getDescription())
                .theory(lesson.getTheory())
                .codeExample(lesson.getCodeExample())
                .build();
    }
}
