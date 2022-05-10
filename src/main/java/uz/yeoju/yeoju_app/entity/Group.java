package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Group extends AbsEntity {

    @Column(unique = true)
    private String name;

    @ManyToOne
    private Faculty faculty;

    public Group(UUID id, String name) {
        super(id);
        this.name = name;
    }
}
