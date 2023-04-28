package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.EducationType;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.payload.dekanat.*;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.DekanImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DekanService implements DekanImplService<DekanDto> {
    private final DekanRepository dekanRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final FacultyService facultyService;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final DekanatRepository dekanatRepository;
    private final PasswordEncoder passwordEncoder;
    private final EducationTypeRepository educationTypeRepository;


//    public ApiResponse getRetakesByStudentId(String studentId){
//        return new ApiResponse(
//                true,
//                "all retake",
//                dekanRepository.getRetakesByUserId(studentId)
//                        .stream()
//                        .map(this::generateRetakeDto)
//                        .collect(Collectors.toSet())
//        );
//    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All dekan",
                dekanRepository.findAll().stream()
                        .map(this::generateDekanDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> DekanDto generateDekanDto(Dekan dekan) {
        return new DekanDto(
                dekan.getId(),
                userService.generateUserDto(dekan.getUser()),
                dekan.getFaculties().stream().map(facultyService::generateFacultyDto).collect(Collectors.toSet())
        );
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
    public ApiResponse saveOrUpdate(DekanDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                Set<FacultyDto> facultyDtos = dto.getFacultyDtos();
                boolean has=true;
                for (FacultyDto facultyDto : facultyDtos) {
                    ApiResponse byId = facultyService.getById(facultyDto.getId());
                    if (!byId.isSuccess()){
                        has = false;
                        break;
                    }
                }
                if (has){
                    dekanRepository.save(generateDekan(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount faculty");
            }
            return new ApiResponse(false,"not fount user");
        }
        else { //update
            UserDto userByLogin = userService.getUserByLogin(dto.getUserDto().getLogin());
            if (userByLogin!=null) {
                Set<FacultyDto> facultyDtos = dto.getFacultyDtos();
                boolean has=true;
                for (FacultyDto facultyDto : facultyDtos) {
                    ApiResponse byId = facultyService.getById(facultyDto.getId());
                    if (!byId.isSuccess()){
                        has = false;
                        break;
                    }
                }
                if (has){
                    dekanRepository.save(generateDekan(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount faculty");
            }
            return new ApiResponse(false,"not fount user");
        }
    }

    private Dekan generateDekan(DekanDto dto) {
        return new Dekan(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                dto.getFacultyDtos().stream().map(facultyService::generateFaculty).collect(Collectors.toSet())
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

    public ApiResponse getStudents(String id) {
        return new ApiResponse(true,"transfer",studentRepository.getStudentsForTransfer(id));
    }

    public ApiResponse getFacultiesForStudentTransfer(){
        return new ApiResponse(true,"faculties",dekanRepository.getFacultiesForStudentTransfer());
    }

    public ApiResponse getForStudentTransferData(String id){
        return new ApiResponse(true,"transfer data",dekanRepository.getForStudentTransferData(id));
    }

    public ApiResponse getGroupByFacultyIdAndCourse(String id, Integer course) {
        return new ApiResponse(true,"groups", dekanRepository.getGroupByFacultyIdAndCourse(id,course));
    }

    public ApiResponse saveDekan(DekanSaveWithEduType dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            if (user.getUserId()!=null) {
                user.setLogin(user.getUserId());
            }
            else {
                user.setLogin(user.getRFID());
            }
            user.setPassword(passwordEncoder.encode("root123"));

            userRepository.save(user);
            Optional<Dekanat> dekanatOptional = dekanatRepository.findById(dto.getDekanatId());

            if (dekanatOptional.isPresent()){
                Optional<EducationType> typeOptional = educationTypeRepository.findEducationTypeByName(dto.getEdu());
                if (typeOptional.isPresent()) {
                    Dekan dekan = new Dekan();
                    dekan.setDekanat(dekanatOptional.get());
                    dekan.setUser(userOptional.get());
                    dekan.setEducationType(typeOptional.get());
                    dekanRepository.saveAndFlush(dekan);
                    return new ApiResponse(true, "saved", dekan);
                }
                else {
                    return new ApiResponse(false,"not fount education type");
                }
            }
            else {
                return new ApiResponse(false,"not fount dekanat");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }

    @Transactional
    public ApiResponse changeStudent(StudentChangeDto dto) {
        Optional<User> userOptional = userRepository.findById(dto.getId());
        User userByRFID = userRepository.findUserByRFID(dto.getRFID());
        if (userOptional.isPresent()){

            if (userByRFID!=null) {
                if (Objects.equals(userByRFID.getId(), dto.getId())) {

                    User user = userOptional.get();

                    user.setLogin(dto.getLogin());
                    user.setFullName(dto.getFullName());
                    user.setRFID(dto.getRFID());
                    user.setEmail(dto.getEmail());
                    user.setCitizenship(dto.getCitizenship());
                    user.setNationality(dto.getNationality());
                    user.setPassportNum(dto.getPassportNum());

                    user.setAccountNonLocked(true);
                    user.setAccountNonExpired(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);

                    userRepository.save(user);

                    Student studentByUserId = studentRepository.findStudentByUserId(dto.getId());
                    if (studentByUserId != null) {
                        Group groupByName = groupRepository.findGroupByName(dto.getGroup());
                        if (groupByName != null) {
                            groupByName.setLevel(dto.getLevel());
                            groupRepository.save(groupByName);
                            studentByUserId.setGroup(groupByName);
                            studentRepository.save(studentByUserId);

                            return new ApiResponse(true, "change student successfully...");
                        } else {
                            return new ApiResponse(false, "not fount group");
                        }
                    } else {
                        return new ApiResponse(false, "not fount student");
                    }
                } else {
                    return new ApiResponse(false, "error.. this rfid already exists.. enter other rfid.");
                }
            }
            else {
                return new ApiResponse(false, "error.. not fount rfid.");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }
}
