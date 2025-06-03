package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userLessonModuleProgress;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.*;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.UserLessonModuleProgressCreator;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserLessonModuleProgressRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLessonModuleProgressImplService implements UserLessonModuleProgressService{
    private final UserLessonModuleProgressRepository userLessonModuleProgressRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final UserTestAnswerRepository userTestAnswerRepository;

    public UserLessonModuleProgressImplService(UserLessonModuleProgressRepository userLessonModuleProgressRepository, ModuleRepository moduleRepository, UserRepository userRepository, UserTestAnswerRepository userTestAnswerRepository) {
        this.userLessonModuleProgressRepository = userLessonModuleProgressRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.userTestAnswerRepository = userTestAnswerRepository;
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
