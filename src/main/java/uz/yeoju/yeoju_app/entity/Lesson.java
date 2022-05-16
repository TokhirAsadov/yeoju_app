package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson extends AbsEntity {

    @Column(unique = true)
    private String name;
    @ManyToOne
    private Kafedra kafedra;
    @ManyToOne
    private Faculty faculty;
    private boolean active = true;

    public Lesson(String id, String name, Kafedra kafedra, Faculty faculty, boolean active) {
        super(id);
        this.name = name;
        this.kafedra = kafedra;
        this.faculty = faculty;
        this.active = active;
    }
}
