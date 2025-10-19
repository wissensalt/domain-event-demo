package com.wissensalt.domaineventdemo.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonTrackingRepository extends JpaRepository<LessonTracking, Long> {

  Optional<LessonTracking> findByLessonId(Long lessonId);
}
