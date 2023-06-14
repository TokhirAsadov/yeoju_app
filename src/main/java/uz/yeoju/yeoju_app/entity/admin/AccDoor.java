package uz.yeoju.yeoju_app.entity.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "acc_door")
public class AccDoor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String changeOperator;
    private Date changeTime;
    private String createOperator;
    private Date createTime;
    private String deleteOperator;
    private Date deleteTime;
    private Integer status;
    @Column(name = "device_id")
    private Long deviceId;
    @Column(name = "door_no")
    private Integer doorNo;
    @Column(name = "door_name")
    private String doorName;
    private Integer lockDelay;
    private Boolean backLock;
    private Integer sensorDelay;
    private Integer opendoorType;
    private Integer inoutState;
    private Integer lockActiveId;
    private Integer longOpenId;
    private Integer wiegandFmtId;
    private Integer cardIntervaltime;
    private Integer readerType;
    private Boolean isAtt;
    private String forcePwd;
    private String supperPwd;
    private Integer doorSensorStatus;
    private Integer mapId;
    private Integer durationApb;
    private Integer readerIoState;
    private Integer openDoorDelay;
    private Boolean doorNormalOpen;
    private Boolean enableNormalOpen;
    private Boolean disenableNormalOpen;
    private Integer wiegandInType;
    private Integer wiegandOutType;
    private Integer wiegandFmtOutId;

    @Column(name = "SRBOn")
    private Integer srbon;
    @Column(name = "ManualCtlMade")
    private Integer manualCtlMade;
    @Column(name = "ErrTimes")
    private Integer errTimes;
    @Column(name = "SensorAlarmTime")
    private Integer sensorAlarmTime;
    @Column(name = "InTimeAPB")
    private Integer inTimeApb;

}
