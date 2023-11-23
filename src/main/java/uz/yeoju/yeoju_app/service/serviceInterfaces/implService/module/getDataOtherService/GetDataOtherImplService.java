package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GetDataOtherImplService implements GetDataOtherService{
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);// request time that how time is during

    private final WebClient webClient;// get web client

    @Override
    public ApiResponse getStudentsResults(String studentId) {
        return null;
    }

    @Override
    public ResToken getResToken(SignInDto sign) {
        return null;
    }
}
