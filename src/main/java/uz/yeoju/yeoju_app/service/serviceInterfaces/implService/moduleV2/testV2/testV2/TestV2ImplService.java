package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.TestType;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleProgressResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleTestDto;
import uz.yeoju.yeoju_app.payload.moduleV2.TopicFileOfLineV2Dto;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Creator;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.*;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestV2ImplService implements TestV2Service {
    private final TestV2Repository testV2Repository;
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final TopicFileOfLineV2Repository topicFileOfLineV2Repository;
    private final TestQuestionV2Repository testQuestionV2Repository;
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;
    private final StudentReadCourseCounterRepository studentReadCourseCounterRepository;

    public TestV2ImplService(TestV2Repository testV2Repository, ModuleRepository moduleRepository, CourseRepository courseRepository, StudentRepository studentRepository, UserLessonModuleProgressRepository userLessonModuleProgressRepository, TopicFileOfLineV2Repository topicFileOfLineV2Repository, TestQuestionV2Repository testQuestionV2Repository, UserTestAnswerV2Repository userTestAnswerV2Repository, StudentReadCourseCounterRepository studentReadCourseCounterRepository) {
        this.testV2Repository = testV2Repository;
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.topicFileOfLineV2Repository = topicFileOfLineV2Repository;
        this.testQuestionV2Repository = testQuestionV2Repository;
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
        this.studentReadCourseCounterRepository = studentReadCourseCounterRepository;
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
        }
        else if (!moduleRepository.existsById(creator.getModuleId())) {
            return new ApiResponse(false, "Module with ID " + creator.getModuleId() + " does not exist");
        }
        else if (testV2Repository.existsByCourseId(creator.getModuleId())) {
            return new ApiResponse(false, "Test already exists for the module with ID " + creator.getModuleId());
        }
        else {
            testV2.setModule(moduleRepository.findById(creator.getModuleId()).orElseThrow(() -> new IllegalArgumentException("Module not found")));
            testV2Repository.save(testV2);
            return new ApiResponse(true, "Test created successfully for module with ID " + creator.getModuleId());
        }
    }


    @Override
    public ApiResponse findByStudentIdAndEducationYearIdV2(String studentId, String educationYearId) {
        Student student = studentRepository.findStudentByUserId(studentId);
        if (student == null) {
            throw new UserNotFoundException("Student not found by userId: " + studentId);
        }

        User user = student.getUser();

        List<CourseResponse> response = courseRepository
                .getCourseByStudentIdAndEducationYearId(user.getId(), educationYearId)
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

                        List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
                        List<TopicFileOfLineV2Dto> topicFiles = topics.stream()
                                .map(tf -> new TopicFileOfLineV2Dto(tf.getName(), tf.getFileType(), tf.getContentType(), tf.getPackageName()))
                                .collect(Collectors.toList());

                        TestV2 moduleTest = testV2Repository.findByModuleId(module.getId());
                        ModuleTestDto moduleTestDto = null;
                        Map<String, Object> moduleTestResultInfo = new HashMap<>();

                        if (moduleTest != null) {
                            List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(user.getId(), moduleTest.getId());
                            List<TestQuestionV2> questions = answers.stream().map(UserTestAnswerV2::getQuestionV2).collect(Collectors.toList());

                            moduleTestDto = new ModuleTestDto(
                                    moduleTest.getId(),
                                    moduleTest.getTitle(),
                                    questions.size()
                            );

                            int totalQuestions = questions.size();
                            int correctAnswers = 0;
                            int attempted = 0;
                            int moduleScore = 0;

                            for (UserTestAnswerV2 answer : answers) {
                                TestQuestionV2 question = answer.getQuestionV2();
                                attempted++;
                                if (question.getType() == TestType.WRITTEN) {
                                    if (!Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy())) {
                                        moduleScore += answer.getScore() != null ? answer.getScore() : 0;
                                    }
                                } else if (answer.isCorrect()) {
                                    correctAnswers++;
                                    if (question.getType() == TestType.SINGLE_CHOICE) {
                                        moduleScore += moduleTest.getSingleChoiceBall();
                                    } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                                        moduleScore += moduleTest.getMultipleChoiceBall();
                                    }
                                }
                            }

                            double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                            moduleTestResultInfo.put("testId", moduleTest.getId());
                            moduleTestResultInfo.put("testTitle", moduleTest.getTitle());
                            moduleTestResultInfo.put("passingPercentage", moduleTest.getPassingBall());
                            moduleTestResultInfo.put("studentScore", moduleScore);
                            moduleTestResultInfo.put("attemptedQuestions", attempted);
                            moduleTestResultInfo.put("totalQuestions", totalQuestions);
                            moduleTestResultInfo.put("correctAnswers", correctAnswers);
                            moduleTestResultInfo.put("percent", Math.round(percent * 100.0) / 100.0);
                            moduleTestResultInfo.put("testCompleted", attempted != 0);
                        }

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

                    boolean allModulesPassed = modules.stream().allMatch(module -> {
                        boolean isCompleted = userLessonModuleProgressRepository.existsByUserAndModuleAndCompletedTrue(user, module);
                        if (!isCompleted) return false;

                        TestV2 testV2 = testV2Repository.findByModuleId(module.getId());
                        if (testV2 == null) return true;

                        List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(user.getId(), testV2.getId());
                        int totalQuestions = answers.size();
                        int correctAnswers = (int) answers.stream()
                                .filter(answer -> {
                                    TestQuestionV2 q = answer.getQuestionV2();
                                    return q != null && answer.isCorrect();
                                })
                                .count();

                        double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;
                        return percent >= testV2.getPassingBall();
                    });

                    boolean canStartTest = totalModules > 0 && allModulesPassed;

                    Map<String, Object> finalTestResultInfo = new HashMap<>();
                    TestV2 finalTest = testV2Repository.findByCourseId(course.getId());

                    if (finalTest != null) {
                        List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(user.getId(), finalTest.getId());
                        List<TestQuestionV2> questions = answers.stream().map(UserTestAnswerV2::getQuestionV2).collect(Collectors.toList());

                        int totalQuestions = questions.size();
                        int correctAnswers = 0;
                        int attempted = 0;
                        int finalScore = 0;
                        boolean teacherDidNotCheck = false;

                        for (UserTestAnswerV2 answer : answers) {
                            TestQuestionV2 question = answer.getQuestionV2();
                            attempted++;

                            if (question.getType() == TestType.WRITTEN) {
                                if (Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy())) {
                                    teacherDidNotCheck = true;
                                    continue;
                                }
                                finalScore += answer.getScore() != null ? answer.getScore() : 0;
                            } else if (answer.isCorrect()) {
                                correctAnswers++;
                                if (question.getType() == TestType.SINGLE_CHOICE) {
                                    finalScore += finalTest.getSingleChoiceBall();
                                } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                                    finalScore += finalTest.getMultipleChoiceBall();
                                }
                            }
                        }

                        double percent = totalQuestions > 0 ? (correctAnswers * 100.0 / totalQuestions) : 0.0;

                        finalTestResultInfo.put("teacherDidNotCheck", teacherDidNotCheck);
                        finalTestResultInfo.put("courseTestId", finalTest.getId());
                        finalTestResultInfo.put("testTitle", finalTest.getTitle());
                        finalTestResultInfo.put("passingPercentage", finalTest.getPassingBall());
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
                        finalTestResultInfo.put("teacherDidNotCheck", true);
                    }

                    studentReadCourseCounterRepository.increaseStudentReadCounter(studentId, course.getId());

                    return new CourseResponse(
                            course.getId(),
                            course.getTitle(),
                            Math.round((doneModules * 100.0 / (totalModules == 0 ? 1 : totalModules)) * 100.0) / 100.0,
                            doneModules + "/" + totalModules,
                            moduleProgressList,
                            canStartTest,
                            finalTestResultInfo
                    );
                })
                .collect(Collectors.toList());

        return new ApiResponse(true, "Courses with module progress V2", response);
    }


}
