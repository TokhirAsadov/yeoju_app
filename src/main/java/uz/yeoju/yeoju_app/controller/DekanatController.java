package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.DekanatService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dekanat")
@RequiredArgsConstructor
public class DekanatController {

    private final DekanatService service;

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody DekanatSaveDto dto){
        return ResponseEntity.status(201).body(service.saveDekanat(dto));
    }

    @GetMapping("/all")
    public HttpEntity<?> allDekanats(){
        return ResponseEntity.ok(service.findAllDekanats());
    }


    @GetMapping("/getDatasForSavedDekan")
    public HttpEntity<?> getDatasForSavedDekan(){
        return ResponseEntity.ok(service.getDekansSavedDatas());
    }

    @GetMapping("/getDatasForSavedKafedraMudiri")
    public HttpEntity<?> getDatasForSavedKafedraMudiri(){
        return ResponseEntity.ok(service.getKafedrasSavedDatas());
    }
}
