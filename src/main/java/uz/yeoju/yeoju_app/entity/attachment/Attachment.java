package uz.yeoju.yeoju_app.entity.attachment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
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
    // private String name;

}
