package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Creator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;

@Service
public class TestV2ImplService implements TestV2Service {
    private final TestV2Repository testV2Repository;
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    public TestV2ImplService(TestV2Repository testV2Repository, ModuleRepository moduleRepository, CourseRepository courseRepository) {
        this.testV2Repository = testV2Repository;
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    @Transactional
    public ApiResponse create(TestV2Creator creator) {
        int total = creator.multipleChoiceBall * creator.multipleChoiceCount +
                     creator.writtenBall * creator.writtenCount +
                     creator.singleChoiceBall * creator.singleChoiceCount;
        if (total != 100) {
            return new ApiResponse(false, "Total score must be 100, but was " + total);
        }
        if (creator.getPassingBall() < 0 || creator.getPassingBall() > 100) {
            return new ApiResponse(false, "Passing ball must be between 0 and 100");
        }

        TestV2 testV2 = new TestV2();
        testV2.setTitle(creator.getTitle());
        testV2.setSingleChoiceBall(creator.getSingleChoiceBall());
        testV2.setSingleChoiceCount(creator.getSingleChoiceCount());
        testV2.setMultipleChoiceBall(creator.getMultipleChoiceBall());
        testV2.setMultipleChoiceCount(creator.getMultipleChoiceCount());
        testV2.setWrittenBall(creator.getWrittenBall());
        testV2.setWrittenCount(creator.getWrittenCount());
        testV2.setPassingBall(creator.getPassingBall());

        if (creator.getModuleId() == null || creator.getModuleId().isEmpty()) {
            if (creator.getCourseId() == null || creator.getCourseId().isEmpty()) {
                return new ApiResponse(false, "Module ID or Course ID must be provided");
            } else if (!courseRepository.existsById(creator.getCourseId())) {
                return new ApiResponse(false, "Course with ID " + creator.getCourseId() + " does not exist");
            } else if (testV2Repository.existsByCourseId(creator.getCourseId())) {
                return new ApiResponse(false, "Test already exists for the course with ID " + creator.getCourseId());
            } else {
                testV2.setCourse(courseRepository.findById(creator.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found")));
                testV2Repository.save(testV2);
                return new ApiResponse(true, "Test created successfully for course with ID " + creator.getCourseId());
            }
        } else if (!moduleRepository.existsById(creator.getModuleId())) {
            return new ApiResponse(false, "Module with ID " + creator.getModuleId() + " does not exist");
        } else if (testV2Repository.existsByCourseId(creator.getModuleId())) {
            return new ApiResponse(false, "Test already exists for the module with ID " + creator.getModuleId());
        } else {
            testV2.setModule(moduleRepository.findById(creator.getModuleId()).orElseThrow(() -> new IllegalArgumentException("Module not found")));
            testV2Repository.save(testV2);
            return new ApiResponse(true, "Test created successfully for module with ID " + creator.getModuleId());
        }
    }
}
