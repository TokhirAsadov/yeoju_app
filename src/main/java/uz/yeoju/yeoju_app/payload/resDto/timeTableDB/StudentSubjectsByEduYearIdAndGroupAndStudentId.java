package uz.yeoju.yeoju_app.payload.resDto.timeTableDB;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;

import java.util.Date;
import java.util.Set;

public interface StudentSubjectsByEduYearIdAndGroupAndStudentId {
    String getStudentId();
    String getGroupName();
    String getRoom();
    String getSubject();
    Integer getYear();
    Integer getWeek();
    Integer getDay();
    Integer getSection();

    @Value("#{@educationYearRepository.getTimesForRoomStatisticsByUserIdTimeNEW(target.studentId)}")
    Date getTime();

    @Value("#{@educationYearRepository.getTimesForRoomStatisticsByUserIdNEW(target.studentId,target.room,target.year,target.week,target.day,target.section)}")
    Set<TeacherStatisticsOfWeekday> getStatistics();
}
