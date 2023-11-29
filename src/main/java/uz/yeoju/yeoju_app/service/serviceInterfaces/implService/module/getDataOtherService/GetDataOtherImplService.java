package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.getDataOtherService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetDataOtherImplService implements GetDataOtherService{
    private final UserRepository userRepository;
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(10);// request time that how time is during

    private final WebClient webClient;// get web client

    @Override
    public ApiResponse getStudentsResults(String studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "results student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/result/findByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+studentId);
        }
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
    public ApiResponse getStudentFails(String studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "fails student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/failtable/getFailsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+studentId);
        }
    }

    @Override
    public ApiResponse getStudentGPA(String studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "GPA student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/gpa/getGPAsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+studentId);
        }
    }

    @Override
    public ApiResponse getStudentsFinals(String studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "Finals by student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/final/getFinalsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+studentId);
        }
    }

    @Override
    public Object getDataByStudentId(String studentId) {
        Optional<User> optionalUser = userRepository.findById(studentId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "fails student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/failtable/getFailTableWithFullNameAndPassportByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+studentId);
        }
    }

    @Override
    public Object getDataByStudentId2(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "fails student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/failtable/getFailTableWithFullNameAndPassportByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+login);
        }
    }

    @Override
    public ApiResponse getStudentsResults2(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "results student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/result/findByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+login);
        }
    }

    @Override
    public ApiResponse getStudentFails2(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "fails student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/failtable/getFailsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+login);
        }
    }

    @Override
    public ApiResponse getStudentGPA2(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "GPA student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/gpa/getGPAsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+login);
        }
    }

    @Override
    public ApiResponse getStudentsFinals2(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new ApiResponse(true, "Finals by student id: "+user.getLogin(),
                    webClient
                            .get()
                            .uri("/final/getFinalsByStudentId/"+user.getLogin())
                            .retrieve()
                            .bodyToMono(Object.class)
                            .block(REQUEST_TIMEOUT)
            );
        }
        else {
            return new ApiResponse(false,"student not found by id: "+login);
        }
    }
}
