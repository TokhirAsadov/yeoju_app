package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userLessonModuleProgress;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.moduleV2.UserTestAnswer;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTestAnswerCleanupService {

    private final UserTestAnswerRepository userTestAnswerRepository;

    @Transactional
    @Scheduled(fixedRate = 10 * 60 * 1000) // Har 10 daqiqada ishga tushadi
    public void deleteOldFailedAnswers() {
        Timestamp twoHoursAgo = new Timestamp(System.currentTimeMillis() - 2 * 60 * 60 * 1000);

        List<UserTestAnswer> expiredAnswers = userTestAnswerRepository
                .findAllByShouldBeDeletedTrueAndCreatedAtBefore(twoHoursAgo);

        if (!expiredAnswers.isEmpty()) {
            userTestAnswerRepository.deleteAll(expiredAnswers);
            System.out.println("Deleted " + expiredAnswers.size() + " expired user test answers.");
        }
    }
}

