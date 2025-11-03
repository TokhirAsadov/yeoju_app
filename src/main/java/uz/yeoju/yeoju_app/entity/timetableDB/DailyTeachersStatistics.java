package uz.yeoju.yeoju_app.entity.timetableDB;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import uz.yeoju.yeoju_app.entity.User;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DailyTeachersStatistics {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @Column(updatable = false)
    @NotNull
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToOne
    private User teacher;

    private Integer year;
    private Integer month;
    private Integer day;
    private Integer week;
    private Integer weekday;

    private Integer totalAttended;

    private Integer totalMissed;


}
