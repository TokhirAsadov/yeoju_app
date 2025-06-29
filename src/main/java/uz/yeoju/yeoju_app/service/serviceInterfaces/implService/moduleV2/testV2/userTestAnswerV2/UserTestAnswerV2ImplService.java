package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.userTestAnswerV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.UserTestAnswerV2Repository;

@Service
public class UserTestAnswerV2ImplService implements UserTestAnswerV2Service {
    private final UserTestAnswerV2Repository userTestAnswerV2Repository;

    public UserTestAnswerV2ImplService(UserTestAnswerV2Repository userTestAnswerV2Repository) {
        this.userTestAnswerV2Repository = userTestAnswerV2Repository;
    }
}
