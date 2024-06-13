package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.uquvbulimi.ErrorReminderRepository;

@Service
@RequiredArgsConstructor
public class ErrorReminderImplService implements ErrorReminderService{
    private final ErrorReminderRepository errorReminderRepository;

    @Override
    public ApiResponse getAllErrorsForSpecialUser(User user) {
        return null;
    }
}
