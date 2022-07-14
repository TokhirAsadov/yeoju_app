package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Kafedra extends AbsEntity {

    private String nameUz;
    private String nameRu;
    private String nameEn;

    public Kafedra(String nameUz) {
        this.nameUz = nameUz;
    }

    public Kafedra(String id, String nameUz, String nameRu, String nameEn) {
        super(id);
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }
}
