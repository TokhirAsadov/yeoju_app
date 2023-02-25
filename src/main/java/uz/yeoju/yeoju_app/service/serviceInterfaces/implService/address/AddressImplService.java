package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.address.SelectAddressDto;
import uz.yeoju.yeoju_app.repository.AddressUserRepository;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.util.stream.Collectors;

@Service
public class AddressImplService implements AddressService{

    @Autowired
    private AddressUserRepository addressUserRepository;

    @Autowired
    private UserService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all addresses",addressUserRepository.findAll().stream().map(this::generateAddressDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse findById(String id) {
        if (addressUserRepository.findById(id).isPresent()) {
            return new ApiResponse(true,"address",generateAddressDto(addressUserRepository.findById(id).get()));
        }
        else {
            return new ApiResponse(false,"not fount address");
        }
    }

    @Override
    public ApiResponse save(AddressDto dto) {
        addressUserRepository.save(generateAddress(dto));
        if (dto.getId()==null)
            return new ApiResponse(true,"saved");
        return new ApiResponse(true,"updated");
    }

    @Override
    public ApiResponse saveForUser(User user, AddressDto dto) {
        ApiResponse response = userService.findById(user.getId());
        if (response.isSuccess()){
            return save(
                            new AddressDto(
                                dto.getId(),
                                userService.generateUserDto((User) response.getObj()),
                                dto.getCountry(),
                                dto.getRegion(),
                                dto.getArea(),
                                dto.getAddress(),
                                dto.getConstant()
                            )
                        );
        }
        return new ApiResponse(false,"not fount user");
    }

    @Override
    public ApiResponse saveSecond(User user, SelectAddressDto dto) {
        return null;
    }


    public AddressUser generateAddress(AddressDto dto){
        return new AddressUser(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                dto.getCountry(),
                dto.getRegion(),
                dto.getArea(),
                dto.getAddress(),
                dto.getConstant()
        );
    }

    public AddressDto generateAddressDto(AddressUser address){
        return new AddressDto(
                address.getId(),
                userService.generateUserDto(address.getUser()),
                address.getCountry(),
                address.getRegion(),
                address.getArea(),
                address.getAddress(),
                address.getIsConstant()
        );
    }
}
