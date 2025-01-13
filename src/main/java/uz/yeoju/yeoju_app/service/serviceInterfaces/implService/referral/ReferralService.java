package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ReferralCreateDto;
import uz.yeoju.yeoju_app.payload.dekanat.NotificationOuterCreateDto;

public interface ReferralService {
    ApiResponse findAllReferrals();
    ApiResponse getAllCounters();
    ApiResponse getStudentReferrals(String studentId);
    ApiResponse createAndUpdate(ReferralCreateDto dto);
//
    ApiResponse delete(User user, String id);
}
