package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter.NotificationOuterService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/notificationOuter")
@RequiredArgsConstructor
public class NotificationOuterController {
    private final NotificationOuterService service;


}
