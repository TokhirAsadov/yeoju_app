package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.OwnerDto;
import uz.yeoju.yeoju_app.payload.dekanat.AddNewStudentDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;
import uz.yeoju.yeoju_app.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DekanatImplService implements DekanatService{

    private final DekanatRepository dekanatRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final EducationTypeRepository educationTypeRepository;
    private final FacultyRepository facultyRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;

    @Override
    public ApiResponse findAllDekanats() {

//        Dekanat dekanat = new Dekanat();

        return new ApiResponse(true,"all dekanat", dekanatRepository.findAll());
    }

    @Override
    @Transactional
    public ApiResponse saveDekanat(DekanatSaveDto dto) {

        if (dekanatRepository.existsDekanatByName(dto.getName())) {
            return new ApiResponse(false,"Already exists dekanat");
        }

        Dekanat dekanat = new Dekanat();
        dekanat.setName(dto.getName());

        Set<Faculty> faculties = new HashSet<>();

        for (String id : dto.getIdsOfFaculties()) {
            Optional<Faculty> facultyOptional = facultyRepository.findById(id);
            if (facultyOptional.isPresent()){
              faculties.add(facultyOptional.get());
            }
            else {
               return new ApiResponse(false,"not fount faculty");
            }
        }

        dekanat.setFaculties(faculties);
        dekanatRepository.save(dekanat);

        return new ApiResponse(true,"saved dekanat successfully...");
    }

    @Override
    public ApiResponse saveDekanatV2(DekanatDto dto) {
        if (dto.getId() == null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }


    private ApiResponse update(DekanatDto dto) {
        Optional<Dekanat> optional = dekanatRepository.findById(dto.getId());
        if (optional.isPresent()){

            Dekanat dekanat = optional.get();
            Dekanat dekanatByName = dekanatRepository.findDekanatByName(dto.getName());
            if (dekanatByName !=null) {
                if (Objects.equals(dekanatByName.getId(), dekanat.getId())) {
                    Optional<User> userOptional = userRepository.findById(dto.getOwner().getValue());
                    if (userOptional.isPresent()) {
                        dekanat.setOwner(userOptional.get());
                        dekanat.setRoom(dto.getRoom());
                        dekanat.setPhone(dto.getPhone());
                        dekanat.setName(dto.getName());
                        EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
                        if (educationTypeByName!=null) {
                            dekanat.setEduType(educationTypeByName);
                        }

                        Set<Role> roleSet = new HashSet<>();
                        Set<Position> positionSet = new HashSet<>();
                        Set<Faculty> faculties = new HashSet<>();
                        dto.getFacultiesName().forEach(faculty -> {
                            Optional<Faculty> facultyByShortName = facultyRepository.findFacultyByShortName(faculty);
                            facultyByShortName.ifPresent(faculties::add);
                        });
                        dto.getRoles().forEach(role -> {
                            if (roleRepository.findRoleByRoleName(role).isPresent())
                                roleSet.add(roleRepository.findRoleByRoleName(role).get());
                        });
                        dto.getPositions().forEach(position -> {
                            if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                        });

                        dekanat.setRoles(roleSet);
                        dekanat.setPositions(positionSet);
                        dekanat.setFaculties(faculties);

                        dekanatRepository.save(dekanat);
                        return new ApiResponse(true, dekanat.getName() + " dekanat updated successfully!..");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! not fount owner!"
                        );
                    }
                } else {
                    return new ApiResponse(
                            false,
                            "error! not saved dekanat! Please, enter other dekanat name!.."
                    );
                }
            }
            else {
                if (!dekanatRepository.existsDekanatByName(dto.getName())){
                    Optional<User> userOptional = userRepository.findById(dto.getOwner().getValue());
                    if (userOptional.isPresent()) {
                        dekanat.setName(dto.getName());
                        dekanat.setOwner(userOptional.get());
                        dekanat.setRoom(dto.getRoom());
                        dekanat.setPhone(dto.getPhone());

                        EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
                        if (educationTypeByName!=null) {
                            dekanat.setEduType(educationTypeByName);
                        }

                        Set<Role> roleSet = new HashSet<>();
                        Set<Position> positionSet = new HashSet<>();
                        Set<Faculty> faculties = new HashSet<>();
                        dto.getFacultiesName().forEach(faculty -> {
                            Optional<Faculty> facultyByShortName = facultyRepository.findFacultyByShortName(faculty);
                            facultyByShortName.ifPresent(faculties::add);
                        });
                        dto.getRoles().forEach(role -> {
                            if (roleRepository.findRoleByRoleName(role).isPresent())
                                roleSet.add(roleRepository.findRoleByRoleName(role).get());
                        });
                        dto.getPositions().forEach(position -> {
                            if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                        });

                        dekanat.setRoles(roleSet);
                        dekanat.setPositions(positionSet);
                        dekanat.setFaculties(faculties);

                        dekanatRepository.save(dekanat);
                        return new ApiResponse(true, dekanat.getName() + " dekanat updated successfully!..");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! not fount owner!"
                        );
                    }
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! not saved dekanat! Please, enter other dekanat!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount dekanat"
            );
        }
    }
    private ApiResponse save(DekanatDto dto) {
        if (!dekanatRepository.existsDekanatByName(dto.getName())){
            Optional<User> userOptional = userRepository.findById(dto.getOwner().getValue());
            if (userOptional.isPresent()) {
                Dekanat dekanat = generateDekanat(dto);
                EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
                if (educationTypeByName!=null) {
                    dekanat.setEduType(educationTypeByName);
                }
                dekanat.setOwner(userOptional.get());
                dekanat.setPhone(dto.getPhone());
                dekanat.setRoom(dto.getRoom());
                dekanatRepository.saveAndFlush(dekanat);
                return new ApiResponse(true, "new dekanat saved successfully!...");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! not fount owner!"
                );
            }
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved dekanat! Please, enter other dekanat!"
            );
        }
    }

    private Dekanat generateDekanat(DekanatDto dto) {

        Set<Role> roles = new HashSet<>();
        Set<Position> positions = new HashSet<>();
        Set<Faculty> faculties = new HashSet<>();
        dto.getFacultiesName().forEach(faculty -> {
            Optional<Faculty> facultyByShortName = facultyRepository.findFacultyByShortName(faculty);
            facultyByShortName.ifPresent(faculties::add);
        });

        dto.getRoles().forEach(role -> {
            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
            roleOptional.ifPresent(roles::add);
        });
        dto.getPositions().forEach(position -> {
            Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
            positionOptional.ifPresent(positions::add);
        });

        return new Dekanat(dto.getId(), dto.getName(),faculties,roles,positions);
    }


    @Override
    public ApiResponse getDekansForSaved() {
        return new ApiResponse(true,"datas",dekanatRepository.getDekansForSaved());
    }

    @Override
    public ApiResponse getDekansSavedDatas() {
        return new ApiResponse(true,"datas",dekanatRepository.getForDekanRoleSettings());
    }

    @Override
    public ApiResponse getKafedrasSavedDatas() {
        return new ApiResponse(true,"datas",dekanatRepository.getForKafedraRoleSettings());
    }

    @Override
    public ApiResponse getDekanatDataForDekan(String id) {
        return new ApiResponse(true,"dekanat data for dekan",dekanatRepository.getDekanatDataForDekan(id));
    }

    @Override
    public ApiResponse getDekanatById(String id) {
        Optional<Dekanat> dekanatOptional = dekanatRepository.findById(id);
        if (dekanatOptional.isPresent()) {
            Dekanat dekanat = dekanatOptional.get();
            System.out.println(dekanat+" <----------------------------------------------------- dekanat");

            return new ApiResponse(
                    true,
                    "find by id",
                    new DekanatDto(
                            dekanat.getId(),
                            dekanat.getName(),
                            dekanat.getFaculties().stream().map(Faculty::getShortName).collect(Collectors.toSet()),
                            dekanat.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()),
                            dekanat.getPositions().stream().map(Position::getUserPositionName).collect(Collectors.toSet()),
                            new OwnerDto(dekanat.getOwner().getId(),dekanat.getOwner().getFullName()),
                            dekanat.getRoom(),
                            dekanat.getPhone()

                    )
            );
        }
        else {
            return new ApiResponse(false,"not fount dekanat");
        }
    }

    @Override
    public ApiResponse getUserForDekanSave(String id,Boolean bool) {

        return  new ApiResponse(true,"items",bool ? dekanatRepository.getUserForSectionSave(id) : dekanatRepository.getUserForDekanSave(id));
    }

    @Override
    public ApiResponse deleteById(String id) {
        Optional<Dekanat> dekanatOptional = dekanatRepository.findById(id);
        if (dekanatOptional.isPresent()){
            dekanatRepository.deleteById(id);
            return new ApiResponse(true,"deleted dean's office by id -> "+id);
        }
        else {

            return new ApiResponse(false,"not fount by id -> "+id);
        }
    }

    @Override
    public ApiResponse getStatisticsOfGroupForDean(String groupId, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<??? 31",dekanatRepository.getDateForDean31(date,groupId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<??? 30",dekanatRepository.getDateForDean30(date,groupId));
        }else if (maxDay==29){
            return new ApiResponse(true,"<??? 29",dekanatRepository.getDateForDean29(date,groupId));
        }else {
            return new ApiResponse(true,"<??? 28",dekanatRepository.getDateForDean28(date,groupId));
        }
    }

    @Override
    public ApiResponse addNewStudent(AddNewStudentDto dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isPresent()){
            Student studentByUserId = studentRepository.findStudentByUserId(dto.getUserId());
            if (studentByUserId==null){
                Group groupByName = groupRepository.findGroupByName(dto.getGroupName());
                if (groupByName!=null){
                    User user = userOptional.get();
                    Student student = new Student();
                    student.setUser(user);
                    student.setGroup(groupByName);
                    studentRepository.save(student);
                    Optional<Role> role_student = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                    user.getRoles().add(role_student.get());
                    user.setRoles(user.getRoles());
                    userRepository.save(user);
                    return new ApiResponse(true,user.getFullName()+" add student successfully");
                }
                else {
                    return new ApiResponse(false,"not fount group -> "+dto.getGroupName());
                }
            }
            else {
                Group groupByName = groupRepository.findGroupByName(dto.getGroupName());
                if (groupByName!=null) {
                    studentByUserId.setGroup(groupByName);
                    studentRepository.save(studentByUserId);
                    return new ApiResponse(true,"add student successfully");
                }
                else {
                    return new ApiResponse(false,"not fount group -> "+dto.getGroupName());
                }
            }
        }
        else {
            return new ApiResponse(false,"not fount user by id -> "+dto.getUserId());
        }
    }

}
