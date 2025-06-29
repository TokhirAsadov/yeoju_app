package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testOptionV2;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.moduleV2.testV2.TestOptionV2Repository;

@Service
public class TestOptionV2ImplService implements TestOptionV2Service {
    private final TestOptionV2Repository testOptionV2Repository;

    public TestOptionV2ImplService(TestOptionV2Repository testOptionV2Repository) {
        this.testOptionV2Repository = testOptionV2Repository;
    }
}
