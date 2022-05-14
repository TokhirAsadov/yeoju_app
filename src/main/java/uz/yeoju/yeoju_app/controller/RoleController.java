package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.service.useServices.RoleService;

@RestController
@RequestMapping("/v1/role")
@RequiredArgsConstructor
public class RoleController {
    public final RoleService roleService;

    @GetMapping("/allRoles")
    public HttpEntity<?> allRoles(){
        return ResponseEntity.ok(roleService.findAll());
    }

}
