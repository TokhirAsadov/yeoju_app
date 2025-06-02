package uz.yeoju.yeoju_app.payload.moduleV2;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface GetTeacherLessonInModule {
    String getPlanId();
    String getTeacherId();
    String getTeacherFullName();
    String getLessonId();
    String getLessonName();
    String getEducationType();
    String getEducationLanguage();
    Integer getLevel();

    @Value("#{@courseRepository.getCoursesByCreatorIdAndPlanId(target.teacherId, target.planId)}")
    List<GetCourse> getCourses();
}
