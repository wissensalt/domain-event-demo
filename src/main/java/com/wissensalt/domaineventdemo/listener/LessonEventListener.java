package com.wissensalt.domaineventdemo.listener;

import com.wissensalt.domaineventdemo.domain.LessonRepository;
import com.wissensalt.domaineventdemo.domain.LessonTracking;
import com.wissensalt.domaineventdemo.domain.LessonTrackingRepository;
import com.wissensalt.domaineventdemo.event.LessonEndedEvent;
import com.wissensalt.domaineventdemo.event.LessonStartedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class LessonEventListener {

  private final LessonTrackingRepository lessonTrackingRepository;
  private final LessonRepository lessonRepository;

  @EventListener
  public void listenLessonStartedEvent(LessonStartedEvent event) {
    log.info("Received lesson started event: {}", event);
    // Additional processing can be done here if needed
  }

  @EventListener
  public void listenLessonEndedEvent(LessonEndedEvent event) {
    log.info("Received lesson ended event: {}", event);
    // Additional processing can be done here if needed
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void handleLessonStartedEvent(LessonStartedEvent event) {
    log.info("Handling lesson started event: {}", event);
    lessonRepository.findById(event.lessonId())
        .ifPresent(lesson -> {
          final LessonTracking lessonTracking = new LessonTracking();
          lessonTracking.setLessonId(lesson.getId());
          if (lessonTracking.getStartTime() == null) {
            lessonTracking.setStartTime(event.startTime());
            lessonTrackingRepository.save(lessonTracking);
          }
          log.info("Lesson with ID {} has started at {}", event.lessonId(), event.startTime());
        });
  }

  @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
  public void handleLessonEndedEvent(LessonEndedEvent event) {
    log.info("Handling lesson ended event: {}", event);
    lessonRepository.findById(event.lessonId())
        .ifPresent(lesson -> {
          lessonTrackingRepository.findByLessonId(lesson.getId())
              .ifPresent(lessonTracking -> {
                if (lessonTracking.getEndTime() == null) {
                  lessonTracking.setEndTime(event.endTime());
                  lessonTrackingRepository.save(lessonTracking);
                }

              });
          log.info("Lesson with ID {} has ended at {}", event.lessonId(), event.endTime());
        });
  }
}
