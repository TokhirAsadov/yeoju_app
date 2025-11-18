package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

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

    @Value("#{@dailyTeacherMissedLessonRepository.getTeacherMissedLessonByDay(target.teacherId, target.year, target.month, target.day)}")
    List<GetDailyTeacherMissedLesson> getMissedLessons();
}
