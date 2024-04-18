package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jaxen.util.SingletonList;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.enums.Gandername;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;
import uz.yeoju.yeoju_app.exceptions.PageSizeException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserInfoAndUserAndStudentDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.USERINFOImplService;
import uz.yeoju.yeoju_app.utills.CommandUtills;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class
USERINFOService implements USERINFOImplService<USERINFO> {
    private final UserInfoRepo userInfoRepo;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public final StudentRepository studentRepository;
    public final GroupRepository groupRepository;
    public final FacultyRepository facultyRepository;
    public final EducationLanRepository eduLanRepo;
    public final EducationFormRepository eduFormRepo;
    public final EducationTypeRepository eduTypeRepo;



    public ApiResponse byPageable(Integer page, Integer size ) throws PageSizeException {
        List<USERINFO> userinfoList=new ArrayList<>();
        long totalElements=0;

            Page<USERINFO> userinfoPage = userInfoRepo.findAll(CommandUtills.descOrAscByCreatedAtPageable(page,size,false));
            totalElements=userinfoPage.getTotalElements();


        return new ApiResponse(true,"All By Pageable",userinfoPage,totalElements);
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
    public ApiResponse saveOrUpdate(USERINFO userinfo) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

    @Transactional
    public ApiResponse createUserInfo(UserInfoAndUserAndStudentDto dto) {
        Long badgenumber = userInfoRepo.getBadgenumber();

        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getCardNo());
        System.out.println(dto.toString());
        if (byCardNo==null){
            USERINFO userinfo = new USERINFO();
            userinfo.setBadgenumber(badgenumber+1);
            userinfo.setName(dto.getName());
            userinfo.setLastname(dto.getLastname());
            userinfo.setCardNo(dto.getCardNo());
            System.out.println(userinfo.toString());
            userInfoRepo.save(userinfo);

            User user = new User();
            user.setFullName(dto.getName()+" "+dto.getLastname());
            user.setLogin(dto.getLogin());
            user.setRFID(dto.getCardNo());
            user.setPassportNum(dto.getPassport());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
            if (roleOptional.isPresent()){
                user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
            }
            else {
                user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
            }
            userRepository.save(user);
            return new ApiResponse(true,"saved user successfully.");
        }
        else {

            User userByRFID = userRepository.findUserByRFID(dto.getCardNo());
            if (userByRFID==null){
                User user = new User();
                user.setFullName(dto.getName()+" "+dto.getLastname());
                user.setLogin(dto.getLogin());
                user.setRFID(dto.getCardNo());
                user.setPassportNum(dto.getPassport());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                user.setEnabled(true);

                Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
                if (roleOptional.isPresent()){
                    user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                }
                else {
                    user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                }

                userRepository.save(user);

                return new ApiResponse(true,"saved user successfully.");
            }

            return new ApiResponse(false,"Bunday card_no li user mavjud. Boshqa card_no kiriting");
        }
    }





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
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
//                System.out.println( (long)row.getCell(1).getNumericCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
//                System.out.println(row.getCell(3).getNumericCellValue());
                System.out.println(row.getCell(4).getStringCellValue());
//                System.out.println(row.getCell(5).getStringCellValue());
//                System.out.println(row.getCell(6).getNumericCellValue());
//                System.out.println(row.getCell(7).getStringCellValue());

//                Long badgenumber = userInfoRepo.getBadgenumber();
//
                Integer count = userRepository.getCountByLogin(row.getCell(1).getStringCellValue());
                System.out.println(count+" <------------------------------------------- count");
                if (count==null || count<=1) {
                    Optional<User> optionalUser = userRepository.findUserByLogin(row.getCell(1).getStringCellValue());
                    if (!optionalUser.isPresent()) {

                        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(2).getStringCellValue());
                        if (byCardNo == null) {
                            USERINFO userinfo = new USERINFO();
                            Long badgenumber = userInfoRepo.getBadgenumber();
                            if (badgenumber==null) {
                                userinfo.setBadgenumber(1L);
                            }
                            else {
                                userinfo.setBadgenumber(badgenumber + 1);
                            }
//                            userinfo.setName(row.getCell(0).getStringCellValue());
//                            userinfo.setLastname(row.getCell(0).getStringCellValue());
                            userinfo.setCardNo(row.getCell(2).getStringCellValue());
                            System.out.println(userinfo.toString());
                            userInfoRepo.save(userinfo);
                        }

                        User user = new User();

                        user.setFullName(row.getCell(0).getStringCellValue());

                        user.setLogin(row.getCell(1).getStringCellValue());
                        user.setRFID(row.getCell(2).getStringCellValue());
                        user.setPassportNum(row.getCell(3).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(3).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(4).getStringCellValue());
                        if (roleOptional.isPresent()) {
                            Role role = roleOptional.get();
                            if (role.getRoleName().equals("ROLE_STUDENT")) {
                                user.setRoles(new HashSet<>(new SingletonList(role)));
                                userRepository.saveAndFlush(user);
                                Group groupByName = groupRepository.findGroupByName(row.getCell(5).getStringCellValue());
                                if (groupByName != null) {
                                    Student student = new Student();
                                    student.setTeachStatus(TeachStatus.TEACHING);
                                    student.setUser(user);
                                    student.setGroup(groupByName);
                                    student.setRektororder(row.getCell(7).getStringCellValue());
                                    studentRepository.save(student);
                                } else {
                                    Group group = new Group();
                                    group.setActive(true);
                                    group.setName(row.getCell(5).getStringCellValue());
                                    group.setLevel((int) row.getCell(6).getNumericCellValue());

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
                                        else
                                            group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                                    }

                                    Student student = new Student();

                                    Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(group.getName().substring(0, 3));

                                    if (facultyOptional.isPresent()) {
                                        group.setFaculty(facultyOptional.get());
                                        Group save1 = groupRepository.save(group);
                                        student.setTeachStatus(TeachStatus.TEACHING);
                                        student.setUser(user);
                                        student.setGroup(save1);
                                        student.setRektororder(row.getCell(7).getStringCellValue());
                                        studentRepository.save(student);
                                    } else {
                                        return new ApiResponse(false, "not fount faculty");
                                    }

                                }
                            }
                            else {
                                user.setRoles(new HashSet<>(new SingletonList(role)));
                            }


                        } else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(user);
                    }
                    else {
                        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(3).getStringCellValue());
                        if (byCardNo == null) {
                            USERINFO userinfo = new USERINFO();
                            Long badgenumber = userInfoRepo.getBadgenumber();
                            if (badgenumber==null) {
                                userinfo.setBadgenumber(1L);
                            }
                            else {
                                userinfo.setBadgenumber(badgenumber + 1);
                            }
//                            userinfo.setName(row.getCell(0).getStringCellValue());
//                            userinfo.setLastname(row.getCell(0).getStringCellValue());
                            userinfo.setCardNo(row.getCell(2).getStringCellValue());
                            System.out.println(userinfo.toString());
                            userInfoRepo.save(userinfo);
                        }
                        User user = optionalUser.get();
                        user.setFullName(row.getCell(0).getStringCellValue());

                        user.setLogin(row.getCell(1).getStringCellValue());
                        user.setRFID(row.getCell(2).getStringCellValue());
                        user.setPassportNum(row.getCell(3).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(3).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(4).getStringCellValue());
                        if (roleOptional.isPresent()) {
                            Role role = roleOptional.get();
                            if (role.getRoleName().equals("ROLE_STUDENT")) {
                                user.setRoles(new HashSet<>(new SingletonList(role)));
                                userRepository.saveAndFlush(user);
                                Group groupByName = groupRepository.findGroupByName(row.getCell(5).getStringCellValue());
                                if (groupByName != null) {
                                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());
                                    if (studentByUserId != null) {
                                        studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                        studentByUserId.setUser(user);
                                        studentByUserId.setGroup(groupByName);
                                        studentByUserId.setRektororder(row.getCell(7).getStringCellValue());
                                        studentRepository.save(studentByUserId);
                                    } else {
                                        Student student = new Student();
                                        student.setTeachStatus(TeachStatus.TEACHING);
                                        student.setUser(user);
                                        student.setGroup(groupByName);
                                        student.setRektororder(row.getCell(7).getStringCellValue());
                                        studentRepository.save(student);
                                    }

                                } else {

                                    Group group = new Group();
                                    group.setActive(true);
                                    group.setName(row.getCell(5).getStringCellValue());
                                    group.setLevel((int) row.getCell(6).getNumericCellValue());

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
                                        else
                                            group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
                                    }

                                    Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(group.getName().substring(0, 3));

                                    if (facultyOptional.isPresent()) {
                                        group.setFaculty(facultyOptional.get());
                                        Group save1 = groupRepository.save(group);


                                        Student studentByUserId = studentRepository.findStudentByUserId(user.getId());
                                        if (studentByUserId != null) {
                                            studentByUserId.setTeachStatus(TeachStatus.TEACHING);
                                            studentByUserId.setUser(user);
                                            studentByUserId.setGroup(save1);
                                            studentByUserId.setRektororder(row.getCell(7).getStringCellValue());
                                            studentRepository.save(studentByUserId);
                                        } else {
                                            Student student = new Student();
                                            student.setTeachStatus(TeachStatus.TEACHING);
                                            student.setUser(user);
                                            student.setGroup(save1);
                                            student.setRektororder(row.getCell(7).getStringCellValue());
                                            studentRepository.save(student);
                                        }
                                    } else {
                                        return new ApiResponse(false, "not fount faculty");
                                    }

                                }
                            } else {
                                user.setRoles(new HashSet<>(new SingletonList(role)));
                            }


                        } else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(user);
//                        return new ApiResponse(false, row.getCell(5).getStringCellValue() + " <- bunday card_no (rfid) li user mavjud. Boshqa card_no kiriting");
                    }
                }

            }
            return new ApiResponse(true,"saved users");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }



    @Transactional
    public ApiResponse savingTeachers(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcelTeachers(file);
            }
        }
        return null;
    }

    @Transactional
    public ApiResponse readDataFromExcelTeachers(MultipartFile file) {
        System.out.println(" ----------------------------- teacher saving ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println(row.getCell(1).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());

                Integer count = userRepository.getCountByLogin(row.getCell(1).getStringCellValue());
                System.out.println(count+" <------------------------------------------- count");
                if (count==null || count<=1) {
                    Optional<User> optionalUser = userRepository.findUserByLogin(row.getCell(1).getStringCellValue());
                    if (!optionalUser.isPresent()) {

                        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(2).getStringCellValue());
                        if (byCardNo == null) {
                            USERINFO userinfo = new USERINFO();
                            Long badgenumber = userInfoRepo.getBadgenumber();
                            userinfo.setBadgenumber(badgenumber + 1);
                            userinfo.setName(row.getCell(0).getStringCellValue());
                            userinfo.setLastname(row.getCell(0).getStringCellValue());
                            userinfo.setCardNo(row.getCell(2).getStringCellValue());
                            System.out.println(userinfo.toString());
                            userInfoRepo.save(userinfo);
                        }

                        User user = new User();

                        user.setFullName(row.getCell(0).getStringCellValue());
                        user.setLogin(row.getCell(1).getStringCellValue());
                        user.setRFID(row.getCell(2).getStringCellValue());
                        user.setPassword(passwordEncoder.encode("root123"));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);
                        user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_TEACHER").get())));
                        userRepository.save(user);
                    }
                }

            }
            return new ApiResponse(true,"Teachers data was added successfully");
        }catch (Exception e){
            return new ApiResponse(false,"error teachers data was added");
        }
    }
    /*@Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println( row.getCell(1).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                System.out.println((long)row.getCell(3).getNumericCellValue());
                System.out.println(row.getCell(4).getStringCellValue());
                System.out.println(row.getCell(5).getStringCellValue());
                System.out.println(row.getCell(6).getStringCellValue());
                System.out.println(row.getCell(7).getStringCellValue());
                System.out.println(row.getCell(8).getStringCellValue());

                Long badgenumber = userInfoRepo.getBadgenumber();

                USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(5).getStringCellValue());

                if (byCardNo==null){
                    USERINFO userinfo = new USERINFO();
                    userinfo.setBadgenumber((long) row.getCell(3).getNumericCellValue());
                    userinfo.setName(row.getCell(1).getStringCellValue());
                    userinfo.setLastname(row.getCell(0).getStringCellValue());
                    userinfo.setCardNo(row.getCell(5).getStringCellValue());
                    System.out.println(userinfo.toString());
                    userInfoRepo.saveOrUpdate(userinfo);

                    User user = new User();
                    user.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                    user.setLogin(row.getCell(4).getStringCellValue());
                    user.setRFID(row.getCell(5).getStringCellValue());
                    user.setPassportNum(row.getCell(6).getStringCellValue());
                    user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);

                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                    if (roleOptional.isPresent()){
                        user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                    }
                    else {
                        user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                    }
                    userRepository.saveOrUpdate(user);
                }
                else {

                    User userByRFID = userRepository.findUserByRFID(row.getCell(5).getStringCellValue());
                    if (userByRFID==null){
                        User user = new User();
                        user.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                        user.setLogin(row.getCell(4).getStringCellValue());
                        user.setRFID(row.getCell(5).getStringCellValue());
                        user.setPassportNum(row.getCell(6).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.saveOrUpdate(user);
                    }
                    else {
                        userByRFID.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                        userByRFID.setLogin(row.getCell(4).getStringCellValue());
                        userByRFID.setRFID(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(6).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);
                        userByRFID.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.saveOrUpdate(userByRFID);
//                        return new ApiResponse(false, row.getCell(5).getStringCellValue() + " <- bunday card_no (rfid) li user mavjud. Boshqa card_no kiriting");
                    }
                }
            }
            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }*/


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






    /*@Transactional
    public ApiResponse createUserInfo(UserInfoAndUserAndStudentDto dto) {
        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getCardNo());
        System.out.println(dto.toString());
        if (byCardNo==null){
            USERINFO userinfo = new USERINFO();
            userinfo.setName(dto.getName());
            userinfo.setLastname(dto.getLastname());
            userinfo.setCardNo(dto.getCardNo());
            System.out.println(userinfo.toString());
            userInfoRepo.saveOrUpdate(userinfo);

            User user = new User();
            user.setFullName(dto.getName()+" "+dto.getLastname());
            user.setLogin(dto.getLogin());
            user.setRFID(dto.getCardNo());
            user.setPassportNum(dto.getPassport());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
            if (roleOptional.isPresent()){
                user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
            }
            else {
                user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
            }


            userRepository.saveOrUpdate(user);

            return new ApiResponse(true,"saved user successfully.");

        }
        else {
            return new ApiResponse(false,"Bunday card_no li user mavjud. Boshqa card_no kiriting");
        }
    }*/
}

//todo ------------------------------           namangan
//if (group.getName().charAt(group.getName().length() - 2) == 'U')
//                                        group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
//                                    if (group.getName().charAt(group.getName().length() - 2) == 'R')
//                                        group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("RUSSIAN").get());
//                                    if (group.getName().charAt(group.getName().length() - 2) == 'E')
//                                        group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("ENGLISH").get());
//
//                                    if (group.getName().indexOf('_') == 3) {
//                                        group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
//                                    } else {
//                                        if (group.getName().charAt(3) == 'P')
//                                            group.setEducationType(eduTypeRepo.findEducationTypeByName("SIRTQI").get());
//                                        else
//                                            group.setEducationType(eduTypeRepo.findEducationTypeByName("KECHKI").get());
//                                    }
