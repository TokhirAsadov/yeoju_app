package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson extends AbsEntity {

    @Column(unique = true)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Kafedra kafedra;

//todo -----------------  dekanatlar uchun fakulty(yunalishlarni quwiw kk)  -------------
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Faculty faculty;
    private boolean active = true;

    public Lesson(String name) {
        this.name = name;
    }

    public Lesson(String id, String name,/* Kafedra kafedra, Faculty faculty,*/ boolean active) {
        super(id);
        this.name = name;
//        this.kafedra = kafedra;
//        this.faculty = faculty;
        this.active = active;
    }

    public Lesson(String id, String name, Kafedra kafedra, boolean active) {
        super(id);
        this.name = name;
        this.kafedra = kafedra;
        this.active = active;
    }
}
