package uz.yeoju.yeoju_app.entity.ws;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.dekanat.StudentTransferDekan;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MsMessageDekan extends AbsEntity {

    @ManyToOne
    private StudentTransferDekan transfer;

    @Enumerated(EnumType.STRING)
    private MsStatus status;

    private String message;
    private String receiverId;
}
