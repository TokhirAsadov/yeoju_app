package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.service.useServices.UserService;

@RestController
@RequestMapping("/v1/user")
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
    public HttpEntity<?> getUserById(
            @PathVariable String  id,
            @RequestParam String login,
            @RequestParam String rfid,
            @RequestParam String email
    ){
        if (!id.equals("")) {
            return ResponseEntity.ok(userService.findById(id));
        }
        else if (!login.equals("")){
            return ResponseEntity.ok(new ApiResponse(true,"user by login",userService.getUserByLogin(login)));
        } else if (!rfid.equals("")) {
            return ResponseEntity.ok(new ApiResponse(true,"user by rfid",userService.getUserByRFID(rfid)));
        } else if (!email.equals("")) {
            return ResponseEntity.ok(new ApiResponse(true,"user by email",userService.getUserByEmail(email)));
        }
        return ResponseEntity.ok(new ApiResponse(false,"Error params!!!"));
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
