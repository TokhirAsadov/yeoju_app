package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.EducationType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.EducationTypeDto;
import uz.yeoju.yeoju_app.repository.EducationTypeRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.EducationTypeImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTypeService implements EducationTypeImplService<EducationTypeDto> {
    public final EducationTypeRepository educationTypeRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all education types",
                educationTypeRepository.findAll().stream().map(this::generateEduTypeDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return educationTypeRepository
                .findById(id)
                .map(educationType -> new ApiResponse(true, "Fount education type by id",generateEduTypeDto(educationType) ))
                .orElseGet(() -> new ApiResponse(false, "Not fount education type by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        EducationType educationType = educationTypeRepository.getById(id);
        return new ApiResponse(true, "Fount education type by id", educationType);
    }

    @Override
    public ApiResponse saveOrUpdate(EducationTypeDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(EducationTypeDto dto){
        Optional<EducationType> optional = educationTypeRepository.findById(dto.getId());
        if (optional.isPresent()){
            EducationType type = optional.get();
            EducationType typeByName = educationTypeRepository.getEducationTypeByName(dto.getName());
            if (typeByName!=null) {
                if (
                        Objects.equals(typeByName.getId(), type.getId())
                                ||
                                !educationTypeRepository.existsEducationTypeByName(dto.getName())
                ) {
                    type.setName(dto.getName());
                    educationTypeRepository.save(type);
                    return new ApiResponse(true, "type updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved type! Please, enter other type userPositionName!.."
                    );
                }
            }
            else {
                if (!educationTypeRepository.existsEducationTypeByName(dto.getName())){
                    type.setName(dto.getName());
                    educationTypeRepository.save(type);
                    return new ApiResponse(true,"type updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved type! Please, enter other type userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount faculty"
            );
        }
    }

    public ApiResponse save(EducationTypeDto dto){
        if (!educationTypeRepository.existsEducationTypeByName(dto.getName())){
            EducationType type = generateEduType(dto);
            educationTypeRepository.saveAndFlush(type);
            return new ApiResponse(true,"new type saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved type! Please, enter other type userPositionName!"
            );
        }
    }

    public EducationType generateEduType(EducationTypeDto dto) {
        return new EducationType(dto.getName());
    }
    public EducationTypeDto generateEduTypeDto(EducationType type) {
        return new EducationTypeDto(type.getId(),type.getName());
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (educationTypeRepository.findById(id).isPresent()) {
            educationTypeRepository.deleteById(id);
            return new ApiResponse(true,"education type deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount education type!");
        }
    }
}
