package uz.yeoju.yeoju_app.payload.resDto.kafedra.month;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

public interface GetTeachersOfKafedra31 {
    String getId();
    String getFullName();
    String getLogin();
    String getPassport();
    String getRfid();
    String getEmail();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@teacherRepository.getCountTouchOfTeacher(target.id)}")
    Integer getCountTouch();

    @Value("#{@teacherRepository.getDaysOfMonthStatistics(target.id)}")
    Get31OfMonthStatistics getMonthly();

    @Value("#{@positionRepository.getNameOfPosition(target.id)}")
    String getPositions();

    //TODO == BUYERGA UQITUVCHILARNING MAQOLALARI HAMDA POSITIONLARINI OLIB KELISHIM KK ==
}
