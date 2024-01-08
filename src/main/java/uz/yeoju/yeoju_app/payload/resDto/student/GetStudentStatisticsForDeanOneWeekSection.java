package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;

import java.util.List;

public interface GetStudentStatisticsForDeanOneWeekSection {
    String getStudentId();
    String getBetweenDuringDate();
    String getRoom();
    Integer getWeekday();
    Integer getSection();
    String getLesson();
    Integer getWeekNumber();
    Integer getWeek();
    Integer getYear();

    @Value("#{@userRepository.getTimesForRoomStatisticsByUserIdAndWeekNEW(target.studentId,target.room,target.year,target.week,target.weekday,target.section)}")
    List<TeacherStatisticsOfWeekday> getStatistics();
}
