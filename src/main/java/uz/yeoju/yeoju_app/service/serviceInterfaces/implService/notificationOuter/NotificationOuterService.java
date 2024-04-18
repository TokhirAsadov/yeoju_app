package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;

public interface NotificationOuterService {
    ApiResponse findAllNotifications();
    ApiResponse getAllCounters();
    ApiResponse getStudentNotifications(String studentId);
    ApiResponse createAndUpdate(NotificationOuterCreateDto dto);

    ApiResponse delete(User user, String id);
}
