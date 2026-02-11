package org.example.project3.controller;

import org.example.project3.entity.LessonEntity;
import org.example.project3.service.LessonService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/topic/{topicId}")
    public LessonEntity getByTopic(@PathVariable Long topicId) {
        return lessonService.getByTopicId(topicId);
    }

    @PostMapping
    public LessonEntity create(@RequestBody LessonEntity lesson) {
        return lessonService.create(lesson);
    }
}

