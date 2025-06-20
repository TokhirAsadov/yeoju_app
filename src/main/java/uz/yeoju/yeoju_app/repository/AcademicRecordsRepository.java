package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.dekanat.AcademicRecords;

public interface AcademicRecordsRepository extends JpaRepository<AcademicRecords, Long> {
    // Custom query methods can be defined here if needed
}
