package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.userTestAnswer;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.moduleV2.TestOption;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;
import uz.yeoju.yeoju_app.entity.moduleV2.UserTestAnswer;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.TestResultDto;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerFinisher;
import uz.yeoju.yeoju_app.payload.moduleV2.UserTestAnswerFinisherChild;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestOptionRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TestQuestionRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.UserTestAnswerRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserTestAnswerImplService implements UserTestAnswerService{
    private final UserTestAnswerRepository userTestAnswerRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final UserRepository userRepository;
    private final TestOptionRepository testOptionRepository;

    public UserTestAnswerImplService(UserTestAnswerRepository userTestAnswerRepository, TestQuestionRepository testQuestionRepository, UserRepository userRepository, TestOptionRepository testOptionRepository) {
        this.userTestAnswerRepository = userTestAnswerRepository;
        this.testQuestionRepository = testQuestionRepository;
        this.userRepository = userRepository;
        this.testOptionRepository = testOptionRepository;
    }


}
