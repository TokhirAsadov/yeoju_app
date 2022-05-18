package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;

    @GetMapping("/getAllUsers")
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }


    @PostMapping("/createUser")
    public HttpEntity<?> saveUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(201).body(userService.saveOrUpdate(dto));
    }

    @GetMapping("/getUserById/{id}")
    public HttpEntity<?> getUserById(@PathVariable String  id){
            return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/getUserByEmail/{email}")
    public HttpEntity<?> getUserByEmail(@PathVariable String  email){
            return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PostMapping("/updateUser")
    public HttpEntity<?> updateFaculty(@RequestBody UserDto dto){
        return ResponseEntity.status(202).body(userService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteUser/{id}")
    public HttpEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.status(204).body(userService.deleteById(id));
    }
}
