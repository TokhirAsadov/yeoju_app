package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;
import uz.yeoju.yeoju_app.repository.NotificationOuterRepository;

@Service
@RequiredArgsConstructor
public class NotificationOuterImplService implements NotificationOuterService{
    private final NotificationOuterRepository repository;

    @Override
    public ApiResponse findAllNotifications() {
        return new ApiResponse(true,"all notifications",repository.findAll());
    }

    @Override
    public ApiResponse createAndUpdate(NotificationOuterCreateDto dto) {
        return null;
    }
}
