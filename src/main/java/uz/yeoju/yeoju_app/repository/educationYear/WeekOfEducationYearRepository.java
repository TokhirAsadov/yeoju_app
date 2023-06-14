package uz.yeoju.yeoju_app.repository.educationYear;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

public interface WeekOfEducationYearRepository extends JpaRepository<WeekOfEducationYear,String> {
    boolean existsByStart(Date start);
    Optional<WeekOfEducationYear> findWeekOfEducationYearByStart(Date start);
}
