package org.example.project3.service;

import lombok.RequiredArgsConstructor;
import org.example.project3.dto.TopicDto;
import org.example.project3.dto.TopicPageDto;
import org.example.project3.entity.LessonEntity;
import org.example.project3.entity.TopicEntity;
import org.example.project3.repository.LessonRepository;
import org.example.project3.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final LessonRepository lessonRepository;

    public List<TopicDto> getTopics(String search) {
        List<TopicEntity> topics;

        if (search == null || search.isBlank()) {
            topics = topicRepository.findAll();
        } else {
            topics = topicRepository.findByTitleContainingIgnoreCase(search);
        }

        return topics.stream()
                .map(t -> TopicDto.builder()
                        .id(t.getId())
                        .title(t.getTitle())
                        .build())
                .toList();
    }

    public TopicDto createTopic(TopicPageDto dto) {
        TopicEntity topic = TopicEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();

        topic = topicRepository.save(topic);

        LessonEntity lesson = new LessonEntity();
        lesson.setTopic(topic);
        lesson.setTheory(dto.getTheory());
        lesson.setCodeExample(dto.getCodeExample());

        lessonRepository.save(lesson);

        return TopicDto.builder()
                .id(topic.getId())
                .title(topic.getTitle())
                .build();
    }
}

