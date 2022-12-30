package uz.yeoju.yeoju_app.payload.resDto.rektor.dashboard;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.TeacherCountComeAndAll;

import java.util.List;

public interface RektorDashboard {

    String getId();

    //todo-------------- rahbariyatni ham olib borish kk -------------
    // @Value("#{@studentRepository.getStudentComeCount()}")
    //    List<Integer> getStudents();

    @Value("#{@teacherRepository.getCountComeAndAll()}")
    List<TeacherCountComeAndAll> getTeachers();

    @Value("#{@studentRepository.getStaffComeCount()}")
    List<Integer> getStaffs();

    @Value("#{@studentRepository.getStudentComeCount()}")
    List<Integer> getStudents();

    @Value("#{@teacherRepository.getCountWorkerStatus()}")
    List<TeacherStatusAndPosition> getCountTeachersStatus();

    @Value("#{@teacherRepository.getCountTeachersPosition()}")
    List<TeacherStatusAndPosition> getCountTeachersPositions();

    @Value("#{@studentRepository.getCountStudentLang()}")
    List<StudentEduLangFormType> getCountStudentLang();

    @Value("#{@studentRepository.getCountStudentForm()}")
    List<StudentEduLangFormType> getCountStudentForm();

    @Value("#{@studentRepository.getCountStudentType()}")
    List<StudentEduLangFormType> getCountStudentType();
}
