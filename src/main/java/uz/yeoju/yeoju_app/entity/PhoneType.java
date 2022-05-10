package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsLongEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PhoneType extends AbsLongEntity {

    @Column(unique = true)
    private String name;

    public PhoneType(Long id, String name) {
        super(id);
        this.name = name;
    }
}
