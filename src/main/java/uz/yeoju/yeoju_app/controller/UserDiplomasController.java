package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.UserDiplomaDto;
import uz.yeoju.yeoju_app.service.useServices.UserDiplomaService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/userDiploma")
@RequiredArgsConstructor
public class UserDiplomasController {
    public final UserDiplomaService userDiplomaService;

    @GetMapping("/allUserDiplomas")
    public HttpEntity<?> getAllUserDiplomas(){
        return ResponseEntity.ok(userDiplomaService.findAll());
    }

    @GetMapping("/getUserDiplomaById/{id}")
    public HttpEntity<?> getUserDiplomaByIdOrUserIdOrActive(
            @PathVariable String id,
            @RequestParam String user_id,
            @RequestParam boolean active)
    {
        return id != null ? ResponseEntity.ok(userDiplomaService.findById(id))
                :
                user_id != null ? ResponseEntity.ok(userDiplomaService.findUserDiplomasByUserId(user_id))
                :
                ResponseEntity.ok(userDiplomaService.findUserDiplomasByActive(active));
    }

    @PostMapping("/createUserDiploma")
    public HttpEntity<?> createNewUserDiploma(@RequestBody UserDiplomaDto dto){
        return ResponseEntity.status(201).body(userDiplomaService.saveOrUpdate(dto));
    }

    @PostMapping("/updateUserDiploma")
    public HttpEntity<?> updateUserDiploma(@RequestBody UserDiplomaDto dto){
        return ResponseEntity.status(202).body(userDiplomaService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteUserDiploma/{id}")
    public HttpEntity<?> deleteUserDiploma(@PathVariable String id){
        return ResponseEntity.status(204).body(userDiplomaService.deleteById(id));
    }
}
