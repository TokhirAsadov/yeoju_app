package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseCreator;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.PlanOfSubjectV2Repository;

@Service
public class CourseImplService implements CourseService{
    private final CourseRepository courseRepository;
    private final PlanOfSubjectV2Repository planRepository;

    public CourseImplService(CourseRepository courseRepository, PlanOfSubjectV2Repository planRepository) {
        this.courseRepository = courseRepository;
        this.planRepository = planRepository;
    }

    @Override
    public void createCourse(CourseCreator creator) {
        if (planRepository.existsById(creator.planId)){
            Course course = new Course(creator.planId, planRepository.findById(creator.planId).get());
            courseRepository.save(course);
        } else {
            throw new UserNotFoundException("Plan did not found by id: "+creator.planId);
        }
    }

    @Override
    public boolean deleteCourse(String courseId) {
        if(courseRepository.existsById(courseId)){
            courseRepository.deleteById(courseId);
            return true;
        } else {
            throw new UserNotFoundException("Course did not found by id: "+courseId);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Courses",courseRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (courseRepository.existsById(id)) {
            return new ApiResponse(true,"Course is found by id: "+id,courseRepository.findById(id).get());
        }
        else {
            throw new UserNotFoundException("Course not found by id: "+id);
        }
    }
}
