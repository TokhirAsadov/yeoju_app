package uz.yeoju.yeoju_app.repository.kafedra;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfDekanat;

import java.util.List;

public interface TableOfDekanatRepository extends JpaRepository<TableOfDekanat,String> {
    List<TableOfDekanat> findAllByDekanatIdOrderByCreatedAtDesc(String dekanat_id);

}
