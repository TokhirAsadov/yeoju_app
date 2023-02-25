package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "USERINFO")
public class USERINFO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long USERID;

    @Column(name = "Badgenumber")
    private Long Badgenumber;

    @Column(name = "SSN")
    private String SSN="0";
    @Column(name = "Name")
    private String Name;
    @Column(name = "Gender")
    private String Gender;
//    @Column(name = "TITLE")
//    private String TITLE;
//    @Column(name = "PAGER")
//    private String PAGER;
//    @Column(name = "BIRTHDAY")
//    private LocalDateTime BIRTHDAY;
//    @Column(name = "HIREDDAY")
//    private LocalDateTime HIREDDAY;
//    private String street;
//    @Column(name = "CITY")
//    private String CITY;
//    @Column(name = "STATE")
//    private String STATE;
//    @Column(name = "ZIP")
//    private String ZIP;
//    @Column(name = "OPHONE")
//    private String OPHONE;
//    @Column(name = "FPHONE")
//    private String FPHONE;
//    @Column(name = "VERIFICATIONMETHOD")
//    private Integer VERIFICATIONMETHOD;
//    @Column(name = "DEFAULTDEPTID")
//    private Integer DEFAULTDEPTID=1;
//    @Column(name = "SECURITYFLAGS")
//    private Integer SECURITYFLAGS;
//    @Column(name = "ATT")
//    private Integer ATT=1;
//    @Column(name = "INLATE")
//    private Integer INLATE=1;
//    @Column(name = "OUTEARLY")
//    private Integer OUTEARLY=1;
//    @Column(name = "OVERTIME")
//    private Integer OVERTIME=1;
//    @Column(name = "SEP")
//    private Integer SEP=1;
//    @Column(name = "HOLIDAY")
//    private Integer HOLIDAY=1;
//    @Column(name = "MINZU")
//    private String MINZU;
//    @Column(name = "PASSWORD")
//    private String PASSWORD;
//    @Column(name = "LUNCHDURATION")
//    private Integer LUNCHDURATION;
//    @Column(name = "PHOTO")
//    private byte[] PHOTO; /***      image      ***/
//
//    private String mverifypass;
//    @Column(name = "Notes")
//    private String Notes; /***      image      ***/
//    private Integer privilege;
//    @Column(name = "InheritDeptSch")
//    private Integer InheritDeptSch;
//    @Column(name = "InheritDeptSchClass")
//    private Integer InheritDeptSchClass;
//    @Column(name = "AutoSchPlan")
//    private Integer AutoSchPlan=1;
//    @Column(name = "MinAutoSchInterval")
//    private Integer MinAutoSchInterval=24;
//    @Column(name = "RegisterOT")
//    private Integer RegisterOT=1;
//    @Column(name = "InheritDeptRule")
//    private Integer InheritDeptRule;
//    @Column(name = "EMPRIVILEGE")
//    private Integer EMPRIVILEGE;
    @Column(name = "CardNo")
    private String CardNo;
//    private String change_operator;
//    private LocalDateTime change_time;
//    private String create_operator;
//    private LocalDateTime create_time;
//    private String delete_operator;
//    private LocalDateTime delete_time;
    private Integer status=0;
    private String lastname;

//    @Column(name = "AccGroup")
//    @JoinColumn(name = "AccGroup")
//    private Integer AccGroup=0;
//    @Column(name = "TimeZones")
//    private String TimeZones;
//    private String identitycard;
//    @Column(name = "UTime")
//    private LocalDateTime UTime;
//    @Column(name = "Education")
//    private String Education;
//    @Column(name = "OffDuty")
//    private Integer OffDuty;
//    @Column(name = "DelTag")
//    private Integer DelTag;
//    private Integer morecard_group_id=0;
//    private Boolean set_valid_time;
//    private LocalDateTime acc_startdate;
//    private LocalDateTime accEnddate;
//    private String birthplace;
//    @Column(name = "Political")
//    private String Political;
//    private String contry;
//    private Integer hiretype=0;
//    private String email;
//    private LocalDateTime firedate;
//    private Integer isatt;
//    private String homeaddress;
//    private Integer emptype=0;
//    private String bankcode1;
//    private String bankcode2;
//    private Integer isblacklist;
//    private Integer Iuser1;
//    private Integer Iuser2;
//    private Integer Iuser3;
//    private Integer Iuser4;
//    private Integer Iuser5;
//    private String Cuser1;
//    private String Cuser2;
//    private String Cuser3;
//    private String Cuser4;
//    private String Cuser5;
//    private LocalDateTime Duser1;

    public USERINFO(String name, String gender, String cardNo, String lastname) {
        Name = name;
        Gender = gender;
        CardNo = cardNo;
        this.lastname = lastname;
    }
}
