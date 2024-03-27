package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateGradesWithThemeDto;
import uz.yeoju.yeoju_app.payload.module.CreateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.GradeOfStudentByTeacherRepository;
import uz.yeoju.yeoju_app.repository.module.ThemeOfSubjectForGradeByTeacherRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThemeOfSubjectForGradeByTeacherImplService implements ThemeOfSubjectForGradeByTeacherService{

    private final ThemeOfSubjectForGradeByTeacherRepository repository;
    private final EducationYearRepository educationYearRepository;
    private final GradeOfStudentByTeacherRepository gradeRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse createThemeWithGrade(User user,CreateGradesWithThemeDto dto) {
        if (dto.id == null) {
            ApiResponse response = createTheme(user, new CreateThemeOfSubjectForGradeDto(dto.themeName, dto.maxGrade, dto.groupId, dto.subjectId,dto.educationId));
            if (response.isSuccess()) {
                ThemeOfSubjectForGradeByTeacher theme = repository.getById((String) response.getObj());
                dto.getGrades().forEach(grade->{
                    Boolean exists = gradeRepository.existsByEducationYearIdAndThemeIdAndStudentId(dto.educationId, theme.getId(), grade.studentId);
                    if (!exists) {
                        Optional<User> userOptional = userRepository.findById(grade.studentId);
                        if (userOptional.isPresent()) {
                            Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationId());
                            if (educationYearOptional.isPresent()) {
                                Optional<Lesson> lessonOptional = lessonRepository.findById(dto.getSubjectId());
                                if (lessonOptional.isPresent()) {
                                    User student = userOptional.get();
                                    EducationYear educationYear = educationYearOptional.get();
                                    Lesson lesson = lessonOptional.get();
                                    Boolean enableGrade = gradeRepository.isEnableGrade(grade.studentId, dto.groupId, dto.getEducationId(), dto.getSubjectId(), grade.grade);
                                    if (enableGrade){
                                        GradeOfStudentByTeacher gradeOfStudent = new GradeOfStudentByTeacher();
                                        gradeOfStudent.setStudent(student);
                                        gradeOfStudent.setEducationYear(educationYear);
                                        gradeOfStudent.setLesson(lesson);
                                        gradeOfStudent.setGrade(grade.grade);
                                        gradeOfStudent.setDescription(theme.getName());
                                        gradeOfStudent.setTime(dto.getTime());
                                        gradeOfStudent.setTheme(theme);
                                        gradeRepository.save(gradeOfStudent);
                                    }
                                    else {
                                        Float maxEnableGrade = gradeRepository.getMaxEnableGrade(grade.studentId, dto.groupId, dto.getEducationId(), dto.getSubjectId());
                                        throw new UserNotFoundException("You can only give maximum "+ maxEnableGrade +" ball to that student!.");
                                    }
                                }
                                else {
                                    throw new UserNotFoundException("not found subject by id: " + dto.getSubjectId());
                                }
                            }
                            else {
                                throw new UserNotFoundException("not found education year by id: " + dto.getEducationId());
                            }
                        }
                        else {
                            throw new UserNotFoundException("not found student by id: " + grade.studentId);
                        }
                    }
                });
                return new ApiResponse(true,"All grades have been saved successful");
            }
            else {
                throw new UserNotFoundException("some error occurred");
            }
        }
        else {
            boolean existsTheme = repository.existsById(dto.id);
            if (existsTheme){
                ThemeOfSubjectForGradeByTeacher theme = repository.getById(dto.id);
                dto.getGrades().forEach(grade->{
                    Boolean exists = gradeRepository.existsByEducationYearIdAndThemeIdAndStudentId(dto.educationId, theme.getId(), grade.studentId);
                    if (!exists) {
                        Optional<User> userOptional = userRepository.findById(grade.studentId);
                        if (userOptional.isPresent()) {
                            Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationId());
                            if (educationYearOptional.isPresent()) {
                                Optional<Lesson> lessonOptional = lessonRepository.findById(dto.getSubjectId());
                                if (lessonOptional.isPresent()) {
                                    User student = userOptional.get();
                                    EducationYear educationYear = educationYearOptional.get();
                                    Lesson lesson = lessonOptional.get();
                                    Boolean enableGrade = gradeRepository.isEnableGrade(grade.studentId, dto.groupId, dto.getEducationId(), dto.getSubjectId(), grade.grade);
                                    if (enableGrade){
                                        GradeOfStudentByTeacher gradeOfStudent = new GradeOfStudentByTeacher();
                                        gradeOfStudent.setStudent(student);
                                        gradeOfStudent.setEducationYear(educationYear);
                                        gradeOfStudent.setLesson(lesson);
                                        gradeOfStudent.setGrade(grade.grade);
                                        gradeOfStudent.setDescription(theme.getName());
                                        gradeOfStudent.setTime(dto.getTime());
                                        gradeOfStudent.setTheme(theme);
                                        gradeRepository.save(gradeOfStudent);
                                    }
                                    else {
                                        Float maxEnableGrade = gradeRepository.getMaxEnableGrade(grade.studentId, dto.groupId, dto.getEducationId(), dto.getSubjectId());
                                        throw new UserNotFoundException("You can only give maximum "+ maxEnableGrade +" ball to that student!.");
                                    }
                                }
                                else {
                                    throw new UserNotFoundException("not found subject by id: " + dto.getSubjectId());
                                }
                            }
                            else {
                                throw new UserNotFoundException("not found education year by id: " + dto.getEducationId());
                            }
                        }
                        else {
                            throw new UserNotFoundException("not found student by id: " + grade.studentId);
                        }
                    }
                });
                return new ApiResponse(true,"All grades have been saved successful");
            }
            else {
                throw new UserNotFoundException("Theme was not found by id: "+dto.id);
            }
        }
    }

    @Override
    public ApiResponse createTheme(User user, CreateThemeOfSubjectForGradeDto dto) {
        boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
        if (existsEducationYear){
            boolean existsLesson = lessonRepository.existsById(dto.subjectId);
            if (existsLesson){
                boolean existsGroup = groupRepository.existsById(dto.groupId);
                if (existsGroup) {
                    Boolean exists = repository.existsByNameAndLessonIdAndEducationYearIdAndCreatedBy(dto.name, dto.subjectId, dto.educationYearId, user.getId());
                    if (!exists) {
                        EducationYear educationYear = educationYearRepository.getById(dto.educationYearId);
                        Lesson lesson = lessonRepository.getById(dto.subjectId);
                        Group group = groupRepository.getById(dto.groupId);
                        ThemeOfSubjectForGradeByTeacher theme = new ThemeOfSubjectForGradeByTeacher(dto.name,dto.maxGrade,group, lesson, educationYear);
                        repository.saveAndFlush(theme);
                        return new ApiResponse(true, "Theme was saved successfully!.", theme.getId());
                    } else {
                        throw new UserNotFoundException("Theme already exists by name: " + dto.name);
                    }
                }
                else {
                    throw new UserNotFoundException("Group was not found by id: "+dto.groupId);
                }
            }
            else {
                throw new UserNotFoundException("Lesson was not found by id: "+dto.subjectId);
            }
        }
        else {
            throw new UserNotFoundException("Education year was not found by id: "+dto.educationYearId);
        }

    }

    @Override
    public ApiResponse updateTheme(User user,UpdateThemeOfSubjectForGradeDto dto) {
        boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
        if (existsEducationYear){
            boolean existsLesson = lessonRepository.existsById(dto.subjectId);
            if (existsLesson){
                boolean existsById = repository.existsById(dto.id);
                if (existsById) {
                    boolean existsGroup = groupRepository.existsById(dto.groupId);
                    if (existsGroup) {
                        Boolean exists = repository.existsByNameAndLessonIdAndEducationYearIdAndCreatedBy(dto.name, dto.subjectId, dto.educationYearId, user.getId());
                        if (exists) {
                            EducationYear educationYear = educationYearRepository.getById(dto.educationYearId);
                            Lesson lesson = lessonRepository.getById(dto.subjectId);
                            Group group = groupRepository.getById(dto.groupId);
                            ThemeOfSubjectForGradeByTeacher theme = repository.getById(dto.id);
                            theme.setLesson(lesson);
                            theme.setGroup(group);
                            theme.setEducationYear(educationYear);
                            theme.setName(dto.name);
                            theme.setMaxGrade(dto.maxGrade);
                            repository.save(theme);
                            return new ApiResponse(true, "Theme was updated successfully!.");
                        } else {
                            throw new UserNotFoundException("Theme already exists by name: " + dto.name);
                        }
                    }
                    else {
                        throw new UserNotFoundException("Group was not found by id: " + dto.groupId);
                    }
                }
                else {
                    throw new UserNotFoundException("Theme was not found by id: " + dto.id);
                }
            }
            else {
                throw new UserNotFoundException("Lesson was not found by id: "+dto.subjectId);
            }
        }
        else {
            throw new UserNotFoundException("Education year was not found by id: "+dto.educationYearId);
        }
    }

    @Override
    public ApiResponse getThemeByLessonIdAndEducationYearIdAndCreatorId(String groupId,String lessonId, String educationYearId, String creatorId) {
        Set<ThemeOfSubjectForGradeByTeacher> themes = repository.findAllByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(groupId,lessonId, educationYearId, creatorId);
        return new ApiResponse(true,"All themes",themes);
    }

    @Override
    public ApiResponse getFirstByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String groupId,String lessonId, String educationYearId, String creatorId) {
        ThemeOfSubjectForGradeByTeacher theme = repository.findFirstByGroupIdAndLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(groupId,lessonId, educationYearId, creatorId);
        return new ApiResponse(true,"last theme is this",theme);
    }

    @Override
    public ApiResponse getThemes(String groupId, String lessonId, String educationYearId, String teacherId) {
        return new ApiResponse(true,"all themes with students' grades",repository.getThemes(groupId,lessonId,educationYearId,teacherId));
    }
}
