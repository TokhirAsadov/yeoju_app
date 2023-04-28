package uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface TeacherStatusAndPosition2 {

    Long getId();
    Integer getCount();
    String getName();

    @Value("#{@teacherRepository.getCountTeachersPositionWithWorkerStatus(target.id)}")
    List<TeacherStatusAndPosition> getWorkStatus();

    @Value("#{@teacherRepository.getComeCountTeachersPositionByPositionName(target.name)}")
    Integer getCome();
}
