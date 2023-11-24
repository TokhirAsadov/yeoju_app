package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

@Service
@RequiredArgsConstructor
public class WorkOtherImplService implements WorkOtherService{

    @Override
    public ResToken getResToken(SignInDto sign) {
        return null;
    }
}
