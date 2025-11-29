package uz.yeoju.yeoju_app.repository.kafedra;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfSection;

import java.util.List;

public interface TableOfSectionRepository extends JpaRepository<TableOfSection,String> {
    List<TableOfSection> findAllBySectionIdOrderByCreatedAtDesc(String section_id);

}
