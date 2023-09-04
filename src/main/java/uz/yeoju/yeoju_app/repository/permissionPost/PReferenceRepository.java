package uz.yeoju.yeoju_app.repository.permissionPost;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.permissionPost.PReference;

import java.util.List;

public interface PReferenceRepository extends JpaRepository<PReference,String> {
    List<PReference> findAllByOrderByCreatedAtDesc();
    List<PReference> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);
    List<PReference> findAllByDeanIdOrderByCreatedAtDesc(String dean_id);
}
