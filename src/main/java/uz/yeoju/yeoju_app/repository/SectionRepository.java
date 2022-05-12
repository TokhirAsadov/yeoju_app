package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Section;

import java.util.UUID;

public interface SectionRepository extends JpaRepository<Section, UUID> {
    Section getSectionByName(String name);
    boolean existsSectionByName(String name);
}
