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


}

