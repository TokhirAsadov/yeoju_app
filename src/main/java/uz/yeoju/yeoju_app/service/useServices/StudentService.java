package uz.yeoju.yeoju_app.service.useServices;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.enums.Gandername;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;
import uz.yeoju.yeoju_app.entity.uquvbulim.DataOfLastActive;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.payload.payres.FacultyStatisticDto;
import uz.yeoju.yeoju_app.payload.payres.StudentFullNameAndAscAndDescDateDto;
import uz.yeoju.yeoju_app.payload.resDto.student.*;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.uquvbulimi.DataOfLastActiveRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.StudentImplService;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.io.FilenameUtils;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository.DataOfLastActiveService;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentImplService<StudentDto> {
    public final StudentRepository studentRepository;
    public final PasswordEncoder passwordEncoder;
    public final UserInfoRepo userInfoRepo;
    private final UserService userService;
    private final FacultyService facultyService;
    //private final GroupService userService;

    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final GanderRepository ganderRepository;
    public final GroupRepository groupRepository;

    public final FacultyRepository facultyRepository;
    public final EducationLanRepository eduLanRepo;
    public final EducationFormRepository eduFormRepo;
    public final EducationTypeRepository eduTypeRepo;
    public final DataOfLastActiveService dataOfLastActiveService;
    public final DataOfLastActiveRepository dataOfLastActiveRepository;


    @Transactional
    public ApiResponse saving(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcel(file);
            }
        }
        return null;
    }


    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            int count = 0;
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println((int)row.getCell(1).getNumericCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                System.out.println(row.getCell(3).getStringCellValue());
                System.out.println(row.getCell(4).getStringCellValue());
                System.out.println(row.getCell(4)!=null && !Objects.equals(row.getCell(4).getStringCellValue(), ""));
                System.out.println(row.getCell(5).getStringCellValue());
                System.out.println(row.getCell(6).getStringCellValue());
                System.out.println(row.getCell(7).getStringCellValue());
//                System.out.println(row.getCell(8).getStringCellValue());
//                System.out.println(row.getCell(9).getStringCellValue());

                if (row.getCell(4)!=null && !Objects.equals(row.getCell(4).getStringCellValue(), "")) {
                    User userByRFID = userRepository.findUserByRFID(row.getCell(4).getStringCellValue());

                    if (userByRFID !=null) {
                        USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(4).getStringCellValue());
                        if (userinfoByCardNoForSaving1!=null) {
//                            userinfoByCardNoForSaving1.setCardNo(row.getCell(4).getStringCellValue());
                            userinfoByCardNoForSaving1.setLastname(row.getCell(0).getStringCellValue());
                            userinfoByCardNoForSaving1.setName(row.getCell(0).getStringCellValue());
                            userInfoRepo.save(userinfoByCardNoForSaving1);
                        }
                        else {
                            /**============== save user info ==============**/
                            USERINFO userinfo = new USERINFO();
                            userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                            userinfo.setCardNo(row.getCell(4).getStringCellValue());
                            userinfo.setLastname(row.getCell(0).getStringCellValue());
                            userinfo.setName(row.getCell(0).getStringCellValue());
                            userInfoRepo.save(userinfo);    // save userinfo
                        }

                        if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                            Group group = new Group();
                            group.setActive(true);
                            group.setName(row.getCell(2).getStringCellValue());
                            group.setLevel((int) row.getCell(1).getNumericCellValue());

                            if (group.getName().charAt(group.getName().length() - 1) == 'U')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'R')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'E')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());

                            if (group.getName().indexOf('-') == 3) {
                                group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
                            } else {
                                if (group.getName().charAt(3) == 'P')
                                    group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
                                else group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                            }



                            Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(row.getCell(2).getStringCellValue().substring(0, 3));

                            System.out.println(row.getCell(2).getStringCellValue().substring(0, 2)+" <------------------------------");
                            System.out.println(row.getCell(2).getStringCellValue().substring(0, 3)+" <------------------------------");
                            System.out.println(facultyOptional.get().toString()+" ----+++++++++++++---------++++++++----------");
                            group.setFaculty(facultyOptional.get());

                            Group save1 = groupRepository.save(group);

                            userByRFID.setEnabled(true);
                            userByRFID.setAccountNonExpired(true);
                            userByRFID.setAccountNonLocked(true);
                            userByRFID.setCredentialsNonExpired(true);

                            userByRFID.setRFID(row.getCell(4).getStringCellValue());
                            userByRFID.setFullName(row.getCell(0).getStringCellValue());
                            userByRFID.setLogin(row.getCell(3).getStringCellValue());
                            userByRFID.setPassportNum(row.getCell(5).getStringCellValue());
                            userByRFID.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                            Set<Role> userRoles = new HashSet<>();
                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                            roleOptional.ifPresent(userRoles::add);
                            userByRFID.setRoles(userRoles);
//                        userByRFID.setNationality(row.getCell(9).getStringCellValue());
//                        if (row.getCell(4).getStringCellValue()=="MALE"){
//                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                            userByRFID.setGander(ganderByGandername);
//                        }
//                        else {
//                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                            userByRFID.setGander(ganderByGandername);
//                        }

                            userRepository.save(userByRFID);
                            count++;

                            //todo-----------
                            if (studentRepository.existsStudentByUserId(userByRFID.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(userByRFID.getId());

                                studentByUserId.setUser(userByRFID);
                                studentByUserId.setGroup(save1);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(userByRFID);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(save1);
                                Student save = studentRepository.save(student);
                            }


                        }
                        else {
                            userByRFID.setEnabled(true);
                            userByRFID.setAccountNonExpired(true);
                            userByRFID.setAccountNonLocked(true);
                            userByRFID.setCredentialsNonExpired(true);
                            userByRFID.setRFID(row.getCell(4).getStringCellValue());
                            userByRFID.setFullName(row.getCell(0).getStringCellValue());
                            userByRFID.setLogin(row.getCell(3).getStringCellValue());
                            userByRFID.setPassportNum(row.getCell(5).getStringCellValue());
                            userByRFID.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                            Set<Role> userRoles = new HashSet<>();
                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                            roleOptional.ifPresent(userRoles::add);
                            userByRFID.setRoles(userRoles);
//                        userByRFID.setNationality(row.getCell(9).getStringCellValue());
//                        if (row.getCell(4).getStringCellValue()=="MALE"){
//                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                            userByRFID.setGander(ganderByGandername);
//                        }
//                        else {
//                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                            userByRFID.setGander(ganderByGandername);
//                        }

                            User save1 = userRepository.save(userByRFID);
                            count++;

                            // group
                            Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                            groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                            groupRepository.save(groupByName);

                            // student
                            if (studentRepository.existsStudentByUserId(save1.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(save1.getId());

                                studentByUserId.setUser(save1);
                                studentByUserId.setGroup(groupByName);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(userByRFID);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(groupByName);
                                Student save = studentRepository.save(student);
                            }

                        }
                    }
                    else {
                        USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(4).getStringCellValue());
                        if (userinfoByCardNoForSaving1!=null) {
//                            userinfoByCardNoForSaving1.setCardNo(row.getCell(4).getStringCellValue());
                            userinfoByCardNoForSaving1.setLastname(row.getCell(0).getStringCellValue());
                            userinfoByCardNoForSaving1.setName(row.getCell(0).getStringCellValue());
                            userInfoRepo.save(userinfoByCardNoForSaving1);
                        }
                        else {
                            /**============== save user info ==============**/
                            USERINFO userinfo = new USERINFO();
                            userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                            userinfo.setCardNo(row.getCell(4).getStringCellValue());
                            userinfo.setLastname(row.getCell(0).getStringCellValue());
                            userinfo.setName(row.getCell(0).getStringCellValue());
                            userInfoRepo.save(userinfo);    // save userinfo
                        }

                        Optional<User> optionalUserByLogin = userRepository.findUserByLogin(row.getCell(3).getStringCellValue());
                        if (optionalUserByLogin.isPresent()) {
                            User user = optionalUserByLogin.get();
                            user.setEnabled(true);
                            user.setAccountNonExpired(true);
                            user.setAccountNonLocked(true);
                            user.setCredentialsNonExpired(true);
                            user.setRFID(row.getCell(4).getStringCellValue());
                            user.setFullName(row.getCell(0).getStringCellValue());
                            user.setLogin(row.getCell(3).getStringCellValue());
                            user.setPassportNum(row.getCell(5).getStringCellValue());
                            user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                            Set<Role> userRoles = new HashSet<>();
                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                            roleOptional.ifPresent(userRoles::add);
                            user.setRoles(userRoles);
//                    user.setNationality(row.getCell(9).getStringCellValue());
//                    if (row.getCell(4).getStringCellValue()=="MALE"){
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
//                    else {
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
                            userRepository.saveAndFlush(user);
                            count++;


                            if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                                Group group = new Group();
                                group.setActive(true);
                                group.setName(row.getCell(2).getStringCellValue());
                                group.setLevel((int) row.getCell(1).getNumericCellValue());

                                if (group.getName().charAt(group.getName().length() - 1) == 'U')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
                                if (group.getName().charAt(group.getName().length() - 1) == 'R')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
                                if (group.getName().charAt(group.getName().length() - 1) == 'E')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());

                                if (group.getName().indexOf('-') == 3) {
                                    group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
                                } else {
                                    if (group.getName().charAt(3) == 'P')
                                        group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
                                    else group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                                }

                                Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(row.getCell(2).getStringCellValue().substring(0, 3));
                                group.setFaculty(facultyOptional.get());
                                System.out.println(facultyOptional.get().toString()+" ----+++++++++++++---------++++++++----------");
                                Group save1 = groupRepository.save(group);


                                if (studentRepository.existsStudentByUserId(user.getId())) {
                                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                    studentByUserId.setUser(user);
                                    studentByUserId.setGroup(save1);
                                    studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                    studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                    Student save = studentRepository.save(studentByUserId);
                                }
                                else {
                                    Student student = new Student();
                                    student.setUser(user);
                                    student.setTeachStatus(TeachStatus.TEACHING);
                                    student.setRektororder(row.getCell(6).getStringCellValue());
                                    student.setGroup(save1);
                                    Student save = studentRepository.save(student);
                                }

                            }
                            else {

                                // group
                                Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                                groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                                groupRepository.save(groupByName);

                                // student
                                if (studentRepository.existsStudentByUserId(user.getId())) {
                                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                    studentByUserId.setUser(user);
                                    studentByUserId.setGroup(groupByName);
                                    studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                    studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                    Student save = studentRepository.save(studentByUserId);
                                }
                                else {
                                    Student student = new Student();
                                    student.setUser(user);
                                    student.setTeachStatus(TeachStatus.TEACHING);
                                    student.setRektororder(row.getCell(6).getStringCellValue());
                                    student.setGroup(groupByName);
                                    Student save = studentRepository.save(student);
                                }

                            }
                        }
                        else {
                            User user = new User();
                            user.setEnabled(true);
                            user.setAccountNonExpired(true);
                            user.setAccountNonLocked(true);
                            user.setCredentialsNonExpired(true);
                            user.setRFID(row.getCell(4).getStringCellValue());
                            user.setFullName(row.getCell(0).getStringCellValue());
                            user.setLogin(row.getCell(3).getStringCellValue());
                            user.setPassportNum(row.getCell(5).getStringCellValue());
                            user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                            Set<Role> userRoles = new HashSet<>();
                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                            roleOptional.ifPresent(userRoles::add);
                            user.setRoles(userRoles);
//                    user.setNationality(row.getCell(9).getStringCellValue());
//                    if (row.getCell(4).getStringCellValue()=="MALE"){
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
//                    else {
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
                            userRepository.saveAndFlush(user);
                            count++;


                            if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                                Group group = new Group();
                                group.setActive(true);
                                group.setName(row.getCell(2).getStringCellValue());
                                group.setLevel((int) row.getCell(1).getNumericCellValue());

                                if (group.getName().charAt(group.getName().length() - 1) == 'U')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
                                if (group.getName().charAt(group.getName().length() - 1) == 'R')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
                                if (group.getName().charAt(group.getName().length() - 1) == 'E')
                                    group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());

                                if (group.getName().indexOf('-') == 3) {
                                    group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
                                } else {
                                    if (group.getName().charAt(3) == 'P')
                                        group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
                                    else group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                                }

                                Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(row.getCell(2).getStringCellValue().substring(0, 3));
                                group.setFaculty(facultyOptional.get());
                                System.out.println(facultyOptional.get().toString()+" ----+++++++++++++---------++++++++----------");
                                Group save1 = groupRepository.save(group);


                                if (studentRepository.existsStudentByUserId(user.getId())) {
                                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                    studentByUserId.setUser(user);
                                    studentByUserId.setGroup(save1);
                                    studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                    studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                    Student save = studentRepository.save(studentByUserId);
                                }
                                else {
                                    Student student = new Student();
                                    student.setUser(user);
                                    student.setTeachStatus(TeachStatus.TEACHING);
                                    student.setRektororder(row.getCell(6).getStringCellValue());
                                    student.setGroup(save1);
                                    Student save = studentRepository.save(student);
                                }

                            }
                            else {

                                // group
                                Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                                groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                                groupRepository.save(groupByName);

                                // student
                                if (studentRepository.existsStudentByUserId(user.getId())) {
                                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                    studentByUserId.setUser(user);
                                    studentByUserId.setGroup(groupByName);
                                    studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                    studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                    Student save = studentRepository.save(studentByUserId);
                                }
                                else {
                                    Student student = new Student();
                                    student.setUser(user);
                                    student.setTeachStatus(TeachStatus.TEACHING);
                                    student.setRektororder(row.getCell(6).getStringCellValue());
                                    student.setGroup(groupByName);
                                    Student save = studentRepository.save(student);
                                }

                            }
                        }



                    }
                }
                else {


                    Optional<User> optionalUserByLogin = userRepository.findUserByLogin(row.getCell(3).getStringCellValue());
                    if (optionalUserByLogin.isPresent()) {
                        User user = optionalUserByLogin.get();
                        user.setEnabled(true);
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
//                    user.setRFID(row.getCell(4).getStringCellValue());
                        user.setFullName(row.getCell(0).getStringCellValue());
                        user.setLogin(row.getCell(3).getStringCellValue());
                        user.setPassportNum(row.getCell(5).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                        Set<Role> userRoles = new HashSet<>();
                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                        roleOptional.ifPresent(userRoles::add);
                        user.setRoles(userRoles);

                        System.out.println("-------> "+user);

                        userRepository.saveAndFlush(user);
                        count++;

                        System.out.println("-------> "+2);
                        if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                            System.out.println("-------> "+3);
                            Group group = new Group();
                            group.setActive(true);
                            group.setName(row.getCell(2).getStringCellValue());
                            group.setLevel((int) row.getCell(1).getNumericCellValue());

                            if (group.getName().charAt(group.getName().length() - 1) == 'U')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'R')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'E')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());

                            if (group.getName().indexOf('-') == 3) {
                                group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
                            } else {
                                if (group.getName().charAt(3) == 'P')
                                    group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
                                else group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                            }

                            Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(row.getCell(2).getStringCellValue().substring(0, 3));
                            group.setFaculty(facultyOptional.get());
                            System.out.println(facultyOptional.get().toString()+" ----+++++++++++++---------++++++++----------");
                            Group save1 = groupRepository.save(group);


                            if (studentRepository.existsStudentByUserId(user.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                studentByUserId.setUser(user);
                                studentByUserId.setGroup(save1);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                                System.out.println("-------> save1"+save);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(user);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(save1);
                                Student save = studentRepository.save(student);
                            System.out.println("-------> save1"+save);
                            }
                        }
                        else {

                            // group
                            Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                            groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                            groupRepository.save(groupByName);

                            // student
                            if (studentRepository.existsStudentByUserId(user.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                studentByUserId.setUser(user);
                                studentByUserId.setGroup(groupByName);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                                System.out.println("-------> save2"+save);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(user);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(groupByName);
                                Student save = studentRepository.save(student);
                            System.out.println("-------> save2"+save);
                            }
                        }
                    }
                    else {
                        System.out.println("-------> new user");
                        User user = new User();
                        user.setEnabled(true);
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
//                    user.setRFID(row.getCell(4).getStringCellValue());
                        user.setFullName(row.getCell(0).getStringCellValue());
                        user.setLogin(row.getCell(3).getStringCellValue());
                        user.setPassportNum(row.getCell(5).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
                        Set<Role> userRoles = new HashSet<>();
                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                        roleOptional.ifPresent(userRoles::add);
                        user.setRoles(userRoles);
//                    user.setNationality(row.getCell(9).getStringCellValue());
//                    if (row.getCell(4).getStringCellValue()=="MALE"){
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
//                    else {
//                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                        user.setGander(ganderByGandername);
//                    }
                        userRepository.saveAndFlush(user);
                        System.out.println("-------> save1"+user);
                        count++;


                        if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                            Group group = new Group();
                            group.setActive(true);
                            group.setName(row.getCell(2).getStringCellValue());
                            group.setLevel((int) row.getCell(1).getNumericCellValue());

                            if (group.getName().charAt(group.getName().length() - 1) == 'U')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'R')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
                            if (group.getName().charAt(group.getName().length() - 1) == 'E')
                                group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());

                            if (group.getName().indexOf('-') == 3) {
                                group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
                            } else {
                                if (group.getName().charAt(3) == 'P')
                                    group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
                                else group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                            }

                            Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(row.getCell(2).getStringCellValue().substring(0, 3));
                            group.setFaculty(facultyOptional.get());
                            System.out.println(facultyOptional.get().toString()+" ----+++++++++++++---------++++++++----------");
                            Group save1 = groupRepository.save(group);


                            if (studentRepository.existsStudentByUserId(user.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                studentByUserId.setUser(user);
                                studentByUserId.setGroup(save1);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(user);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(save1);
                                Student save = studentRepository.save(student);
                            }

                        }
                        else {

                            // group
                            Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                            groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                            groupRepository.save(groupByName);

                            // student
                            if (studentRepository.existsStudentByUserId(user.getId())) {
                                Student studentByUserId = studentRepository.findStudentByUserId(user.getId());

                                studentByUserId.setUser(user);
                                studentByUserId.setGroup(groupByName);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(6).getStringCellValue());

                                Student save = studentRepository.save(studentByUserId);
                            }
                            else {
                                Student student = new Student();
                                student.setUser(user);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setRektororder(row.getCell(6).getStringCellValue());
                                student.setGroup(groupByName);
                                Student save = studentRepository.save(student);
                            }

                        }
                    }




                }


            }

            dataOfLastActiveRepository.save(new DataOfLastActive(count+" ta student ma'lumoti yangilandi!."));
            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }

    @Transactional
    public ApiResponse savingRektororders(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcel2(file);
            }
        }
        return null;
    }

    @Transactional
    public ApiResponse readDataFromExcel2(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());

                User userByRFID = userRepository.getUserByLoginOrEmailOrRFID(row.getCell(0).getStringCellValue(),row.getCell(0).getStringCellValue(),row.getCell(0).getStringCellValue());

                if (userByRFID !=null) {

                    Student studentByUserId1 = studentRepository.findStudentByUserId(userByRFID.getId());
                    if (studentByUserId1!=null) {
//                        studentByUserId1.setRektororder(row.getCell(2).getStringCellValue());
                        studentByUserId1.setLengthOfStudying(row.getCell(6).getStringCellValue());
                        studentRepository.save(studentByUserId1);
                    }

                }


            }


            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }


    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            assert extension != null;
            if (extension.equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());

            } else if (extension.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }








    public ApiResponse getStudentWithRFIDForToday(String groupName){
        List<StudentWithRFID> list = studentRepository.getStudentWithRFID(groupName);

        List<StudentFullNameAndAscAndDescDateDto> ascAndDescDateDtos = new ArrayList<>();
        int id = 1;
        for (StudentWithRFID rfid : list) {
            StudentWithAscAndDescDate ascAndDescDate = studentRepository.getStudentWithAscAndDescDateForToday(rfid.getRFID());
            if (ascAndDescDate != null) {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                rfid.getId(),
                                rfid.getAccountNonLocked(),
                                rfid.getFullName(),
                                rfid.getFirstName(),
                                rfid.getLastName(),
                                rfid.getMiddleName(),
                                rfid.getEmail(),
                                rfid.getPassport(),
                                rfid.getLogin(),
                                rfid.getRFID(),
                                rfid.getStatus(),
                                ascAndDescDate.getTimeAsc(),
                                ascAndDescDate.getTimeDesc()
                        )
                );
            }
            else {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                rfid.getId(),
                                rfid.getAccountNonLocked(),
                                rfid.getFullName(),
                                rfid.getFirstName(),
                                rfid.getLastName(),
                                rfid.getMiddleName(),
                                rfid.getEmail(),
                                rfid.getPassport(),
                                rfid.getLogin(),
                                rfid.getRFID(),
                                rfid.getStatus()
                        )
                );
            }
            id++;
        }
        id = 1;
        return new ApiResponse(true,"send",ascAndDescDateDtos);

    }

    public ApiResponse getStudentWithRFID(String groupName,LocalDateTime dateFrom,LocalDateTime dateTo){
        List<StudentWithRFID> list = studentRepository.getStudentWithRFID(groupName);

        List<StudentFullNameAndAscAndDescDateDto> ascAndDescDateDtos = new ArrayList<>();
        int id = 1;
        for (StudentWithRFID rfid : list) {
            StudentWithAscAndDescDate ascAndDescDate = studentRepository.getStudentWithAscAndDescDate(rfid.getRFID(), dateFrom, dateTo);
            if (ascAndDescDate != null) {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                rfid.getId(),
                                rfid.getAccountNonLocked(),
                                rfid.getFullName(),
                                rfid.getFirstName(),
                                rfid.getLastName(),
                                rfid.getMiddleName(),
                                rfid.getEmail(),
                                rfid.getPassport(),
                                rfid.getLogin(),
                                rfid.getRFID(),
                                rfid.getStatus(),
                                ascAndDescDate.getTimeAsc(),
                                ascAndDescDate.getTimeDesc()
                        )
                );
            }
            else {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                rfid.getId(),
                                rfid.getAccountNonLocked(),
                                rfid.getFullName(),
                                rfid.getFirstName(),
                                rfid.getLastName(),
                                rfid.getMiddleName(),
                                rfid.getEmail(),
                                rfid.getPassport(),
                                rfid.getLogin(),
                                rfid.getRFID(),
                                rfid.getStatus()
                        )
                );
            }
            id++;
        }
        id = 1;
        return new ApiResponse(true,"send",ascAndDescDateDtos);

    }

    public ApiResponse getStudentsByTimeIntervalAndLevelAndFacultyName(Integer timeInterval, Integer level, String facultyName){
        List<FacultyStatistic> statistics = studentRepository.getStudentsByTimeIntervalAndLevelAndFacultyName(timeInterval, level, facultyName);
        return new ApiResponse(true,"send",statistics);
    }


    public ApiResponse getGroupsStatisticByFacultyNameAndGroupLevel(Integer level,String facultyName, LocalDateTime dateFrom, LocalDateTime dateTo){
        List<FacultyStatistic> statistics = studentRepository.getGroupsStatisticByFacultyNameAndGroupLevel(level, facultyName, dateFrom, dateTo);
        return new ApiResponse(true,"send",statistics);
    }

    public ApiResponse getGroupsByLevelAndFacultyName(Integer level,String facultyName){
        List<String> groups = studentRepository.getGroupsByLevelAndFacultyName(level, facultyName);
        return new ApiResponse(true,"send",groups);
    }


    public ApiResponse getComeCountStudentAndFacultyName(Integer level, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<FacultyStatisticComing> comingList = studentRepository.getComeCountStudentAndFacultyName(level, dateFrom, dateTo);
        List<FacultyStatisticAll> allList = studentRepository.getAllCountStudentAndFaculty(level);
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatisticAll all : allList) {
            for (FacultyStatisticComing coming : comingList) {
                if (coming.getName().equals(all.getName())) {
                    FacultyStatisticDto dto = new FacultyStatisticDto(all.getName(), coming.getComeCount(), (all.getAllCount() - coming.getComeCount()));
                    dtos.add(dto);
                }
            }
        }
        return new ApiResponse(true, "send ------------", dtos);
    }

    public ApiResponse getAllCourses() {
        return new ApiResponse(true, "all getAllCourses", studentRepository.getAllCourses());
    }

    public ApiResponse getFacultyByCourseLevel(Integer level) {
        List<String> facultyByLevels = studentRepository.getFacultyByCourseLevel(level);
        return new ApiResponse(true, "send", facultyByLevels);
    }

    public ApiResponse getStudentCountByGroupAndFaculty(String facultyName, Integer level, LocalDateTime startTime, LocalDateTime endTime) {
        List<FacultyStatistic> statistics = studentRepository.getStudentCountByGroupAndFaculty(facultyName, level, startTime, endTime);
        return new ApiResponse(true, "send", statistics);
    }

    public ApiResponse getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(Integer level, Integer weekOrMonth,String eduType) {
        long start = System.currentTimeMillis();
        System.out.println(level);
        System.out.println("Kirdi methodga");
//        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(level, weekOrMonth);
        List<FacultyStatisticsWithWeekOrMonth> list2 = studentRepository.getSchoolStatisticsByWeekOrMonth(level, weekOrMonth,eduType);

//        System.out.println("DB dan keldi: " + (System.currentTimeMillis() - start));
//
//        start=System.currentTimeMillis();
//        List<FacultyStatisticDto> dtos = new ArrayList<>();
//        for (FacultyStatistic item : list) {
//            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
//            dtos.add(dto);
//        }
//
//        System.out.println("Method tugadi");
//        System.out.println(System.currentTimeMillis() - start);

        System.out.println("database dan keldi --------------------------------------------------------------------------");
        System.out.println(list2);

        return new ApiResponse(true, "send",list2);
    }
    public ApiResponse getFacultyAndComingCountWithAllByGroupLevel(Integer level,String eduType, LocalDateTime startTime, LocalDateTime endTime) {
        long start = System.currentTimeMillis();
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(level);
        System.out.println("Kirdi methodga");
//        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAllByGroupLevel(level, startTime, endTime);
        List<FacultyStatisticsWithSchoolCode> list2 = studentRepository.getSchoolStatistics(level,eduType, startTime, endTime);


//        System.out.println("DB dan keldi: " + (System.currentTimeMillis() - start));
//
//        start=System.currentTimeMillis();
//        List<FacultyStatisticDto> dtos = new ArrayList<>();
//        for (FacultyStatistic item : list) {
//            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
//            dtos.add(dto);
//        }
//
//        System.out.println("Method tugadi");
//        System.out.println(System.currentTimeMillis() - start);

        System.out.println("-----------------------------------------------------------------------------------------------------------------");
        return new ApiResponse(true, "send", list2);
    }

    public ApiResponse getFacultyAndComingCountWithAll(LocalDateTime startTime, LocalDateTime endTime) {
        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAll(startTime, endTime);
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatistic item : list) {
            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
            dtos.add(dto);
        }

        return new ApiResponse(true, "send", dtos);
    }

    public ApiResponse createStudentsByRfidAndGroupNames(List<String> rfid, List<String> groupNames) {
        System.out.println(rfid.size());
        System.out.println(groupNames.size());
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int i = 0; i < rfid.size(); i++) {
            User userByRFID = userRepository.findUserByRFID(rfid.get(i));
            Group groupByName = groupRepository.findGroupByName(groupNames.get(i));

            Student student = new Student();
            student.setGroup(groupByName);
            student.setUser(userByRFID);
            student.setTeachStatus(TeachStatus.TEACHING);
            studentRepository.save(student);
            System.out.println(student);
        }

        return new ApiResponse(true, "success");
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all students",
                studentRepository.findAll().stream().map(this::generateStudentDto).collect(Collectors.toSet())
        );
    }

    private StudentDto generateStudentDto(Student student) {
        return new StudentDto(
                student.getId(),
                userService.generateUserDto(student.getUser()),
                generateGroupDto(student.getGroup()),
                student.getPassportSerial(),
                student.getBornYear(),
                student.getDescription(),
                student.getEnrollmentYear(),
                student.getCitizenship()
        );
    }

    private GroupDto generateGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getLevel(),
                facultyService.generateFacultyDto(group.getFaculty())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return studentRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount user by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount user by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Student student = studentRepository.getById(id);
        return new ApiResponse(true, "Fount user by id", student);
    }

    @Override
    public ApiResponse saveOrUpdate(StudentDto studentDto) {
        if (studentDto.getId() == null) {
            return save(studentDto);
        } else {
            return update(studentDto);
        }
    }

    public ApiResponse update(StudentDto dto) {
        Optional<Student> optional = studentRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Student student = optional.get();
            Student studentByUserId = studentRepository.findStudentByUserId(dto.getUserDto().getId());
            if (studentByUserId != null) {
                if (
                        Objects.equals(studentByUserId.getId(), student.getId())
                                ||
                                !studentRepository.existsStudentByUserId(dto.getUserDto().getId())
                ) {
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true, "user updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! did not saveOrUpdate user!"
                    );
                }
            } else {
                if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())) {
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true, "user updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! did not saveOrUpdate user!"
                    );
                }
            }
        } else {
            return new ApiResponse(
                    false,
                    "error... not fount user"
            );
        }
    }

    private Group generateGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getLevel(),
                facultyService.generateFaculty(groupDto.getFacultyDto())
        );
    }

    public ApiResponse save(StudentDto dto) {
        if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())) {
            Student student = generateStudent(dto);
            studentRepository.saveAndFlush(student);
            return new ApiResponse(true, "new user saved successfully!...");
        } else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate user!"
            );
        }
    }

    private Student generateStudent(StudentDto dto) {
        return new Student(
                userService.generateUser(dto.getUserDto()),
                generateGroup(dto.getGroupDto()),
                dto.getPassportSerial(),
                dto.getBornYear(),
                dto.getDescription(),
                dto.getEnrollmentYear(),
                dto.getCitizenship()
        );
    }

    @Override
    public ApiResponse deleteById(String id) {
        if (studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            return new ApiResponse(true, "user deleted successfully!..");
        } else {
            return new ApiResponse(false, "error... not fount user!");
        }
    }

    @Override
    public ApiResponse findStudentByUserId(String user_id) {
        Student studentByUserId = studentRepository.findStudentByUserId(user_id);
        if (studentByUserId != null) {
            return new ApiResponse(
                    true,
                    "fount user by user id",
                    generateStudentDto(studentByUserId)
            );
        } else {
            return new ApiResponse(
                    false,
                    "not fount user by user id"
            );
        }
    }

    @Override
    public ApiResponse findStudentsByGroupId(String group_id) {
        return new ApiResponse(
                true,
                "List of all students by group id",
                studentRepository.findStudentsByGroupId(group_id)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

//    @Override
//    public ApiResponse findStudentsByEducationFormId(String educationForm_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education form id",
//                studentRepository.findStudentsByEducationFormId(educationForm_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

//    @Override
//    public ApiResponse findStudentsByEducationTypeId(String educationType_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education type id",
//                studentRepository.findStudentsByEducationTypeId(educationType_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

//    @Override
//    public ApiResponse findStudentsByEducationLanguageId(String educationLanguage_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education language id",
//                studentRepository.findStudentsByEducationLanguageId(educationLanguage_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

    @Override
    public ApiResponse findStudentByPassportSerial(String passportSerial) {
        Student studentByPassportSerial = studentRepository.findStudentByPassportSerial(passportSerial);
        if (studentByPassportSerial != null) {
            return new ApiResponse(
                    true,
                    "fount user by Passport Serial",
                    generateStudentDto(studentByPassportSerial)
            );
        } else {
            return new ApiResponse(
                    false,
                    "not fount user by Passport Serial"
            );
        }
    }

    @Override
    public ApiResponse findStudentsByBornYear(Timestamp bornYear) {
        return new ApiResponse(
                true,
                "List of all students by born year",
                studentRepository.findStudentsByBornYear(bornYear)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByEnrollmentYear(Timestamp enrollmentYear) {
        return new ApiResponse(
                true,
                "List of all students by enrollment year",
                studentRepository.findStudentsByEnrollmentYear(enrollmentYear)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByCitizenship(String citizenship) {
        return new ApiResponse(
                true,
                "List of all students by citizenship",
                studentRepository.findStudentsByCitizenship(citizenship)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }



    @Transactional
    public ApiResponse uploadStudentsFromDean(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcel3(file);
            }
        }
        return null;
    }

    @Transactional
    public ApiResponse readDataFromExcel3(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            long step = 1;
            while (rows.hasNext()){
                step++;
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue()+" FIO");
                System.out.println(row.getCell(1).getStringCellValue()+" order");
                System.out.println(row.getCell(2).getStringCellValue()+" group");
                System.out.println(row.getCell(3).getStringCellValue()+" sex");
                System.out.println(row.getCell(4).getStringCellValue()+" ID");
                System.out.println(row.getCell(5).getStringCellValue()+" Passport");
//                System.out.println(row.getCell(6).getStringCellValue()+" RFID");
//                System.out.println(row.getCell(7).getStringCellValue());
//                System.out.println(row.getCell(8).getStringCellValue());
//                System.out.println(row.getCell(9).getStringCellValue());

                Integer countByLogin = userRepository.getCountByLogin(row.getCell(4).getStringCellValue());
                if (countByLogin==null || countByLogin<=1) {
                    User userByLogin = userRepository.getUserByLogin(row.getCell(4).getStringCellValue());
                    if (userByLogin != null) {
                        Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                        if (groupByName != null) {
                            Student studentByUserId = studentRepository.findStudentByUserId(userByLogin.getId());
                            if (studentByUserId != null) {
                                Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                                Role role = optionalRole.get();
                                studentByUserId.setGroup(groupByName);
                                studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                studentByUserId.setRektororder(row.getCell(1).getStringCellValue());
                                studentRepository.save(studentByUserId);
                                userByLogin.setPassportNum(row.getCell(5).getStringCellValue());
                                Set<Role> roles = new HashSet<Role>();
                                roles.add(role);
                                userByLogin.setRoles(roles);
                                userRepository.save(userByLogin);
                            } else {

                                Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                                Role role = optionalRole.get();
                                userByLogin.setPassportNum(row.getCell(5).getStringCellValue());
                                Set<Role> roles = new HashSet<Role>();
                                roles.add(role);
                                userByLogin.setRoles(roles);
                                userRepository.save(userByLogin);

                                Student student = new Student();
                                student.setUser(userByLogin);
                                student.setTeachStatus(TeachStatus.TEACHING);
                                student.setGroup(groupByName);
                                student.setRektororder(row.getCell(1).getStringCellValue());
                                studentRepository.save(student);

                            }


                        }//todo---------------------
                        else {
                            return new ApiResponse(false, "error.. not found group by:" + row.getCell(2).getStringCellValue());
                        }
                    }
                }
//                else {
//                    Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
//                    if (groupByName!=null){
//
//                        User userByRFID = userRepository.findUserByRFID(row.getCell(6).getStringCellValue());
//                        if (userByRFID==null) {
//                            User user = new User();
//                            if (row.getCell(3).getStringCellValue().equalsIgnoreCase("male")){
//                                Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
//                                user.setGander(ganderByGandername);
//                            }
//                            else {
//                                Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.FEMALE);
//                                user.setGander(ganderByGandername);
//                            }
//                            user.setFullName(row.getCell(0).getStringCellValue());
//                            user.setLogin(row.getCell(4).getStringCellValue());
//                            user.setPassportNum(row.getCell(5).getStringCellValue());
//                            user.setPassword(passwordEncoder.encode(row.getCell(5).getStringCellValue()));
//                            user.setRFID(row.getCell(6).getStringCellValue());
//                            Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_STUDENT");
//                            Role role = optionalRole.get();
//                            Set<Role> roles = new HashSet<Role>();
//                            roles.add(role);
//                            user.setRoles(roles);
//                            userRepository.saveAndFlush(user);
//
//                            USERINFO userinfo = new USERINFO();
//                            userinfo.setName(row.getCell(0).getStringCellValue());
//                            userinfo.setCardNo(row.getCell(6).getStringCellValue());
//                            Long badgenumber = userInfoRepo.getBadgenumber();
//                            userinfo.setBadgenumber(badgenumber+1);
//                            userInfoRepo.save(userinfo);
//
//                            Student student = new Student();
//                            student.setUser(user);
//                            student.setGroup(groupByName);
//                            student.setRektororder(row.getCell(1).getStringCellValue());
//                            studentRepository.save(student);
//                        }
//                        else {
//                            Student studentByUserId = studentRepository.findStudentByUserId(userByRFID.getId());
//                            if (studentByUserId != null){
//                                Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_STUDENT");
//                                Role role = optionalRole.get();
//                                studentByUserId.setGroup(groupByName);
//                                studentByUserId.setRektororder(row.getCell(1).getStringCellValue());
//                                studentRepository.save(studentByUserId);
//                                userByRFID.setPassportNum(row.getCell(5).getStringCellValue());
//                                Set<Role> roles = new HashSet<Role>();
//                                roles.add(role);
//                                userByRFID.setRoles(roles);
//                                userRepository.save(userByRFID);
//                            }
//                            else {
//
//                                Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_STUDENT");
//                                Role role = optionalRole.get();
//                                userByRFID.setPassportNum(row.getCell(5).getStringCellValue());
//                                Set<Role> roles = new HashSet<Role>();
//                                roles.add(role);
//                                userByRFID.setRoles(roles);
//                                userRepository.save(userByRFID);
//
//                                Student student = new Student();
//                                student.setUser(userByRFID);
//                                student.setGroup(groupByName);
//                                student.setRektororder(row.getCell(1).getStringCellValue());
//                                studentRepository.save(student);
//
//                            }
//                        }
//
//
//                    }//todo---------------------
//                    else {
//                        return new ApiResponse(false,"error.. not found group by:"+row.getCell(2).getStringCellValue());
//                    }
//                }


            }

            ApiResponse response = dataOfLastActiveService.create(step + " ta student ma'lumotlari yangilandi.");
            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }

    public ApiResponse changeGroupOfStudent(String userId, String groupName) {
        Student studentByUserId = studentRepository.findStudentByUserId(userId);
        Group groupByName = groupRepository.findGroupByName(groupName);
        if (groupByName!=null){
            studentByUserId.setGroup(groupByName);
            studentRepository.save(studentByUserId);
            return new ApiResponse(true,"changed group successfully");
        }
        else {
            return new ApiResponse(false,"not found group by: "+groupName);
        }
    }

    public ApiResponse changeTeachStatusOfStudent(String userId, TeachStatus teachStatus) {
        Student studentByUserId = studentRepository.findStudentByUserId(userId);
        studentByUserId.setTeachStatus(teachStatus);
        studentRepository.save(studentByUserId);
        return new ApiResponse(true,"changed teach status successfully");
    }

    public ApiResponse changeActiveOfStudent(String userId) {
        Student studentByUserId = studentRepository.findStudentByUserId(userId);
        studentRepository.deleteById(studentByUserId.getId());
        return new ApiResponse(true,"deleted student successfully");
    }

    public ApiResponse getDataForStudentReference(User user, String studentId) {
        return new ApiResponse(true,"data for reference",studentRepository.getDataForStudentReference(studentId));
    }

    public ApiResponse getDataForStudentReference2( String studentId) {
        return new ApiResponse(true,"data for reference",studentRepository.getDataForStudentReference(studentId));
    }

    public ApiResponse monitoring(User user, String groupId, String educationYearId, Integer year, Integer week, Integer day) {
        return new ApiResponse(true,"student's monitoring",studentRepository.getStudentMonitoring(user.getId(),groupId,educationYearId,year,week,day));
    }
}
