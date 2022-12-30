package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.dekanat.StudentTransferDekan;

import java.util.UUID;

public interface StudentTransferDekanRepository extends JpaRepository<StudentTransferDekan, UUID> {
}
