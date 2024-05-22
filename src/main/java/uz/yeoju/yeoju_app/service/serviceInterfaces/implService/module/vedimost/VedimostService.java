package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;

public interface VedimostService {
    ApiResponse findAllVedimosts();
    ApiResponse createVedimost(VedimostCreaterDto dto);

    ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId);
}
