package uz.yeoju.yeoju_app.entity.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Machines")
public class Machines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MachineAlias")
    private String machineAlias;

    @Column(name = "ConnectType")
    private Integer connectType;

    @Column(name = "IP")
    private String ip;

    @Column(name = "SerialPort")
    private Integer serialPort;

    @Column(name = "Port")
    private Integer port;

    @Column(name = "Baudrate")
    private Integer baudrate;

    @Column(name = "MachineNumber")
    private Integer machineNumber;

    @Column(name = "IsHost")
    private Boolean isHost;

    @Column(name = "Enabled")
    private Boolean enabled;

    @Column(name = "CommPassword")
    private String commPassword;

    @Column(name = "UILanguage")
    private Integer uiLanguage;

    @Column(name = "DateFormat")
    private Integer dateFormat;

    @Column(name = "InOutRecordWarn")
    private Integer inOutRecordWarn;

    @Column(name = "Idle")
    private Integer idle;

    @Column(name = "Voice")
    private Integer voice;

    @Column(name = "FirmwareVersion")
    private String firmwareVersion;

    @Column(name = "ProductType")
    private String productType;

    @Column(name = "LockControl")
    private Integer lockControl;

    @Column(name = "Purpose")
    private Integer purpose;

    @Column(name = "ProduceKind")
    private Integer produceKind;

    private String sn;

    @Column(name = "PhotoStamp")
    private String photoStamp;

    @Column(name = "IsIfChangeConfigServer2")
    private Integer isIfChangeConfigServer2;

    private Integer pushver;
    private String changeOperator;
    private Date changeTime;
    private String createOperator;
    private Date createTime;
    private String deleteOperator;
    private Date deleteTime;
    private Integer status;
    private Integer deviceType;
    private Date lastActivity;
    private String transTimes;

    @Column(name = "TransInterval")
    private Integer transInterval;
    private String longStamp;

//    private Image oplogStamp
//    private Image photoStamp

    @Column(name = "UpdateDB")
    private String updateDB;
    private String deviceName;
    private Integer transactionCount;
    private String mainTime;
    private Integer maxUserCount;
    private Integer maxFingerCount;
    private Integer maxAttlogCount;
    private String algVer;
    private String flashSize;
    private String freeFlashSize;
    private String language;
    private String lngEncode;
    private String volume;
    private String isTft;
    private String platform;
    private String brightness;
    private String oemVendor;
    private String city;
    @Column(name = "AccFun")
    private Integer accFun;
    @Column(name = "TZAdj")
    private Integer tzAdj;
    private Integer commType;
    private String agentIpaddress;
    private String subnetMask;
    private String gateway;
    private Integer areaId;
    private Integer acpanelType;
    private Boolean syncTime;
    private Boolean fourToTwo;
    private String videoLogin;
    private Integer fpMthreshold;
    @Column(name = "Fpversion")
    private Integer fpversion;
    private Integer maxCommSize;
    private Integer maxCommCount;
    private Boolean realtime;
    private Integer delay;
    private Boolean encrypt;
    private Integer dstimeId;
    private Integer doorCount;
    private Integer readerCount;
    private Integer auxInCount;
    private Integer auxOutCount;
    @Column(name = "IsOnlyRFMachine")
    private Integer isOnlyRFMachine;
    private String alias;
    private String ipaddress;
    private Integer comPort;
    private Integer comAddress;
    @Column(name = "SimpleEventType")
    private Integer simpleEventType;
    @Column(name = "FvFunOn")
    private Integer fvFunOn;
    private Integer fvcount;

//    private Image deviceOption;

    @Column(name = "DevSDKType")
    private Integer devSDKType;

//    @Column(name = "UTableDesc")
//    private Image uTableDesc;

    @Column(name = "IsTFTMachine")
    private Boolean isTFTMachine;
    @Column(name = "PinWidth")
    private Integer pinWidth;
    @Column(name = "UserExtFmt")
    private Integer userExtFmt;
    @Column(name = "FP1_NThreshold")
    private Integer fP1_NThreshold;
    @Column(name = "FP1_1Threshold")
    private Integer fP1_1Threshold;
    @Column(name = "Face1_NThreshold")
    private Integer face1_NThreshold;
    @Column(name = "Face1_1Threshold")
    private Integer face1_1Threshold;
    @Column(name = "Only1_1Mode")
    private Integer only1_1Mode;
    @Column(name = "OnlyCheckCard")
    private Integer onlyCheckCard;
    @Column(name = "MifireMustRegistered")
    private Integer mifireMustRegistered;
    @Column(name = "RFCardOn")
    private Integer rFCardOn;
    @Column(name = "Mifire")
    private Integer mifire;
    @Column(name = "MifireId")
    private Integer MifireId;
    @Column(name = "NetOn")
    private Integer netOn;
    @Column(name = "RS232On")
    private Integer rS232On;
    @Column(name = "RS485On")
    private Integer rS485On;
    @Column(name = "FreeType")
    private Integer freeType;
    @Column(name = "FreeTime")
    private Integer freeTime;
    @Column(name = "NoDisplayFun")
    private Integer noDisplayFun;
    @Column(name = "VoiceTipsOn")
    private Integer voiceTipsOn;
    @Column(name = "TOMenu")
    private Integer tOMenu;
    @Column(name = "StdVolume")
    private Integer stdVolume;
    @Column(name = "VRYVH")
    private Integer vRYVH;
    @Column(name = "KeyPadBeep")
    private Integer keyPadBeep;
    @Column(name = "BatchUpdate")
    private Integer batchUpdate;
    @Column(name = "CardFun")
    private Integer cardFun;
    @Column(name = "FaceFunOn")
    private Integer faceFunOn;
    @Column(name = "FaceCount")
    private Integer faceCount;
    @Column(name = "TimeAPBFunOn")
    private Integer timeAPBFunOn;
    @Column(name = "FingerFunOn")
    private String fingerFunOn;
    @Column(name = "CompatOldFirmware")
    private String compatOldFirmware;
//    @Column(name = "ParamValues")
//    private Image paramValues;
    @Column(name = "WirelessSSID")
    private String wirelessSSID;
    @Column(name = "WirelessKey")
    private String wirelessKey;
    @Column(name = "WirelessAddr")
    private String wirelessAddr;
    @Column(name = "WirelessMask")
    private String wirelessMask;
    @Column(name = "WirelessGateWay")
    private String WirelessGateWay;
    @Column(name = "IsWireless")
    private Boolean isWireless;
    private Integer managercount;
    private Integer usercount;
    private Integer fingercount;
    @Column(name = "SecretCount")
    private Integer secretCount;
    @Column(name = "ACFun")
    private Integer aCFun;
    @Column(name = "BiometricType")
    private String biometricType;
    @Column(name = "BiometricVersion")
    private String biometricVersion;
    @Column(name = "BiometricMaxCount")
    private String biometricMaxCount;
    @Column(name = "BiometricUsedCount")
    private String biometricUsedCount;
    @Column(name = "WIFI")
    private Integer wifi;
    @Column(name = "WIFIOn")
    private Integer wifiOn;
    @Column(name = "WIFIDHCP")
    private Integer WIFIDHCP;
    @Column(name = "IsExtend")
    private Integer isExtend;
    @Column(name = "DoorInTimeAPB")
    private Integer doorInTimeAPB;
    @Column(name = "DoorMultiCardInterTime")
    private Integer doorMultiCardInterTime;
    @Column(name = "DoorFirstCardOpenDoor")
    private Integer doorFirstCardOpenDoor;
    @Column(name = "DoorMultiCardOpenDoor")
    private Integer doorMultiCardOpenDoor;



}
