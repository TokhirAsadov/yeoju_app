package uz.yeoju.yeoju_app.test.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.test.service.EnableTeacherControlTestService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/controlTest")
@RequiredArgsConstructor
public class EnableTeacherControlTestController {
    private final EnableTeacherControlTestService service;

}
