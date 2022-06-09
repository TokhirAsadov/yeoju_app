package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "groups")
public class Group extends AbsEntity {

    @Column(unique = true)
    private String name;
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.LAZY)
    private EducationForm educationForm;

    @ManyToOne(fetch = FetchType.LAZY)
    private EducationType educationType;

    @ManyToOne(fetch = FetchType.LAZY)
    private EducationLanguage educationLanguage;

    public Group(String id, String name) {
        super(id);
        this.name = name;
    }

    public Group( String name, Integer level, Faculty faculty) {
        this.name = name;
        this.level = level;
        this.faculty = faculty;
    }
}
