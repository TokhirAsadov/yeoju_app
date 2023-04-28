package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.attachment.UserDiploma;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDiplomaDto;
import uz.yeoju.yeoju_app.repository.UserDiplomaRepo;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserDiplomaImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDiplomaService implements UserDiplomaImplService<UserDiplomaDto> {
    public final UserDiplomaRepo userDiplomaRepo;
    public final UserService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all user diplomas",
                userDiplomaRepo.findAll().stream().map(this::generateUserDiplomaDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return userDiplomaRepo
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount user diploma by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not user diploma sheet by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        UserDiploma userDiploma = userDiplomaRepo.getById(id);
        return new ApiResponse(true, "Fount user diploma by id", userDiploma);
    }

    @Override
    public ApiResponse saveOrUpdate(UserDiplomaDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(UserDiplomaDto dto){
        Optional<UserDiploma> optional = userDiplomaRepo.findById(dto.getId());
        if (optional.isPresent()){
            UserDiploma userDiploma = optional.get();
            UserDiploma userDiplomaByActive = userDiplomaRepo.findUserDiplomaByActive(true);
            if (userDiplomaByActive !=null) {
                if (Objects.equals(userDiplomaByActive.getId(), userDiploma.getId())) {
                    userDiploma.setUser(userService.generateUser(dto.getUserDto()));
                    userDiploma.setAttachmentList(dto.getAttachmentList());
                    userDiploma.setActive(dto.isActive());
                    userDiplomaRepo.save(userDiploma);
                    return new ApiResponse(true, "user diploma updated successfully!..");
                }
                else {
                    return new ApiResponse(true, "Error... You have user diploma an active...");
                }
            }
            else {
                userDiploma.setUser(userService.generateUser(dto.getUserDto()));
                userDiploma.setAttachmentList(dto.getAttachmentList());
                userDiploma.setActive(dto.isActive());
                userDiplomaRepo.save(userDiploma);
                return new ApiResponse(true, "user diploma updated successfully!..");
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount user diploma"
            );
        }
    }

    public ApiResponse save(UserDiplomaDto dto){
        if (!userDiplomaRepo.existsUserDiplomaByUserId(dto.getUserDto().getId())){
            UserDiploma userDiploma = generateUserDiploma(dto);
            userDiplomaRepo.saveAndFlush(userDiploma);
            return new ApiResponse(true,"new user diploma saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate user diplomas! You have an active user diplomas...!"
            );
        }
    }

    public UserDiploma generateUserDiploma(UserDiplomaDto dto) {
        return new UserDiploma(
                userService.generateUser(dto.getUserDto()),
                dto.getAttachmentList(),
                dto.isActive()
        );
    }
    public UserDiplomaDto generateUserDiplomaDto(UserDiploma diploma) {
        return new UserDiplomaDto(
                diploma.getId(),
                userService.generateUserDto(diploma.getUser()),
                diploma.getAttachmentList(),
                diploma.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (userDiplomaRepo.findById(id).isPresent()) {
            userDiplomaRepo.deleteById(id);
            return new ApiResponse(true,"user diplomas deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount user diplomas!");
        }
    }


    @Override
    public ApiResponse findUserDiplomasByUserId(String user_id) {
        return new ApiResponse(
                true,
                "List of all user diplomas by user_id",
                userDiplomaRepo.findUserDiplomasByUserId(user_id)
                        .stream()
                        .map(this::generateUserDiplomaDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse findUserDiplomasByActive(boolean active) {
        return new ApiResponse(
                true,
                "List of all user diplomas by active = "+active,
                userDiplomaRepo.findUserDiplomasByActive(active)
                        .stream()
                        .map(this::generateUserDiplomaDto)
                        .collect(Collectors.toList())
        );
    }
}
