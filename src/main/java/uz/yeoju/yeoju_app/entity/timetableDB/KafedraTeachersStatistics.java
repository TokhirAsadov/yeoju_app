package uz.yeoju.yeoju_app.entity.timetableDB;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class KafedraTeachersStatistics {
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
    private Kafedra kafedra;

    private String day;

    private Integer totalAttended;

    private Integer totalMissed;

    public KafedraTeachersStatistics(Kafedra kafedra, String day, Integer totalAttended, Integer totalMissed) {
        this.kafedra = kafedra;
        this.day = day;
        this.totalAttended = totalAttended;
        this.totalMissed = totalMissed;
    }
}
