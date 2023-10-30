package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.PNotification;
import uz.yeoju.yeoju_app.entity.permissionPost.PReference;
import uz.yeoju.yeoju_app.entity.permissionPost.TypeOfReference;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearsForSelected;
import uz.yeoju.yeoju_app.payload.permissionDto.ChangeStatusDto;
import uz.yeoju.yeoju_app.payload.permissionDto.CreateReferenceDto;
import uz.yeoju.yeoju_app.payload.permissionDto.PReferenceDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.permissionPost.PReferenceRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.notification.PNotificationService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ReferenceImplService implements ReferenceService{
    private final PReferenceRepository referenceRepository;
    private final UserRepository userRepository;
    private final PNotificationService notificationService;
    private final UserService userService;
    private final EducationYearRepository educationYearRepository;


    @Override
    public PReference getReferenceById(String id) {
        return referenceRepository.findById(id).orElseThrow(() -> new RuntimeException("reference not found!"));
    }

    @Override
    public List<PReferenceDto> getAll() {
        return referenceRepository.findAllByOrderByCreatedAtDesc().stream().map(this::generateDto).collect(Collectors.toList());
    }

    @Override
    public List<PReferenceDto> getAllById(String userId) {
        return referenceRepository.findAllByCreatedByOrderByCreatedAtDesc(userId).stream().map(this::generateDto).collect(Collectors.toList());
    }

    @Override
    public List<PReferenceDto> getAllByIdForDean(String userId) {
        return referenceRepository.findAllByDeanIdAndStatusOrderByCreatedAtDesc(userId,PPostStatus.AT_PROCESS).stream().map(this::generateDto).collect(Collectors.toList());
    }

    @Override
    public List<PReferenceDto> getHistoryForDean(String userId) {
        return Stream.of(
                referenceRepository.findAllByDeanIdAndStatusOrderByCreatedAtDesc(userId,PPostStatus.CONFIRM).stream().map(this::generateDto).collect(Collectors.toList()),
                referenceRepository.findAllByDeanIdAndStatusOrderByCreatedAtDesc(userId,PPostStatus.REJECT).stream().map(this::generateDto).collect(Collectors.toList())
        ).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public ApiResponse create(User user, CreateReferenceDto dto) {
        if (Objects.equals(dto.getStudentId(), user.getId())) {
            Optional<User> optional = userRepository.findById(dto.getDeanId());
            if (optional.isPresent()) {
                Optional<EducationYear> educationYearOptional = educationYearRepository.findById(dto.getEducationYearId());
                if (educationYearOptional.isPresent()) {
                    User dean = optional.get();
                    EducationYear educationYear = educationYearOptional.get();

                    notificationService.save(PNotification.builder()
                            .delivered(false)
                            .content("new permission from " + user.getFirstName().charAt(0)+"."+user.getLastName())
                            .type(PPostStatus.REFERENCE)
                            .userFrom(user)
                            .userTo(dean).build());

                    PReference reference = new PReference();
                    Long maxNumeration = referenceRepository.getMaxNumeration();
                    if (maxNumeration==null) {
                        reference.setNumeration(1L);
                    }
                    else {
                        reference.setNumeration(maxNumeration+1);
                    }
                    reference.setStudent(user);
                    reference.setDean(dean);
                    reference.setStatus(PPostStatus.AT_PROCESS);
                    reference.setType(dto.getType());
                    reference.setEducationYear(educationYear);
                    reference.setDescription(dto.getDescription());
                    referenceRepository.saveAndFlush(reference);
                    return new ApiResponse(true,"send new reference successfully,please wait a request by dean");
                }
                else {
                    return new ApiResponse(false,"not found education year by id: "+dto.getEducationYearId());
                }
            }
            else {
                return new ApiResponse(false,"not found dean by id: "+dto.getDeanId());
            }
        }
        else {
            return new ApiResponse(false,"siz qoida buzarlik qilyapsiz!!!!!!!!!");
        }
    }

    @Override
    public ApiResponse changeStatusOfReference(User user, ChangeStatusDto dto) {
        PReference referenceById = getReferenceById(dto.getId());
        referenceById.setStatus(dto.getStatus());
        if (dto.getStatus().equals(PPostStatus.CONFIRM)) {
            Long maxQueue = referenceRepository.getMaxQueue();
            if (maxQueue==null) {
                referenceById.setQueue(1L);
            }
            else {
                referenceById.setQueue(maxQueue+1);
            }
        }

        Optional<User> userOptional = userRepository.findById(referenceById.getCreatedBy());
        notificationService.save(PNotification.builder()
                .delivered(false)
                .content(dto.getStatus().toString()+" new answer from " + user.getFullName())
//                .content(dto.getStatus().toString()+" new answer from " + user.getFirstName().charAt(0)+"."+user.getLastName())
                .type(PPostStatus.REFERENCE)
                .userFrom(user)
                .userTo(userOptional.get()).build());

        return new ApiResponse(true,"change status",referenceRepository.save(referenceById));
    }

    @Override
    public Flux<ServerSentEvent<List<PReferenceDto>>> streamReferences(String userId,Boolean bool) {
        return Flux.interval(Duration.ofSeconds(1))
                .publishOn(Schedulers.boundedElastic())
                .map(sequence -> ServerSentEvent.<List<PReferenceDto>>builder().id(String.valueOf(sequence))
                        .event("reference-list-event").data(bool ? getAllByIdForDean(userId) : getAllById(userId))
                        .build());
    }

    @Override
    public ApiResponse getTypesOfReference() {
        return new ApiResponse(true,"all types of reference", TypeOfReference.values());
    }

    @Override
    public ApiResponse checkPreference(String studentId) {
        EducationYearsForSelected educationYearsForSelected = educationYearRepository.getEducationYearsForSelected2();
        Boolean bool = referenceRepository.existsPReferenceByStudentIdAndEducationYearIdAndStatus(studentId, educationYearsForSelected.getId(),PPostStatus.CONFIRM);
        Boolean bool2 = referenceRepository.existsPReferenceByStudentIdAndEducationYearIdAndStatus(studentId, educationYearsForSelected.getId(),PPostStatus.AT_PROCESS);

        if (bool){
            return new ApiResponse(!bool,"Siz bu o`quv yili uchun boshqa ma'lumotnoma olishingiz mumkin emas.");
        }
        else if (bool2){
            return new ApiResponse(!bool2,"Siz so`rov jo`natgansiz. Iltimos dekanatning javobini kuting");
        }
        else {
            return new ApiResponse(!bool,"Biroz kuting.");
        }
    }

    public PReferenceDto generateDto(PReference reference){
        return new PReferenceDto(
                reference.getId(),
                reference.getQueue(),
                reference.getNumeration(),
                reference.getCreatedAt(),
                reference.getUpdatedAt(),
                reference.getCreatedBy(),
                reference.getUpdatedBy(),

                reference.getStatus(),
                userService.getUserFields(reference.getCreatedBy()),
                reference.getType(),
                reference.getDescription(),
                reference.getEducationYear().getName()
        );
    }

}
