package uz.yeoju.yeoju_app.payload.resDto.dekan;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.student.ForStudentTransfer;

import java.util.List;

public interface ForStudentTransferData {
    String getId();

    @Value("#{@studentRepository.getStudentsForTransfer(target.id)}")
    List<ForStudentTransfer> getTransferStudents();

    @Value("#{@dekanRepository.getFacultiesForStudentTransfer()}")
    List<FacultiesResDto> getTransferFaculties();
}
