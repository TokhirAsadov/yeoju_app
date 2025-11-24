package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userLessonModuleProgress;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestOptionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestQuestionV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.TestV2;
import uz.yeoju.yeoju_app.entity.moduleV2.testV2.UserTestAnswerV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.UserLessonModuleProgressCreator;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class UserLessonModuleProgressImplService implements UserLessonModuleProgressService{
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final UserTestAnswerRepository userTestAnswerRepository;
    private final TestV2Repository testV2Repository;
    private final TestQuestionV2Repository testQuestionV2Repository;
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;

    public UserLessonModuleProgressImplService(UserLessonModuleProgressRepository userLessonModuleProgressRepository, ModuleRepository moduleRepository, UserRepository userRepository, UserTestAnswerRepository userTestAnswerRepository, TestV2Repository testV2Repository, TestQuestionV2Repository testQuestionV2Repository, UserTestAnswerV2Repository userTestAnswerV2Repository) {
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.testV2Repository = testV2Repository;
        this.testQuestionV2Repository = testQuestionV2Repository;
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
    }

    @Override
    public void create(UserLessonModuleProgressCreator creator) {
        // 1. Foydalanuvchini tekshirish
        User user = userRepository.findById(creator.userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + creator.userId));

        // 2. Modulni tekshirish
        Module currentModule = moduleRepository.findById(creator.moduleId)
                .orElseThrow(() -> new RuntimeException("Lesson module not found by id: " + creator.moduleId));

        // 3. Kurs va modul ro‘yxatini olish
        Course course = currentModule.getCourse();

        List<Module> orderedModules = course.getModules().stream()
                .sorted(Comparator.comparing(Module::getCreatedAt))
                .collect(Collectors.toList());

        int currentIndex = -1;
        for (int i = 0; i < orderedModules.size(); i++) {
            if (orderedModules.get(i).getId().equals(currentModule.getId())) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            throw new RuntimeException("Current module not found in course modules");
        }

        // 4. Oldingi modullarni tekshirish
        for (int i = 0; i < currentIndex; i++) {
            Module previousModule = orderedModules.get(i);

            // 4.1 Progress mavjudmi
            boolean isCompleted = userLessonModuleProgressRepository
                    .existsByUserIdAndModuleIdAndCompletedTrue(user.getId(), previousModule.getId());

            if (!isCompleted) {
                throw new RuntimeException("You must complete previous module before continuing: " + previousModule.getTitle());
            }

            Test moduleTest = previousModule.getModuleTest();

            if (moduleTest != null && moduleTest.getQuestions() != null && !moduleTest.getQuestions().isEmpty()) {
                boolean allQuestionsAttempted = true;
                int correctAnswers = 0;
                int totalQuestions = moduleTest.getQuestions().size();

                for (TestQuestion question : moduleTest.getQuestions()) {
                    Optional<UserTestAnswer> answerOpt = userTestAnswerRepository.findByUserAndQuestion(user, question);

                    if (!answerOpt.isPresent()) {
                        allQuestionsAttempted = false;
                        break;
                    }

                    UserTestAnswer answer = answerOpt.get();
                    if (answer.isCorrect()) {
                        correctAnswers++;
                    }
                }

                if (!allQuestionsAttempted) {
                    throw new RuntimeException("You must complete the test for module: " + previousModule.getTitle());
                }

                double scorePercent = ((double) correctAnswers / totalQuestions) * 100;

                if (scorePercent < moduleTest.getPassingPercentage()) {
                    // Eng oxirgi test javobining vaqtini topish
                    Optional<Timestamp> latestCreatedAtOpt = moduleTest.getQuestions().stream()
                            .map(question -> userTestAnswerRepository.findByUserAndQuestion(user, question))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .map(UserTestAnswer::getCreatedAt)
                            .max(Timestamp::compareTo);

                    Timestamp now = Timestamp.from(Instant.now());

                    if (latestCreatedAtOpt.isPresent()) {
                        Timestamp latestCreatedAt = latestCreatedAtOpt.get();

                        long diffInMillis = now.getTime() - latestCreatedAt.getTime();
                        long diffInMinutes = diffInMillis / (1000 * 60);

                        if (diffInMinutes < 120) {
                            long minutesLeft = 120 - diffInMinutes;
                            throw new RuntimeException("You must wait " + minutesLeft +
                                    " more minutes before retrying the test for module: " + previousModule.getTitle());
                        }
                    }
                    // ⚠️ Belgilab qo‘yish: bu foydalanuvchining javoblari o‘chiriladi
                    for (TestQuestion question : moduleTest.getQuestions()) {
                        userTestAnswerRepository.findByUserAndQuestion(user, question)
                                .ifPresent(answer -> {
                                    answer.setShouldBeDeleted(true);
                                    userTestAnswerRepository.save(answer);
                                });
                    }

                    throw new RuntimeException("Test for module \"" + previousModule.getTitle()
                            + "\" not passed. Required: " + moduleTest.getPassingPercentage()
                            + "%, but got: " + scorePercent + "%");
                }
            }
        }

        // 5. Progressni saqlash
        UserLessonModuleProgress progress = new UserLessonModuleProgress(user, currentModule, true);
        userLessonModuleProgressRepository.save(progress);
    }

    @Override
    public void createV2(UserLessonModuleProgressCreator creator) {
        // 1. Foydalanuvchini tekshirish
        User user = userRepository.findById(creator.userId)
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + creator.userId));

        // 2. Modulni tekshirish
        Module currentModule = moduleRepository.findById(creator.moduleId)
                .orElseThrow(() -> new RuntimeException("Lesson module not found by id: " + creator.moduleId));

        // 3. Kurs va modul ro‘yxatini olish
        Course course = currentModule.getCourse();

        List<Module> orderedModules = course.getModules().stream()
                .sorted(Comparator.comparing(Module::getCreatedAt))
                .collect(Collectors.toList());

        int currentIndex = -1;
        for (int i = 0; i < orderedModules.size(); i++) {
            if (orderedModules.get(i).getId().equals(currentModule.getId())) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == -1) {
            throw new RuntimeException("Current module not found in course modules");
        }

        // 4. Oldingi modullarni tekshirish
        for (int i = 0; i < currentIndex; i++) {
            Module previousModule = orderedModules.get(i);

            boolean isCompleted = userLessonModuleProgressRepository
                    .existsByUserIdAndModuleIdAndCompletedTrue(user.getId(), previousModule.getId());

            if (!isCompleted) {
                throw new RuntimeException("You must complete previous module before continuing: " + previousModule.getTitle());
            }

            TestV2 moduleTest = testV2Repository.findByModuleId(previousModule.getId());
            if (moduleTest != null) {
                List<UserTestAnswerV2> userAnswers = userTestAnswerV2Repository
                        .findAllByUserIdAndTestV2Id(user.getId(), moduleTest.getId());

                if (userAnswers.isEmpty()) {
                    throw new RuntimeException("You must complete the test for module: " + previousModule.getTitle());
                }

                double totalScore = 0;

                for (UserTestAnswerV2 answer : userAnswers) {
                    TestQuestionV2 question = answer.getQuestionV2();

                    if (question.getType() == TestType.WRITTEN) {
                        if (!Objects.equals(answer.getCreatedBy(), answer.getUpdatedBy())) {
                            totalScore += Optional.ofNullable(answer.getScore()).orElse(0);
                        }
                    } else if (question.getType() == TestType.SINGLE_CHOICE && answer.isCorrect()) {
                        totalScore += moduleTest.getSingleChoiceBall();
                    } else if (question.getType() == TestType.MULTIPLE_CHOICE) {
                        long correctCount = question.getOptions().stream()
                                .filter(TestOptionV2::getCorrect)
                                .count();
                        if (correctCount > 0 && answer.getSelectedOptionsV2() != null) {
                            double dividedScore = (double) moduleTest.getMultipleChoiceBall() / correctCount;
                            for (TestOptionV2 option : answer.getSelectedOptionsV2()) {
                                if (Boolean.TRUE.equals(option.getCorrect())) {
                                    totalScore += dividedScore;
                                }
                            }
                        }
                    }
                }

                int roundedScore = (int) Math.round(totalScore);

                if (roundedScore < moduleTest.getPassingBall()) {
                    Optional<Timestamp> latestCreatedAtOpt = userAnswers.stream()
                            .map(UserTestAnswerV2::getCreatedAt)
                            .max(Timestamp::compareTo);

                    Timestamp now = Timestamp.from(Instant.now());

                    if (latestCreatedAtOpt.isPresent()) {
                        Timestamp latest = latestCreatedAtOpt.get();
                        long diffMin = (now.getTime() - latest.getTime()) / (1000 * 60);
                        if (diffMin < 120) {
                            throw new RuntimeException("You must wait " + (120 - diffMin)
                                    + " more minutes before retrying the test for module: " + previousModule.getTitle());
                        }
                    }

                    // Belgilash: foydalanuvchi javoblarini o‘chirish
                    for (UserTestAnswerV2 answer : userAnswers) {
                        answer.setShouldBeDeleted(true);
                        userTestAnswerV2Repository.save(answer);
                    }

                    throw new RuntimeException("Test for module \"" + previousModule.getTitle()
                            + "\" not passed. Required ball: " + moduleTest.getPassingBall()
                            + ", but got: " + roundedScore);
                }
            }
        }

        // 5. Progressni saqlash
        UserLessonModuleProgress progress = new UserLessonModuleProgress(user, currentModule, true);
        userLessonModuleProgressRepository.save(progress);
    }


    @Override
    public boolean delete(String id) {
        if(userLessonModuleProgressRepository.existsById(id)){
            userLessonModuleProgressRepository.deleteById(id);
            return true;
        } else {
            throw new UserNotFoundException("User lesson module progress did not found by id: "+id);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"User lesson module progress",userLessonModuleProgressRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (userLessonModuleProgressRepository.existsById(id)) {
            return new ApiResponse(true,"User lesson module progress is found by id: "+id,userLessonModuleProgressRepository.findById(id).get());
        }
        else {
            throw new UserNotFoundException("User lesson module progress not found by id: "+id);
        }
    }
}
