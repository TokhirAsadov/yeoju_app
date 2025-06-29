package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testQuestionV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestQuestionV2Repository;

@Service
public class TestQuestionV2ImplService implements TestQuestionV2Service{
    private final TestQuestionV2Repository testQuestionV2Repository;

    public TestQuestionV2ImplService(TestQuestionV2Repository testQuestionV2Repository) {
        this.testQuestionV2Repository = testQuestionV2Repository;
    }
}
