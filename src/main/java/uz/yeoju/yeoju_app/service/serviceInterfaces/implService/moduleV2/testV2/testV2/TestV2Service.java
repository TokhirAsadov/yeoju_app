package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Creator;

import javax.validation.Valid;

public interface TestV2Service {
    ApiResponse create(@Valid TestV2Creator creator);
    ApiResponse findByStudentIdAndEducationYearIdV2(String studentId, String educationYearId);
}
