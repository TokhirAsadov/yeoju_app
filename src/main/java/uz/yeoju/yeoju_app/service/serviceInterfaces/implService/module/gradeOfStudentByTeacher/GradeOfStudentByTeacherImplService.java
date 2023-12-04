package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.gradeOfStudentByTeacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreateGradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.payload.resDto.module.GetGradesOfStudent;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.GetStudentDataForMiddleGrade;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.GradeOfStudentByTeacherRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GradeOfStudentByTeacherImplService implements GradeOfStudentByTeacherService{
    private final GradeOfStudentByTeacherRepository gradeRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final EducationYearRepository educationYearRepository;


    @Override
    public ApiResponse getGradesOfStudent(String teacherId, String studentId, String educationYearId, String subjectId) {
        Set<GetGradesOfStudent> studentSet = gradeRepository.getGradesOfStudentByTeacherIdAndStudentIdAndEducationYearIdAndLessonId(teacherId, studentId, educationYearId, subjectId);
        return new ApiResponse(true,"Grades of students",studentSet);
    }

    @Override
    public ApiResponseTwoObj getAvgGradesOfStudent(String teacherId, String studentId, String educationYearId, String subjectId, String groupId) {
        return new ApiResponseTwoObj(true,"svg Grades of students",gradeRepository.getSumGrade(teacherId, studentId, educationYearId, subjectId),gradeRepository.getMaxStep(educationYearId, subjectId, groupId));
    }

    @Override
    public ApiResponse getAllMiddleGradesOfGroup(String educationYearId, String groupId) {
        Set<GetStudentDataForMiddleGrade> gradeSet = gradeRepository.getStudentDataForMiddleGrade(educationYearId, groupId);
        return new ApiResponse(true,"all grades of students of group",gradeSet);
    }

    @Override
    public ApiResponse createGrade(User user, CreateGradeOfStudentByTeacher dto) {
        Optional<User> userOptional = userRepository.findById(dto.getStudentId());
        if (userOptional.isPresent()) {
            Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationYearId());
            if (educationYearOptional.isPresent()) {
                Optional<Lesson> lessonOptional = lessonRepository.findById(dto.getSubjectId());
                if (lessonOptional.isPresent()) {
                    User student = userOptional.get();
                    EducationYear educationYear = educationYearOptional.get();
                    Lesson lesson = lessonOptional.get();
                    GradeOfStudentByTeacher gradeOfStudent = new GradeOfStudentByTeacher();
                    gradeOfStudent.setStudent(student);
                    gradeOfStudent.setEducationYear(educationYear);
                    gradeOfStudent.setLesson(lesson);
                    gradeOfStudent.setGrade(dto.getGrade());
                    gradeOfStudent.setTime(dto.getTime());
                    gradeOfStudent.setDescription(dto.getDescription());
                    gradeRepository.save(gradeOfStudent);
                    return new ApiResponse(true,"created successfully");
                }
                else {
                    return new ApiResponse(false,"not found subject by id: " + dto.getSubjectId());
                }
            }
            else {
                return new ApiResponse(false,"not found education year by id: " + dto.getEducationYearId());
            }
        }
        else {
            return new ApiResponse(false,"not found student by id: " + dto.getStudentId());
        }
    }

    @Override
    public ApiResponse updateGrade(User user, CreateGradeOfStudentByTeacher dto) {
        Optional<GradeOfStudentByTeacher> optional = gradeRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Optional<User> userOptional = userRepository.findById(dto.getStudentId());
            if (userOptional.isPresent()) {
                Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationYearId());
                if (educationYearOptional.isPresent()) {
                    Optional<Lesson> lessonOptional = lessonRepository.findById(dto.getSubjectId());
                    if (lessonOptional.isPresent()) {
                        GradeOfStudentByTeacher gradeOfStudent = optional.get();
                        gradeOfStudent.setGrade(dto.getGrade());
                        gradeOfStudent.setTime(dto.getTime());
                        gradeOfStudent.setDescription(dto.getDescription());
                        gradeRepository.save(gradeOfStudent);
                        return new ApiResponse(true, "updated successfully");
                    } else {
                        return new ApiResponse(false, "not found subject by id: " + dto.getSubjectId());
                    }
                } else {
                    return new ApiResponse(false, "not found education year by id: " + dto.getEducationYearId());
                }
            } else {
                return new ApiResponse(false, "not found student by id: " + dto.getStudentId());
            }
        }
        else {
            return new ApiResponse(false, "not found grade by id: " + dto.getId());
        }
    }

    @Override
    public ApiResponse delete(User user, String id) {
        Optional<GradeOfStudentByTeacher> optional = gradeRepository.findByIdAndCreatedBy(id, user.getId());
        if (optional.isPresent()) {
            gradeRepository.deleteById(id);
            return new ApiResponse(true,"Grade was deleted successfully");
        }
        else {
            return new ApiResponse(false,"Not Found grade");
        }
    }
}
