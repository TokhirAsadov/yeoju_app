package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Lesson;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    Lesson getLessonByName(String name);
    boolean existsLessonByName(String name);
}
