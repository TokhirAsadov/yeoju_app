package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Student;

import java.sql.Timestamp;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findStudentByUserId(String user_id);
    List<Student> findStudentsByGroupId(String group_id);
    List<Student> findStudentsByEducationFormId(String educationForm_id);
    List<Student> findStudentsByEducationTypeId(String educationType_id);
    List<Student> findStudentsByEducationLanguageId(String educationLanguage_id);
    Student findStudentByPassportSerial(String passportSerial);
    List<Student> findStudentsByBornYear(Timestamp bornYear);
    List<Student> findStudentsByEnrollmentYear(Timestamp enrollmentYear);
    List<Student> findStudentsByCitizenship(String citizenship);

//    List<Student> findStudentsByTeachStatusId(String teachStatus_id);
}
