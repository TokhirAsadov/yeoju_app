package uz.yeoju.yeoju_app.service.useServices;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.StudentImplService;

import java.sql.Timestamp;

public class StudentService implements StudentImplService<StudentDto> {
    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(StudentDto studentDto) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

    @Override
    public ApiResponse findStudentByUserId(String user_id) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByGroupId(String group_id) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByEducationFormId(String educationForm_id) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByEducationTypeId(String educationType_id) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByEducationLanguageId(String educationLanguage_id) {
        return null;
    }

    @Override
    public ApiResponse findStudentByPassportSerial(String passportSerial) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByBornYear(Timestamp bornYear) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByEnrollmentYear(Timestamp enrollmentYear) {
        return null;
    }

    @Override
    public ApiResponse findStudentsByCitizenship(String citizenship) {
        return null;
    }
}
