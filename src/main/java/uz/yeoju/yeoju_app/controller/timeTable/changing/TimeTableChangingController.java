package uz.yeoju.yeoju_app.controller.timeTable.changing;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing.TimeTableChangingService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/changingTimeTable")
@RequiredArgsConstructor
public class TimeTableChangingController {
    private final TimeTableChangingService service;
}
