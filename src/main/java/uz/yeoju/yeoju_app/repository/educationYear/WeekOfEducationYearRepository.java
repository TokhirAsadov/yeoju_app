package uz.yeoju.yeoju_app.repository.educationYear;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface WeekOfEducationYearRepository extends JpaRepository<WeekOfEducationYear,String> {
    boolean existsByStart(Date start);

    @Query(value = "select * from WeekOfEducationYear order by createdAt desc ",nativeQuery = true)
    List<WeekOfEducationYear> findAllByCreatedAt();

    Optional<WeekOfEducationYear> findWeekOfEducationYearByStart(Date start);
//    Optional<WeekOfEducationYear> findWeekOfEducationYearBySortNumberAndWeekNumber();
    List<WeekOfEducationYear> findWeekOfEducationsBySortNumberOrWeekNumber(Integer sortNumber, Integer weekNumber);
    Boolean existsBySortNumberAndYear(Integer sortNumber, Integer year);

    Optional<WeekOfEducationYear> findWeekOfEducationBySortNumberAndYear(Integer sortNumber, Integer year);
    Boolean existsByWeekNumberAndYear(Integer weekNumber, Integer year);
}
