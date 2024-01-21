package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.service.useServices.MailService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/auth")
@RequiredArgsConstructor
public class AuthController {

    public final UserService userService;
    public final MailService mailService;
    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignInDto signInDto){
        ResToken resToken = userService.login(signInDto);
        return ResponseEntity.ok(resToken);
    }

    @GetMapping("/email/{email}")
    public HttpEntity<?> email(@PathVariable String email){
        ApiResponse userByEmail = userService.getUserByEmail(email);
        if (userByEmail.isSuccess()){
            mailService.sendMessage(userService.generateUser((UserDto) userByEmail.getObj()));
            return ResponseEntity.ok(new ApiResponse(true,"Sending login and password. Check your email!.."));
        }
        return ResponseEntity.ok(userByEmail);
    }
}
