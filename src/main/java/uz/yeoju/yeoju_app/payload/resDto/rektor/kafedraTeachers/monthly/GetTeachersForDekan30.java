package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetDate30;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.Date;

public interface GetTeachersForDekan30 {
    String getId();
    String getFullName();
    String getLogin();
    String getPassport();
    String getRfid();
    String getEmail();

    Date getDate();

    Float getRate();

    @Value("#{@positionRepository.getUserPositionForEditUserPosition(target.id)}")
    UserForTeacherSaveItem getUserPosition();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@teacherRepository.getCountTouchOfTeacher(target.id)}")
    Integer getCountTouch();

    @Value("#{@teacherRepository.getDaysOfMonthForMonthlyStatistics30(target.id,target.date)}")
    GetDate30 getMonthly();

    //TODO == BUYERGA UQITUVCHILARNING MAQOLALARI HAMDA POSITIONLARINI OLIB KELISHIM KK ==
}
