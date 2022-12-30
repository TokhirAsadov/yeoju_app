package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;

public interface DekanatService {
    ApiResponse findAllDekanats();

    ApiResponse saveDekanat(DekanatSaveDto dto);

    ApiResponse getDekansForSaved();
    ApiResponse getDekansSavedDatas();

    ApiResponse getKafedrasSavedDatas();
}
