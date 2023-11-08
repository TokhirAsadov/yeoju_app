package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface DataOfLastActiveService {
    ApiResponse findAll();
    ApiResponse findByCreatorId(String id);
    ApiResponse create(String passage);
}
