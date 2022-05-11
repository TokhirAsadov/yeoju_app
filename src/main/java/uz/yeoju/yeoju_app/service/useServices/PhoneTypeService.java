package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.PhoneType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.PhoneTypeDto;
import uz.yeoju.yeoju_app.repository.PhoneTypeRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.PhoneTypeImplService;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhoneTypeService implements PhoneTypeImplService<PhoneTypeDto> {
    public final PhoneTypeRepository phoneTypeRepository;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"List of all phone type", phoneTypeRepository.findAll());
    }

    @Override
    public ApiResponse findById(Long id) {
        return phoneTypeRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount phone type by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not phone type faculty by id"));
    }

    @Override
    public ApiResponse getById(Long id) {
        PhoneType phoneType = phoneTypeRepository.getById(id);
        return new ApiResponse(true, "Fount phoneType by id", phoneType);
    }

    @Override
    public ApiResponse saveOrUpdate(PhoneTypeDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(PhoneTypeDto dto){
        Optional<PhoneType> optional = phoneTypeRepository.findById(dto.getId());
        if (optional.isPresent()){
            PhoneType phoneType = optional.get();
            PhoneType typeByName = phoneTypeRepository.getPhoneTypeByName(dto.getName());
            if (
                    Objects.equals(typeByName.getId(), phoneType.getId())
                    ||
                            !phoneTypeRepository.existsPhoneTypeByName(dto.getName())
            ){
                typeByName.setName(dto.getName());
                phoneTypeRepository.save(typeByName);
                return new ApiResponse(true,"phone type updated successfully!..");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! nor saved phone type! Please, enter other phone type name!.."
                );
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount phone type"
            );
        }
    }

    public ApiResponse save(PhoneTypeDto dto){
        if (!phoneTypeRepository.existsPhoneTypeByName(dto.getName())){
            PhoneType phoneType = generatePhoneType(dto);
            phoneTypeRepository.saveAndFlush(phoneType);
            return new ApiResponse(true,"new phoneType saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved phone type! Please, enter other phone type name!"
            );
        }
    }

    public PhoneType generatePhoneType(PhoneTypeDto dto) {
        return new PhoneType(dto.getName());
    }
    public PhoneTypeDto generatePhoneTypeDto(PhoneType type) {
        return new PhoneTypeDto(type.getId(),type.getName());
    }


    @Override
    public ApiResponse deleteById(Long id) {
        if (phoneTypeRepository.findById(id).isPresent()) {
            phoneTypeRepository.deleteById(id);
            return new ApiResponse(true,"phone type deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount phone type!");
        }
    }
}
