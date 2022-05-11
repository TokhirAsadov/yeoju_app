package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserImplService<UserDto> {

    public final UserRepository userRepository;
    public final GanderService ganderService;


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"List of all users", userRepository.findAll());
    }

    @Override
    public ApiResponse findById(UUID id) {
        return userRepository
                .findById(id)
                .map(user -> new ApiResponse(true, "Fount user by id", user))
                .orElseGet(() -> new ApiResponse(false, "Not fount user by id"));
    }

    @Override
    public ApiResponse getById(UUID id) {
        User user = userRepository.getById(id);
        return new ApiResponse(true, "Fount user by id", user);
    }

    @Override
    public ApiResponse saveOrUpdate(UserDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        return null;
    }

    public ApiResponse update(UserDto dto){
        Optional<User> optional = userRepository.findById(dto.getId());
        if (optional.isPresent()){
            User user = optional.get();
            User userByEmail = userRepository.getUserByEmail(dto.getEmail());
            User userByRFID = userRepository.getUserByRFID(dto.getRFID());
            User userByLogin = userRepository.getUserByLogin(dto.getLogin());
            if (
                    Objects.equals(userByLogin.getId(), user.getId())
                            &&
                            Objects.equals(userByEmail.getId(), user.getId())
                            &&
                            Objects.equals(userByRFID.getId(), user.getId())
            ){
                user.setFullName(dto.getFio());
                user.setLogin(dto.getLogin());
                user.setPassword(dto.getPassword());
                user.setEmail(dto.getEmail());
                user.setRFID(dto.getRFID());
                user.setGander(ganderService.generateGander(dto.getGanderDto()));
                /**
                 * ROLE BASED ni tugirlash kerak!
                 * **/
              //  user.setRoles(dto.getRoleDtos());
                userRepository.save(user);
                return new ApiResponse(true,"user updated successfully");
            }else {
                return new ApiResponse(
                        false,
                        "error! nor saved user! Please, enter other login,email or RFID"
                );
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount user"
            );
        }
    }

    public ApiResponse save(UserDto dto){
        if (!existsUserByLoginOrEmailOrRFID(dto.getLogin(), dto.getEmail(), dto.getRFID())){
            User user = null;//generateUser(dto);
            userRepository.saveAndFlush(user);
            return new ApiResponse(true,"new user saved successfully");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! nor saved user! Please, enter other login,email or RFID"
            );
        }
    }

    @Override
    public UserDto getUserByLogin(String login) {
        return null;
    }

    @Override
    public UserDto getUserByRFID(String rfid) {
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID) {
        return false;
    }
//
//    private User generateUser(UserDto dto) {
//        return new User(
//                dto.getFullName(),
//                dto.getLogin(),
//                dto.getPassword(),
//                dto.getRFID(),
//                dto.getEmail(),
//                ganderService.generateGander(dto.getGanderDto()),
//                dto.getRoleDtos()
//        );
//    }
//
//    @Override
//    public ApiResponse deleteById(Long id) {
//        if (userRepository.findById(id).isPresent()) {
//            userRepository.deleteById(id);
//            return new ApiResponse(true,"user deleted successfully!..");
//        }
//        else {
//            return new ApiResponse(false,"error... not fount user");
//        }
//    }
//
//
//    @Override
//    public UserDto getUserByLogin(String login) {
//        return generateUserDto(userRepository.getUserByLogin(login));
//    }
//
//    private UserDto generateUserDto(User user) {
//        return new UserDto(
//          user.getId(),
//          user.getFullName(),
//          user.getLogin(),
//          user.getPassword(),
//          user.getRFID(),
//          user.getEmail(),
//          ganderService.generateGanderDto(user.getGander()),
//          user.getRoles()
//        );
//    }
//
//    @Override
//    public UserDto getUserByRFID(String rfid) {
//        return generateUserDto(userRepository.getUserByRFID(rfid));
//    }
//
//    @Override
//    public UserDto getUserByEmail(String email) {
//        return generateUserDto(userRepository.getUserByEmail(email));
//    }
//
//    @Override
//    public boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID) {
//        return userRepository.existsUserByLoginOrEmailOrRFID(login,email,RFID);
//    }
//

}
