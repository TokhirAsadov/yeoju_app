package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Kafedra;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.KafedraDto;
import uz.yeoju.yeoju_app.repository.KafedraRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.KafedraImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafedraService implements KafedraImplService<KafedraDto> {
    public final KafedraRepository kafedraRepository;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all kafedra",
                kafedraRepository.findAll().stream().map(this::generateKafedraDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return kafedraRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount Kafedra by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount Kafedra by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Kafedra kafedra = kafedraRepository.getById(id);
        return new ApiResponse(true, "Fount Kafedra by id", kafedra);
    }

    @Override
    public ApiResponse saveOrUpdate(KafedraDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(KafedraDto dto){
        Optional<Kafedra> optional = kafedraRepository.findById(dto.getId());
        if (optional.isPresent()){
            Kafedra kafedra = optional.get();
            Kafedra kafedraByName = kafedraRepository.getKafedraByName(dto.getName());
            if (kafedraByName !=null) {
                if (
                        Objects.equals(kafedraByName.getId(), kafedra.getId())
                                ||
                                !kafedraRepository.existsKafedraByName(dto.getName())
                ) {
                    kafedra.setName(dto.getName());
                    kafedraRepository.save(kafedra);
                    return new ApiResponse(true, "kafedra updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved kafedra! Please, enter other kafedra name!.."
                    );
                }
            }
            else {
                if (!kafedraRepository.existsKafedraByName(dto.getName())){
                    kafedra.setName(dto.getName());
                    kafedraRepository.save(kafedra);
                    return new ApiResponse(true,"kafedra updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved kafedra! Please, enter other kafedra name!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount Kafedra"
            );
        }
    }

    public ApiResponse save(KafedraDto dto){
        if (!kafedraRepository.existsKafedraByName(dto.getName())){
            Kafedra kafedra = generateKafedra(dto);
            kafedraRepository.saveAndFlush(kafedra);
            return new ApiResponse(true,"new Kafedra saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved Kafedra! Please, enter other Kafedra name!"
            );
        }
    }

    public Kafedra generateKafedra(KafedraDto dto) {
        return new Kafedra(dto.getName());
    }
    public KafedraDto generateKafedraDto(Kafedra kafedra) {
        return new KafedraDto(kafedra.getId(), kafedra.getName());
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (kafedraRepository.findById(id).isPresent()) {
            kafedraRepository.deleteById(id);
            return new ApiResponse(true,"Kafedra deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount Kafedra!");
        }
    }
}
