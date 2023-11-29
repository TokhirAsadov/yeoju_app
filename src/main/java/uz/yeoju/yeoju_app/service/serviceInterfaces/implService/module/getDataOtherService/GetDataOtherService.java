package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

public interface GetDataOtherService {
    ApiResponse getStudentsResults(String studentId);
    ResToken getResToken(SignInDto sign);

    ApiResponse getStudentFails(String studentId);

    ApiResponse getStudentGPA(String studentId);
    ApiResponse getStudentsFinals(String studentId);
    Object getDataByStudentId(String studentId);
    Object getDataByStudentId2(String login);

    ApiResponse getStudentsResults2(String login);

    ApiResponse getStudentFails2(String login);

    ApiResponse getStudentGPA2(String login);
    ApiResponse getStudentsFinals2(String login);
}
