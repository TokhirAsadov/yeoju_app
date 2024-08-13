package uz.yeoju.yeoju_app.test.payload.restDto;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetEnableTeachersByKafedraIdRestDto {
    String getId();
    String getTeacherId();
    String getTeacherName();

    @Value("#{@enableTeacherControlTestRepository.getLessonsOfEnableTeachers(target.id)}")
    Set<GetLessonsOfEnableTeachersRestDto> getLessons();
}
