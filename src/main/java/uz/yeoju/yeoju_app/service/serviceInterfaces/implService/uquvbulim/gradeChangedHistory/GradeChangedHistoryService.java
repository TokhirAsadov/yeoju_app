package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.gradeChangedHistory;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreateGradeChangedHistoryDto;

public interface GradeChangedHistoryService {
    ApiResponse findAllHistory();
    ApiResponse findById(String id);
    ApiResponse createHistory(CreateGradeChangedHistoryDto dto);
}
