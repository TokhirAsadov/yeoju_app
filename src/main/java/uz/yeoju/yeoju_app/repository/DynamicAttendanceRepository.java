package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.DynamicAttendance;

public interface DynamicAttendanceRepository extends JpaRepository<DynamicAttendance,String> {
    Boolean existsByYearAndWeekAndWeekdayAndSectionAndCreatedByAndStudentId(Integer year, Integer week, Integer weekday, Integer section, String createdBy, String student_id);
}
