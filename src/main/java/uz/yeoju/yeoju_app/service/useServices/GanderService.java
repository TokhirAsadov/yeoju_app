package uz.yeoju.yeoju_app.service.useServices;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Gander;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GanderImplService;

@Service
public class GanderService implements GanderImplService<Gander> {
    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public ApiResponse getById(Long id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(Gander gander) {
        return null;
    }

    @Override
    public ApiResponse deleteById(Long id) {
        return null;
    }
}
