package uz.yeoju.yeoju_app.payload.resDto.student;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.StudentsDynamicAttendance;

import java.util.Set;

public interface GetAllSubjectsByDayAndGroupAndStudentId {
    String getId();
    String getStudentId();
    String getRoom();
    Integer getDay();
    Integer getSection();
    Integer getWeek();
    Integer getYear();
    String getGroupName();
    String getSubject();

    @Value("#{@educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(target.studentId,target.room,target.year,target.week,target.day,target.section)}")
    Set<StudentsDynamicAttendance> getStatistics();

}
