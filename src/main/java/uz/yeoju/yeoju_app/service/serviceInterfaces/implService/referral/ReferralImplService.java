package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.dekanat.Referral;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GetReferralDto;
import uz.yeoju.yeoju_app.repository.ReferralRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferralImplService implements ReferralService{

    private final ReferralRepository referralRepository;

    @Override
    public ApiResponse findAllReferrals() {
        return new ApiResponse(true,"all notifications", referralRepository.findAll().stream().map(this::generateDto).collect(Collectors.toSet()));
    }

    public GetReferralDto generateDto(Referral notification){
        return new GetReferralDto(
                notification.getId(),
                notification.getQueue(),
                notification.getDynamicSection(),
                notification.getNumberOfDecision(),
                notification.getDecisionDate(),
                notification.getCourse(),
                notification.getFromDate(),
                notification.getToDate(),
                notification.getFaculties().stream().map(Faculty::getShortName).collect(Collectors.toSet()),
                notification.getGroups().stream().map(Group::getName).collect(Collectors.toSet())
        );
    }
}
