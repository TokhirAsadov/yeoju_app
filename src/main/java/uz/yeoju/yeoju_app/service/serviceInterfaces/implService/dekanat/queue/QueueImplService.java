package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.dekanat.queue.Queue;
import uz.yeoju.yeoju_app.entity.dekanat.queue.QueueStatusEnum;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.queue.QueueStatusChangerDto;
import uz.yeoju.yeoju_app.repository.QueueRepository;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueImplService implements QueueService{
    private final QueueRepository queueRepository;


    @Override
    public ApiResponse findAllQueueOfToday() {
        return new ApiResponse(true,"All queues of Today",queueRepository.getAllQueuesOfTodayForDean());
    }

    @Override
    public ApiResponse getQueueForStudent(String userId) {
        return new ApiResponse(true,"Your all queues",queueRepository.findAllByCreatedByOrderByCreatedAtDesc(userId));
    }


    @Override
    public ApiResponse getQueueForDeanByStatus(QueueStatusEnum statusEnum) {
        return new ApiResponse(true,"Queues by status "+statusEnum.name(),queueRepository.findAllByStatusOrderByCalledAt(statusEnum));
    }




}
