package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.RoleDto;
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

    @PostMapping("/getRolesBySection")
    public HttpEntity<?> getRolesBySection(@RequestBody SectionDto sectionDto){
        return ResponseEntity.ok(roleService.findRolesBySection(sectionDto));
    }

    @GetMapping("/getRoleById/{id}")
    public HttpEntity<?> getRoleById(@PathVariable String id){
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/createRole")
    public HttpEntity<?> createRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.status(201).body(roleService.saveOrUpdate(roleDto));
    }
}
