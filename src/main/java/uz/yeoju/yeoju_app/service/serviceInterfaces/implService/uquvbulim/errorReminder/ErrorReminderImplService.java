package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.uquvbulimi.ErrorReminderRepository;

@Service
@RequiredArgsConstructor
public class ErrorReminderImplService implements ErrorReminderService{
    private final ErrorReminderRepository errorReminderRepository;
}
