package uz.yeoju.yeoju_app.repository.permissionPost;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.PermissionPost;

import java.util.List;

public interface PermissionPostRepository extends JpaRepository<PermissionPost,String> {

    List<PermissionPost> findAllByStatusOrderByCreatedAt(PPostStatus status);

    List<PermissionPost> findAllByOrderByCreatedAtDesc();
    List<PermissionPost> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);
}
