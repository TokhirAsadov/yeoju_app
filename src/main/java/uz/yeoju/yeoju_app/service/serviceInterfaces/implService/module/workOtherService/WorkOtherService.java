package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

public interface WorkOtherService {
    Object sendMultipartDataOtherServer(MultipartHttpServletRequest request,String url);
    ResToken getResToken(SignInDto sign);
    Object getDataFromOther(Integer page,Integer size);
}
