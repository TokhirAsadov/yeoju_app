package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;

@Service
@RequiredArgsConstructor
public class NotificationOuterImplService implements NotificationOuterService{
    @Override
    public ApiResponse findAllNotifications() {
        return null;
    }
}
