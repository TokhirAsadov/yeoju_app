package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;

import java.util.List;

public interface KafedraMudirRepository extends JpaRepository<KafedraMudiri, String> {

}
