package com.wissensalt.domaineventdemo.event;

import java.time.LocalDateTime;

public record LessonEndedEvent(Long lessonId, LocalDateTime endTime) {

}