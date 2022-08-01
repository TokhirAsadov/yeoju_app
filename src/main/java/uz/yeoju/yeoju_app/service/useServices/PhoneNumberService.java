package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.PhoneNumber;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.PhoneNumberDto;
import uz.yeoju.yeoju_app.repository.PhoneNumberRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.PhoneNumberImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhoneNumberService implements PhoneNumberImplService<PhoneNumberDto> {
    public final PhoneNumberRepository numberRepository;
    public final UserService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all phone numbers",
                numberRepository.findAll().stream().map(this::generatePhoneNumberDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return numberRepository
                .findById(id)
                .map(educationType -> new ApiResponse(true, "Fount phone number by id", generatePhoneNumberDto(educationType) ))
                .orElseGet(() -> new ApiResponse(false, "Not fount phone number by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        PhoneNumber number = numberRepository.getById(id);
        return new ApiResponse(true, "Fount phone number by id", number);
    }

    @Override
    public ApiResponse saveOrUpdate(PhoneNumberDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(PhoneNumberDto dto){
        Optional<PhoneNumber> optional = numberRepository.findById(dto.getId());
        if (optional.isPresent()){
            PhoneNumber number = optional.get();
            PhoneNumber numberByPhoneNumber = numberRepository.getPhoneNumberByAndPhoneNumber(dto.getPhoneNumber());
            if (numberByPhoneNumber!=null) {
                if (
                        Objects.equals(numberByPhoneNumber.getId(), number.getId())
                                ||
                                !numberRepository.existsPhoneNumberByPhoneNumber(dto.getPhoneNumber())
                ) {
                    number.setPhoneNumber(dto.getPhoneNumber());
                    number.setUser(userService.generateUser(dto.getUserDto()));
                    number.setPhoneType(dto.getPhoneType());
                    number.setHasTg(dto.isHasTg());
                    number.setHasInstagram(dto.isHasInstagram());
                    number.setHasFacebook(dto.isHasFacebook());
                    number.setActive(dto.isActive());
                    numberRepository.save(number);
                    return new ApiResponse(true, "phone number updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved number! Please, enter other phone number!.."
                    );
                }
            }
            else {
                if ( !numberRepository.existsPhoneNumberByPhoneNumber(dto.getPhoneNumber())){
                    number.setPhoneNumber(dto.getPhoneNumber());
                    number.setUser(userService.generateUser(dto.getUserDto()));
                    number.setPhoneType(dto.getPhoneType());
                    number.setHasTg(dto.isHasTg());
                    number.setHasInstagram(dto.isHasInstagram());
                    number.setHasFacebook(dto.isHasFacebook());
                    number.setActive(dto.isActive());
                    numberRepository.save(number);
                    return new ApiResponse(true,"phone number updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved number! Please, enter other phone number!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount number"
            );
        }
    }

    public ApiResponse save(PhoneNumberDto dto){
        if (!numberRepository.existsPhoneNumberByPhoneNumber(dto.getPhoneNumber())){
            PhoneNumber number = generatePhoneNumber(dto);
            System.out.println(number);
            numberRepository.save(number);
            return new ApiResponse(true,"new phone number saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved phone number! Please, enter other phone number!"
            );
        }
    }

    public PhoneNumber generatePhoneNumber(PhoneNumberDto dto) {
        return new PhoneNumber(
                dto.getId(),
                dto.getPhoneNumber(),
                userService.generateUser(dto.getUserDto()),
                dto.getPhoneType(),
                dto.isHasTg(),
                dto.isHasInstagram(),
                dto.isHasFacebook(),
                dto.isActive()
                );
    }
    public PhoneNumberDto generatePhoneNumberDto(PhoneNumber number) {
        return new PhoneNumberDto(
                number.getId(),
                number.getPhoneNumber(),
                userService.generateUserDto(number.getUser()),
                number.getPhoneType(),
                number.isHasTg(),
                number.isHasInstagram(),
                number.isHasFacebook(),
                number.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (numberRepository.findById(id).isPresent()) {
            numberRepository.deleteById(id);
            return new ApiResponse(true,"phone number deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount phone number!");
        }
    }

    @Override
    public ApiResponse findPhoneNumbersByUser(String userId) {
        return new ApiResponse(
                true,
                "fount phone numbers by user",
                numberRepository.findPhoneNumbersByUserId(userId).stream()
                        .map(this::generatePhoneNumberDto)
                        .collect(Collectors.toList())
                );
    }
}
