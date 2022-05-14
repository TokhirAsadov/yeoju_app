package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.service.useServices.GanderService;

@RestController
@RequestMapping("/v1/gander")
@RequiredArgsConstructor
public class GanderController {

    public final GanderService ganderService;


}
