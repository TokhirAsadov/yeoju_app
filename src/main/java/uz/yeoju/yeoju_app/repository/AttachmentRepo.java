package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;

public interface AttachmentRepo extends JpaRepository<Attachment, String> {


    /**
     * huddi shu originalName lik file database da bormi yoki yo'q ligini tekshiruvchi boolean qaytaradigan Jpa query.
     * @param originalName String
     * @return boolean
     */
    boolean existsByOriginalName(String originalName);

}
