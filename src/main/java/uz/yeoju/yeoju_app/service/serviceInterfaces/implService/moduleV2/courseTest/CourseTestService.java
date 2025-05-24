package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.courseTest;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CTestCreator;

public interface CourseTestService {
    ApiResponse create(CTestCreator creator);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String courseTestId);
    boolean delete(String id);
}
