package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreateGradesWithThemeDto;
import uz.yeoju.yeoju_app.payload.module.CreateThemeOfSubjectForGradeDto;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeOfSubjectForGradeDto;

public interface ThemeOfSubjectForGradeByTeacherService {

    ApiResponse createThemeWithGrade(User user,CreateGradesWithThemeDto dto);
    ApiResponse createTheme(User user,CreateThemeOfSubjectForGradeDto dto);
    ApiResponse updateTheme(User user,UpdateThemeOfSubjectForGradeDto dto);
    ApiResponse getThemeByLessonIdAndEducationYearIdAndCreatorId(String groupId,String lessonId, String educationYearId, String creatorId);
    ApiResponse getFirstByLessonIdAndEducationYearIdAndCreatedByOrderByCreatedAtDesc(String groupId,String lessonId, String educationYearId, String creatorId);
    ApiResponse getThemes(String groupId, String lessonId, String educationYearId, String teacherId);

    ApiResponseTwoObj getTableOfGroup(String teacherId, String educationYearId, String lessonId, String groupId);

    ApiResponse deleteTheme(User user, String themeId);

    ApiResponse getGradesByThemeId(String themeId);
}
