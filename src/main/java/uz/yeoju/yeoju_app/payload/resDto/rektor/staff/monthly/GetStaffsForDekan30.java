package uz.yeoju.yeoju_app.payload.resDto.rektor.staff.monthly;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetDate30;

import java.util.Date;

public interface GetStaffsForDekan30 {
    String getId();
    String getFullName();
    String getLogin();
    String getPassport();
    String getRfid();
    String getEmail();

    Date getDate();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@teacherRepository.getCountTouchOfTeacher(target.id)}")
    Integer getCountTouch();

    @Value("#{@teacherRepository.getDaysOfMonthForMonthlyStatistics30(target.id,target.date)}")
    GetDate30 getMonthly();

    //TODO == BUYERGA UQITUVCHILARNING MAQOLALARI HAMDA POSITIONLARINI OLIB KELISHIM KK ==
}
