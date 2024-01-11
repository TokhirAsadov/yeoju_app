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

    @GetMapping("/getAllStudentQueues/{studentId}")
    public HttpEntity<?> getAllStudentQueues(@CurrentUser User user, @PathVariable String studentId){
        return ResponseEntity.ok(queueService.getQueueForStudent(studentId));
    }

    @PostMapping("/createQueue")
    public HttpEntity<?> createQueue(@CurrentUser User user){
        ApiResponse response = queueService.createQueue(user.getId());
        return ResponseEntity.status(response.isSuccess() ? 201 : 403).body(response);
    }

    @PutMapping("/changeQueue")
    public HttpEntity<?> changeQueue(@CurrentUser User user,@RequestBody QueueStatusChangerDto dto){
        ApiResponse response = queueService.queueStatusChanger(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 403).body(response);
    }

    @GetMapping("/getQueueForDeanByStatus/{status}")
    public HttpEntity<?> getQueueForDeanByStatus(@CurrentUser User user, @PathVariable QueueStatusEnum status){
        return ResponseEntity.ok(queueService.getQueueForDeanByStatus(status));
    }
}
