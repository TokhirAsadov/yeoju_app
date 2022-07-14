package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Gander;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GanderDto;
import uz.yeoju.yeoju_app.repository.GanderRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GanderImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GanderService implements GanderImplService<GanderDto> {
    public final GanderRepository ganderRepository;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all gander",
                ganderRepository.findAll().stream().map(this::generateGanderDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(Long id) {
        return ganderRepository
                .findById(id)
                .map(gander -> new ApiResponse(true, "Fount gander by id", gander))
                .orElseGet(() -> new ApiResponse(false, "Not fount gander by id"));
    }

    @Override
    public ApiResponse getById(Long id) {
        Gander gander = ganderRepository.getById(id);
        return new ApiResponse(true, "Fount gander by id", gander);
    }

    @Override
    public ApiResponse saveOrUpdate(GanderDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(GanderDto dto){
        Optional<Gander> optional = ganderRepository.findById(dto.getId());
        if (optional.isPresent()){
            Gander gander = optional.get();
            Gander ganderByGanderName = ganderRepository.getGanderByGandername(dto.getGanderName());
            if (ganderByGanderName!=null) {
                if (
                        Objects.equals(ganderByGanderName.getId(), gander.getId())
                                ||
                                !ganderRepository.existsGanderByGandername(dto.getGanderName())
                ) {
                    gander.setGandername(dto.getGanderName());
                    ganderRepository.save(gander);
                    return new ApiResponse(true, "gander updated successfully");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved gander! Please, enter other gander userPositionName!.."
                    );
                }
            }
            else {
                if ( !ganderRepository.existsGanderByGandername(dto.getGanderName()) ){
                    gander.setGandername(dto.getGanderName());
                    ganderRepository.save(gander);
                    return new ApiResponse(true,"gander updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved gander! Please, enter other gander userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount gander"
            );
        }
    }

    public ApiResponse save(GanderDto dto){
        if (!ganderRepository.existsGanderByGandername(dto.getGanderName())){
            Gander gander = generateGander(dto);
            ganderRepository.saveAndFlush(gander);
            return new ApiResponse(true,"new gander saved successfully");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved gander! Please, enter other gander userPositionName"
            );
        }
    }

    public Gander generateGander(GanderDto dto) {
        return new Gander(dto.getId(),dto.getGanderName());
    }
    public GanderDto generateGanderDto(Gander gander) {
        return gander!=null ? new GanderDto(gander.getId(),gander.getGandername()) : null;
    }


    @Override
    public ApiResponse deleteById(Long id) {
        if (ganderRepository.findById(id).isPresent()) {
            ganderRepository.deleteById(id);
            return new ApiResponse(true,"gander deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount gander");
        }
    }
}
