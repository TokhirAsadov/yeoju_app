package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Faculty extends AbsEntity {

    @Column(unique = true)
    private String name;

    private Integer schoolCode;
    private String shortName;


    public Faculty(String name) {
        this.name = name;
    }

    public Faculty(String id, String name) {
        super(id);
        this.name = name;
    }

    public Faculty(String id, String name, String shortName) {
        super(id);
        this.name = name;
        this.shortName = shortName;
    }


}
