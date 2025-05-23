package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.UserLessonModuleProgress;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.PlanOfSubjectV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseImplService implements CourseService{
    private final CourseRepository courseRepository;
    private final PlanOfSubjectV2Repository planRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;

    public CourseImplService(CourseRepository courseRepository, PlanOfSubjectV2Repository planRepository, UserRepository userRepository, StudentRepository studentRepository, UserLessonModuleProgressRepository userLessonModuleProgressRepository) {
        this.courseRepository = courseRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
    }

    @Override
    @Transactional
    public void createCourse(CourseCreator creator) {
        if (planRepository.existsById(creator.planId)){
            Course course = new Course(creator.title, planRepository.findById(creator.planId).get());
            courseRepository.save(course);
        } else {
            throw new UserNotFoundException("Plan did not found by id: "+creator.planId);
        }
    }

    @Override
    public boolean deleteCourse(String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        for (Module module : course.getModules()) {
            // Har bir modul uchun progresslarni topib o'chirish
            List<UserLessonModuleProgress> progresses = userLessonModuleProgressRepository.findAllByModule(module);
            userLessonModuleProgressRepository.deleteAll(progresses);
        }

        courseRepository.delete(course);
        return true;
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

    @Override
    public ApiResponse findByPlanId(String planId) {
        return new ApiResponse(true,"Courses",courseRepository.findAllByPlanId(planId)
                .stream()
                .sorted(Comparator.comparing(AbsEntity::getCreatedAt))
                .map(course -> new CourseResponse(
                        course.getId(),
                        course.getTitle()
                )).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse getCourseByIdV1(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<ModuleAdminResponse> modules = course.getModules().stream()
                .map(module -> new ModuleAdminResponse(
                        module.getId(),
                        module.getTitle(),
                        module.getTheme()
                ))
                .collect(Collectors.toList());

        return new ApiResponse(true, "Modules", modules);
    }

    @Override
    public ApiResponse getCourseByIdV2(String id, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Module> modules = course.getModules();
        int totalModules = modules.size();
        int doneModules = 0;

        List<ModuleProgressResponse> responseList = new ArrayList<>();

        for (Module module : modules) {
            boolean isCompleted = userLessonModuleProgressRepository
                    .existsByUserAndModuleAndCompletedTrue(user, module);

            if (isCompleted) {
                doneModules++;
            }

            responseList.add(new ModuleProgressResponse(
                    module.getId(),
                    module.getTitle(),
                    isCompleted ,
                    isCompleted ? "1/1" : "0/1",
                    module.getTheme()
            ));
        }

        double overallProgress = totalModules > 0 ? (doneModules * 100.0 / totalModules) : 0.0;
        String doneOfTotal = doneModules + "/" + totalModules;

        boolean canStartTest = doneModules == totalModules && totalModules > 0;

        CourseResponse courseResponse = new CourseResponse(
                course.getId(),
                course.getTitle(),
                Math.round(overallProgress * 100.0) / 100.0,
                doneOfTotal,
                responseList,
                canStartTest // YANGI: testni boshlash ruxsati
        );

        return new ApiResponse(true, "Course progress", courseResponse);
    }

    @Override
    public ApiResponse findByStudentIdAndEducationYearId(String studentId, String educationYearId) {
        Student student = studentRepository.findStudentByUserId(studentId);
        if (student == null) {
            throw new UserNotFoundException("Student not found by userId: " + studentId);
        }

        User user = student.getUser();

        List<CourseResponse> response = courseRepository
                .findAllByPlanEducationYearIdAndPlanGroupsId(educationYearId, student.getGroup().getId())
                .stream()
                .sorted(Comparator.comparing(AbsEntity::getCreatedAt))
                .map(course -> {
                    List<Module> modules = course.getModules();
                    int totalModules = modules.size();

                    List<ModuleProgressResponse> moduleProgressList = new ArrayList<>();
                    int doneModules = 0;

                    for (Module module : modules) {
                        boolean isCompleted = userLessonModuleProgressRepository
                                .existsByUserAndModuleAndCompletedTrue(user, module);
                        if (isCompleted) doneModules++;

                        moduleProgressList.add(new ModuleProgressResponse(
                                module.getId(),
                                module.getTitle(),
                                isCompleted,
                                isCompleted ? "1/1" : "0/1",
                                module.getTheme()
                        ));
                    }

                    double progress = totalModules > 0 ? (doneModules * 100.0 / totalModules) : 0.0;
                    boolean canStartTest = totalModules > 0 && doneModules == totalModules;

                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            Math.round(progress * 100.0) / 100.0,
                            doneModules + "/" + totalModules,
                            moduleProgressList,
                            canStartTest // 🔧 Qo‘shildi
                    );
                })
                .collect(Collectors.toList());

        return new ApiResponse(true, "Courses with module progress", response);
    }




}
