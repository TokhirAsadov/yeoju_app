package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestV2Repository;

@Service
public class TestV2ImplService implements TestV2Service {
    private final TestV2Repository testV2Repository;

    public TestV2ImplService(TestV2Repository testV2Repository) {
        this.testV2Repository = testV2Repository;
    }
}
