package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testQuestion;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestQuestionImplService implements TestQuestionService {
    private final CourseTestRepository courseTestRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final TestOptionRepository testOptionRepository;
    private final CourseTestRepository testRepository;
    private final UserRepository userRepository;
    private final UserTestAnswerRepository userTestAnswerRepository;
    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;

    public TestQuestionImplService(CourseTestRepository courseTestRepository, TestQuestionRepository testQuestionRepository, TestOptionRepository testOptionRepository, CourseTestRepository testRepository, UserRepository userRepository, UserTestAnswerRepository userTestAnswerRepository, ModuleRepository moduleRepository, CourseRepository courseRepository, UserLessonModuleProgressRepository userLessonModuleProgressRepository) {
        this.courseTestRepository = courseTestRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.testOptionRepository = testOptionRepository;
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
    }


    @Deprecated
    @Override
    @Transactional
    public ApiResponse create(TestQuestionCreator creator) {
        if (!courseTestRepository.existsById(creator.courseTestId)) {
            return new ApiResponse(false,"Course test not found by id="+creator.courseTestId);
        }
        Integer exists = testQuestionRepository.existsByNormalizedQuestionText(creator.questionText);
        if (exists != null && exists == 1) {
            return new ApiResponse(false, "Bunday savol allaqachon mavjud!");
        }

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestionText(creator.questionText);
//        testQuestion.setOptions(creator.options);
//        testQuestion.setCorrectAnswers(creator.correctAnswers);
        testQuestion.setTest(courseTestRepository.getById(creator.courseTestId));
        testQuestion.setType(creator.type);
        TestQuestion save = testQuestionRepository.save(testQuestion);
        return new ApiResponse(true,"Test Question created successfully",save.getId());
    }

    @Override
    @Transactional
    public ApiResponse createV2(TestQuestionCreatorV2 creator) {

        if (!courseTestRepository.existsById(creator.courseTestId)) {
            return new ApiResponse(false,"Course test not found by id="+creator.courseTestId);
        }
        Integer exists = testQuestionRepository.existsByNormalizedQuestionText(creator.questionText);
        if (exists != null && exists == 1) {
            return new ApiResponse(false, "Bunday savol allaqachon mavjud!");
        }

        Set<String> normalizedTexts = new HashSet<>();
        for (TestOptionCreatorV2 option : creator.getOptions()) {
            String normalized = option.getText().trim().toLowerCase();
            if (!normalizedTexts.add(normalized)) {
                return new ApiResponse(false, "Option '" + option.getText() + "' takrorlangan (ignoring case and spaces)");
            }
        }

        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setQuestionText(creator.questionText);
        testQuestion.setTest(courseTestRepository.getById(creator.courseTestId));
        testQuestion.setType(creator.type);
        TestQuestion save = testQuestionRepository.save(testQuestion);

        creator.options.forEach(option -> {
            TestOption testOption = new TestOption();
            testOption.setQuestion(save);
            testOption.setText(option.text);
            testOption.setCorrect(option.isCorrect);
            testOption.setScore(option.score);
            testOptionRepository.save(testOption);
        });

        return new ApiResponse(true, "Test savoli va variantlar muvaffaqiyatli saqlandi");
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Course Test",testQuestionRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String testId) {
        TestQuestion testQuestion = testQuestionRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test question not found by id="+testId));
        return new ApiResponse(true,"Test question by id="+testId,testQuestion);
    }

    @Override
    public boolean deleteById(String testQuestionId) {
        TestQuestion testQuestion = testQuestionRepository.findById(testQuestionId)
                .orElseThrow(() -> new RuntimeException("Test question not found by id=" + testQuestionId));
        testOptionRepository.deleteAll(testQuestion.getOptions());
//        // qo‘lda options’ni o‘chirib yuborish
//        testQuestion.setOptions(new ArrayList<>()); // yoki null
////        testQuestion.setCorrectAnswers(new ArrayList<>()); // yoki null
//        testQuestionRepository.save(testQuestion); // majburiy emas, lekin ehtiyot chorasi

        testQuestionRepository.delete(testQuestion);
        return true;
    }

    @Override
    public ApiResponse findTestQuestionsByCourseIdWithShuffledOptions(String userId, String testId) {
        // ⚠️ Avval tekshiruvdan o'tkazamiz
        try {
            validateTestAccess(testId, userId);
        } catch (RuntimeException e) {
            return new ApiResponse(false, e.getMessage());
        }

        // Test savollarini olish
        List<TestQuestion> testQuestions = testQuestionRepository.findAllByTestId(testId);
        if (testQuestions.isEmpty()) {
            return new ApiResponse(false, "Test questions not found by test id=" + testId);
        }

        Set<TestQuestionResponseV2> collect = testQuestions.stream().map(test -> {
            List<TestOptionResponseV2> shuffledOptions = new ArrayList<>(
                    test.getOptions().stream()
                            .map(option -> new TestOptionResponseV2(option.getId(), option.getText()))
                            .collect(Collectors.toList())
            );
            Collections.shuffle(shuffledOptions);
            return new TestQuestionResponseV2(
                    test.getId(),
                    test.getType(),
                    test.getTest().getId(),
                    test.getQuestionText(),
                    shuffledOptions
            );
        }).collect(Collectors.toSet());

        return new ApiResponse(true, "Test questions by test id=" + testId, collect);
    }


    public void validateTestAccess(String testId, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));

        Module module = moduleRepository.findByModuleTestId(testId);
        Course course = null;
        List<Module> modules;

        if (module != null) {
            // Modul testiga tegishli
            course = module.getCourse();

            modules = course.getModules().stream()
                    .sorted(Comparator.comparing(Module::getCreatedAt))
                    .collect(Collectors.toList());

            int index = -1;
            for (int i = 0; i < modules.size(); i++) {
                if (modules.get(i).getId().equals(module.getId())) {
                    index = i;
                    break;
                }
            }

            if (index == -1) {
                throw new RuntimeException("Current module not found in course modules");
            }

            // Oldingi modullarni tekshiramiz
            for (int i = 0; i < index; i++) {
                checkModuleCompletedAndPassed(user, modules.get(i));
            }

            // 🔴 Yangi qo‘shiladigan joy — test o‘zini ham tekshirish
            checkModuleTestRetakeAccess(user, module);

        } else {
            // Final test bo‘lsa — barcha modullarni tekshiramiz
            course = courseRepository.findByFinalTestId(testId);
            if (course == null) {
                throw new RuntimeException("Test not linked to any module or course");
            }

            modules = course.getModules().stream()
                    .sorted(Comparator.comparing(Module::getCreatedAt))
                    .collect(Collectors.toList());

            for (Module m : modules) {
                checkModuleCompletedAndPassed(user, m);
            }
        }
    }

    private void checkModuleCompletedAndPassed(User user, Module module) {
        boolean completed = userLessonModuleProgressRepository
                .existsByUserIdAndModuleIdAndCompletedTrue(user.getId(), module.getId());

        if (!completed) {
            throw new RuntimeException("You must complete module: " + module.getTitle());
        }

        Test mTest = module.getModuleTest();
        if (mTest != null && !mTest.getQuestions().isEmpty()) {
            int correct = 0;
            int total = mTest.getQuestions().size();
            boolean allAnswered = true;

            for (TestQuestion question : mTest.getQuestions()) {
                Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
                if (!answerOpt.isPresent()) {
                    allAnswered = false;
                    break;
                }
                if (answerOpt.get().isCorrect()) {
                    correct++;
                }
            }

            if (!allAnswered) {
                throw new RuntimeException("You must complete the test for module: " + module.getTitle());
            }

            double percent = ((double) correct / total) * 100;
            if (percent < mTest.getPassingPercentage()) {
                throw new RuntimeException("Test for module \"" + module.getTitle()
                        + "\" not passed. Required: " + mTest.getPassingPercentage()
                        + "%, but got: " + percent + "%");
            }
        }
    }

    private void checkModuleTestRetakeAccess(User user, Module module) {
        Test test = module.getModuleTest();
        if (test == null || test.getQuestions().isEmpty()) {
            return; // test yo‘q — tekshirmaymiz
        }

        int correct = 0;
        int total = test.getQuestions().size();
        boolean allAnswered = true;

        for (TestQuestion question : test.getQuestions()) {
            Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);
            if (!answerOpt.isPresent()) {
                allAnswered = false;
                break;
            }
            if (answerOpt.get().isCorrect()) {
                correct++;
            }
        }

        if (allAnswered) {
            double percent = ((double) correct / total) * 100;
            if (percent < test.getPassingPercentage()) {
                throw new RuntimeException("Test for module \"" + module.getTitle()
                        + "\" not passed. Required: " + test.getPassingPercentage()
                        + "%, but got: " + percent + "%");
            }
        }
    }


    @Override
    public ApiResponse findByCourseTestId(String courseTestId) {
        List<TestQuestion> testQuestions = testQuestionRepository.findAllByTestId(courseTestId);
        if (testQuestions.isEmpty()) {
            return new ApiResponse(false, "Test questions not found by course test id=" + courseTestId);
        }
        Set<TestQuestionResponse> collect = testQuestions.stream().map(test -> {
            return new TestQuestionResponse(
                    test.getId(),
                    test.getTest().getId(),
                    test.getQuestionText(),
                    test.getType(),
                    test.getOptions().stream().map(option -> new TestOptionResponse(
                            option.getId(),
                            option.getText(),
                            option.getScore(),
                            option.getCorrect()
                    )).collect(Collectors.toList())
            );
        }).collect(Collectors.toSet());
        return new ApiResponse(true, "Test questions by course test id=" + courseTestId, collect);
    }

    @Override
    public ApiResponse getStudentCourseTestAnswers(String testId, String studentId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found"));

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TestQuestionAnswerDto> result = new ArrayList<>();

        for (TestQuestion question : test.getQuestions()) {
            Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);

            List<TestOptionDto> optionDtos = question.getOptions().stream()
                    .map(option -> {
                        boolean selected = answerOpt.isPresent() &&
                                answerOpt.get().getSelectedOptions() != null &&
                                answerOpt.get().getSelectedOptions().stream()
                                        .anyMatch(opt -> opt.getId().equals(option.getId()));
                        return new TestOptionDto(
                                option.getId(),
                                option.getText(),
                                option.getCorrect(),
                                option.getScore(),
                                selected
                        );
                    })
                    .collect(Collectors.toList());

            String userTestAnswerId = null;
            String writtenAnswer = null;
            Boolean isCorrect = false;
            Integer score = 0;
            Boolean teacherChecked = false;

            if (answerOpt.isPresent()) {
                UserTestAnswer answer = answerOpt.get();
                userTestAnswerId = answer.getId();
                writtenAnswer = answer.getWrittenAnswer();
                isCorrect = answer.isCorrect();
                score = answer.getScore() != null ? answer.getScore() : 0;
                teacherChecked = !Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy());
            }

            result.add(new TestQuestionAnswerDto(
                    userTestAnswerId,
                    question.getId(),
                    question.getQuestionText(),
                    question.getType().name(),
                    optionDtos,
                    writtenAnswer,
                    isCorrect,
                    score,
                    teacherChecked
            ));
        }

        return new ApiResponse(true, "Student's course test answers with all options", result);
    }

    @Override
    @Transactional
    public ApiResponse giveScoreToWrittenUserAnswer(GiveScoreToWrittenUserAnswerDto dto) {
        UserTestAnswer userTestAnswer = userTestAnswerRepository.findById(dto.getUserTestAnswerId())
                .orElseThrow(() -> new RuntimeException("User Test Answer not found"));
        /*if (userTestAnswer.getWrittenAnswer() == null || userTestAnswer.getWrittenAnswer().isEmpty()) {
            return new ApiResponse(false, "User Test Answer has no written answer to score");
        } else */if (userTestAnswer.getScore() != null && !userTestAnswer.getCreatedBy().equals(userTestAnswer.getUpdatedBy())) {
            return new ApiResponse(false, "User Test Answer already has a score");
        } else {
            userTestAnswer.setScore(dto.getScore());
            return new ApiResponse(true, "Score given to User Test Answer", userTestAnswerRepository.save(userTestAnswer));
        }
    }

}
