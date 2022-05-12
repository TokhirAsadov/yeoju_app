package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.service.useServices.SectionService;

@RestController
@RequestMapping("/v1/section")
@RequiredArgsConstructor
public class SectionController {
    public final SectionService sectionService;


}
