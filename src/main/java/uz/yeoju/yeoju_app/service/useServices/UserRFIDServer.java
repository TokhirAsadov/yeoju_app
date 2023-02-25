package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.payload.*;

import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.secret.JwtProvider;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserImplRFIDService;


import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserRFIDServer implements UserImplRFIDService<UserRFIDDto> {

    public final UserRFIDRepository userRFIDRepository;
    public final GanderService ganderService;
    public final PasswordEncoder passwordEncoder;
    public final RoleRepository roleRepository;

    public final AuthenticationManager manager;
    public final JwtProvider provider;
    public final RoleService roleService;

    public final StudentRepository studentRepository;
    public final GroupRepository groupRepository;
    public final FacultyRepository facultyRepository;
    public final EducationLanRepository eduLanRepo;
    public final EducationFormRepository eduFormRepo;
    public final EducationTypeRepository eduTypeRepo;



    public UserRFIDDto generateUserRFIDDto(User user) {
        return new UserRFIDDto(
                user.getId(),
                user.getFullName(),
                user.getLogin(),
                user.getRFID()

        );
    }

    @Override
    public UserRFIDDto getUserRFIDByRFID(String rfid) {
        return generateUserRFIDDto(userRFIDRepository.findUserByRFID(rfid));
    }


    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(UserRFIDDto userRFIDDto) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

    @Override
    public UserRFIDDto getUserByLogin(String login) {
        return null;
    }

    @Override
    public UserRFIDDto getUserByRFID(String rfid) {
        return null;
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID) {
        return false;
    }
}
