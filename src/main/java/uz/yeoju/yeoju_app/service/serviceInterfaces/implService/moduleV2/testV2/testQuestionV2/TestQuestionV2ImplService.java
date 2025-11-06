package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestOptionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.GetCountOfQuestionsWithType;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestOptionV2Creator;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestQuestionV2Creator;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestOptionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestQuestionV2ImplService implements TestQuestionV2Service{
    private final TestQuestionV2Repository testQuestionV2Repository;
    private final TestOptionV2Repository testOptionV2Repository;
    private final CourseRepository courseRepository;
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;
    private final UserRepository userRepository;
    private final TestV2Repository testV2Repository;
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final ModuleRepository moduleRepository;

    public TestQuestionV2ImplService(TestQuestionV2Repository testQuestionV2Repository, TestOptionV2Repository testOptionV2Repository, CourseRepository courseRepository, UserTestAnswerV2Repository userTestAnswerV2Repository, UserRepository userRepository, TestV2Repository testV2Repository, UserLessonModuleProgressRepository userLessonModuleProgressRepository, ModuleRepository moduleRepository) {
        this.testQuestionV2Repository = testQuestionV2Repository;
        this.testOptionV2Repository = testOptionV2Repository;
        this.courseRepository = courseRepository;
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
        this.userRepository = userRepository;
        this.testV2Repository = testV2Repository;
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    @Transactional
    public ApiResponse createTestQuestionV2(TestQuestionV2Creator creator) {
        if (creator.getCourseId() == null || creator.getCourseId().isEmpty()) {
            throw new IllegalArgumentException("Course ID cannot be null or empty");
        }
        if (!courseRepository.existsById(creator.getCourseId())) {
            throw new IllegalArgumentException("Course with ID " + creator.getCourseId() + " does not exist");
        }
        if (creator.getQuestionText() == null || creator.getQuestionText().isEmpty()) {
            throw new IllegalArgumentException("Question text cannot be null or empty");
        }
        if (creator.getOptions() == null || creator.getOptions().isEmpty()) {
            throw new IllegalArgumentException("Options cannot be null or empty");
        }

        Set<String> normalizedTexts = new HashSet<>();
        for (TestOptionV2Creator option : creator.getOptions()) {
            String normalized = option.getText().trim().toLowerCase();
            if (!normalizedTexts.add(normalized)) {
                return new ApiResponse(false, "Option '" + option.getText() + "' takrorlangan (ignoring case and spaces)");
            }
        }

        TestQuestionV2 testQuestionV2 = new TestQuestionV2();
        testQuestionV2.setQuestionText(creator.getQuestionText());
        testQuestionV2.setType(creator.getType());
        testQuestionV2.setCourse(courseRepository.findById(creator.getCourseId()).orElseThrow(() -> new IllegalArgumentException("Course not found")));
        if (creator.moduleId != null && !creator.moduleId.isEmpty()) {
            if (!moduleRepository.existsById(creator.moduleId)) {
                throw new IllegalArgumentException("Module with ID " + creator.moduleId + " does not exist");
            }
            else {
                Module module = moduleRepository.findById(creator.moduleId).orElseThrow(() -> new IllegalArgumentException("Module not found"));
                testQuestionV2.setModule(module);
            }
        }
        TestQuestionV2 save = testQuestionV2Repository.save(testQuestionV2);

        creator.options.forEach(option -> {
            TestOptionV2 testOptionV2 = new TestOptionV2();
            testOptionV2.setText(option.getText());
            testOptionV2.setCorrect(option.isCorrect());
            testOptionV2.setQuestionV2(save);
            testOptionV2Repository.save(testOptionV2);
        });

        return new ApiResponse(true, "Test savoli va variantlar muvaffaqiyatli saqlandi");
    }

    @Override
    @Transactional
    public ApiResponse updateTestQuestionV2(String questionId, TestQuestionV2Creator creator) {
        // 1. Check existence
        Optional<TestQuestionV2> optionalQuestion = testQuestionV2Repository.findById(questionId);
        if (!optionalQuestion.isPresent()) {
            return new ApiResponse(false, "Test question with ID " + questionId + " not found");
        }

        TestQuestionV2 question = optionalQuestion.get();

        // 2. Validate and update question fields
        if (creator.getQuestionText() != null && !creator.getQuestionText().isEmpty()) {
            question.setQuestionText(creator.getQuestionText());
        }

        if (creator.getType() != null) {
            question.setType(creator.getType());
        }

        if (creator.getCourseId() != null && !creator.getCourseId().isEmpty()) {
            if (!courseRepository.existsById(creator.getCourseId())) {
                return new ApiResponse(false, "Course with ID " + creator.getCourseId() + " does not exist");
            }
            question.setCourse(courseRepository.findById(creator.getCourseId()).get());
        }

        if (creator.getModuleId() != null && !creator.getModuleId().isEmpty()) {
            if (!moduleRepository.existsById(creator.getModuleId())) {
                return new ApiResponse(false, "Module with ID " + creator.getModuleId() + " does not exist");
            }
            question.setModule(moduleRepository.findById(creator.getModuleId()).get());
        }

        // 3. Check for duplicate options (case-insensitive)
        Set<String> normalizedTexts = new HashSet<>();
        for (TestOptionV2Creator option : creator.getOptions()) {
            String normalized = option.getText().trim().toLowerCase();
            if (!normalizedTexts.add(normalized)) {
                return new ApiResponse(false, "Option '" + option.getText() + "' is duplicated (ignoring case/spaces)");
            }
        }

        // 4. Save updated question (without touching options yet)
        testQuestionV2Repository.save(question);

        // 5. Map existing options by normalized text
        Map<String, TestOptionV2> existingOptionMap = question.getOptions()
                .stream()
                .collect(Collectors.toMap(o -> o.getText().trim().toLowerCase(), o -> o));

        // 6. Update existing options or create new ones
        for (TestOptionV2Creator optionCreator : creator.getOptions()) {
            String normalized = optionCreator.getText().trim().toLowerCase();

            if (existingOptionMap.containsKey(normalized)) {
                // update existing option
                TestOptionV2 option = existingOptionMap.get(normalized);
                option.setCorrect(optionCreator.isCorrect());
                testOptionV2Repository.save(option);
            } else {
                // create new option
                TestOptionV2 newOption = new TestOptionV2();
                newOption.setText(optionCreator.getText());
                newOption.setCorrect(optionCreator.isCorrect());
                newOption.setQuestionV2(question);
                testOptionV2Repository.save(newOption);
            }
        }

        return new ApiResponse(true, "Test savoli va variantlari muvaffaqiyatli yangilandi");
    }


//    @Override
//    @Transactional
//    public ApiResponse updateTestQuestionV2(String questionId, TestQuestionV2Creator creator) {
//        // 1. Check existence
//        Optional<TestQuestionV2> optionalQuestion = testQuestionV2Repository.findById(questionId);
//        if (!optionalQuestion.isPresent()) {
//            return new ApiResponse(false, "Test question with ID " + questionId + " not found");
//        }
//
//        TestQuestionV2 question = optionalQuestion.get();
//
//        // 2. Validate new data
//        if (creator.getQuestionText() != null && !creator.getQuestionText().isEmpty()) {
//            question.setQuestionText(creator.getQuestionText());
//        }
//
//        if (creator.getType() != null) {
//            question.setType(creator.getType());
//        }
//
//        if (creator.getCourseId() != null && !creator.getCourseId().isEmpty()) {
//            if (!courseRepository.existsById(creator.getCourseId())) {
//                return new ApiResponse(false, "Course with ID " + creator.getCourseId() + " does not exist");
//            }
//            question.setCourse(courseRepository.findById(creator.getCourseId()).orElseThrow(null));
//        }
//
//        if (creator.getModuleId() != null && !creator.getModuleId().isEmpty()) {
//            if (!moduleRepository.existsById(creator.getModuleId())) {
//                return new ApiResponse(false, "Module with ID " + creator.getModuleId() + " does not exist");
//            }
//            question.setModule(moduleRepository.findById(creator.getModuleId()).orElseThrow(null));
//        }
//
//        // 3. Check option duplication (case and space insensitive)
//        Set<String> normalizedTexts = new HashSet<>();
//        for (TestOptionV2Creator option : creator.getOptions()) {
//            String normalized = option.getText().trim().toLowerCase();
//            if (!normalizedTexts.add(normalized)) {
//                return new ApiResponse(false, "Option '" + option.getText() + "' is duplicated (ignoring case/spaces)");
//            }
//        }
//
//        // 4. Save updated question
//        testQuestionV2Repository.save(question);
//
//        // Step 5.1: Delete selectedOptions references for this question
//        userTestAnswerV2Repository.deleteAllSelectedOptionsByQuestionId(question.getId());
//
//        // 5. Delete old options
//        testOptionV2Repository.deleteAllByQuestionV2Id(question.getId());
//
//
//
//
//        // 6. Save new options
//        for (TestOptionV2Creator optionCreator : creator.getOptions()) {
//            TestOptionV2 option = new TestOptionV2();
//            option.setText(optionCreator.getText());
//            option.setCorrect(optionCreator.isCorrect());
//            option.setQuestionV2(question);
//            testOptionV2Repository.save(option);
//        }
//
//        return new ApiResponse(true, "Test savoli va variantlari muvaffaqiyatli yangilandi");
//    }


    @Override
    public ApiResponse checkEnoughQuestions(String courseId, int count, TestType type) {
        if (courseId == null || courseId.isEmpty()) {
            return new ApiResponse(false, "Course ID cannot be null or empty");
        }
        if (!courseRepository.existsById(courseId)) {
            return new ApiResponse(false, "Course with ID " + courseId + " does not exist");
        }

        long questionCount = testQuestionV2Repository.countByCourseIdAndType(courseId, type);
        if (questionCount < count) {
            return new ApiResponse(false, "Insufficient questions for the specified type. Required: " + count + ", Available: " + questionCount);
        }
        return new ApiResponse(true, "Sufficient questions available for the specified type");
    }

    @Override
    public ApiResponse checkEnoughQuestionsForModule(String moduleId, int count, TestType type) {
        if (moduleId == null || moduleId.isEmpty()) {
            return new ApiResponse(false, "Module ID cannot be null or empty");
        }
        if (!moduleRepository.existsById(moduleId)) {
            return new ApiResponse(false, "Module with ID " + moduleId + " does not exist");
        }

        long questionCount = testQuestionV2Repository.countByModuleIdAndType(moduleId, type);
        if (questionCount < count) {
            return new ApiResponse(false, "Insufficient questions for the specified type in the module. Required: " + count + ", Available: " + questionCount);
        }
        return new ApiResponse(true, "Sufficient questions available for the specified type in the module");
    }

    @Override
    public ApiResponse getCountOfTypeOfQuestionsByCourseId(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return new ApiResponse(false, "Course ID cannot be null or empty");
        }
        if (!courseRepository.existsById(courseId)) {
            return new ApiResponse(false, "Course with ID " + courseId + " does not exist");
        }

        List<GetCountOfQuestionsWithType> getCountOfQuestionsWithTypes = testQuestionV2Repository.countQuestionsByTypeForCourse(courseId);

        return new ApiResponse(true, "Bu course ga tegishli barcha test question larining type va soni.", getCountOfQuestionsWithTypes);
    }

    @Override
    public ApiResponse getCountOfTypeOfQuestionsByModuleId(String moduleId) {
        if (moduleId == null || moduleId.isEmpty()) {
            return new ApiResponse(false, "Module ID cannot be null or empty");
        }
        if (!moduleRepository.existsById(moduleId)) {
            return new ApiResponse(false, "Module with ID " + moduleId + " does not exist");
        }

        List<GetCountOfQuestionsWithType> getCountOfQuestionsWithTypes = testQuestionV2Repository.countQuestionsByTypeForModule(moduleId);

        return new ApiResponse(true, "Bu module ga tegishli barcha test question larining type va soni.", getCountOfQuestionsWithTypes);
    }

    @Override
    public ApiResponse getRandomQuestionsForTest(String testId, String moduleId) {
        if (testId == null || testId.isEmpty()) {
            return new ApiResponse(false, "Test ID cannot be null or empty");
        }

        if (moduleId != null && !moduleId.isEmpty() && !moduleRepository.existsById(moduleId)) {
            List<TestQuestionV2> randomQuestions = testQuestionV2Repository.getRandomQuestionsForTest(testId);
            if (randomQuestions != null && !randomQuestions.isEmpty()) {
                return new ApiResponse(true, "Random questions retrieved successfully", randomQuestions);
            }
        }
        else if (moduleId != null && !moduleId.isEmpty()) {
            List<TestQuestionV2> randomQuestions = testQuestionV2Repository.getRandomQuestionsByTestIdAndModuleId(testId, moduleId);
            if (randomQuestions != null && !randomQuestions.isEmpty()) {
                return new ApiResponse(true, "Random questions retrieved successfully", randomQuestions);
            }
        } else {
            return new ApiResponse(false, "Module ID cannot be null or empty");
        }

        return new ApiResponse(false, "No random questions found for the given test ID");
    }

    @Override
    public boolean deleteById(String id) {
        testQuestionV2Repository.findById(id).ifPresent(testQuestionV2 -> {
            testOptionV2Repository.deleteAll(testQuestionV2.getOptions());
            testQuestionV2Repository.delete(testQuestionV2);
        });
        return true;
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Course Test",testQuestionV2Repository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        TestQuestionV2 testQuestion = testQuestionV2Repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Test question v2 not found by id="+id));
        return new ApiResponse(true,"Test question v2 by id="+id,testQuestion);
    }

    @Override
    public ApiResponse findByCourseId(String courseId) {
        if (courseId == null || courseId.isEmpty()) {
            return new ApiResponse(false, "Course ID cannot be null or empty");
        }
        if (!courseRepository.existsById(courseId)) {
            return new ApiResponse(false, "Course with ID " + courseId + " does not exist");
        }
        List<TestQuestionV2> questions = testQuestionV2Repository.findAllByCourseId(courseId);
        return new ApiResponse(true, "Test questions for course ID= " + courseId, questions);
    }

    @Override
    public ApiResponse giveScoreToWrittenUserAnswer(GiveScoreToWrittenUserAnswerDto dto) {
        UserTestAnswerV2 userTestAnswer = userTestAnswerV2Repository.findById(dto.getUserTestAnswerId())
                .orElseThrow(() -> new RuntimeException("User Test Answer V2 not found"));

        // Avval testni olish
        TestV2 test = userTestAnswer.getTestV2();
        if (test == null) {
            return new ApiResponse(false, "Related TestV2 not found");
        }

        // Maksimum written ball
        int maxWrittenBall = test.getWrittenBall();

        // Berilgan ball maksimal balldan oshmasligi kerak
        if (dto.getScore() > maxWrittenBall) {
            return new ApiResponse(false, "Score exceeds the allowed maximum written score: " + maxWrittenBall);
        }

        // Allaqachon baholanganmi?
        if (userTestAnswer.getScore() != null && !userTestAnswer.getCreatedBy().equals(userTestAnswer.getUpdatedBy())) {
            return new ApiResponse(false, "User Test Answer V2 already has a score");
        }

        // Bahoni qo‘llash
        userTestAnswer.setScore(dto.getScore());

        return new ApiResponse(true, "Score given to User Test Answer V2", userTestAnswerV2Repository.save(userTestAnswer));
    }

    /*public ApiResponse giveScoreToWrittenUserAnswer(GiveScoreToWrittenUserAnswerDto dto) {
        UserTestAnswerV2 userTestAnswer = userTestAnswerV2Repository.findById(dto.getUserTestAnswerId())
                .orElseThrow(() -> new RuntimeException("User Test Answer V2 not found"));
        *//*if (userTestAnswer.getWrittenAnswer() == null || userTestAnswer.getWrittenAnswer().isEmpty()) {
            return new ApiResponse(false, "User Test Answer has no written answer to score");
        } else *//*if (userTestAnswer.getScore() != null && !userTestAnswer.getCreatedBy().equals(userTestAnswer.getUpdatedBy())) {
            return new ApiResponse(false, "User Test Answer V2 already has a score");
        } else {
            userTestAnswer.setScore(dto.getScore());
            return new ApiResponse(true, "Score given to User Test Answer V2", userTestAnswerV2Repository.save(userTestAnswer));
        }
    }*/


    @Override
    public ApiResponse findTestQuestionsByCourseIdWithShuffledOptions(String userId, String testId,String moduleId) {
        // 1. Testni topamiz
        TestV2 test = testV2Repository.findById(testId)
                .orElseThrow(() -> new RuntimeException("Test not found by id: " + testId));

        // 2. Tekshiruv
        try {
            validateTestAccessV2(testId, userId);
        } catch (RuntimeException e) {
            return new ApiResponse(false, e.getMessage());
        }

        List<TestQuestionV2> questions = new ArrayList<>();
        if (moduleId != null && !moduleId.isEmpty() && moduleRepository.existsById(moduleId)) {
            List<TestQuestionV2> randomQuestionsByTestIdAndModuleId = testQuestionV2Repository.getRandomQuestionsByTestIdAndModuleId(testId, moduleId);
            List<TestQuestionResponseV2> response = randomQuestionsByTestIdAndModuleId.stream().map(q -> {
                List<TestOptionResponseV2> optionResponses = q.getOptions().stream()
                        .map(opt -> new TestOptionResponseV2(opt.getId(), opt.getText()))
                        .collect(Collectors.toList());
                Collections.shuffle(optionResponses); // Shuffle options

                return new TestQuestionResponseV2(
                        q.getId(),
                        q.getType(),
                        testId,
                        q.getQuestionText(),
                        optionResponses
                );
            }).collect(Collectors.toList());

            return new ApiResponse(true, "Random test questions generated from test settings", response);
        } else {
            List<TestQuestionV2> randomQuestionsForTest = testQuestionV2Repository.getRandomQuestionsForTest(testId);
            List<TestQuestionResponseV2> response = randomQuestionsForTest.stream().map(q -> {
                List<TestOptionResponseV2> optionResponses = q.getOptions().stream()
                        .map(opt -> new TestOptionResponseV2(opt.getId(), opt.getText()))
                        .collect(Collectors.toList());
                Collections.shuffle(optionResponses); // Shuffle options

                return new TestQuestionResponseV2(
                        q.getId(),
                        q.getType(),
                        testId,
                        q.getQuestionText(),
                        optionResponses
                );
            }).collect(Collectors.toList());

            return new ApiResponse(true, "Random test questions generated from test settings", response);
        }



    }

    /*public ApiResponse findTestQuestionsByCourseIdWithShuffledOptions(String userId, String testId) {
        // ⚠️ Avval tekshiruvdan o'tkazamiz
        try {
            validateTestAccessV2(testId, userId);
        } catch (RuntimeException e) {
            return new ApiResponse(false, e.getMessage());
        }

        // User testda ishlatadigan savollarni olish (random emas, foydalanuvchi ishlagan)
        List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(userId, testId);
        if (answers.isEmpty()) {
            return new ApiResponse(false, "User has not attempted the test yet or test questions not found");
        }

        Set<TestQuestionResponseV2> collect = answers.stream().map(answer -> {
            TestQuestionV2 test = answer.getQuestionV2();
            List<TestOptionResponseV2> shuffledOptions = new ArrayList<>(
                    test.getOptions().stream()
                            .map(option -> new TestOptionResponseV2(option.getId(), option.getText()))
                            .collect(Collectors.toList())
            );
            Collections.shuffle(shuffledOptions);
            return new TestQuestionResponseV2(
                    test.getId(),
                    test.getType(),
                    testId,
                    test.getQuestionText(),
                    shuffledOptions
            );
        }).collect(Collectors.toSet());

        return new ApiResponse(true, "Test questions for user by test id=" + testId, collect);
    }*/

    @Override
    public ApiResponse getStudentCourseTestAnswers(String testId, String studentId) {
        TestV2 test = testV2Repository.findById(testId)
                .orElseThrow(() -> new RuntimeException("TestV2 not found"));

        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(studentId, testId);

        List<TestQuestionAnswerDto> result = new ArrayList<>();

        for (UserTestAnswerV2 answer : answers) {
            TestQuestionV2 question = answer.getQuestionV2();

            List<TestOptionV2> correctOptions = question.getOptions().stream()
                    .filter(opt -> Boolean.TRUE.equals(opt.getCorrect()))
                    .collect(Collectors.toList());

            int correctOptionCount = correctOptions.size();
            double dividedScore;

            if (question.getType() == TestType.MULTIPLE_CHOICE && correctOptionCount > 0) {
                dividedScore = ((double) test.getMultipleChoiceBall()) / correctOptionCount;

            } else {
                dividedScore = 0;
            }

            List<TestOptionDto> optionDtos = question.getOptions().stream()
                    .map(option -> {
                        boolean selected = answer.getSelectedOptionsV2() != null &&
                                answer.getSelectedOptionsV2().stream()
                                        .anyMatch(opt -> opt.getId().equals(option.getId()));

                        Integer optionScore = null;

                        if (Boolean.TRUE.equals(option.getCorrect())) {
                            if (question.getType() == TestType.SINGLE_CHOICE) {
                                optionScore = test.getSingleChoiceBall();
                            } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                                optionScore = (int) Math.round(dividedScore);// ✅ teng taqsimlangan score

                            } else if (question.getType() == TestType.WRITTEN) {
                                optionScore = test.getWrittenBall();
                            }
                        }

                        return new TestOptionDto(
                                option.getId(),
                                option.getText(),
                                option.getCorrect(),
                                optionScore,
                                selected
                        );
                    })
                    .collect(Collectors.toList());



            String userTestAnswerId = answer.getId();
            String writtenAnswer = answer.getWrittenAnswer();
            Boolean isCorrect = answer.isCorrect();
            Integer score = question.getType().equals(TestType.WRITTEN)
                    ?  test.getWrittenBall() : question.getType().equals(TestType.SINGLE_CHOICE) ?
                    test.getSingleChoiceBall() : test.getMultipleChoiceBall();
            Boolean teacherChecked = !Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy());

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
    public ApiResponse findByModuleId(String moduleId) {
        if (moduleId == null || moduleId.isEmpty()) {
            return new ApiResponse(false, "Module ID cannot be null or empty");
        }
        if (!moduleRepository.existsById(moduleId)) {
            return new ApiResponse(false, "Module with ID " + moduleId + " does not exist");
        }
        List<TestQuestionV2> questions = testQuestionV2Repository.findAllByModuleId(moduleId);
        return new ApiResponse(true, "Test questions for course ID= " + moduleId, questions);
    }

    public void validateTestAccessV2(String testId, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + userId));

        TestV2 test = testV2Repository.findById(testId)
                .orElseThrow(() -> new RuntimeException("TestV2 not found by id: " + testId));

        Module module = test.getModule();
        Course course;
        List<Module> modules;

        if (module != null) {
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
            for (int i = 0; i < index; i++) {
                checkModuleCompletedAndPassedV2(user, modules.get(i));
            }
            checkModuleTestRetakeAccessV2(user, module);
        } else {
            course = test.getCourse();
            if (course == null) {
                throw new RuntimeException("Test not linked to any module or course");
            }
            modules = course.getModules().stream()
                    .sorted(Comparator.comparing(Module::getCreatedAt))
                    .collect(Collectors.toList());
            for (Module m : modules) {
                checkModuleCompletedAndPassedV2(user, m);
            }
        }
    }

    private void checkModuleCompletedAndPassedV2(User user, Module module) {
        boolean completed = userLessonModuleProgressRepository
                .existsByUserIdAndModuleIdAndCompletedTrue(user.getId(), module.getId());

        if (!completed) {
            throw new RuntimeException("You must complete module: " + module.getTitle());
        }

        TestV2 test = testV2Repository.findByModuleId(module.getId());
        if (test != null) {
            List<UserTestAnswerV2> answers = userTestAnswerV2Repository
                    .findAllByUserIdAndTestV2Id(user.getId(), test.getId());

            if (answers.isEmpty()) {
                throw new RuntimeException("You must complete the test for module: " + module.getTitle());
            }

            double totalScore = 0;

            for (UserTestAnswerV2 answer : answers) {
                TestQuestionV2 question = answer.getQuestionV2();

                if (question.getType() == TestType.WRITTEN) {
                    if (!Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy())) {
                        totalScore += Optional.ofNullable(answer.getScore()).orElse(0);
                    }
                } else if (question.getType() == TestType.SINGLE_CHOICE && answer.isCorrect()) {
                    totalScore += test.getSingleChoiceBall();
                } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                    long correctCount = question.getOptions().stream()
                            .filter(TestOptionV2::getCorrect)
                            .count();
                    if (correctCount > 0 && answer.getSelectedOptionsV2() != null) {
                        double dividedScore = (double) test.getMultipleChoiceBall() / correctCount;
                        for (TestOptionV2 option : answer.getSelectedOptionsV2()) {
                            if (Boolean.TRUE.equals(option.getCorrect())) {
                                totalScore += dividedScore;
                            }
                        }
                    }
                }
            }

            int roundedScore = (int) Math.round(totalScore);

            if (roundedScore < test.getPassingBall()) {
                throw new RuntimeException("Test for module '" + module.getTitle()
                        + "' not passed. Required: " + test.getPassingBall()
                        + ", but got: " + roundedScore);
            }
        }
    }


    /*private void checkModuleCompletedAndPassedV2(User user, Module module) {
        boolean completed = userLessonModuleProgressRepository
                .existsByUserIdAndModuleIdAndCompletedTrue(user.getId(), module.getId());

        if (!completed) {
            throw new RuntimeException("You must complete module: " + module.getTitle());
        }

        TestV2 test = testV2Repository.findByModuleId(module.getId());
        if (test != null) {
            List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(user.getId(), test.getId());
            if (answers.isEmpty()) {
                throw new RuntimeException("You must complete the test for module: " + module.getTitle());
            }

            long correctAnswers = answers.stream().filter(UserTestAnswerV2::isCorrect).count();
            double percent = (correctAnswers * 100.0) / answers.size();
            if (percent < test.getPassingBall()) {
                throw new RuntimeException("Test for module '" + module.getTitle() + "' not passed. Required: "
                        + test.getPassingBall() + "%, but got: " + percent + "%");
            }
        }
    }*/

    /*private void checkModuleTestRetakeAccessV2(User user, Module module) {
        TestV2 test = testV2Repository.findByModuleId(module.getId());
        if (test == null) return;

        List<UserTestAnswerV2> answers = userTestAnswerV2Repository.findAllByUserIdAndTestV2Id(user.getId(), test.getId());
        if (!answers.isEmpty()) {
            long correctAnswers = answers.stream().filter(UserTestAnswerV2::isCorrect).count();
            double percent = (correctAnswers * 100.0) / answers.size();
            if (percent < test.getPassingBall()) {
                throw new RuntimeException("Test for module '" + module.getTitle() + "' not passed. Required: "
                        + test.getPassingBall() + "%, but got: " + percent + "%");
            }
        }
    }*/

    private void checkModuleTestRetakeAccessV2(User user, Module module) {
        TestV2 test = testV2Repository.findByModuleId(module.getId());
        if (test == null) return;

        List<UserTestAnswerV2> answers = userTestAnswerV2Repository
                .findAllByUserIdAndTestV2Id(user.getId(), test.getId());

        if (!answers.isEmpty()) {
            double totalScore = 0;

            for (UserTestAnswerV2 answer : answers) {
                TestQuestionV2 question = answer.getQuestionV2();

                if (question.getType() == TestType.WRITTEN) {
                    if (!Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy())) {
                        totalScore += Optional.ofNullable(answer.getScore()).orElse(0);
                    }
                } else if (question.getType() == TestType.SINGLE_CHOICE && answer.isCorrect()) {
                    totalScore += test.getSingleChoiceBall();
                } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                    long correctCount = question.getOptions().stream()
                            .filter(TestOptionV2::getCorrect)
                            .count();

                    if (correctCount > 0 && answer.getSelectedOptionsV2() != null) {
                        double dividedScore = (double) test.getMultipleChoiceBall() / correctCount;
                        for (TestOptionV2 option : answer.getSelectedOptionsV2()) {
                            if (Boolean.TRUE.equals(option.getCorrect())) {
                                totalScore += dividedScore;
                            }
                        }
                    }
                }
            }

            int roundedScore = (int) Math.round(totalScore);

            if (roundedScore < test.getPassingBall()) {
                throw new RuntimeException("Test for module '" + module.getTitle()
                        + "' not passed. Required: " + test.getPassingBall()
                        + ", but got: " + roundedScore);
            }
        }
    }

}
