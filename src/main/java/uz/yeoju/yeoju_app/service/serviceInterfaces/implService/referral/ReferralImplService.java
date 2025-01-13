package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.referral;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GetReferralDto;
import uz.yeoju.yeoju_app.payload.ReferralCreateDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetStudentReferrals;
import uz.yeoju.yeoju_app.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReferralImplService implements ReferralService{

    private final ReferralRepository referralRepository;
    private final FacultyRepository facultyRepository;
    private final DekanatRepository dekanatRepository;
    private final GroupRepository groupRepository;
    private final ReferralCounterRepository counterRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse findAllReferrals() {
        return new ApiResponse(true,"all notifications", referralRepository.findAll().stream().map(this::generateDto).collect(Collectors.toSet()));
    }

    @Override
    public ApiResponse getAllCounters() {
        return new ApiResponse(true,"all counters",counterRepository.getAllCounters());
    }

    @Override
    public ApiResponse getStudentReferrals(String studentId) {
        for (GetStudentReferrals outer : referralRepository.getStudentReferrals(studentId)) {
            Boolean exists = counterRepository.existsByUserIdAndReferralId(studentId, outer.getId());
            if (!exists) {
                Optional<User> userOptional = userRepository.findById(studentId);
                if (userOptional.isPresent()){
                    Optional<Referral> outerOptional = referralRepository.findById(outer.getId());
                    if (outerOptional.isPresent()){
                        User user = userOptional.get();
                        Referral referral = outerOptional.get();
                        Long aLong = counterRepository.maxQueue();
                        if (aLong==null || aLong<1) {
                            counterRepository.save(new ReferralCounter(
                                    1L,
                                    user,
                                    referral
                            ));
                        }
                        else {
                            counterRepository.save(new ReferralCounter(
                                    aLong+1L,
                                    user,
                                    referral
                            ));
                        }

                    }
                    else {
                        return new ApiResponse(false,"referral was not found by id: " + outer.getId());
                    }
                }
                else {
                    return new ApiResponse(false,"student not found by id: " + studentId);
                }
            }
        }


        return new ApiResponse(true,"student referrals",referralRepository.getStudentNotificationOuters2(studentId));
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

    @Override
    public ApiResponse delete(User user, String id) {
        Optional<Referral> referralOptional = referralRepository.findById(id);
        if (referralOptional.isPresent()){
            Referral referral = referralOptional.get();
            if (Objects.equals(referral.getCreatedBy(), user.getId())){
                Boolean b = counterRepository.existsByUserIdAndReferralId(user.getId(), referral.getId());
                if (!b) {
                    referralRepository.deleteById(id);
                    return new ApiResponse(true,"referral was deleted successfully");
                }
                else {
                    return new ApiResponse(false,"You cannot delete this referral. Because this referral was already used other place.");
                }
            }
            else {
                return new ApiResponse(false,"You cannot delete any referral.");
            }
        }
        return new ApiResponse(false,"referral was not found by id: " + id);
    }

    public ApiResponse save(ReferralCreateDto dto){
        Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dto.getDekanatId());
        if (optionalDekanat.isPresent()) {
            Dekanat dekanat = optionalDekanat.get();
            List<Faculty> faculties = facultyRepository.findAllById(dto.facultiesId);
            List<Group> groups = groupRepository.findAllById(dto.getGroupsId());
            Referral referral = new Referral();
            Long l = referralRepository.maxQueue()==null ? 0 : referralRepository.maxQueue()+1L;
            referral.setQueue(l);
            referral.setFaculties(new HashSet<>(faculties));
            referral.setGroups(new HashSet<>(groups));
            referral.setDekanat(dekanat);
            referral.setDecisionDate(dto.getDecisionDate());
            referral.setNumberOfDecision(dto.getNumberOfDecision());
            referral.setCourse(dto.getCourse());
            referral.setFromDate(dto.getFromDate());
            referral.setToDate(dto.getToDate());
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
