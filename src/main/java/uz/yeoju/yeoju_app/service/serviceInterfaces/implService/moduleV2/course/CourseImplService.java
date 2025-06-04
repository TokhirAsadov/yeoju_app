package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.course;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.FacultyRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.*;

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
    private final TopicFileOfLineV2Repository topicFileOfLineV2Repository;
    private final StudentReadCourseCounterRepository studentReadCourseCounterRepository;
    private final FacultyRepository facultyRepository;

    public CourseImplService(CourseRepository courseRepository, PlanOfSubjectV2Repository planRepository, UserRepository userRepository, StudentRepository studentRepository, UserLessonModuleProgressRepository userLessonModuleProgressRepository, UserTestAnswerRepository userTestAnswerRepository, GroupRepository groupRepository, TopicFileOfLineV2Repository topicFileOfLineV2Repository, StudentReadCourseCounterRepository studentReadCourseCounterRepository, FacultyRepository facultyRepository) {
        this.courseRepository = courseRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.groupRepository = groupRepository;
        this.topicFileOfLineV2Repository = topicFileOfLineV2Repository;
        this.studentReadCourseCounterRepository = studentReadCourseCounterRepository;
        this.facultyRepository = facultyRepository;
    }

    @Override
    @Transactional
    public void createCourse(CourseCreator creator) {
        if (planRepository.existsById(creator.planId)){
            List<Faculty> facultyList = facultyRepository.findAllById(creator.getFacultiesIds());
            Course course = new Course(creator.title, planRepository.findById(creator.planId).get(),new HashSet<>(facultyList));
            courseRepository.save(course);
        } else {
            throw new UserNotFoundException("Plan did not found by id: "+creator.planId);
        }
    }

    @Override
    public ApiResponse updateCourse(CourseUpdator updator) {
        Course course = courseRepository.findById(updator.getId())
                .orElseThrow(() -> new UserNotFoundException("Course not found by id: " + updator.getId()));
        List<Faculty> facultyList = facultyRepository.findAllById(updator.getFacultiesIds());
        if(!updator.title.equals("")&&updator.title!=null){
            course.setTitle(updator.title);
        }
        Set<Faculty>faculties = course.getFaculties();
        HashSet<Faculty> objects = new HashSet<>();
        objects.addAll(faculties);
        objects.addAll(facultyList);
        course.setFaculties(objects);
        courseRepository.save(course);
        return new ApiResponse(true, "Course updated successfully", course.getId());
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

        course.setFaculties(new HashSet<>());
        courseRepository.save(course);

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
                .map(course -> new CourseResponseV2(
                        course.getId(),
                        course.getTitle(),
                        course.getFaculties().stream()
                                .map(faculty -> new JsonObject(faculty.getId(), faculty.getShortName())).collect(Collectors.toList())
                )).collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponseTwoObj getCourseByIdV1(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<ModuleAdminResponse> modules = course.getModules().stream()
                .sorted(Comparator.comparing(Module::getCreatedAt))
                .map(module -> new ModuleAdminResponse(
                        module.getId(),
                        module.getTitle(),
                        module.getTheme()
                ))
                .collect(Collectors.toList());


        return new ApiResponseTwoObj(true, "Modules", modules, course.getFinalTest());
    }

    @Override
    public ApiResponse getCourseByIdV2(String id, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Module> modules = course.getModules().stream().sorted(Comparator.comparing(Module::getCreatedAt))
                .collect(Collectors.toList());
        int totalModules = modules.size();
        int doneModules = 0;

        List<ModuleProgressResponse> responseList = new ArrayList<>();

        for (Module module : modules) {
            boolean isCompleted = userLessonModuleProgressRepository
                    .existsByUserAndModuleAndCompletedTrue(user, module);

            if (isCompleted) {
                doneModules++;
            }

            List<TopicFileOfLineV2Dto> topicFiles = new ArrayList<>();

            List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
            boolean isTopicFilesEmpty = topics.isEmpty();
            if (!isTopicFilesEmpty&&topics.size()>0) {
                topics.forEach(tf -> {
                    topicFiles.add(new TopicFileOfLineV2Dto(
                            tf.getName(),
                            tf.getFileType(),
                            tf.getContentType(),
                            tf.getPackageName()
                    ));
                });
            }

            Test moduleTest = module.getModuleTest();
            ModuleTestDto moduleTestDto = null;
            Map<String, Object> testResultInfo = new HashMap<>();

            if (moduleTest != null) {
                moduleTestDto = new ModuleTestDto(
                        moduleTest.getId(),
                        moduleTest.getTitle(),
                        moduleTest.getQuestions() != null ? moduleTest.getQuestions().size() : 0
                );

                List<TestQuestion> questions = moduleTest.getQuestions();
                int totalQuestions = questions.size();
                int correctAnswers = 0;
                int attempted = 0;

                for (TestQuestion question : questions) {
                    Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                    if (answerOpt.isPresent()) {
                        attempted++;
                        if (answerOpt.get().isCorrect()) {
                            correctAnswers++;
                        }
                    }
                }

                double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                testResultInfo.put("testId", moduleTest.getId());
                testResultInfo.put("testTitle", moduleTest.getTitle());
                testResultInfo.put("attemptedQuestions", attempted);
                testResultInfo.put("totalQuestions", totalQuestions);
                testResultInfo.put("correctAnswers", correctAnswers);
                testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                testResultInfo.put("testCompleted", attempted != 0);
            } else {
                testResultInfo.put("testTitle", "No test available");
                testResultInfo.put("attemptedQuestions", 0);
                testResultInfo.put("totalQuestions", 0);
                testResultInfo.put("correctAnswers", 0);
                testResultInfo.put("percent", 0.0);
                testResultInfo.put("testCompleted", false);
            }



            responseList.add(new ModuleProgressResponse(
                    module.getId(),
                    module.getTitle(),
                    isCompleted ,
                    isCompleted ? "1/1" : "0/1",
                    module.getTheme(),
                    topicFiles,
                    moduleTestDto, // ✅ qo‘shildi
                    testResultInfo
            ));
        }

        double overallProgress = totalModules > 0 ? (doneModules * 100.0 / totalModules) : 0.0;
        String doneOfTotal = doneModules + "/" + totalModules;

        boolean canStartTest = doneModules == totalModules && totalModules > 0;



        // ✅ TEST NATIJALARINI OLIB BORISH

        Map<String, Object> testResultInfo = new HashMap<>();

        Test test = course.getFinalTest();
        if (test != null) {
            List<TestQuestion> questions = test.getQuestions();
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

                testResultInfo.put("testTitle", test.getTitle());
                testResultInfo.put("attemptedQuestions", attempted);
                testResultInfo.put("totalQuestions", totalQuestions);
                testResultInfo.put("correctAnswers", correctAnswers);
                testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                testResultInfo.put("testCompleted", true);
            }
        }
        else {
            testResultInfo.put("testTitle", "No test available");
            testResultInfo.put("attemptedQuestions", 0);
            testResultInfo.put("totalQuestions", 0);
            testResultInfo.put("correctAnswers", 0);
            testResultInfo.put("percent", 0.0);
            testResultInfo.put("testCompleted", false);
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
        // Talabani olish
        Student student = studentRepository.findStudentByUserId(studentId);
        if (student == null) {
            throw new UserNotFoundException("Student not found by userId: " + studentId);
        }

        User user = student.getUser();

        // Kurslarni olish (berilgan ta'lim yiliga va guruhga ko'ra)
        List<CourseResponse> response = courseRepository
                .getCourseByStudentIdAndEducationYearId(student.getUser().getId(),educationYearId )
                .stream()
                .sorted(Comparator.comparing(AbsEntity::getCreatedAt))
                .map(course -> {
                    List<Module> modules = course.getModules().stream()
                            .sorted(Comparator.comparing(Module::getCreatedAt))
                            .collect(Collectors.toList());

                    int totalModules = modules.size();
                    int doneModules = 0;

                    List<ModuleProgressResponse> moduleProgressList = new ArrayList<>();

                    for (Module module : modules) {
                        boolean isCompleted = userLessonModuleProgressRepository.existsByUserAndModuleAndCompletedTrue(user, module);
                        if (isCompleted) doneModules++;

                        // Topic fayllarini olish
                        List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
                        List<TopicFileOfLineV2Dto> topicFiles = topics.stream()
                                .map(tf -> new TopicFileOfLineV2Dto(tf.getName(), tf.getFileType(), tf.getContentType(), tf.getPackageName()))
                                .collect(Collectors.toList());

                        // Modul test natijalari
                        Test moduleTest = module.getModuleTest();
                        ModuleTestDto moduleTestDto = null;
                        Map<String, Object> moduleTestResultInfo = new HashMap<>();

                        if (moduleTest != null) {
                            moduleTestDto = new ModuleTestDto(
                                    moduleTest.getId(),
                                    moduleTest.getTitle(),
                                    moduleTest.getQuestions() != null ? moduleTest.getQuestions().size() : 0
                            );

                            List<TestQuestion> questions = moduleTest.getQuestions();
                            int totalQuestions = questions.size();
                            int correctAnswers = 0;
                            int attempted = 0;
                            int moduleScore = 0;

                            for (TestQuestion question : questions) {
                                Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                                if (answerOpt.isPresent()) {
                                    attempted++;
                                    UserTestAnswer userTestAnswer = answerOpt.get();
                                    if (userTestAnswer.isCorrect()) {
                                        correctAnswers++;
                                    }
                                    for (int coreCount = 0; coreCount < userTestAnswer.getSelectedOptions().size(); coreCount++) {
                                        if (userTestAnswer.getSelectedOptions().get(coreCount).getCorrect()) {
                                            moduleScore = moduleScore + userTestAnswer.getSelectedOptions().get(coreCount).getScore();
                                        }
                                    }

                                }
                            }

                            double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                            moduleTestResultInfo.put("testId", moduleTest.getId());
                            moduleTestResultInfo.put("testTitle", moduleTest.getTitle());
                            moduleTestResultInfo.put("passingPercentage", moduleTest.getPassingPercentage());
                            moduleTestResultInfo.put("studentScore", moduleScore);
                            moduleTestResultInfo.put("attemptedQuestions", attempted);
                            moduleTestResultInfo.put("totalQuestions", totalQuestions);
                            moduleTestResultInfo.put("correctAnswers", correctAnswers);
                            moduleTestResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                            moduleTestResultInfo.put("testCompleted", attempted != 0);
                        } else {
                            moduleTestResultInfo.put("testTitle", "No test available");
                            moduleTestResultInfo.put("attemptedQuestions", 0);
                            moduleTestResultInfo.put("totalQuestions", 0);
                            moduleTestResultInfo.put("correctAnswers", 0);
                            moduleTestResultInfo.put("percent", 0.0);
                            moduleTestResultInfo.put("testCompleted", false);
                        }

                        // Modul holati va progress javobi
                        moduleProgressList.add(new ModuleProgressResponse(
                                module.getId(),
                                module.getTitle(),
                                isCompleted,
                                isCompleted ? "1/1" : "0/1",
                                module.getTheme(),
                                topicFiles,
                                moduleTestDto,
                                moduleTestResultInfo
                        ));
                    }

                    // Modul progress foizi
                    double progress = totalModules > 0 ? (doneModules * 100.0 / totalModules) : 0.0;
                    boolean canStartTest = totalModules > 0 && doneModules == totalModules;

                    // Kursning final test natijalari
                    Map<String, Object> finalTestResultInfo = new HashMap<>();
                    Test finalTest = course.getFinalTest();

                    if (finalTest != null) {
                        List<TestQuestion> questions = finalTest.getQuestions();
                        int totalQuestions = questions.size();
                        int correctAnswers = 0;
                        int attempted = 0;
                        int finalScore = 0;

                        for (TestQuestion question : questions) {
                            Optional<UserTestAnswer> userAnswerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                            if (userAnswerOpt.isPresent()) {
                                attempted++;
                                UserTestAnswer userTestAnswer = userAnswerOpt.get();
                                if (userTestAnswer.isCorrect()) {
                                    correctAnswers++;
                                }
                                for (int coreCount = 0; coreCount < userTestAnswer.getSelectedOptions().size(); coreCount++) {
                                    if (userTestAnswer.getSelectedOptions().get(coreCount).getCorrect()) {
                                        finalScore = finalScore + userTestAnswer.getSelectedOptions().get(coreCount).getScore();
                                    }
                                }
                            }
                        }

                        double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                        finalTestResultInfo.put("courseTestId", finalTest.getId());
                        finalTestResultInfo.put("testTitle", finalTest.getTitle());
                        finalTestResultInfo.put("passingPercentage", finalTest.getPassingPercentage());
                        finalTestResultInfo.put("studentFinalScore", finalScore);
                        finalTestResultInfo.put("attemptedQuestions", attempted);
                        finalTestResultInfo.put("totalQuestions", totalQuestions);
                        finalTestResultInfo.put("correctAnswers", correctAnswers);
                        finalTestResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                        finalTestResultInfo.put("testCompleted", attempted != 0);
                    } else {
                        finalTestResultInfo.put("testTitle", "No test available");
                        finalTestResultInfo.put("attemptedQuestions", 0);
                        finalTestResultInfo.put("totalQuestions", 0);
                        finalTestResultInfo.put("correctAnswers", 0);
                        finalTestResultInfo.put("percent", 0.0);
                        finalTestResultInfo.put("testCompleted", false);
                    }

                    // O'qish counterini oshirish
                    studentReadCourseCounterRepository.increaseStudentReadCounter(studentId, course.getId());

                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            Math.round(progress * 100.0) / 100.0,
                            doneModules + "/" + totalModules,
                            moduleProgressList,
                            canStartTest,
                            finalTestResultInfo
                    );
                })
                .collect(Collectors.toList());
//        List<CourseResponse> response = null;
        return new ApiResponse(true, "Courses with module progress", response);
    }


    /*public ApiResponse findByStudentIdAndEducationYearId(String studentId, String educationYearId) {
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
                    List<Module> modules = course.getModules().stream().sorted(Comparator.comparing(Module::getCreatedAt))
                            .collect(Collectors.toList());
                    int totalModules = modules.size();

                    List<ModuleProgressResponse> moduleProgressList = new ArrayList<>();
                    int doneModules = 0;

                    for (Module module : modules) {
                        boolean isCompleted = userLessonModuleProgressRepository
                                .existsByUserAndModuleAndCompletedTrue(user, module);
                        if (isCompleted) doneModules++;

                        List<TopicFileOfLineV2Dto> topicFiles = new ArrayList<>();

                        List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
                        boolean isTopicFilesEmpty = topics.isEmpty();
                        if (!isTopicFilesEmpty&&topics.size()>0) {
                            topics.forEach(tf -> {
                                topicFiles.add(new TopicFileOfLineV2Dto(
                                        tf.getName(),
                                        tf.getFileType(),
                                        tf.getContentType(),
                                        tf.getPackageName()
                                ));
                            });
                        }

                        Test moduleTest = module.getModuleTest();
                        ModuleTestDto moduleTestDto = null;
                        Map<String, Object> testResultInfo = new HashMap<>();

                        if (moduleTest != null) {
                            moduleTestDto = new ModuleTestDto(
                                    moduleTest.getId(),
                                    moduleTest.getTitle(),
                                    moduleTest.getQuestions() != null ? moduleTest.getQuestions().size() : 0
                            );

                            List<TestQuestion> questions = moduleTest.getQuestions();
                            int totalQuestions = questions.size();
                            int correctAnswers = 0;
                            int attempted = 0;

                            for (TestQuestion question : questions) {
                                Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                                if (answerOpt.isPresent()) {
                                    attempted++;
                                    if (answerOpt.get().isCorrect()) {
                                        correctAnswers++;
                                    }
                                }
                            }

                            double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                            testResultInfo.put("testId", moduleTest.getId());
                            testResultInfo.put("testTitle", moduleTest.getTitle());
                            testResultInfo.put("passingPercentage", moduleTest.getPassingPercentage());
                            testResultInfo.put("attemptedQuestions", attempted);
                            testResultInfo.put("totalQuestions", totalQuestions);
                            testResultInfo.put("correctAnswers", correctAnswers);
                            testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                            testResultInfo.put("testCompleted", attempted != 0);
                        } else {
                            testResultInfo.put("testTitle", "No test available");
                            testResultInfo.put("attemptedQuestions", 0);
                            testResultInfo.put("totalQuestions", 0);
                            testResultInfo.put("correctAnswers", 0);
                            testResultInfo.put("percent", 0.0);
                            testResultInfo.put("testCompleted", false);
                        }




                        moduleProgressList.add(new ModuleProgressResponse(
                                module.getId(),
                                module.getTitle(),
                                isCompleted,
                                isCompleted ? "1/1" : "0/1",
                                module.getTheme(),
                                topicFiles,
                                moduleTestDto, // ✅ qo‘shildi
                                testResultInfo // ✅ qo‘shildi
                        ));
                    }

                    double progress = totalModules > 0 ? (doneModules * 100.0 / totalModules) : 0.0;
                    boolean canStartTest = totalModules > 0 && doneModules == totalModules;

                    // ✅ TEST NATIJALARINI OLIB BORISH
                    Map<String, Object> testResultInfo = new HashMap<>();

                    Test test = course.getFinalTest();
                    if (test != null) {
                        List<TestQuestion> questions = test.getQuestions();
                        int totalQuestions = questions.size();

                        if (totalQuestions > 0) {
                            int correctAnswers = 0;
                            int attempted = 0;

                            System.out.println("-----------------TEST RESULTS-----------------");

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

                            testResultInfo.put("courseTestId", test.getId());
                            testResultInfo.put("testTitle", test.getTitle());
                            testResultInfo.put("passingPercentage", test.getPassingPercentage());
                            testResultInfo.put("attemptedQuestions", attempted);
                            testResultInfo.put("totalQuestions", totalQuestions);
                            testResultInfo.put("correctAnswers", correctAnswers);
                            testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                            testResultInfo.put("testCompleted", attempted!=0);
                        }
                    }
                    else {
                        testResultInfo.put("testTitle", "No test available");
                        testResultInfo.put("attemptedQuestions", 0);
                        testResultInfo.put("totalQuestions", 0);
                        testResultInfo.put("correctAnswers", 0);
                        testResultInfo.put("percent", 0.0);
                        testResultInfo.put("testCompleted", false);
                    }

                    //todo------------- counterni oshirish -------------------
                    studentReadCourseCounterRepository.increaseStudentReadCounter(studentId, course.getId());

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
    }*/

    public ApiResponse getCourseProgressForGroup(String groupId, String courseId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new UserNotFoundException("Group not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new UserNotFoundException("Course not found"));

        List<Student> students = studentRepository.findAllByGroupId(groupId);
        List<Module> modules = course.getModules().stream().sorted(Comparator.comparing(Module::getCreatedAt))
                .collect(Collectors.toList());
        int totalModules = modules.size();

        // Barcha modullar bo‘yicha progressni oldindan yuklab olish
        List<UserLessonModuleProgress> allProgress = userLessonModuleProgressRepository
                .findAllByModuleInAndCompletedTrue(modules);

        // StudentID + ModuleID kombinatsiyasi bo‘yicha completed map
        Set<String> completedMap = allProgress.stream()
                .map(p -> p.getUser().getId() + "_" + p.getModule().getId())
                .collect(Collectors.toSet());

        // Barcha test javoblarini oldindan yuklab olish
        List<TestQuestion> testQuestions = course.getFinalTest()!=null ? course.getFinalTest().getQuestions() : Collections.emptyList();
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

                List<TopicFileOfLineV2Dto> topicFiles = new ArrayList<>();

                List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
                boolean isTopicFilesEmpty = topics.isEmpty();
                if (!isTopicFilesEmpty&&topics.size()>0) {
                    topics.forEach(tf -> {
                        topicFiles.add(new TopicFileOfLineV2Dto(
                                tf.getName(),
                                tf.getFileType(),
                                tf.getContentType(),
                                tf.getPackageName()
                        ));
                    });
                }
                Test moduleTest = module.getModuleTest();
                ModuleTestDto moduleTestDto = null;
                Map<String, Object> testResultInfo = new HashMap<>();

                if (moduleTest != null) {
                    moduleTestDto = new ModuleTestDto(
                            moduleTest.getId(),
                            moduleTest.getTitle(),
                            moduleTest.getQuestions() != null ? moduleTest.getQuestions().size() : 0
                    );

                    List<TestQuestion> questions = moduleTest.getQuestions();
                    int totalQuestions = questions.size();
                    int correctAnswers = 0;
                    int attempted = 0;

                    for (TestQuestion question : questions) {
                        Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                        if (answerOpt.isPresent()) {
                            attempted++;
                            if (answerOpt.get().isCorrect()) {
                                correctAnswers++;
                            }
                        }
                    }

                    double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                    testResultInfo.put("testId", moduleTest.getId());
                    testResultInfo.put("testTitle", moduleTest.getTitle());
                    testResultInfo.put("attemptedQuestions", attempted);
                    testResultInfo.put("totalQuestions", totalQuestions);
                    testResultInfo.put("correctAnswers", correctAnswers);
                    testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                    testResultInfo.put("testCompleted", attempted != 0);
                } else {
                    testResultInfo.put("testTitle", "No test available");
                    testResultInfo.put("attemptedQuestions", 0);
                    testResultInfo.put("totalQuestions", 0);
                    testResultInfo.put("correctAnswers", 0);
                    testResultInfo.put("percent", 0.0);
                    testResultInfo.put("testCompleted", false);
                }


                moduleProgressList.add(new ModuleProgressResponse(
                        module.getId(),
                        module.getTitle(),
                        isCompleted,
                        isCompleted ? "1/1" : "0/1",
                        module.getTheme(),
                        topicFiles,
                        moduleTestDto, // ✅ qo‘shildi
                        testResultInfo // ✅ qo‘shildi
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

                testResultInfo.put("testTitle", course.getFinalTest().getTitle());
                testResultInfo.put("attemptedQuestions", attempted);
                testResultInfo.put("totalQuestions", total);
                testResultInfo.put("correctAnswers", correct);
                testResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                testResultInfo.put("testCompleted", attempted!=0);
            } else {
                testResultInfo.put("testTitle", "No test or not completed");
                testResultInfo.put("attemptedQuestions", 0);
                testResultInfo.put("totalQuestions", 0);
                testResultInfo.put("correctAnswers", 0);
                testResultInfo.put("percent", 0.0);
                testResultInfo.put("testCompleted", false);
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

    @Override
    public ApiResponse getGroups(String facultyId, String courseId) {
        return new ApiResponse(true, "Groups for course",
                courseRepository.getGroups(courseId, facultyId));
    }


}
