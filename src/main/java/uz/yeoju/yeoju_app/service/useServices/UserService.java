package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ResToken;
import uz.yeoju.yeoju_app.payload.SignInDto;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.JwtProvider;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserImplService<UserDto> {

    public final UserRepository userRepository;
    public final GanderService ganderService;
    public final AuthenticationManager manager;
    public final JwtProvider provider;
    public final RoleService roleService;

    public ResToken login(SignInDto signInDto){
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getLogin(),
                signInDto.getPassword()
        ));
        User user = (User) auth.getPrincipal();
        String token = provider.generateToken(user);
        return new ResToken(token);
    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all users",
                userRepository.findAll().stream().map(this::generateUserDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return userRepository
                .findById(id)
                .map(user -> new ApiResponse(true, "Fount user by id", user))
                .orElseGet(() -> new ApiResponse(false, "Not fount user by id"));
    }

    @Override
    public ApiResponse getById(String id) {
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

    public ApiResponse update(UserDto dto){
        Optional<User> optional = userRepository.findById(dto.getId());
        if (optional.isPresent()){
            User user = optional.get();
            User userByEmail = userRepository.getUserByEmail(dto.getEmail());
            User userByRFID = userRepository.getUserByRFID(dto.getRFID());
            User userByLogin = userRepository.getUserByLogin(dto.getLogin());

            if (userByLogin!=null && userByEmail!=null && userByRFID!=null) {
                if (
                    Objects.equals(userByLogin.getId(), user.getId())
                    &&
                    Objects.equals(userByEmail.getId(), user.getId())
                    &&
                    Objects.equals(userByRFID.getId(), user.getId())
                ) {
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }
            }
            else if (userByLogin != null && userByEmail != null && userByRFID == null) {

                if (
                    Objects.equals(userByLogin.getId(), user.getId())
                            &&
                    Objects.equals(userByEmail.getId(), user.getId())
                ){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }

            }
            else if (userByLogin != null && userByEmail == null && userByRFID!=null) {

                if (
                    Objects.equals(userByLogin.getId(), user.getId())
                            &&
                    Objects.equals(userByRFID.getId(), user.getId())
                ){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }

            }
            else if (userByLogin == null && userByEmail != null && userByRFID != null) {

                if (
                        Objects.equals(userByEmail.getId(), user.getId())
                                &&
                        Objects.equals(userByRFID.getId(), user.getId())
                ){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }

            }
            else if (userByLogin != null && userByEmail == null && userByRFID == null) {

                if (Objects.equals(userByLogin.getId(), user.getId())){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }

            }
            else if (userByLogin == null && userByEmail != null && userByRFID == null) {
                if (Objects.equals(userByEmail.getId(), user.getId())){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }
            }
            else if (userByLogin == null && userByEmail == null && userByRFID != null){
                if (Objects.equals(user.getId(), userByRFID.getId())){
                    user.setFullName(dto.getFullName());
                    user.setLogin(dto.getLogin());
                    user.setPassword(dto.getPassword());
                    user.setEmail(dto.getEmail());
                    user.setRFID(dto.getRFID());
                    user.setGander(ganderService.generateGander(dto.getGanderDto()));
                    user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                    userRepository.save(user);
                    return new ApiResponse(true, "user updated successfully");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor updated user! Please, enter other login,email or RFID"
                    );
                }
            }
            else {
                user.setFullName(dto.getFullName());
                user.setLogin(dto.getLogin());
                user.setPassword(dto.getPassword());
                user.setEmail(dto.getEmail());
                user.setRFID(dto.getRFID());
                user.setGander(ganderService.generateGander(dto.getGanderDto()));
                user.setRoles(dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet()));
                userRepository.save(user);
                return new ApiResponse(true, "user updated successfully");
            }

            }
        else {
            return new ApiResponse(
                    false,
                    "error! not fount user!"
            );
        }
    }

    public ApiResponse save(UserDto dto){
        if (!existsUserByLoginOrEmailOrRFID(dto.getLogin(), dto.getEmail(), dto.getRFID())){
            User user = generateUser(dto);
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

    public User generateUser(UserDto dto) {
        return new User(
                dto.getFullName(),
                dto.getLogin(),
                dto.getPassword(),
                dto.getRFID(),
                dto.getEmail(),
                ganderService.generateGander(dto.getGanderDto()),
                dto.getRoleDtos().stream().map(roleService::generateRole).collect(Collectors.toSet())
        );
    }
    public UserDto generateUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getFullName(),
                user.getLogin(),
                user.getPassword(),
                user.getRFID(),
                user.getEmail(),
                ganderService.generateGanderDto(user.getGander()),
                user.getRoles().stream().map(roleService::generateRoleDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse deleteById(String id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return new ApiResponse(true,"user deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount user");
        }
    }


    @Override
    public UserDto getUserByLogin(String login) {
        return generateUserDto(userRepository.getUserByLogin(login));
    }

    @Override
    public UserDto getUserByRFID(String rfid) {
        return generateUserDto(userRepository.getUserByRFID(rfid));
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return generateUserDto(userRepository.getUserByEmail(email));
    }

    @Override
    public boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID) {
        return userRepository.existsUserByLoginOrEmailOrRFID(login,email,RFID);
    }


}
