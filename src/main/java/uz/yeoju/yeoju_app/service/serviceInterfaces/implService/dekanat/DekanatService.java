package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.AddNewStudentDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;

import java.util.Date;
import java.util.Set;

public interface DekanatService {
    ApiResponse findAllDekanats();

    ApiResponse saveDekanat(DekanatSaveDto dto);
    ApiResponse saveDekanatV2(DekanatDto dto);

    ApiResponse getDekansForSaved();
    ApiResponse getDekansSavedDatas();

    ApiResponse getKafedrasSavedDatas();

    ApiResponse getDekanatDataForDekan(String id);

    ApiResponse getDekanatById(String id);

    ApiResponse getUserForDekanSave(String id,Boolean bool);

    ApiResponse deleteById(String id);

    ApiResponse getStatisticsOfGroupForDean(String groupId, Date date);

    ApiResponse addNewStudent(AddNewStudentDto dto);

    ApiResponse getDekanatsForSelect();

    ApiResponse getStaffsForTableByDekanatId(String dekanatId);

    ApiResponse getStatisticsForTable(String dekanatId, Date date, Set<String> staffsIds);
}
