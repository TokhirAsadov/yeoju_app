package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.attachment.PassportCopy;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.PassportCopyDto;
import uz.yeoju.yeoju_app.repository.PassportCopyRepo;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.PassportCopyImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassportCopyService implements PassportCopyImplService<PassportCopyDto> {
    public final PassportCopyRepo passportCopyRepo;
    public final UserService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all passport copies",
                passportCopyRepo.findAll().stream().map(this::generatePassportCopyDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return passportCopyRepo
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount passport copy by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not passport copy sheet by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        PassportCopy passportCopy = passportCopyRepo.getById(id);
        return new ApiResponse(true, "Fount passport copy by id", passportCopy);
    }

    @Override
    public ApiResponse saveOrUpdate(PassportCopyDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(PassportCopyDto dto){
        Optional<PassportCopy> optional = passportCopyRepo.findById(dto.getId());
        if (optional.isPresent()){
            PassportCopy passportCopy = optional.get();
            PassportCopy passportCopyByActive = passportCopyRepo.findPassportCopyByActive(true);
            if (passportCopyByActive!=null) {
                if (Objects.equals(passportCopyByActive.getId(), passportCopy.getId())) {
                    passportCopy.setUser(userService.generateUser(dto.getUserDto()));
                    passportCopy.setAttachment(dto.getAttachment());
                    passportCopy.setActive(dto.isActive());
                    passportCopyRepo.save(passportCopy);
                    return new ApiResponse(true, "passport copy updated successfully!..");
                }
                else {
                    return new ApiResponse(true, "Error... You have passport copy an active...");
                }
            }
            else {
                passportCopy.setUser(userService.generateUser(dto.getUserDto()));
                passportCopy.setAttachment(dto.getAttachment());
                passportCopy.setActive(dto.isActive());
                passportCopyRepo.save(passportCopy);
                return new ApiResponse(true, "passport copy updated successfully!..");
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount passport copy"
            );
        }
    }

    public ApiResponse save(PassportCopyDto dto){
        if (!passportCopyRepo.existsPassportCopyByUserId(dto.getUserDto().getId())){
            PassportCopy passportCopy = generatePassportCopy(dto);
            passportCopyRepo.saveAndFlush(passportCopy);
            return new ApiResponse(true,"new passport copy saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate passport copy! You have an active passport copy...!"
            );
        }
    }

    public PassportCopy generatePassportCopy(PassportCopyDto dto) {
        return new PassportCopy(
                userService.generateUser(dto.getUserDto()),
                dto.getAttachment(),
                dto.isActive()
        );
    }
    public PassportCopyDto generatePassportCopyDto(PassportCopy copy) {
        return new PassportCopyDto(
                copy.getId(),
                userService.generateUserDto(copy.getUser()),
                copy.getAttachment(),
                copy.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (passportCopyRepo.findById(id).isPresent()) {
            passportCopyRepo.deleteById(id);
            return new ApiResponse(true,"passport copy deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount passport copy!");
        }
    }


    @Override
    public ApiResponse findPassportCopiesByUserId(String user_id) {
        return new ApiResponse(
                true,
                "List of all passport copies by User_id",
                passportCopyRepo.findPassportCopiesByUserId(user_id)
                        .stream()
                        .map(this::generatePassportCopyDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse findPassportCopiesByActive(boolean active) {
        return new ApiResponse(
                true,
                "List of all passport copies by active = "+active,
                passportCopyRepo.findPassportCopiesByActive(active)
                        .stream()
                        .map(this::generatePassportCopyDto)
                        .collect(Collectors.toList())
        );
    }
}
