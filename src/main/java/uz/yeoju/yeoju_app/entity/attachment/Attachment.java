package uz.yeoju.yeoju_app.entity.attachment;


import lombok.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String originalName;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String contentType;

    // Mabodo, file system ga saqlamoqchi bo'lsak kerak bo'ladi, ikkita bir xil nomlik
    // file ni bir biridan farqlash uchun:
    // private String userPositionName;

}
