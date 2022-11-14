package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;

import java.time.LocalDateTime;
import java.util.Date;

public interface KafedraMudiriService {

    ApiResponse findAll();
    ApiResponse save(KafedraMudiriSaving saving);

    ApiResponse getStatistics(User user);

    ApiResponse getStatistics(User user, String userId,Date date);
}
