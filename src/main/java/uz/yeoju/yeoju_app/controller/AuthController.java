package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/auth")
@RequiredArgsConstructor
public class AuthController {

    public final UserService userService;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody SignInDto signInDto){
        ResToken resToken = userService.login(signInDto);
        return ResponseEntity.ok(resToken);
    }

}
