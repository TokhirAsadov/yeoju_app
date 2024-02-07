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

    @Query(value = "select K.room, u.fullName, tfh.schedule,tfh.day from TeacherConnectSubject tcs\n" +
            "join TeacherConnectSubject_groups TCSg on tcs.id = TCSg.TeacherConnectSubject_id\n" +
            "join WeekOfEducationYear WOEY on tcs.weeks_id = WOEY.id\n" +
            "join EducationYear_WeekOfEducationYear EYWOEY on WOEY.id = EYWOEY.weeks_id\n" +
            "join EducationYear EY on EYWOEY.EducationYear_id = EY.id\n" +
            "join users u on tcs.user_id = u.id\n" +
            "join TeachersFreeHours TFH on TFH.createdBy=u.id\n" +
            "join Teacher T on u.id = T.user_id\n" +
            "join Kafedra K on T.kafedra_id = K.id\n" +
            "where TCSg.groups_id=?1 and ey.id=?2 and tcs.lesson_id=?3 group by K.room, u.fullName, tfh.schedule,tfh.day",nativeQuery = true)
    Set<GetFreeHours> getFreeHours(String groupId, String educationYearId, String subjectId);

}
