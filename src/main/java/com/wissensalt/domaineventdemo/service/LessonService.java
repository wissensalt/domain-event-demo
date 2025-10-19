package com.wissensalt.domaineventdemo.service;

import com.wissensalt.domaineventdemo.DomainEventDemoApplication.LessonRequest;
import com.wissensalt.domaineventdemo.domain.Lesson;
import com.wissensalt.domaineventdemo.domain.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LessonService {

  private final LessonRepository repository;

  public LessonService(LessonRepository repository) {
    this.repository = repository;
  }

  public Lesson createLesson(LessonRequest request) {
    final Lesson lesson = new Lesson(
        request.getTitle(),
        request.getContent()
    );

    return repository.save(lesson);
  }

  public void manageLesson(Long lessonId, Long durationMillis) {
    final Lesson lesson = repository.findById(lessonId).orElseThrow();
    lesson.startLesson();
    try {
      Thread.sleep(durationMillis);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    lesson.endLesson();
    repository.save(lesson);
  }
}
