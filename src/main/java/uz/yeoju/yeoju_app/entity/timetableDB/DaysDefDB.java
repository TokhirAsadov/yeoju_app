package uz.yeoju.yeoju_app.entity.timetableDB;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DaysDefDB extends AbsEntity {
    private String id;
    private String name;
    private String shortName;
    private String day;

    public DaysDefDB(String id, String id1, String name, String shortName, String day) {
        super(id);
        this.id = id1;
        this.name = name;
        this.shortName = shortName;
        this.day = day;
    }
}
