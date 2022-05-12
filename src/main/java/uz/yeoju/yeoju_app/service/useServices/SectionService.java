package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SectionService implements FacultyImplService<SectionDto> {
    public final SectionRepository sectionRepository;


    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(UUID id) {
        return null;
    }

    @Override
    public ApiResponse getById(UUID id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(SectionDto sectionDto) {
        return null;
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        return null;
    }
}
