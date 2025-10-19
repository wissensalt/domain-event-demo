package com.wissensalt.domaineventdemo;

import com.wissensalt.domaineventdemo.domain.Lesson;
import com.wissensalt.domaineventdemo.service.LessonService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@SpringBootApplication
public class DomainEventDemoApplication {

  private final LessonService lessonService;

  public DomainEventDemoApplication(LessonService lessonService) {
    this.lessonService = lessonService;
  }

  public static void main(String[] args) {
    SpringApplication.run(DomainEventDemoApplication.class, args);
  }

  @Setter
  @Getter
  public static class LessonRequest {

    private String title;
    private String content;

  }

  @PostMapping("/lesson")
  public boolean manageLesson(@RequestBody LessonRequest request) {
    log.info("Received lesson creation request: {}", request);
    final Lesson lesson = lessonService.createLesson(request);
    lessonService.manageLesson(lesson.getId(), 3000L);

    return true;
  }

}
