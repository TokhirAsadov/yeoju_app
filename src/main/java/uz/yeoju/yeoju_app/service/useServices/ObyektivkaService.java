package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.attachment.Obyektivka;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ObyektivkaDto;
import uz.yeoju.yeoju_app.repository.ObyektivkaRepo;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.ObyektivkaImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ObyektivkaService implements ObyektivkaImplService<ObyektivkaDto> {
    public final ObyektivkaRepo obyektivkaRepo;
    public final UserService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all personal sheets",
                obyektivkaRepo.findAll().stream().map(this::generateObyektivkaDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return obyektivkaRepo
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount personal sheet by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount personal sheet by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Obyektivka obyektivka = obyektivkaRepo.getById(id);
        return new ApiResponse(true, "Fount personal sheet by id", obyektivka);
    }

    @Override
    public ApiResponse saveOrUpdate(ObyektivkaDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(ObyektivkaDto dto){
        Optional<Obyektivka> optional = obyektivkaRepo.findById(dto.getId());
        if (optional.isPresent()){
            Obyektivka obyektivka = optional.get();
            Obyektivka obyektivkaByActive = obyektivkaRepo.findObyektivkaByActive(true);
            if (obyektivkaByActive!=null) {
                if (Objects.equals(obyektivkaByActive.getId(), obyektivka.getId())) {
                    obyektivka.setUser(userService.generateUser(dto.getUserDto()));
                    obyektivka.setAttachment(dto.getAttachment());
                    obyektivka.setActive(dto.isActive());
                    obyektivkaRepo.save(obyektivka);
                    return new ApiResponse(true, "personal sheet updated successfully!..");
                }
                else {
                    return new ApiResponse(true, "Error... You have an active personal sheet...");
                }
            }
            else {
                obyektivka.setUser(userService.generateUser(dto.getUserDto()));
                obyektivka.setAttachment(dto.getAttachment());
                obyektivka.setActive(dto.isActive());
                obyektivkaRepo.save(obyektivka);
                return new ApiResponse(true, "personal sheet updated successfully!..");
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount personal sheet"
            );
        }
    }

    public ApiResponse save(ObyektivkaDto dto){
        if (!obyektivkaRepo.existsObyektivkaByUserId(dto.getUserDto().getId())){
            Obyektivka obyektivka = generateObyektivka(dto);
            obyektivkaRepo.saveAndFlush(obyektivka);
            return new ApiResponse(true,"new personal sheet saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate personal sheet! You have an active personal sheet...!"
            );
        }
    }

    public Obyektivka generateObyektivka(ObyektivkaDto dto) {
        return new Obyektivka(
                userService.generateUser(dto.getUserDto()),
                dto.getAttachment(),
                dto.isActive()
        );
    }
    public ObyektivkaDto generateObyektivkaDto(Obyektivka obyektivka) {
        return new ObyektivkaDto(
                obyektivka.getId(),
                userService.generateUserDto(obyektivka.getUser()),
                obyektivka.getAttachment(),
                obyektivka.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (obyektivkaRepo.findById(id).isPresent()) {
            obyektivkaRepo.deleteById(id);
            return new ApiResponse(true,"personal sheet deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount personal sheet!");
        }
    }


    @Override
    public ApiResponse findObyektivkasByUserId(String user_id) {
        return new ApiResponse(
                true,
                "List of all personal sheets by User_id",
                obyektivkaRepo.findObyektivkasByUserId(user_id)
                        .stream()
                        .map(this::generateObyektivkaDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse findObyektivkasByActive(boolean active) {
        return new ApiResponse(
                true,
                "List of all personal sheets by active = "+active,
                obyektivkaRepo.findObyektivkasByActive(active)
                        .stream()
                        .map(this::generateObyektivkaDto)
                        .collect(Collectors.toList())
        );
    }
}
