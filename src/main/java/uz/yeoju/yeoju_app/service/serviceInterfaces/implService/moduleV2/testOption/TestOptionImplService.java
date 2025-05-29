package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testOption;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.moduleV2.TestOptionRepository;

@Service
public class TestOptionImplService implements TestOptionService {
    private final TestOptionRepository testOptionRepository;

    public TestOptionImplService(TestOptionRepository testOptionRepository) {
        this.testOptionRepository = testOptionRepository;
    }
}
