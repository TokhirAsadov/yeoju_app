package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.queue;

import uz.yeoju.yeoju_app.entity.dekanat.queue.QueueStatusEnum;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.queue.QueueStatusChangerDto;

public interface QueueService {
    ApiResponse findAllQueueOfToday();
    ApiResponse getQueueForStudent(String userId);

    ApiResponse getQueueForDeanByStatus(QueueStatusEnum statusEnum);
    ApiResponse createQueue(String userId);
    ApiResponse queueStatusChanger(QueueStatusChangerDto dto);

}
