package uz.yeoju.yeoju_app.repository.kafedra;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfKafedra;

public interface TableOfKafedraRepository extends JpaRepository<TableOfKafedra,String> {

    TableOfKafedra findAllByKafedraIdOrderByCreatedAtDesc(String kafedra_id);
    TableOfKafedra findByFileName(String fileName);
    Boolean existsByYearAndMonthAndKafedraId(Integer year, String month, String kafedra_id);
}
