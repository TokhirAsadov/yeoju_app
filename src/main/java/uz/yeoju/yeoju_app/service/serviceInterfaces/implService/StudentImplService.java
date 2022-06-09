package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

import java.sql.Timestamp;
public interface StudentImplService<T> extends MainService<T> {
    ApiResponse findStudentByUserId(String user_id);
    ApiResponse findStudentsByGroupId(String group_id);
//    ApiResponse findStudentsByEducationFormId(String educationForm_id);
//    ApiResponse findStudentsByEducationTypeId(String educationType_id);
//    ApiResponse findStudentsByEducationLanguageId(String educationLanguage_id);
    ApiResponse findStudentByPassportSerial(String passportSerial);
    ApiResponse findStudentsByBornYear(Timestamp bornYear);
    ApiResponse findStudentsByEnrollmentYear(Timestamp enrollmentYear);
    ApiResponse findStudentsByCitizenship(String citizenship);
}
