package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.lineOfTheme;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateThemeAndLine;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeAndLine;

public interface LineOfThemeService {
    ApiResponse getAllQueueByPlanId(String planId);
    ApiResponse createLineAndTheme(CreateThemeAndLine dto);
    ApiResponse updateLineAndTheme(UpdateThemeAndLine dto);
    ApiResponse getOldThemesForCreateLine(String ownerId,String subjectId);
    Boolean isMultiUsedTheme(String themeId);
}
