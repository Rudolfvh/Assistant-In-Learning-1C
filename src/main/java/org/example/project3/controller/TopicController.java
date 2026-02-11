package org.example.project3.controller;

import lombok.RequiredArgsConstructor;
import org.example.project3.dto.TopicDto;
import org.example.project3.dto.TopicPageDto;
import org.example.project3.service.TopicPageService;
import org.example.project3.service.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;      // существующий
    private final TopicPageService topicPageService; // новый

    @GetMapping
    public List<TopicDto> getTopics(
            @RequestParam(required = false) String search
    ) {
        return topicService.getTopics(search);
    }

    @GetMapping("/{id}")
    public TopicPageDto getTopicPage(@PathVariable Long id) {
        return topicPageService.getTopicPage(id);
    }

    @PostMapping
    public TopicDto createTopic(@RequestBody TopicPageDto dto) {
        return topicService.createTopic(dto);
    }
}



