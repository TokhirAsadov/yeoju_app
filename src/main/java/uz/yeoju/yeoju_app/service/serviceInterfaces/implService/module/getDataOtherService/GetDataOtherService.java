package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

public interface GetDataOtherService {
    ApiResponse getStudentsResults(String studentId);
    ResToken getResToken(SignInDto sign);

    ApiResponse getStudentFails(String studentId);

    ApiResponse getStudentGPA(String studentId);
}
