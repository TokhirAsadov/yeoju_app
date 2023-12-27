package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.queue;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.QueueRepository;

@Service
@RequiredArgsConstructor
public class QueueImplService implements QueueService{
    private final QueueRepository queueRepository;


    @Override
    public ApiResponse findAllQueue() {
        return null;
    }
}
