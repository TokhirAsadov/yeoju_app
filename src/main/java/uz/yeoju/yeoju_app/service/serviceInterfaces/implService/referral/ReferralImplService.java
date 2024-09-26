package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.ReferralRepository;

@Service
@RequiredArgsConstructor
public class ReferralImplService implements ReferralService{

    private final ReferralRepository referralRepository;

    @Override
    public ApiResponse findAllReferrals() {
        return null;
    }
}
