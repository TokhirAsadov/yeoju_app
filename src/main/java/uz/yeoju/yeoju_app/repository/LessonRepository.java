package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    Lesson getLessonByName(String name);
    boolean existsLessonByName(String name);

    @Query(value = "select id as value, name as label from Lesson order by name",nativeQuery = true)
    List<UserForTeacherSaveItem> getSubjectsForTeacherSaving();
}
