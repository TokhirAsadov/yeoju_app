package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EducationType extends AbsEntity {

    @Column(unique = true)
    private String name;

    public EducationType(UUID id, String name) {
        super(id);
        this.name = name;
    }
}
