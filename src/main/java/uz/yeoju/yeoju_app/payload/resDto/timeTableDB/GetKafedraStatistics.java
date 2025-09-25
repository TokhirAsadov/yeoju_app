package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

public interface GetKafedraStatistics {
    String getId();
    String getKafedraId();
    String getKafedraName();
    Integer getTotalAttended();
    Integer getTotalMissed();
}
