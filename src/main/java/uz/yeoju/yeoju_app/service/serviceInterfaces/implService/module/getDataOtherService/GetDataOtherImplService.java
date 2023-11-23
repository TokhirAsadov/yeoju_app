package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
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
        ResponseEntity<ResToken> resTokenResponse = webClient.post()
                .uri("/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(sign), SignInDto.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> {
                    //logError("Client error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Bad Request", null, null, null));
                })
                .onStatus(HttpStatus::is5xxServerError, response -> {
                    //logError("Server error occurred");
                    return Mono.error(new WebClientResponseException
                            (response.statusCode().value(), "Server Error", null, null, null));
                })
                .toEntity(ResToken.class)
                .block(REQUEST_TIMEOUT);
        System.out.println(resTokenResponse.getBody()+"----------------------------------------- RES TOKEN");

        return resTokenResponse.getBody();
    }
}
