package uz.yeoju.yeoju_app.entity.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LessonModule extends AbsEntity {

    private String title;
    private String theme;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "module_id")
    private Module module;
}
