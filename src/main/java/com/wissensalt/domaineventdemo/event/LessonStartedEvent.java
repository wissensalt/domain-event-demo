package com.wissensalt.domaineventdemo.event;

import java.time.LocalDateTime;

public record LessonStartedEvent(Long lessonId, LocalDateTime startTime) {

}