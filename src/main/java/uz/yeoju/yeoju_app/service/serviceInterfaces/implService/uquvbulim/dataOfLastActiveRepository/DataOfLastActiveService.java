package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreateAssistant;

public interface DataOfLastActiveService {
    ApiResponse findAll();
    ApiResponse findByCreatorId(String id);
    ApiResponse create(String passage);

    ApiResponse getAssistants();

    ApiResponse createAssistant(CreateAssistant assistant);

    ApiResponse deleteAssistant(String assistantId);
}
