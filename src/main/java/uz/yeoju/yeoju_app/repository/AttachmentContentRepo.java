package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.AttachmentContent;

import java.util.Optional;

public interface AttachmentContentRepo extends JpaRepository<AttachmentContent, String> {


    Optional<AttachmentContent> findByAttachmentId(String attachment_id);

}
