package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.RoleWebClient;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.admin.RoomDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.RoomService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/room")
@RequiredArgsConstructor
public class RoomController {

    public final RoomService roomService;

    @GetMapping("/getUserFromOther")
    public HttpEntity<?> getUserFromOther(){
        return ResponseEntity.ok(roomService.getUserFromOther(new SignInDto("kiut123","kiut123")));
    }


    @PostMapping("/sendMultipartDataOtherServer")
    public HttpEntity<?> sendMultipartDataOtherServer(MultipartHttpServletRequest request) throws IOException {
        return ResponseEntity.ok(roomService.sendMultipartDataOtherServer(request));
    }

    @PostMapping("/createEmployee")
    public HttpEntity<?> createEmployee(@RequestBody RoleWebClient newClient){
        return ResponseEntity.ok(roomService.createRoleWebClient(newClient));
    }

    @GetMapping("/getDataFromOther")
    public HttpEntity<?> getDataFromOther(@RequestParam("page") Integer page,@RequestParam("size") Integer size){
        return ResponseEntity.ok(roomService.getDataFromOther(page,size));
    }

    @GetMapping("/getRoomsForSelect")
    public HttpEntity<?> getRoomsForSelect(@CurrentUser User user){
        return ResponseEntity.ok(roomService.getRoomsForSelect());
    }

    @GetMapping("/rooms")
    public HttpEntity<?> allRooms(){
        return ResponseEntity.ok(roomService.findAll());
    }

    @GetMapping("/getRoomById/{id}")
    public HttpEntity<?> getRoomById(@PathVariable String id){
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping("/createRoom")
    public HttpEntity<?> createRoom(@RequestBody RoomDto dto){
        return ResponseEntity.status(201).body(roomService.saveOrUpdate(dto));
    }

    @PutMapping("/updateRoom")
    public HttpEntity<?> updateRoom(@RequestBody RoomDto dto){
        return ResponseEntity.status(202).body(roomService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteRoom/{id}")
    public HttpEntity<?> deleteRoom(@PathVariable String id){
        return ResponseEntity.status(204).body(roomService.deleteById(id));
    }

}
