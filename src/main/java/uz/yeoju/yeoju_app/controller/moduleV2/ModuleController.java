package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleCreator;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleUpdater;
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

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    HttpEntity<?> update(@RequestBody ModuleUpdater updater){
        ApiResponse response = service.updateModule(updater);
        return ResponseEntity.status(response.isSuccess() ? 200 : 417)
                .body(response);
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

    @PostMapping("/upload/{moduleId}")
    public ResponseEntity<String> uploadDoc(@PathVariable String moduleId, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Fayl bo'sh bo'lishi mumkin emas.");
            }
            service.uploadModuleFile(moduleId,file);
            return ResponseEntity.ok("Mavzu muvaffaqiyatli yuklandi.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xatolik yuz berdi: " + e.getMessage());
        }
    }

}
