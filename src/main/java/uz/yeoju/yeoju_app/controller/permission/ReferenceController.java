package uz.yeoju.yeoju_app.controller.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.permissionPost.PermissionPost;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.permissionDto.ChangeStatusDto;
import uz.yeoju.yeoju_app.payload.permissionDto.CreateReferenceDto;
import uz.yeoju.yeoju_app.payload.permissionDto.PPermissionDto;
import uz.yeoju.yeoju_app.payload.permissionDto.PReferenceDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.reference.ReferenceService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/reference")
@RequiredArgsConstructor
public class ReferenceController {
    private final ReferenceService service;

    @GetMapping("/stream")
    public Flux<ServerSentEvent<List<PReferenceDto>>> streamPosts(@RequestParam(name="userId") String userId,@RequestParam(name="bool") Boolean bool) {
        return service.streamReferences(userId,bool);
    }

    @PostMapping("/create")
    public HttpEntity<?> createPost(@CurrentUser User user, @RequestBody CreateReferenceDto dto) {
        ApiResponse response = service.create(user, dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 401).body(response);
    }
    @PutMapping("/change")
    public ResponseEntity<?> changeStatusOfPost(@CurrentUser User user, @RequestBody ChangeStatusDto dto) {
        return ResponseEntity.ok(service.changeStatusOfReference(user,dto));
    }

    @GetMapping("/getTypesOfReference")
    public ApiResponse getTypesOfReference() {
        return service.getTypesOfReference();
    }
}