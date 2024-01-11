package uz.yeoju.yeoju_app.controller.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.queue.QueueStatusEnum;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.queue.QueueStatusChangerDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.queue.QueueService;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/queue")
@RequiredArgsConstructor
public class QueueController {
    private final QueueService queueService;

    @GetMapping("/findAllQueuesOfTodayForDean")
    public HttpEntity<?> findAllQueuesOfTodayForDean(@CurrentUser User user){
        return ResponseEntity.ok(queueService.findAllQueueOfToday());
    }


}
