package uz.yeoju.yeoju_app.repository.permissionPost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.PReference;

import java.util.List;

public interface PReferenceRepository extends JpaRepository<PReference,String> {

    Boolean existsPReferenceByStudentIdAndEducationYearIdAndStatus(String student_id, String educationYear_id, PPostStatus status);
    List<PReference> findAllByOrderByCreatedAtDesc();
    List<PReference> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);
    List<PReference> findAllByDeanIdOrderByCreatedAtDesc(String dean_id);
}
