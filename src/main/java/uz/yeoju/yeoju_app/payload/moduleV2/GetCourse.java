package uz.yeoju.yeoju_app.payload.moduleV2;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface GetCourse {
    String getId();
    String getTitle();

    @Value("#{@courseRepository.getFacultiesByCourseId(target.id)}")
    List<GetJsonObject> getFaculties();
}
