package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetLessonsByKafedraOwnerId;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, String> {
    Lesson getLessonByName(String name);
    boolean existsLessonByName(String name);

    @Query(value = "select l.id,l.name from Lesson l  where l.id=:id",nativeQuery = true)
    GroupForStudent getLessonNameByLessonId(@Param("id") String id);

    @Query(value = "select l.id as subjectId,l.name as subjectName,l.kafedra_id as kafedraId from Lesson l where l.kafedra_id =?1 order by l.name",nativeQuery = true)
    List<GetLessonsByKafedraOwnerId> getSubjectsByKafedraId(String kafedraId);

    @Query(value = "select id as value, name as label from Lesson order by name",nativeQuery = true)
    List<UserForTeacherSaveItem> getSubjectsForTeacherSaving();


    @Query(value = "select l.name from Lesson l join Teacher_Lesson tl on tl.subjects_id=l.id join Teacher T on tl.Teacher_id = T.id where T.user_id=:id order by name asc",nativeQuery = true)
    List<String> getLessonForTeachers(@Param("id") String id);

    @Query(value = "select name from Lesson order by name asc ",nativeQuery = true)
    List<String> getLessonNames();
    @Query(value = "select l.name  from Lesson l join Kafedra K on l.kafedra_id = K.id where K.owner_id=?1 group by l.name order by l.name",nativeQuery = true)
    List<String> getLessonNames(String id);

    @Query(value = "select l.id as subjectId,l.name as subjectName,l.kafedra_id as kafedraId from Lesson l join Kafedra K on l.kafedra_id = K.id where K.owner_id=?1 group by l.id, l.name, l.kafedra_id order by l.name",nativeQuery = true)
    List<GetLessonsByKafedraOwnerId> getAllLessonByKaferaOwnerId(String id);
    @Query(value = "select l.id as value ,l.name as label,l.kafedra_id as kafedraId from Lesson l join Kafedra K on l.kafedra_id = K.id where K.owner_id=?1 group by l.id, l.name, l.kafedra_id order by l.name",nativeQuery = true)
    List<UserForTeacherSaveItem> getAllLessonByKaferaOwnerId2(String id);


}
