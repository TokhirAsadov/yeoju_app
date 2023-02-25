package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.staff;

import lombok.RequiredArgsConstructor;
import org.jaxen.util.SingletonList;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.entity.staff.Staff;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.staff.StaffSaveFromSection;
import uz.yeoju.yeoju_app.payload.superAdmin.StaffSaveDto;
import uz.yeoju.yeoju_app.repository.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffImplService implements StaffService{
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final UserRepository userRepository;
    private final DekanRepository dekanRepository;
    private final StaffRepository staffRepository;
    private final SectionRepository sectionRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse saveStaff(StaffSaveDto dto) {

        Boolean existsStaffByUserIdAndSectionId = staffRepository.existsStaffByUserIdAndSectionId(dto.getUserId(), dto.getSectionId());

        if (!existsStaffByUserIdAndSectionId){
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            if (userOptional.isPresent()){
                Optional<Role> roleOptional = roleRepository.findById(dto.getRoleId());
                if (roleOptional.isPresent()){
                    Optional<Section> sectionOptional = sectionRepository.findById(dto.getSectionId());
                    if (sectionOptional.isPresent()){

                        User user = userOptional.get();
                        Section section = sectionOptional.get();
                        Role role = roleOptional.get();

                        Set<Role> roles = new HashSet<>();

                        if (!user.getRoles().isEmpty()){
                            Optional<Role> roleOptional2 = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
                            if (!roleOptional2.isPresent()) {
                                roles.addAll(user.getRoles());
                            }
                        }


                        roles.add(role);

                        user.setAccountNonLocked(true);
                        user.setAccountNonExpired(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        user.setRoles(roles);
                        user.setLogin(user.getFullName());
                        user.setPassword(passwordEncoder.encode("root123"));
                        userRepository.save(user);

                        Staff staff = new Staff();
                        staff.setUser(user);
                        staff.setSection(section);

                        staffRepository.save(staff);

                        return new ApiResponse(true,user.getFullName()+" staff saved successfully!.");
                    }
                    else {
                        return new ApiResponse(false,"Error.. Not fount Section.");
                    }
                }
                else {
                    return new ApiResponse(false,"Error.. Not fount Role.");
                }
            }
            else {
                return new ApiResponse(false,"Error.. Not fount User.");
            }
        }
        else {
            return new ApiResponse(false,"Error.. Already exists staff.");
        }
    }

    @Override
    public ApiResponse saveStaff(StaffSaveFromSection dto,String dekanId) {

        if(dto.getId() == null || dto.getId().equals("")) {
            return save(dto,dekanId);
        }
        else {
            return update(dto);
        }

    }


    public ApiResponse update(StaffSaveFromSection dto){

        Optional<Staff> staffOptional = staffRepository.findById(dto.getId());

        if (staffOptional.isPresent()) {
            if (!staffRepository.existsStaffByUserId(dto.getUserId())) {
                Optional<User> userOptional = userRepository.findById(dto.getUserId());
                if (userOptional.isPresent()) {
                    Optional<Role> role_staff = roleRepository.findRoleByRoleName("ROLE_STAFF");
                    if (role_staff.isPresent()) {

                        if (dto.getSectionId()!=null) {
                            Optional<Section> sectionOptional = sectionRepository.findById(dto.getSectionId());
                            if (sectionOptional.isPresent()) {
                                User user = userOptional.get();
                                Role role = role_staff.get();
                                Staff staff = staffOptional.get();


                                Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                                if (positionOptional.isPresent()) {
                                    user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                                } else {
                                    return new ApiResponse(false, "not fount position");
                                }

                                Set<Role> roles = new HashSet<>();
                                if (!user.getRoles().isEmpty()) {
                                    Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
                                    if (!roleOptional.isPresent()) {
                                        roles.addAll(user.getRoles());
                                    }
                                }
                                roles.add(role);
                                user.setRoles(roles);
                                user.setAccountNonLocked(true);
                                user.setAccountNonExpired(true);
                                user.setCredentialsNonExpired(true);
                                user.setEnabled(true);
                                if (user.getLogin()==null || user.getLogin().equals("")){
                                    if (user.getUserId()!=null) {
                                        user.setLogin(user.getUserId());
                                    }
                                    else {
                                        user.setLogin(user.getRFID());
                                    }
                                }
                                user.setPassword(passwordEncoder.encode("root123"));
                                userRepository.save(user);

                                Section section = sectionOptional.get();


                                if (!user.getId().equals(staff.getUser().getId())) {
                                    // old user changed role
                                    Optional<User> oldUserOption = userRepository.findById(staff.getUser().getId());
                                    User oldUser = oldUserOption.get();
                                    Set<Role> roles1 = new HashSet<>();
                                    Optional<Role> role_user = roleRepository.findRoleByRoleName("ROLE_USER");
                                    roles1.add(role_user.get());
                                    oldUser.setRoles(roles1);
                                    userRepository.save(oldUser);
                                }


                                staff.setSection(section);
                                staff.setUser(user);
                                staffRepository.save(staff);

                                return new ApiResponse(true, user.getFullName() + " updated staff successfully..");
                            }
                            else {
                                return new ApiResponse(false, "not fount section");
                            }
                        }
                        else {

                            User user = userOptional.get();
                            Role role = role_staff.get();

                            Staff staff = staffOptional.get();

                            if (!user.getId().equals(staff.getUser().getId())) {
                                // old user changed role
                                Optional<User> oldUserOption = userRepository.findById(staff.getUser().getId());
                                User oldUser = oldUserOption.get();
                                Set<Role> roles1 = new HashSet<>();
                                Optional<Role> role_user = roleRepository.findRoleByRoleName("ROLE_USER");
                                roles1.add(role_user.get());
                                oldUser.setRoles(roles1);
                                userRepository.save(oldUser);
                            }


                            Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                            if (positionOptional.isPresent()) {
                                user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                            } else {
                                return new ApiResponse(false, "not fount position");
                            }

                            Set<Role> roles = new HashSet<>();
                            if (!user.getRoles().isEmpty()) {
                                Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
                                if (!roleOptional.isPresent()) {
                                    roles.addAll(user.getRoles());
                                }
                            }
                            roles.add(role);
                            user.setRoles(roles);
                            user.setAccountNonLocked(true);
                            user.setAccountNonExpired(true);
                            user.setCredentialsNonExpired(true);
                            user.setEnabled(true);
                            if (user.getLogin()==null || user.getLogin().equals("")){
                                if (user.getUserId()!=null) {
                                    user.setLogin(user.getUserId());
                                }
                                else {
                                    user.setLogin(user.getRFID());
                                }
                            }
                            user.setPassword(passwordEncoder.encode("root123"));
                            userRepository.save(user);

                            staff.setUser(user);
                            staffRepository.save(staff);

                            return new ApiResponse(true, user.getFullName() + " updated staff successfully..");
                        }

                    }
                    else {
                        return new ApiResponse(false, "not fount ROLE STAFF");
                    }
                }
                else {
                    return new ApiResponse(false, "not fount user");
                }
            }
            else {

                Optional<Staff> staffOptional1 = staffRepository.findStaffByUserId(dto.getUserId());

                if (staffOptional1.get().getId().equals(dto.getId())){

                    Optional<User> userOptional = userRepository.findById(dto.getUserId());
                    if (userOptional.isPresent()) {
                        Optional<Role> role_staff = roleRepository.findRoleByRoleName("ROLE_STAFF");
                        if (role_staff.isPresent()) {


                            if (dto.getSectionId()!=null) {
                                Optional<Section> sectionOptional = sectionRepository.findById(dto.getSectionId());
                                if (sectionOptional.isPresent()) {
                                    User user = userOptional.get();
                                    Role role = role_staff.get();


                                    Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                                    if (positionOptional.isPresent()) {
                                        user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                                    } else {
                                        return new ApiResponse(false, "not fount position");
                                    }

//                                Set<Role> roles = new HashSet<>();
//                                if (!user.getRoles().isEmpty()) {
//                                    Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
//                                    if (!roleOptional.isPresent()) {
//                                        roles.addAll(user.getRoles());
//                                    }
//                                }
//                                roles.add(role);
//                                user.setRoles(roles);

                                    user.setAccountNonLocked(true);
                                    user.setAccountNonExpired(true);
                                    user.setCredentialsNonExpired(true);
                                    user.setEnabled(true);
                                    if (user.getLogin()==null || user.getLogin().equals("")){
                                        if (user.getUserId()!=null) {
                                            user.setLogin(user.getUserId());
                                        }
                                        else {
                                            user.setLogin(user.getRFID());
                                        }
                                    }
                                    user.setPassword(passwordEncoder.encode("root123"));
                                    userRepository.save(user);


                                    Section section = sectionOptional.get();
                                    Staff staff = staffOptional.get();
                                    staff.setSection(section);


                                    if (!user.getId().equals(staff.getUser().getId())) {
                                        // old user changed role
                                        Optional<User> oldUserOption = userRepository.findById(staff.getUser().getId());
                                        User oldUser = oldUserOption.get();
                                        Set<Role> roles1 = new HashSet<>();
                                        Optional<Role> role_user = roleRepository.findRoleByRoleName("ROLE_USER");
                                        roles1.add(role_user.get());
                                        oldUser.setRoles(roles1);
                                        userRepository.save(oldUser);
                                    }

                                    staff.setUser(user);
                                    staffRepository.save(staff);

                                    return new ApiResponse(true, user.getFullName() + " updated staff successfully..");
                                }
                                else {
                                    return new ApiResponse(false, "not fount section");
                                }
                            }
                            else {

                                User user = userOptional.get();

                                Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                                if (positionOptional.isPresent()) {
                                    user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                                } else {
                                    return new ApiResponse(false, "not fount position");
                                }

//                                Set<Role> roles = new HashSet<>();
//                                if (!user.getRoles().isEmpty()) {
//                                    Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
//                                    if (!roleOptional.isPresent()) {
//                                        roles.addAll(user.getRoles());
//                                    }
//                                }
//                                roles.add(role);
//                                user.setRoles(roles);

                                user.setAccountNonLocked(true);
                                user.setAccountNonExpired(true);
                                user.setCredentialsNonExpired(true);
                                user.setEnabled(true);
                                if (user.getLogin()==null || user.getLogin().equals("")){
                                    if (user.getUserId()!=null) {
                                        user.setLogin(user.getUserId());
                                    }
                                    else {
                                        user.setLogin(user.getRFID());
                                    }
                                }
                                user.setPassword(passwordEncoder.encode("root123"));
                                userRepository.save(user);

                                Staff staff = staffOptional.get();

                                if (!user.getId().equals(staff.getUser().getId())) {
                                    // old user changed role
                                    Optional<User> oldUserOption = userRepository.findById(staff.getUser().getId());
                                    User oldUser = oldUserOption.get();
                                    Set<Role> roles1 = new HashSet<>();
                                    Optional<Role> role_user = roleRepository.findRoleByRoleName("ROLE_USER");
                                    roles1.add(role_user.get());
                                    oldUser.setRoles(roles1);
                                    userRepository.save(oldUser);
                                }

                                staff.setUser(user);
                                staffRepository.save(staff);

                                return new ApiResponse(true, user.getFullName() + " updated staff successfully..");


                            }

                        }
                        else {
                            return new ApiResponse(false, "not fount ROLE STAFF");
                        }
                    }
                    else {
                        return new ApiResponse(false, "not fount user");
                    }

                }
                else {
                    return new ApiResponse(false, "error. already exists staff");
                }


            }
        }
        else {
            return new ApiResponse(false,"not fount staff");
        }

    }

    public ApiResponse save(StaffSaveFromSection dto,String dekanId){
        if (!staffRepository.existsStaffByUserId(dto.getUserId())) {
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            if (userOptional.isPresent()) {
                Optional<Role> role_staff = roleRepository.findRoleByRoleName("ROLE_STAFF");
                if (role_staff.isPresent()) {

                    if (dto.getSectionId()!=null) {
                        Optional<Section> sectionOptional = sectionRepository.findById(dto.getSectionId());
                        if (sectionOptional.isPresent()) {
                            User user = userOptional.get();
                            Role role = role_staff.get();


                            Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                            if (positionOptional.isPresent()) {
                                user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                            } else {
                                return new ApiResponse(false, "not fount position");
                            }

                            Set<Role> roles = new HashSet<>();
                            if (!user.getRoles().isEmpty()) {
                                Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
                                if (!roleOptional.isPresent()) {
                                    roles.addAll(user.getRoles());
                                }
                            }
                            roles.add(role);
                            user.setRoles(roles);
                            user.setAccountNonLocked(true);
                            user.setAccountNonExpired(true);
                            user.setCredentialsNonExpired(true);
                            user.setEnabled(true);
                            if (user.getLogin()==null || user.getLogin().equals("")){
                                if (user.getUserId()!=null) {
                                    user.setLogin(user.getUserId());
                                }
                                else {
                                    user.setLogin(user.getRFID());
                                }
                            }
                            user.setPassword(passwordEncoder.encode("root123"));
                            userRepository.save(user);

                            Section section = sectionOptional.get();
                            Staff staff = new Staff(user, section);
                            staffRepository.save(staff);

                            return new ApiResponse(true, user.getFullName() + " saved staff successfully..");
                        }
                        else {
                            return new ApiResponse(false, "not fount section");
                        }
                    }
                    else {
                        Dekan dekanByUserId = dekanRepository.getDekanByUserId(dekanId);
                        if (dekanByUserId!=null){

                            User user = userOptional.get();
                            Role role = role_staff.get();

                            Optional<Position> positionOptional = positionRepository.findById(dto.getPositionId());

                            if (positionOptional.isPresent()) {
                                user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                            } else {
                                return new ApiResponse(false, "not fount position");
                            }

                            Set<Role> roles = new HashSet<>();
                            if (!user.getRoles().isEmpty()) {
                                Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
                                if (!roleOptional.isPresent()) {
                                    roles.addAll(user.getRoles());
                                }
                            }
                            roles.add(role);
                            user.setRoles(roles);
                            user.setAccountNonLocked(true);
                            user.setAccountNonExpired(true);
                            user.setCredentialsNonExpired(true);
                            user.setEnabled(true);
                            if (user.getLogin()==null || user.getLogin().equals("")){
                                if (user.getUserId()!=null) {
                                    user.setLogin(user.getUserId());
                                }
                                else {
                                    user.setLogin(user.getRFID());
                                }
                            }
                            user.setPassword(passwordEncoder.encode("root123"));
                            userRepository.save(user);


                            Staff staff = new Staff();
                            staff.setUser(user);
                            staff.setDekanat(dekanByUserId.getDekanat());
                            staffRepository.save(staff);

                            return new ApiResponse(true, user.getFullName() + " saved staff successfully..");

                        }


                        return new ApiResponse(false, "not fount dekan");
                    }
                } else {
                    return new ApiResponse(false, "not fount ROLE STAFF");
                }
            } else {
                return new ApiResponse(false, "not fount user");
            }
        }
        else {
            return new ApiResponse(false,"error. already exists staff");
        }
    }



    @Override
    public ApiResponse getStatisticsForRektor(String sectionId, Date date) {


        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay == 31) {
            return new ApiResponse(true, "<??? 31", staffRepository.getDateForRektor31(date, sectionId));
        } else if (maxDay == 30) {
            return new ApiResponse(true, "<??? 30", staffRepository.getDateForRektor30(date, sectionId));
        } else if (maxDay == 29) {
            return new ApiResponse(true, "<??? 29", staffRepository.getDateForRektor29(date, sectionId));
        } else {
            return new ApiResponse(true, "<??? 28", staffRepository.getDateForRektor28(date, sectionId));
        }
    }

    @Override
    public ApiResponse getStaffIdWithUserId(String userId) {
        Optional<Staff> staffOptional = staffRepository.findStaffByUserId(userId);
        return staffOptional.map(staff -> new ApiResponse(true, "staff id", staff.getId())).orElseGet(() -> new ApiResponse(false, "not fount staff"));
    }

    @Override
    public ApiResponse deleteStaff(String staffId,String userId) {
        Optional<Staff> staffOptional = staffRepository.findById(staffId);
        if (staffOptional.isPresent()) {
            if (staffOptional.get().getUser().getId().equals(userId)) {
                return new ApiResponse(false,"You cannot deleted yourself");
            }
        }
        staffRepository.deleteById(staffId);
        return new ApiResponse(true,"deleted staff successfully..");
    }

    @Override
    public ApiResponse getDekanStatisticsForRektor(String id, Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay == 31) {
            return new ApiResponse(true, "<??? 31", staffRepository.getDateForRektor31(date, id));
        } else if (maxDay == 30) {
            return new ApiResponse(true, "<??? 30", staffRepository.getDateForRektor30(date, id));
        } else if (maxDay == 29) {
            return new ApiResponse(true, "<??? 29", staffRepository.getDateForRektor29(date, id));
        } else {
            return new ApiResponse(true, "<??? 28", staffRepository.getDateForRektor28(date, id));
        }
    }

}
