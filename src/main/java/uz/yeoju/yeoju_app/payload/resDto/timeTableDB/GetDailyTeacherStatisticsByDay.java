package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

public interface GetDailyTeacherStatisticsByDay {
    String getId();
    Integer getYear();
    Integer getMonth();
    Integer getDay();
    Integer getTotalAttended();
    Integer getTotalNotAttended();
}
