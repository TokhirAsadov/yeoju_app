package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.admin.Machines;

public interface MachinesRepository extends JpaRepository<Machines,Long> {

}
