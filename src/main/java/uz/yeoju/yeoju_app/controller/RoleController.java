package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.RoleDto;
import uz.yeoju.yeoju_app.service.useServices.RoleService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/role")
@RequiredArgsConstructor
public class RoleController {
    public final RoleService roleService;

    @GetMapping("/allRoles")
    public HttpEntity<?> allRoles(){
        return ResponseEntity.ok(roleService.allRoles());
    }


    @GetMapping("/getRoleById/{id}")
    public HttpEntity<?> getRoleById(@PathVariable String id){
        return ResponseEntity.ok(roleService.findById(id));
    }

    @PostMapping("/createRole")
    public HttpEntity<?> createRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.status(201).body(roleService.saveOrUpdate(roleDto));
    }

    @PostMapping("/updateRole")
    public HttpEntity<?> updateRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.status(203).body(roleService.saveOrUpdate(roleDto));
    }

    @GetMapping("/deleteRole/{id}")
    public HttpEntity<?> deleteRole(@PathVariable String id){
        return ResponseEntity.status(204).body(roleService.deleteById(id));
    }
}
