package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.teacher.TeachersFreeHours;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetFreeHours;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetFreeHoursWithSubject;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TeachersFreeHoursRepository extends JpaRepository<TeachersFreeHours,String> {

    Boolean existsByEducationYearIdAndCreatedBy(String educationYear_id, String createdBy);
    Optional<TeachersFreeHours> findByIdAndCreatedBy(String id, String createdBy);

    List<TeachersFreeHours> findAllByCreatedByOrderByCreatedAt(String createdBy);

    @Query(value = "select g.id as groupId, g.name as groupName, L.id as subjectId,L.name as subject, gcs.educationYear_id as educationYearId from GroupConnectSubject gcs\n" +
            "    join GroupConnectSubject_Lesson GCSL on gcs.id = GCSL.GroupConnectSubject_id\n" +
            "    join Lesson L on GCSL.lessons_id = L.id\n" +
            "    join groups g on gcs.group_id = g.id\n" +
            "where gcs.group_id=?1 and gcs.educationYear_id=?2",nativeQuery = true)
    Set<GetFreeHoursWithSubject> getFreeHoursWithSubject(String groupId,String educationYearId);

}
