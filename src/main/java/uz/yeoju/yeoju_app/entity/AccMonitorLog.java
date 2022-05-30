package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "acc_monitor_log")
public class AccMonitorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String change_operator;
    private LocalDateTime change_time;
    private String create_operator;
    private LocalDateTime create_time;
    private String delete_operator;
    private LocalDateTime delete_time;
    private Integer status;
    private Integer log_tag;
    private LocalDateTime time;
    private String pin;
    private String card_no;
    private Integer device_id;
    private String device_sn;
    private String device_name;
    private Integer verified;
    private Integer state;
    private Integer event_type;
    private String description;
    private Integer event_point_type;
    private Integer event_point_id;
    private String event_point_name;

}
