package com.wissensalt.domaineventdemo.domain;

import com.wissensalt.domaineventdemo.event.LessonEndedEvent;
import com.wissensalt.domaineventdemo.event.LessonStartedEvent;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.domain.AbstractAggregateRoot;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson extends AbstractAggregateRoot<Lesson> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;
  private String title;
  private String content;

  public Lesson(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public void startLesson() {
    registerEvent(new LessonStartedEvent(id, LocalDateTime.now()));
  }

  public void endLesson() {
    registerEvent(new LessonEndedEvent(id, LocalDateTime.now()));
  }

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass = o instanceof HibernateProxy
        ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
        : o.getClass();
    Class<?> thisEffectiveClass = this instanceof HibernateProxy
        ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Lesson lesson = (Lesson) o;
    return getId() != null && Objects.equals(getId(), lesson.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer()
        .getPersistentClass().hashCode() : getClass().hashCode();
  }
}