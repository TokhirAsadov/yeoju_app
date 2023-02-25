package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    Lesson getLessonByName(String name);
    boolean existsLessonByName(String name);

    @Query(value = "select id as value, name as label from Lesson order by name",nativeQuery = true)
    List<UserForTeacherSaveItem> getSubjectsForTeacherSaving();


    @Query(value = "select l.name from Lesson l join Teacher_Lesson tl on tl.subjects_id=l.id join Teacher T on tl.Teacher_id = T.id where T.user_id=:id order by name asc",nativeQuery = true)
    List<String> getLessonForTeachers(@Param("id") String id);

    @Query(value = "select name from Lesson order by name asc ",nativeQuery = true)
    List<String> getLessonNames();
}
