package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;
import uz.yeoju.yeoju_app.payload.moduleV2.GetCourse;
import uz.yeoju.yeoju_app.payload.moduleV2.GetJsonObject;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,String> {
     List<Course> findAllByPlanId(String planId);
     @Query(value = "SELECT c.* FROM Course c\n" +
             "join PlanOfSubjectV2 p on c.plan_id = p.id\n" +
             "JOIN Course_Faculty cf ON c.id = cf.course_id\n" +
             "join Faculty f on cf.faculties_id = f.id\n" +
             "join groups g on f.id = g.faculty_id\n" +
             "join Student s on g.id = s.group_id\n" +
             "WHERE s.user_id=?1 and p.educationYear_id=?2",nativeQuery = true)
     List<Course> getCourseByStudentIdAndEducationYearId(String studentId, String educationYearId);
//     List<Course> findAllByPlanEducationYearIdAndPlanGroupsId(String educationYearId, String groupId);
     boolean existsByIdAndFinalTestIdIsNotNull(String id);
     Course findByFinalTestId(String id);

     @Query(value = "select c.id, c.title from Course c\n" +
             "where c.createdBy=?1 and c.plan_id=?2",nativeQuery = true)
     List<GetCourse> getCoursesByCreatorIdAndPlanId(String creatorId, String planId);


}
