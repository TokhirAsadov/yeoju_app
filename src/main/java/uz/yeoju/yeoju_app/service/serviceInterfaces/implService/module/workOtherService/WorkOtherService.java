package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.ResultDto;

public interface WorkOtherService {
    Object sendMultipartDataOtherServer(MultipartHttpServletRequest request,String url);
    ResToken getResToken(SignInDto sign);
    Object getDataFromOther(String url,Integer page,Integer size);
    Object createOrUpdateResult(ResultDto dto);
    Object createOrUpdateFail(ResultDto dto);
}
