package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface ErrorReminderService {
    ApiResponse getAllErrorsForSpecialUser(User user);
}
