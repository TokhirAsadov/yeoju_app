package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.studentBall.SubjectCredit;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.studentBall.SubjectCreditDto;
import uz.yeoju.yeoju_app.repository.SubjectCreditRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.SubjectCreditImplService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectCreditService implements SubjectCreditImplService<SubjectCreditDto> {
    private final SubjectCreditRepository creditRepository;
    private final GroupService groupService;
    private final LessonService lessonService;


    public ApiResponse getSubjectCreditsByGroupName(String groupName){
        return new ApiResponse(
                true,
                "all retake",
                creditRepository.getSubjectCreditsByGroupName(groupName)
                        .stream()
                        .map(this::generateSubjectCreditDto)
                        .collect(Collectors.toSet())
        );
    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All retake",
                creditRepository.findAll().stream()
                        .map(this::generateSubjectCreditDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> SubjectCreditDto generateSubjectCreditDto(SubjectCredit subjectCredit) {
        return new SubjectCreditDto(
                subjectCredit.getId(),
                lessonService.generateLessonDto(subjectCredit.getLesson()),
                subjectCredit.getCredit(),
                groupService.generateGroupDto(subjectCredit.getGroup()),
                subjectCredit.getYear(),
                subjectCredit.getSemester()
        );
    }

    @Override
    public ApiResponse findById(Long id) {
        return new ApiResponse(
                true,
                "subject credit",
                generateSubjectCreditDto(creditRepository.findById(id).get())
        );
    }

    @Override
    public ApiResponse getById(Long id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(SubjectCreditDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            ApiResponse byId = groupService.getById(dto.getGroupDto().getId());
            if (byId.getObj()!=null) {
                ApiResponse lessonServiceById = lessonService.getById(dto.getLessonDto().getId());
                if (lessonServiceById.getObj() != null){
                    creditRepository.save(generateSubjectCredit(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount subject");
            }
            return new ApiResponse(false,"not fount group");
        }
        else { //update
            ApiResponse byId = groupService.getById(dto.getGroupDto().getId());
            if (byId.getObj()!=null) {
                ApiResponse lessonServiceById = lessonService.getById(dto.getLessonDto().getId());
                if (lessonServiceById.getObj() != null){
                    creditRepository.save(generateSubjectCredit(dto));
                    return new ApiResponse(true,"updated");
                }
                return new ApiResponse(false,"not fount subject");
            }
            return new ApiResponse(false,"not fount group");
        }
    }

    public SubjectCredit generateSubjectCredit(SubjectCreditDto dto) {
        return new SubjectCredit(
                dto.getId(),
                lessonService.generateLesson(dto.getLessonDto()),
                dto.getCredit(),
                groupService.generateGroup(dto.getGroupDto()),
                dto.getYear(),
                dto.getSemester()
        );
    }


    @Override
    public ApiResponse deleteById(Long id) {
        return null;
    }
}
