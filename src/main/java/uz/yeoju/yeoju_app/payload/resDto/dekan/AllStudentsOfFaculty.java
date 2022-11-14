package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.user.UserField;

public interface AllStudentsOfFaculty {
    String getId();

    @Value("#{@userRepository.getUserFields(target.id)}")
    UserField getUser();

    @Value("#{@groupRepository.getGroupFieldByUserId(target.id)}")
    StudentGroupField getGroup();

    @Value("#{@dekanRepository.getForBadStudent(target.id)}")
    GetForBadStudent getStatistics();
}
