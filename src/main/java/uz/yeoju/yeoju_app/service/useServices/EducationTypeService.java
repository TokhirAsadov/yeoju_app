package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.EducationTypeDto;
import uz.yeoju.yeoju_app.repository.EducationTypeRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.EducationTypeImplService;

@Service
@RequiredArgsConstructor
public class EducationTypeService implements EducationTypeImplService<EducationTypeDto> {
    public final EducationTypeRepository educationTypeRepository;

    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(EducationTypeDto educationTypeDto) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }
}
