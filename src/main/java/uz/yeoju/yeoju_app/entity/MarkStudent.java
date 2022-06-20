package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MarkStudent extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    private String teachingYear;

    private Integer semester;

    @ManyToOne(fetch = FetchType.LAZY)
    private LessonWithActionForGroup lessonWithActionForGroup;

    private Integer score;
    private String grade;

}
