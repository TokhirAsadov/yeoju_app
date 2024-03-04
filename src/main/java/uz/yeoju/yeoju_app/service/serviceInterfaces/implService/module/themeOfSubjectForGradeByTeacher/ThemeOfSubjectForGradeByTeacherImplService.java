package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubjectForGradeByTeacher;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.ThemeOfSubjectForGradeByTeacherRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ThemeOfSubjectForGradeByTeacherImplService implements ThemeOfSubjectForGradeByTeacherService{

    private final ThemeOfSubjectForGradeByTeacherRepository repository;
    private final EducationYearRepository educationYearRepository;
    private final LessonRepository lessonRepository;

    @Override
    public ApiResponse createTheme(CreateThemeOfSubjectForGradeDto dto) {
        boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
        if (existsEducationYear){
            boolean existsLesson = lessonRepository.existsById(dto.subjectId);
            if (existsLesson){
                EducationYear educationYear = educationYearRepository.getById(dto.educationYearId);
                Lesson lesson = lessonRepository.getById(dto.subjectId);
                repository.save(new ThemeOfSubjectForGradeByTeacher(dto.name, lesson,educationYear));
                return new ApiResponse(true,"Theme was saved successfully!.");
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
    public ApiResponse getThemeByLessonIdAndEducationYearIdAndCreatorId(String lessonId, String educationYearId, String creatorId) {
        Set<ThemeOfSubjectForGradeByTeacher> themes = repository.findAllByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(lessonId, educationYearId, creatorId);
        return new ApiResponse(true,"All themes",themes);
    }
}
