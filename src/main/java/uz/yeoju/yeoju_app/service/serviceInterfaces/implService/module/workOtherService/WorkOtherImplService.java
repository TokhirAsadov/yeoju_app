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
import uz.yeoju.yeoju_app.entity.RoleWebClient;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.FailTableDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.FinalDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.GpaDto;
import uz.yeoju.yeoju_app.payload.otherServiceDtos.ResultDto;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.time.Duration;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
public class WorkOtherImplService implements WorkOtherService{
    private final UserRepository userRepository;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(100);// request time that how time is during

    private final WebClient webClient;// get web client

    @Override
    public Object sendMultipartDataOtherServer(MultipartHttpServletRequest request,String url) {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return senderFile(file,url);
            }
        }
        return null;
    }

    @Override
    public Object sendMultipartDataOtherServer2(MultipartHttpServletRequest request, String url) {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return senderFile2(file,url);
            }
        }
        return null;
    }

    public Object senderFile2(MultipartFile multipartFile,String url){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());
        ResToken resToken = getResToken(new SignInDto("kiut123","kiut123"));
        return webClient.put()
                .uri(url)
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

    public Object senderFile(MultipartFile multipartFile,String url){
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", multipartFile.getResource());
        ResToken resToken = getResToken(new SignInDto("kiut123","kiut123"));
        return webClient.post()
                .uri(url)
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

    @Override
    public Object getDataFromOther(String url,Integer page, Integer size) {
        ResToken resToken = getResToken(new SignInDto("kiut123","kiut123"));
        return webClient
                .get()
                .uri(url+"?page="+page+"&size="+size)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                .retrieve()
                .bodyToMono(Object.class)
                .block(REQUEST_TIMEOUT);
    }

    @Override
    public Object createOrUpdateResult(ResultDto dto) {
        ResToken resToken = getResToken(new SignInDto("kiut123", "kiut123"));
        if (dto.getId() == null) {
            return webClient.post()
                    .uri("/result/save")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), ResultDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
        else {
            return webClient.put()
                    .uri("/result/update")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), ResultDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
    }

    @Override
    public Object createOrUpdateFail(FailTableDto dto) {
        ResToken resToken = getResToken(new SignInDto("kiut123", "kiut123"));
        if (dto.getId() == null) {
            return webClient.post()
                    .uri("/failtable/save")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), FailTableDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
        else {
            return webClient.put()
                    .uri("/failtable/update")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), FailTableDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
    }

    @Override
    public Object createOrUpdateGPA(GpaDto dto) {
        ResToken resToken = getResToken(new SignInDto("kiut123", "kiut123"));
        if (dto.getId() == null) {
            return webClient.post()
                    .uri("/gpa/save")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), GpaDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
        else {
            return webClient.put()
                    .uri("/gpa/update")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), GpaDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
    }

    @Override
    public Object createOrUpdateFinal(FinalDto dto) {
        ResToken resToken = getResToken(new SignInDto("kiut123", "kiut123"));
        if (dto.getId() == null) {
            return webClient.post()
                    .uri("/final/save")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), FinalDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
        else {
            return webClient.put()
                    .uri("/final/update")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + resToken.getAccessToken())
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(BodyInserters.fromValue(newRole))
                    .body(Mono.just(dto), FinalDto.class)
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
                    .toEntity(ApiResponse.class)
                    .block(REQUEST_TIMEOUT);
        }
    }

}
