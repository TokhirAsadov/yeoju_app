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

    @Override
    public ApiResponse create(CTestCreator creator) {
        if (!courseRepository.existsById(creator.courseId)) {
            return new ApiResponse(false,"Course not found");
        }
        if (courseTestRepository.existsByCourseId(creator.courseId)) {
            return new ApiResponse(false,"Test already exists for this course");
        }
        CourseTest courseTest = new CourseTest();
        courseTest.setTitle(creator.title);
        courseTest.setCourse(courseRepository.getById(creator.courseId));
        CourseTest save = courseTestRepository.save(courseTest);
        return new ApiResponse(true,"Test created successfully",save.getId());
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Course Test",courseTestRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String courseTestId) {
        CourseTest courseTest = courseTestRepository.findById(courseTestId)
                .orElseThrow(() -> new RuntimeException("Course test not found by id="+courseTestId));
        return new ApiResponse(true,"Course test by id="+courseTestId,courseTest);
    }
}
