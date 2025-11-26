package uz.yeoju.yeoju_app.entity.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableOfSection extends AbsEntity {
    private Integer year;
    private String month;
    @ManyToOne
    private Section section;
    private String fileName;
    private String fileType;
    private String contentType;

}
