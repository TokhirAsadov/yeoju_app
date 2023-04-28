package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.studentBall.Retake;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.payload.studentBall.RetakeDto;
import uz.yeoju.yeoju_app.repository.RetakeRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.RetakeImplService;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RetakeService implements RetakeImplService<RetakeDto> {
    private final RetakeRepository retakeRepository;
    private final UserService userService;
    private final LessonService lessonService;


    public ApiResponse getRetakesByStudentId(String studentId){
        return new ApiResponse(
                true,
                "all retake",
                retakeRepository.getRetakesByUserId(studentId)
                        .stream()
                        .map(this::generateRetakeDto)
                        .collect(Collectors.toSet())
        );
    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All retake",
                retakeRepository.findAll().stream()
                        .map(this::generateRetakeDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> RetakeDto generateRetakeDto(Retake retake) {
        return new RetakeDto(
                retake.getId(),
                userService.generateUserDto(retake.getUser()),
                lessonService.generateLessonDto(retake.getLesson()),
                retake.getCommandNumber(),
                retake.getCommandDate(),
                retake.getYear(),
                retake.getSemester(),
                retake.getScore()
        );
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public ApiResponse getById(Long id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(RetakeDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                ApiResponse lessonServiceById = lessonService.getById(dto.getLessonDto().getId());
                if (lessonServiceById.getObj() != null){
                    retakeRepository.save(generateRetake(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount subject");
            }
            return new ApiResponse(false,"not fount user");
        }
        else { //update
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                ApiResponse lessonServiceById = lessonService.getById(dto.getLessonDto().getId());
                if (lessonServiceById.getObj() != null){
                    retakeRepository.save(generateRetake(dto));
                    return new ApiResponse(true,"updated");
                }
                return new ApiResponse(false,"not fount subject");
            }
            return new ApiResponse(false,"not fount user");
        }
    }

    private Retake generateRetake(RetakeDto dto) {
        return new Retake(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                lessonService.generateLesson(dto.getLessonDto()),
                dto.getCommandNumber(),
                dto.getCommandDate(),
                dto.getYear(),
                dto.getSemester(),
                dto.getScore()
        );
    }


    @Override
    public ApiResponse deleteById(Long id) {
        return null;
    }
}
