package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notificationOuter;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;

public interface NotificationOuterService {
    ApiResponse findAllNotifications();
    ApiResponse getStudentNotifications(String studentId);
    ApiResponse createAndUpdate(NotificationOuterCreateDto dto);
}