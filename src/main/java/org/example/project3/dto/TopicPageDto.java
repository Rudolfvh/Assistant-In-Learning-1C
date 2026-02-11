package org.example.project3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopicPageDto {

    private Long id;
    private String title;
    private String description;
    private String theory;
    private String codeExample;
}

