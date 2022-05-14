package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.service.useServices.EducationTypeService;

@RestController
@RequestMapping("/v1/eduType")
@RequiredArgsConstructor
public class EducationTypeController {

    public final EducationTypeService educationTypeService;



}
