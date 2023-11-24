package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService;

import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

public interface WorkOtherService {
    ResToken getResToken(SignInDto sign);
}
