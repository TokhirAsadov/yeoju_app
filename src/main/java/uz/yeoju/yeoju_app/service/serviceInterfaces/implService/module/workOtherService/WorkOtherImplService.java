package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.workOtherService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WorkOtherImplService implements WorkOtherService{
    private final UserRepository userRepository;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);// request time that how time is during

    private final WebClient webClient;// get web client

    @Override
    public Object sendMultipartDataOtherServer(MultipartHttpServletRequest request) {

        return null;
    }

    public Object senderFile(MultipartFile multipartFile){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());
        ResToken resToken = getResToken(new SignInDto("kiut123","kiut123"));
        return webClient.post()
                .uri("/result/importStudentsResults")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
//                .exchangeToMono(response -> {
//                    if (response.statusCode().equals(HttpStatus.OK)) {
//                        return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
//                    } else {
//                        throw new ServiceException("Error uploading file");
//                    }
//                });
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
