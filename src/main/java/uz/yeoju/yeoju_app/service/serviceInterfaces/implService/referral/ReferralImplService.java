package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.entity.dekanat.Referral;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GetReferralDto;
import uz.yeoju.yeoju_app.payload.ReferralCreateDto;
import uz.yeoju.yeoju_app.repository.DekanatRepository;
import uz.yeoju.yeoju_app.repository.FacultyRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.ReferralRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferralImplService implements ReferralService{

    private final ReferralRepository referralRepository;
    private final FacultyRepository facultyRepository;
    private final DekanatRepository dekanatRepository;
    private final GroupRepository groupRepository;

    @Override
    public ApiResponse findAllReferrals() {
        return new ApiResponse(true,"all notifications", referralRepository.findAll().stream().map(this::generateDto).collect(Collectors.toSet()));
    }

    @Override
    public ApiResponse createAndUpdate(ReferralCreateDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse save(ReferralCreateDto dto){
        Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dto.getDekanatId());
        if (optionalDekanat.isPresent()) {
            Dekanat dekanat = optionalDekanat.get();
            List<Faculty> faculties = facultyRepository.findAllById(dto.facultiesId);
            List<Group> groups = groupRepository.findAllById(dto.getGroupsId());
            Referral referral = new Referral();
            referral.setQueue(referralRepository.maxQueue() + 1L);
            referral.setFaculties(new HashSet<>(faculties));
            referral.setGroups(new HashSet<>(groups));
            referral.setDekanat(dekanat);
            referral.setDecisionDate(dto.getDecisionDate());
            referral.setNumberOfDecision(dto.getNumberOfDecision());
            referral.setCourse(dto.getCourse());
            referral.setFromDate(dto.getFromDate());
            referral.setToDate(dto.getToDate());
            referral.setDynamicSection(dto.getDynamicSection());
            referralRepository.save(referral);
            return new ApiResponse(true, "Referral was created successful");
        }
        else {
            return new ApiResponse(false,"dekanat was not found by id: " + dto.getDekanatId());
        }
    }

    public ApiResponse update(ReferralCreateDto dto){
        Optional<Referral> referralOptional = referralRepository.findById(dto.getId());
        if (referralOptional.isPresent()){
            Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dto.getDekanatId());
            if (optionalDekanat.isPresent()) {
                Dekanat dekanat = optionalDekanat.get();
                List<Faculty> faculties = facultyRepository.findAllById(dto.facultiesId);
                List<Group> groups = groupRepository.findAllById(dto.getGroupsId());
                Referral referral = referralOptional.get();
                referral.setDekanat(dekanat);
                referral.setFaculties(new HashSet<>(faculties));
                referral.setGroups(new HashSet<>(groups));
                referral.setDecisionDate(dto.getDecisionDate());
                referral.setNumberOfDecision(dto.getNumberOfDecision());
                referral.setCourse(dto.getCourse());
                referral.setFromDate(dto.getFromDate());
                referral.setToDate(dto.getToDate());
                referral.setDynamicSection(dto.getDynamicSection());
                referralRepository.save(referral);
                return new ApiResponse(true, "notification was updated successful");
            }
            else {
                return new ApiResponse(false,"dekanat was not found by id: " + dto.getDekanatId());
            }
        }
        else {
            return new ApiResponse(false,"notification was not found by id: " + dto.getId());
        }

    }

    public GetReferralDto generateDto(Referral referral){
        return new GetReferralDto(
                referral.getId(),
                referral.getQueue(),
                referral.getDynamicSection(),
                referral.getNumberOfDecision(),
                referral.getDecisionDate(),
                referral.getCourse(),
                referral.getFromDate(),
                referral.getToDate(),
                referral.getDekanat().getName(),
                referral.getFaculties().stream().map(Faculty::getShortName).collect(Collectors.toSet()),
                referral.getGroups().stream().map(Group::getName).collect(Collectors.toSet())
        );
    }
}
