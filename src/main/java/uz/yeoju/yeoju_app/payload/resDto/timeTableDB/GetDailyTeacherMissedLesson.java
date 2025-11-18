package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

public interface GetDailyTeacherMissedLesson {
    String getTeacherId();
    String getId();
    Integer getYear();
    Integer getMonth();
    Integer getDay();
    Integer getWeek();
    Integer getWeekday();
    Integer getHour();
    String getRoom();
}
