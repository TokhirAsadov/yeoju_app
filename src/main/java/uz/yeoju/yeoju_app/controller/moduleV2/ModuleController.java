package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleCreator;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module.ModuleService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/module")
public class ModuleController {
    private final ModuleService service;

    public ModuleController(ModuleService service) {
        this.service = service;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody ModuleCreator creator){
        service.createModule(creator);
    }

    @DeleteMapping("/delete/{id}")
    HttpEntity<?> delete(@PathVariable String id){
        boolean deleted = service.deleteModule(id);
        return ResponseEntity.status(deleted ? 200 : 417)
                .body("Module is deleted.");
    }

    @GetMapping("/findAll")
    HttpEntity<?> findAll(Pageable pageable){
        ApiResponse res = service.findAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/findById/{id}")
    HttpEntity<?> findById(@PathVariable String id){
        ApiResponse res = service.findById(id);
        return ResponseEntity.status(res.isSuccess() ? 200 : 417)
                .body(res);
    }
}
