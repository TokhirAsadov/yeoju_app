package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.UserPhotoDto;
import uz.yeoju.yeoju_app.payload.UserPhotoSaveDto;
import uz.yeoju.yeoju_app.service.useServices.UserPhotoService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/userPhoto")
@RequiredArgsConstructor
public class UserPhotoController {
    public final UserPhotoService userPhotoService;

    @GetMapping("/allUserPhotos")
    public HttpEntity<?> getAllUserPhotos(){
        return ResponseEntity.ok(userPhotoService.findAll());
    }

    @GetMapping("/getUserPhotoById/{id}")
    public HttpEntity<?> getUserPhotoByIdOrUserIdOrActive(
            @PathVariable String id,
            @RequestParam String user_id,
            @RequestParam boolean active)
    {
        return id != null ? ResponseEntity.ok(userPhotoService.findById(id))
                :
                user_id != null ? ResponseEntity.ok(userPhotoService.findUserPhotosByUserId(user_id))
                :
                ResponseEntity.ok(userPhotoService.findUserPhotosByActive(active));
    }

    @PostMapping("/createUserPhoto")
    public HttpEntity<?> createNewUserPhoto(@RequestBody UserPhotoDto dto){
        return ResponseEntity.status(201).body(userPhotoService.saveOrUpdate(dto));
    }

    @PostMapping("/savingUserPhoto")
    public HttpEntity<?> savingNewUserPhoto(@RequestBody UserPhotoSaveDto dto){
        return ResponseEntity.status(201).body(userPhotoService.saving(dto));
    }

    @PutMapping("/updateUserPhoto")
    public HttpEntity<?> updateUserPhoto(@RequestBody UserPhotoDto dto){
        return ResponseEntity.status(202).body(userPhotoService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteUserPhoto/{id}")
    public HttpEntity<?> deleteUserPhoto(@PathVariable String id){
        return ResponseEntity.status(204).body(userPhotoService.deleteById(id));
    }
}
