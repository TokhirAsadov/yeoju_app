package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/getAllUsers")
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }


}
