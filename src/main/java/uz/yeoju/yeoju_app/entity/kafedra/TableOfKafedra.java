package uz.yeoju.yeoju_app.entity.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableOfKafedra extends AbsEntity {
    private Integer year;
    private String month;
    @ManyToOne
    private Kafedra kafedra;
    private String fileName;
    private String fileType;
    private String contentType;

}
