package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.courseTest;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.Test;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CTestCreator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseTestRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;

import java.util.Optional;

@Service
public class CourseTestImplService implements CourseTestService{
    private final CourseTestRepository courseTestRepository;
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;

    public CourseTestImplService(CourseTestRepository courseTestRepository, CourseRepository courseRepository, ModuleRepository moduleRepository) {
        this.courseTestRepository = courseTestRepository;
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public ApiResponse create(CTestCreator creator) {
        if (creator.courseId != null ) {
            if (!courseRepository.existsById(creator.courseId)) {
                return new ApiResponse(false,"Course not found");
            }
            if (courseRepository.existsByIdAndFinalTestIdIsNotNull(creator.courseId)) {
                return new ApiResponse(false,"Test already exists for this course");
            }
            Test test = new Test();
            test.setTitle(creator.title);
            if (creator.passingPercentage != null) {
                test.setPassingPercentage(creator.passingPercentage);
            }
            Test save = courseTestRepository.save(test);
            courseRepository.findById(creator.courseId).ifPresent(i -> {
                i.setFinalTest(save);
            });
            return new ApiResponse(true,"Test created successfully",save.getId());
        }
        if (creator.moduleId != null) {
            if (!moduleRepository.existsById(creator.moduleId)) {
                return new ApiResponse(false,"Module not found");
            }
            if (moduleRepository.existsByIdAndModuleTestIdIsNotNull(creator.moduleId)) {
                return new ApiResponse(false,"Test already exists for this module");
            }
            Test test = new Test();
            test.setTitle(creator.title);
            if (creator.passingPercentage != null) {
                test.setPassingPercentage(creator.passingPercentage);
            }
            Test save = courseTestRepository.save(test);
            moduleRepository.findById(creator.moduleId).ifPresent(i -> {
                i.setModuleTest(save);
            });
            return new ApiResponse(true,"Test created successfully",save.getId());
        }
        return new ApiResponse(false,"Course or module id is required to create a test");

    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Course Test",courseTestRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String courseTestId) {
        Test test = courseTestRepository.findById(courseTestId)
                .orElseThrow(() -> new RuntimeException("Course test not found by id="+courseTestId));
        return new ApiResponse(true,"Course test by id="+courseTestId, test);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        Optional<Test> optional = courseTestRepository.findById(id);
        if (optional.isPresent()) {
            Course course = courseRepository.findByFinalTestId(id);
            if (course != null) {
                course.setFinalTest(null);
                courseRepository.save(course);
            }

            Module module = moduleRepository.findByModuleTestId(id);
            if (module != null) {
                module.setModuleTest(null);
                moduleRepository.save(module);
            }

            courseTestRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
