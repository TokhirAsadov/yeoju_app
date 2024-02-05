package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.payload.teacher.TeacherSaveDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.service.useServices.LessonService;

import java.util.*;

@Service
public class TeacherImplService implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private KafedraRepository kafedraRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LessonService lessonService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all teachers",teacherRepository.findAll());
    }

    @Override
    @Transactional
    public ApiResponse save(TeacherSaveDto dto) {
        System.out.println(dto+" <-------- ");
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(dto.getKafedraId());
        System.out.println(dto.toString()+" ------------------------------- [[[[[[[[[[[[[[[[[[");
        Set<Lesson> lessons = new HashSet<>();
        for (LessonDto lessonDto : dto.getLessonDtos()) {
            Optional<Lesson> lessonOptional = lessonRepository.findById(lessonDto.getId());
            lessonOptional.ifPresent(lessons::add);
        }
        System.out.println(lessons.toString()+" *************************  lessons  *************************** ");
        Optional<Position> positionOptional = positionRepository.findById(Long.valueOf(dto.getPositionId()));
        System.out.println(positionOptional.get().toString()+" ***********************  positionOptional  ***************************** ");

        Optional<Role> role_teacher = roleRepository.findRoleByRoleName("ROLE_TEACHER");
        User user = userOptional.get();
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
//        if (user.getUserId()!=null) {
//            user.setLogin(user.getUserId());
//        }
//        else {
//            user.setLogin(user.getRFID());
//        }
        user.setPassword(passwordEncoder.encode("root123"));

//todo--------------------------------------------
        Set<Role> roles = new HashSet<>();
        if (!user.getRoles().isEmpty()){
            Optional<Role> roleOptional = user.getRoles().stream().filter(i -> i.getRoleName().equals("ROLE_STUDENT") || i.getRoleName().equals("ROLE_USER")).findFirst();
            if (!roleOptional.isPresent()) {
                roles.addAll(user.getRoles());
            }
        }
        roles.add(role_teacher.get());
        user.setRoles(roles);

//        user.setRoles(new HashSet<>(Collections.singletonList(role_teacher.get())));
        user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
        userRepository.save(user);
        System.out.println(user.toString()+" ,,,,,,,,,,,,,,,,,,,,,,,,,,,user,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");

        if (!teacherRepository.existsTeacherByUserId(user.getId())) {
            Teacher teacher = new Teacher();
            teacher.setRate(dto.getRate());
            teacher.setKafedra(kafedraOptional.get());
            teacher.setUser(userOptional.get());
            teacher.setSubjects(lessons);
            teacher.setWorkerStatus(dto.getWorkerStatus());
            System.out.println(teacher.toString() + " ,,,,,,,,,,,,,,,,,,,,,,,,,teacher 1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            teacherRepository.save(teacher);
            System.out.println(teacher.toString() + " ,,,,,,,,,,,,,,,,,,,,,,,,,,,,teacher 2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            return new ApiResponse(true, "saved teacher", teacher);
        }
        else {
            Teacher teacherByUserId = teacherRepository.getTeacherByUserId(user.getId());
            teacherByUserId.setKafedra(kafedraOptional.get());
            teacherByUserId.setRate(dto.getRate());
            teacherByUserId.setUser(userOptional.get());
            teacherByUserId.setSubjects(lessons);
            teacherByUserId.setWorkerStatus(dto.getWorkerStatus());
            System.out.println(teacherByUserId.toString() + " ,,,,,,,,,,,,,,,,,,,,,,,,,teacher 1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            teacherRepository.save(teacherByUserId);
            System.out.println(teacherByUserId.toString() + " ,,,,,,,,,,,,,,,,,,,,,,,,,,,,teacher 2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
            return new ApiResponse(true, "saved teacher", teacherByUserId);
        }
    }

    @Override
    public ApiResponse changeTeacherPosition(String userId, Long positionId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Position> positionOptional = positionRepository.findById(positionId);
        if (userOptional.isPresent()){
            if (positionOptional.isPresent()){
                User user = userOptional.get();
                user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                userRepository.save(user);
                return new ApiResponse(true,"changed position");
            }
            else {
                return new ApiResponse(false,"not fount position");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }

    @Override
    public ApiResponse saveTeacherFromExcel(MultipartHttpServletRequest request) {
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

    @Override
    public ApiResponse getDataForTeacherDocumentPDF(User user, String educationYearId, String subjectId, String groupId) {



        return null;
    }

    @Override
    public ApiResponse getDataOfLeaders(String teacherId, String groupName) {
        Optional<User> byId = userRepository.findById(teacherId);
        if (byId.isPresent()){
            return new ApiResponse(true,"data of leaders",teacherRepository.getDataOfLeader(teacherId,groupName));
        }
        return new ApiResponse(false,"not found user by id: " + teacherId);
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
                System.out.println((int)row.getCell(1).getNumericCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                System.out.println(row.getCell(3).getStringCellValue());
                System.out.println(row.getCell(4).getStringCellValue());
                System.out.println(row.getCell(5).getStringCellValue());
                System.out.println(row.getCell(6).getStringCellValue());
                System.out.println(row.getCell(7).getStringCellValue());
                System.out.println(row.getCell(8).getStringCellValue());
                System.out.println(row.getCell(9).getStringCellValue());

                User userByRFID = userRepository.findUserByRFID(row.getCell(6).getStringCellValue());

               /* if (userByRFID !=null) {

//                    Student studentByUserId1 = studentRepository.findStudentByUserId(userByRFID.getId());

//                    if (studentByUserId1==null){

                    if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                        Group group = new Group();
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

                        Group save1 = groupRepository.saveOrUpdate(group);

                        userByRFID.setEnabled(true);
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);

                        userByRFID.setRFID(row.getCell(6).getStringCellValue());
                        userByRFID.setFullName(row.getCell(0).getStringCellValue());
                        userByRFID.setLogin(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(8).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(8).getStringCellValue()));
                        Set<Role> userRoles = new HashSet<>();
                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                        roleOptional.ifPresent(userRoles::add);
                        userByRFID.setRoles(userRoles);
                        userByRFID.setNationality(row.getCell(9).getStringCellValue());
                        if (row.getCell(4).getStringCellValue()=="MALE"){
                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                            userByRFID.setGander(ganderByGandername);
                        }
                        else {
                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                            userByRFID.setGander(ganderByGandername);
                        }

                        userRepository.saveOrUpdate(userByRFID);

                        //todo-----------
                        if (studentRepository.existsStudentByUserId(userByRFID.getId())) {
                            Student studentByUserId = studentRepository.findStudentByUserId(userByRFID.getRFID());

                            studentByUserId.setUser(userByRFID);
                            studentByUserId.setGroup(save1);

                            Student saveOrUpdate = studentRepository.saveOrUpdate(studentByUserId);
                        }
                        else {
                            Student student = new Student();
                            student.setUser(userByRFID);
                            student.setGroup(save1);
                            Student saveOrUpdate = studentRepository.saveOrUpdate(student);
                        }


                    }
                    else {
                        userByRFID.setEnabled(true);
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);
                        userByRFID.setRFID(row.getCell(6).getStringCellValue());
                        userByRFID.setFullName(row.getCell(0).getStringCellValue());
                        userByRFID.setLogin(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(8).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(8).getStringCellValue()));
                        Set<Role> userRoles = new HashSet<>();
                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                        roleOptional.ifPresent(userRoles::add);
                        userByRFID.setRoles(userRoles);
                        userByRFID.setNationality(row.getCell(9).getStringCellValue());
                        if (row.getCell(4).getStringCellValue()=="MALE"){
                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                            userByRFID.setGander(ganderByGandername);
                        }
                        else {
                            Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                            userByRFID.setGander(ganderByGandername);
                        }

                        userRepository.saveOrUpdate(userByRFID);

                        // group
                        Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                        groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                        groupRepository.saveOrUpdate(groupByName);

                        // student
                        if (studentRepository.existsStudentByUserId(userByRFID.getId())) {
                            Student studentByUserId = studentRepository.findStudentByUserId(userByRFID.getRFID());

                            studentByUserId.setUser(userByRFID);
                            studentByUserId.setGroup(groupByName);

                            Student saveOrUpdate = studentRepository.saveOrUpdate(studentByUserId);
                        }
                        else {
                            Student student = new Student();
                            student.setUser(userByRFID);
                            student.setGroup(groupByName);
                            Student saveOrUpdate = studentRepository.saveOrUpdate(student);
                        }

                    }
                }
                else {
                    User user = new User();
                    user.setEnabled(true);
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setRFID(row.getCell(6).getStringCellValue());
                    user.setFullName(row.getCell(0).getStringCellValue());
                    user.setLogin(row.getCell(5).getStringCellValue());
                    user.setPassportNum(row.getCell(8).getStringCellValue());
                    user.setPassword(passwordEncoder.encode(row.getCell(8).getStringCellValue()));
                    Set<Role> userRoles = new HashSet<>();
                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                    roleOptional.ifPresent(userRoles::add);
                    user.setRoles(userRoles);
                    user.setNationality(row.getCell(9).getStringCellValue());
                    if (row.getCell(4).getStringCellValue()=="MALE"){
                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                        user.setGander(ganderByGandername);
                    }
                    else {
                        Gander ganderByGandername = ganderRepository.getGanderByGandername(Gandername.MALE);
                        user.setGander(ganderByGandername);
                    }
                    userRepository.saveAndFlush(user);


                    if (!groupRepository.existsGroupByName(row.getCell(2).getStringCellValue())) {
                        Group group = new Group();
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
                        Group save1 = groupRepository.saveOrUpdate(group);


                        Student student = new Student();
                        student.setUser(user);
                        student.setGroup(save1);
                        Student saveOrUpdate = studentRepository.saveOrUpdate(student);

                    }
                    else {

                        // group
                        Group groupByName = groupRepository.findGroupByName(row.getCell(2).getStringCellValue());
                        groupByName.setLevel((int) row.getCell(1).getNumericCellValue());
                        groupRepository.saveOrUpdate(groupByName);

                        // student
                        Student student = new Student();
                        student.setUser(user);
                        student.setGroup(groupByName);
                        Student saveOrUpdate = studentRepository.saveOrUpdate(student);

                    }


                }*/


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



}
