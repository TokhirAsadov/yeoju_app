package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeOfSubjectForGradeDto;

public interface ThemeOfSubjectForGradeByTeacherService {

    ApiResponse createTheme(CreateThemeOfSubjectForGradeDto dto);
    ApiResponse updateTheme(UpdateThemeOfSubjectForGradeDto dto);
    ApiResponse getThemeByLessonIdAndEducationYearIdAndCreatorId(String lessonId, String educationYearId, String creatorId);

}
