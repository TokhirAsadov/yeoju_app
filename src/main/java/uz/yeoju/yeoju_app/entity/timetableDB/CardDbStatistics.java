package uz.yeoju.yeoju_app.entity.timetableDB;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class CardDbStatistics {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    @ManyToOne
    private CardDB cardDB;
    private String rfid;
    private Date time;
}
