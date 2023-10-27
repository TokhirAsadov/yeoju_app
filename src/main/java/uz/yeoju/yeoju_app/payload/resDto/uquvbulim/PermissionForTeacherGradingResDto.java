package uz.yeoju.yeoju_app.payload.resDto.uquvbulim;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;

import java.sql.Timestamp;
import java.util.Date;

public interface PermissionForTeacherGradingResDto {
    String getId();
    String getEducationYearId();
    String getTeacherId();
    String getGroupId();
    String getSubjectId();
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
    Date getDeadline();
    PPostStatus getStatus();

    @Value("#{@userRepository.getTeacherDataByUserId(target.teacherId)}")
    TeacherData getTeacher();

    @Value("#{@groupRepository.getGroupNameByGroupId(target.groupId)}")
    GroupForStudent getGroup();

    @Value("#{@lessonRepository.getLessonNameByLessonId(target.subjectId)}")
    GroupForStudent getSubject();
}
