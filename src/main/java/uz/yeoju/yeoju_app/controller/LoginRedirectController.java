package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.service.useServices.MailService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginRedirectController {

    @SneakyThrows
    @GetMapping("/")
    public void login(  HttpServletResponse response){
        response.sendRedirect("/login");
    }

//    @RequestMapping("/foo")
//    void handleFoo(HttpServletResponse response) throws IOException {
//        response.sendRedirect("some-url");
//    }

}
