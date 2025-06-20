package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.dekanat.AcademicRecords;

import java.util.List;

public interface AcademicRecordsRepository extends JpaRepository<AcademicRecords, Long> {
    List<AcademicRecords> findAllByQaydRaqami(String qaydRaqami);
}
