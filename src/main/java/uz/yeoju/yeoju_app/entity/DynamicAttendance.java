package uz.yeoju.yeoju_app.entity;

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
public class DynamicAttendance extends AbsEntity {
    private Integer year;
    private Integer week;
    private Integer weekday;
    private Integer section;
    private Boolean isCome;
    private String room;
    @ManyToOne
    private User student;
}
