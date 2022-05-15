package uz.yeoju.yeoju_app.entity.attachment;

import lombok.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentContent extends AbsEntity {

    @Lob
    @Column(nullable = false)
    private byte[] bytes;

    @OneToOne
    private Attachment attachment;

}
