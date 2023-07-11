package uz.yeoju.yeoju_app.entity.attachment;


import lombok.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Attachment extends AbsEntity {

//    @Column(nullable = false)
    private String originalName;

//    @Column(nullable = false)
    private Long size;

//    @Column(nullable = false)
    private String contentType;

    private String fileName;
    private String url;

    public Attachment(String originalName, Long size, String contentType) {
        this.originalName = originalName;
        this.size = size;
        this.contentType = contentType;
    }

    public Attachment(String originalName, Long size, String contentType, String fileName) {
        this.originalName = originalName;
        this.size = size;
        this.contentType = contentType;
        this.fileName = fileName;
    }

    // Mabodo, file system ga saqlamoqchi bo'lsak kerak bo'ladi, ikkita bir xil nomlik
    // file ni bir biridan farqlash uchun:
    // private String userPositionName;

}
