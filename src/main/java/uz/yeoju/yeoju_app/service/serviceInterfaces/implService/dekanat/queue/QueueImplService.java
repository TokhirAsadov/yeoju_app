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


    @Override
    public ApiResponse createQueue(String userId) {
        Boolean existQueueForToday = queueRepository.existQueueForToday(userId);
        if (!existQueueForToday) {
            Integer maxQueueOfToday = queueRepository.getMaxQueueOfToday();
            if (maxQueueOfToday == null || maxQueueOfToday == 0) {
                queueRepository.save(new Queue(1L));
                return new ApiResponse(true, "Queue was created successfully!. Your queue is 1");
            } else {
                queueRepository.save(new Queue(maxQueueOfToday + 1L));
                return new ApiResponse(true, "Queue was created successfully!. Your queue is " + (maxQueueOfToday + 1L));
            }
        }
        else {
            return new ApiResponse(false, "You already have a queue!. Please, wait...");
        }
    }
    @Override
    public ApiResponse queueStatusChanger(QueueStatusChangerDto dto) {
        Optional<Queue> optionalQueue = queueRepository.findById(dto.id);
        if (optionalQueue.isPresent()) {
            Queue queue = optionalQueue.get();
            queue.setStatus(dto.statusEnum);
            if (dto.statusEnum== QueueStatusEnum.CALLED){
                queue.setCalledAt(new Timestamp(System.currentTimeMillis()));
            }else if (dto.statusEnum== QueueStatusEnum.RUNNING){
                queue.setStartedAt(new Timestamp(System.currentTimeMillis()));
            }else if (dto.statusEnum== QueueStatusEnum.WAITING){
                queue.setWaitingAt(new Timestamp(System.currentTimeMillis()));
            }else {
                queue.setFinishedAt(new Timestamp(System.currentTimeMillis()));
            }
            queueRepository.save(queue);
            return new ApiResponse(true,"Status of queue was changed to "+dto.statusEnum.name());
        }
        else {
            return new ApiResponse(false,"Queue was not found by id: "+dto.id);
        }
    }


}
