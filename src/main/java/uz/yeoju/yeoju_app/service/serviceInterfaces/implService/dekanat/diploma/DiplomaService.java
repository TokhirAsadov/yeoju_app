package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaCreator;

public interface DiplomaService {
    ApiResponse createDiploma(DiplomaCreator creator);
    ApiResponse updateDiploma(DiplomaCreator creator);
}
