package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.dekan.Dekan;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.payload.dekan.DekanDto;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.DekanImplService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DekanService implements DekanImplService<DekanDto> {
    private final DekanRepository dekanRepository;
    private final UserService userService;
    private final FacultyService facultyService;


//    public ApiResponse getRetakesByStudentId(String studentId){
//        return new ApiResponse(
//                true,
//                "all retake",
//                dekanRepository.getRetakesByUserId(studentId)
//                        .stream()
//                        .map(this::generateRetakeDto)
//                        .collect(Collectors.toSet())
//        );
//    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All dekan",
                dekanRepository.findAll().stream()
                        .map(this::generateDekanDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> DekanDto generateDekanDto(Dekan dekan) {
        return new DekanDto(
                dekan.getId(),
                userService.generateUserDto(dekan.getUser()),
                dekan.getFaculties().stream().map(facultyService::generateFacultyDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(DekanDto dto) {

        if (dto.getId() == null){ //save
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                Set<FacultyDto> facultyDtos = dto.getFacultyDtos();
                boolean has=true;
                for (FacultyDto facultyDto : facultyDtos) {
                    ApiResponse byId = facultyService.getById(facultyDto.getId());
                    if (!byId.isSuccess()){
                        has = false;
                        break;
                    }
                }
                if (has){
                    dekanRepository.save(generateDekan(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount faculty");
            }
            return new ApiResponse(false,"not fount user");
        }
        else { //update
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                Set<FacultyDto> facultyDtos = dto.getFacultyDtos();
                boolean has=true;
                for (FacultyDto facultyDto : facultyDtos) {
                    ApiResponse byId = facultyService.getById(facultyDto.getId());
                    if (!byId.isSuccess()){
                        has = false;
                        break;
                    }
                }
                if (has){
                    dekanRepository.save(generateDekan(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount faculty");
            }
            return new ApiResponse(false,"not fount user");
        }
    }

    private Dekan generateDekan(DekanDto dto) {
        return new Dekan(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                dto.getFacultyDtos().stream().map(facultyService::generateFaculty).collect(Collectors.toSet())
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }
}
