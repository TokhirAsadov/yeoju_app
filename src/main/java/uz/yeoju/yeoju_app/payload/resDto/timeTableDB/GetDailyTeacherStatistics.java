package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

public interface GetDailyTeacherStatistics {
    String getTeacherId();
    String getId();
    Integer getYear();
    Integer getMonth();
    Integer getDay();
    Integer getWeek();
    Integer getWeekday();
    Integer getTotalAttended();
    Integer getTotalNotAttended();
}
