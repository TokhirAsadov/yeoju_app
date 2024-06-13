package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.uquvbulim.ErrorReminder;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.ErrorReminderData;
import uz.yeoju.yeoju_app.repository.uquvbulimi.ErrorReminderRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorReminderImplService implements ErrorReminderService{
    private final ErrorReminderRepository errorReminderRepository;

    @Override
    public ApiResponse getAllErrorsForSpecialUser(User user) {
        return new ApiResponse(true,"all errors for special user",errorReminderRepository.findAllByCreatedByAndActiveOrderByCreatedAtDesc(user.getId(), true).stream().map(this::generateErrorData).collect(Collectors.toSet()));
    }

    @Override
    public ApiResponse changeActivityOfError(User user, String type, String id) {
        return null;
    }

    public ErrorReminderData generateErrorData(ErrorReminder errorReminder) {
        return new ErrorReminderData(errorReminder.getId(), errorReminder.getError(),errorReminder.getCreatedAt());
    }
}
