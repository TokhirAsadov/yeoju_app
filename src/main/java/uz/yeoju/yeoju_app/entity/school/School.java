package uz.yeoju.yeoju_app.entity.school;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School extends AbsEntity {
    private Integer code;
    private String nameEn;
    private String nameRu;
    private String nameUz;

    public School(String id, Integer code, String nameEn, String nameRu, String nameUz) {
        super(id);
        this.code = code;
        this.nameEn = nameEn;
        this.nameRu = nameRu;
        this.nameUz = nameUz;
    }
}
