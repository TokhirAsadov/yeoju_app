package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.uquvbulim.DataOfLastActive;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.uquvbulimi.DataOfLastActiveRepository;

@Service
@RequiredArgsConstructor
public class DataOfLastActiveImplService implements DataOfLastActiveService {

    private final DataOfLastActiveRepository repository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all last active data",repository.findAll());
    }

    @Override
    public ApiResponse findByCreatorId(String id) {
        return new ApiResponse(true,"all last active data",repository.findById(id));
    }

    @Override
    public ApiResponse create(String passage) {
        repository.save(new DataOfLastActive(passage));
        return new ApiResponse(true,"saved successfully.");
    }

    @Override
    public ApiResponse getAssistants() {
        return new ApiResponse(true,"all assistants",repository.getAssistants());
    }
}
