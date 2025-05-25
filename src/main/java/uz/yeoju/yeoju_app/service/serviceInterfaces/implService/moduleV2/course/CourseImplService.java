package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.PlanOfSubjectV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseImplService implements CourseService{
    private final CourseRepository courseRepository;
    private final PlanOfSubjectV2Repository planRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final UserTestAnswerRepository userTestAnswerRepository;
    private final GroupRepository groupRepository;

    public CourseImplService(CourseRepository courseRepository, PlanOfSubjectV2Repository planRepository, UserRepository userRepository, StudentRepository studentRepository, UserLessonModuleProgressRepository userLessonModuleProgressRepository, UserTestAnswerRepository userTestAnswerRepository, GroupRepository groupRepository) {
        this.courseRepository = courseRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.groupRepository = groupRepository;
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
    public ApiResponseTwoObj getCourseByIdV1(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<ModuleAdminResponse> modules = course.getModules().stream()
                .map(module -> new ModuleAdminResponse(
                        module.getId(),
                        module.getTitle(),
                        module.getTheme()
                ))
                .collect(Collectors.toList());

        if (course.getTests().isEmpty()) {
            return new ApiResponseTwoObj(true, "Modules", modules);
        }
        else {
            return new ApiResponseTwoObj(true, "Modules", modules, course.getTests().get(0));
        }


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



        // ✅ TEST NATIJALARINI OLIB BORISH

        Map<String, Object> testResultInfo = new HashMap<>();
        CourseTest courseTest = course.getTests().get(0);
        if (courseTest != null) {
            List<TestQuestion> questions = courseTest.getQuestions();
            int totalQuestions = questions.size();

            if (totalQuestions > 0) {
                int correctAnswers = 0;
                int attempted = 0;

                for (TestQuestion question : questions) {
                    Optional<UserTestAnswer> userAnswerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                    if (userAnswerOpt.isPresent()) {
                        attempted++;
                        if (userAnswerOpt.get().isCorrect()) {
                            correctAnswers++;
                        }
                    }
                }

                double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                testResultInfo.put("testTitle", courseTest.getTitle());
                testResultInfo.put("attemptedQuestions", attempted);
                testResultInfo.put("totalQuestions", totalQuestions);
                testResultInfo.put("correctAnswers", correctAnswers);
                testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
            }
        }
        else {
            testResultInfo.put("testTitle", "No test available");
            testResultInfo.put("attemptedQuestions", 0);
            testResultInfo.put("totalQuestions", 0);
            testResultInfo.put("correctAnswers", 0);
            testResultInfo.put("percent", 0.0);
        }

        CourseResponse courseResponse = new CourseResponse(
                course.getId(),
                course.getTitle(),
                Math.round(overallProgress * 100.0) / 100.0,
                doneOfTotal,
                responseList,
                canStartTest,// YANGI: testni boshlash ruxsati,
                testResultInfo
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

                    // ✅ TEST NATIJALARINI OLIB BORISH
                    Map<String, Object> testResultInfo = new HashMap<>();
                    CourseTest courseTest = course.getTests().get(0);
                    if (courseTest != null) {
                        List<TestQuestion> questions = courseTest.getQuestions();
                        int totalQuestions = questions.size();

                        if (totalQuestions > 0) {
                            int correctAnswers = 0;
                            int attempted = 0;

                            for (TestQuestion question : questions) {
                                Optional<UserTestAnswer> userAnswerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                                if (userAnswerOpt.isPresent()) {
                                    attempted++;
                                    if (userAnswerOpt.get().isCorrect()) {
                                        correctAnswers++;
                                    }
                                }
                            }

                            double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                            testResultInfo.put("testTitle", courseTest.getTitle());
                            testResultInfo.put("attemptedQuestions", attempted);
                            testResultInfo.put("totalQuestions", totalQuestions);
                            testResultInfo.put("correctAnswers", correctAnswers);
                            testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                        }
                    }
                    else {
                        testResultInfo.put("testTitle", "No test available");
                        testResultInfo.put("attemptedQuestions", 0);
                        testResultInfo.put("totalQuestions", 0);
                        testResultInfo.put("correctAnswers", 0);
                        testResultInfo.put("percent", 0.0);
                    }

                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            Math.round(progress * 100.0) / 100.0,
                            doneModules + "/" + totalModules,
                            moduleProgressList,
                            canStartTest,
                            testResultInfo // ✅ qo‘shildi
                    );
                })

                .collect(Collectors.toList());

        return new ApiResponse(true, "Courses with module progress", response);
    }

    public ApiResponse getCourseProgressForGroup(String groupId, String courseId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UserNotFoundException("Group not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new UserNotFoundException("Course not found"));

        List<Student> students = studentRepository.findAllByGroupId(groupId);
        List<Module> modules = course.getModules();
        int totalModules = modules.size();

        // Barcha modullar bo‘yicha progressni oldindan yuklab olish
        List<UserLessonModuleProgress> allProgress = userLessonModuleProgressRepository
                .findAllByModuleInAndCompletedTrue(modules);

        // StudentID + ModuleID kombinatsiyasi bo‘yicha completed map
        Set<String> completedMap = allProgress.stream()
                .map(p -> p.getUser().getId() + "_" + p.getModule().getId())
                .collect(Collectors.toSet());

        // Barcha test javoblarini oldindan yuklab olish
        List<TestQuestion> testQuestions = !course.getTests().isEmpty() ? course.getTests().get(0).getQuestions() : Collections.emptyList();
        List<UserTestAnswer> allTestAnswers = userTestAnswerRepository.findAllByQuestionIn(testQuestions);

        // StudentID -> List<UserTestAnswer>
        Map<String, List<UserTestAnswer>> userAnswersMap = allTestAnswers.stream()
                .collect(Collectors.groupingBy(ans -> ans.getUser().getId()));

        // Har bir student uchun response tuzish
        List<StudentCourseProgressResponse> responseList = new ArrayList<>();

        for (Student student : students) {
            User user = student.getUser();
            int doneModules = 0;

            List<ModuleProgressResponse> moduleProgressList = new ArrayList<>();
            for (Module module : modules) {
                boolean isCompleted = completedMap.contains(user.getId() + "_" + module.getId());
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

            Map<String, Object> testResultInfo = new HashMap<>();
            if (canStartTest && !testQuestions.isEmpty()) {
                List<UserTestAnswer> answers = userAnswersMap.getOrDefault(user.getId(), Collections.emptyList());

                int total = testQuestions.size();
                int attempted = answers.size();
                int correct = (int) answers.stream().filter(UserTestAnswer::isCorrect).count();
                double percent = total > 0 ? (correct * 100.0 / total) : 0.0;

                testResultInfo.put("testTitle", course.getTests().get(0).getTitle());
                testResultInfo.put("attemptedQuestions", attempted);
                testResultInfo.put("totalQuestions", total);
                testResultInfo.put("correctAnswers", correct);
                testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
            } else {
                testResultInfo.put("testTitle", "No test or not completed");
                testResultInfo.put("attemptedQuestions", 0);
                testResultInfo.put("totalQuestions", 0);
                testResultInfo.put("correctAnswers", 0);
                testResultInfo.put("percent", 0.0);
            }

            responseList.add(new StudentCourseProgressResponse(
                    student.getUser().getId(),
                    user.getFullName(),
                    doneModules + "/" + totalModules,
                    Math.round(progress * 100.0) / 100.0,
                    moduleProgressList,
                    canStartTest,
                    testResultInfo
            ));
        }

        return new ApiResponse(true, "Course progress for group", responseList);
    }



}
