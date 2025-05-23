package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.courseTest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.moduleV2.CourseTest;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CTestCreator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseTestRepository;

@Service
public class CourseTestImplService implements CourseTestService{
    private final CourseTestRepository courseTestRepository;
    private final CourseRepository courseRepository;

    public CourseTestImplService(CourseTestRepository courseTestRepository, CourseRepository courseRepository) {
        this.courseTestRepository = courseTestRepository;
        this.courseRepository = courseRepository;
    }


}
