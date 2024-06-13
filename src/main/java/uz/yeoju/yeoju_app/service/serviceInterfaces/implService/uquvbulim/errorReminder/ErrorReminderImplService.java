package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.errorReminder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.uquvbulim.ErrorReminder;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.ErrorReminderData;
import uz.yeoju.yeoju_app.repository.uquvbulimi.ErrorReminderRepository;

import java.util.List;
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
        if (type.equals("ALL")){
            List<ErrorReminder> all = errorReminderRepository.findAllByCreatedByAndActiveOrderByCreatedAtDesc(user.getId(), true);
            List<ErrorReminder> collect = all.stream().peek(error -> error.setActive(false)).collect(Collectors.toList());
            errorReminderRepository.saveAll(collect);
            return new ApiResponse(true,"all errors are non-active");
        }
        else if (id!=null && !id.isEmpty()){
            errorReminderRepository.findById(id).ifPresent(errorReminder -> {
                errorReminder.setActive(false);
                errorReminderRepository.save(errorReminder);
            });
            return new ApiResponse(true,"error is non-active");
        }
        return new ApiResponse(false,"Error!.. Type: "+type+"; or id: "+id+" is not valid");
    }

    public ErrorReminderData generateErrorData(ErrorReminder errorReminder) {
        return new ErrorReminderData(errorReminder.getId(), errorReminder.getError(),errorReminder.getCreatedAt());
    }
}
