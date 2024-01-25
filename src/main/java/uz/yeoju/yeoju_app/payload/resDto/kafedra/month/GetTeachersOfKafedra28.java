package uz.yeoju.yeoju_app.payload.resDto.kafedra.month;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

import java.util.List;

public interface GetTeachersOfKafedra28 {
    String getId();
    String getFullName();
    String getLogin();
    String getPassport();
    String getRfid();
    String getEmail();
    String getCitizenship();//fuqaroligi
    String getNationality();//millati
    Float getRate();

    @Value("#{@userPhotoRepo.getUserPhotoRes(target.id)}")
    UserPhotoRes getPhoto();

    @Value("#{@teacherRepository.getCountTouchOfTeacher(target.id)}")
    Integer getCountTouch();

//    @Value("#{@teacherRepository.getDaysOfMonthStatistics(target.id)}")
//    Get28OfMonthStatistics getMonthly();

    @Value("#{@positionRepository.getNameOfPosition(target.id)}")
    String getPositions();

//    @Value("#{@lessonRepository.getLessonForTeachers(target.id)}")
//    List<String> getSubjects();

    @Value("#{@teacherRepository.getWorkerStatus(target.id)}")
    String getStatus();
    //TODO == BUYERGA UQITUVCHILARNING MAQOLALARI HAMDA POSITIONLARINI OLIB KELISHIM KK ==
}
