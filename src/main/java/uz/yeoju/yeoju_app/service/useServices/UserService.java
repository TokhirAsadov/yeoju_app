package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Teacher;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.Table;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers31;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.superAdmin.StudentSaveDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.secret.JwtProvider;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserImplService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserImplService<UserDto> {

    public final UserRepository userRepository;
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



    public ResToken login(SignInDto signInDto){
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getLogin(),
                signInDto.getPassword()
        ));
        User user = (User) auth.getPrincipal();
        String token = provider.generateToken(user);
        return new ResToken(token);
    }


    public ApiResponse getStatisticsForRektorTeacherPage(String id) {
        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            KafedraTeachers31 forRektorByKafedraId31 = userRepository.getKafedraTeachersDataForRektorByKafedraId31(id);
            return new ApiResponse(true,"show", forRektorByKafedraId31);
        }
        else if (days==30){
            KafedraTeachers30 forRektorByKafedraId30 = userRepository.getKafedraTeachersDataForRektorByKafedraId30(id);
            return new ApiResponse(true,"show", forRektorByKafedraId30);
        }else if (maxDay==29){
            KafedraTeachers29 forRektorByKafedraId29 = userRepository.getKafedraTeachersDataForRektorByKafedraId29(id);
            return new ApiResponse(true,"show",forRektorByKafedraId29 );
        }else {
            KafedraTeachers28 forRektorByKafedraId28 = userRepository.getKafedraTeachersDataForRektorByKafedraId28(id);
            return new ApiResponse(true,"show", forRektorByKafedraId28);
        }

    }


    public ApiResponse passport(){
        List<Student> students = studentRepository.findAll();
        Set<User> collect = students.stream().filter(item -> item.getPassportSerial() != null).map(item -> {
                Optional<User> optional = userRepository.findById(item.getUser().getId());
                User user = optional.get();
                user.setPassportNum(item.getPassportSerial());
                return userRepository.save(user);
        }).collect(Collectors.toSet());
        return new ApiResponse(
                true,
                "saved passportNum",
                collect
        );
    }


    public ApiResponse getUserForSearch(String keyword){
        List<SearchDto> search = userRepository.getUserForSearch(keyword);
        if (search!=null) return new ApiResponse(true,"fined",search);
        else return new ApiResponse(true,"Not fount");
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
            User userByEmail = userRepository.findUserByEmail(dto.getEmail());
            User userByRFID = userRepository.findUserByRFID(dto.getRFID());
            User userByLogin = userRepository.getUserByLogin(dto.getLogin());

            if (userByLogin !=null && userByEmail !=null && userByRFID !=null) {
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
            else if (userByLogin != null && userByEmail == null && userByRFID !=null) {

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
                dto.getId(),
                dto.getFullName(),
                dto.getLogin(),
                dto.getPassword(),
                dto.getRFID(),
                dto.getEmail(),
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
                user.getBornYear(),
                user.getCitizenship(),
                user.getNationality(),
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
        return generateUserDto(userRepository.findUserByRFID(rfid));
    }

    @Override
    public ApiResponse getUserByEmail(String email) {
        User userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail !=null){
            return new ApiResponse(true,"user by email",generateUserDto(userByEmail));
        }
        else {
            return new ApiResponse(false,"not fount user by email");
        }
    }

    @Override
    public boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID) {
        return userRepository.existsUserByLoginOrEmailOrRFID(login,email,RFID);
    }



    public ApiResponse generateTeacherByRfid(List<String> ids){
        Optional<Role> roleName = roleRepository.findRoleByRoleName("ROLE_TEACHER");
        Role role = roleName.get();
        for (int i = 0; i < ids.size(); i++) {
            System.out.println(ids.get(i));
            User byRFID = userRepository.findUserByRFID(ids.get(i));
            byRFID.setRoles(new HashSet<Role>(Collections.singletonList(role)));
            userRepository.save(byRFID);
            System.out.println("teach");
        }
        return new ApiResponse(true,"TEACHERS");
    }

    public ApiResponse getItemsForTeacherSaving(String id){
        return new ApiResponse(true,"items",userRepository.getItemsForTeacherSaving(id));
    }

     public ApiResponse getUserForTeacherSavingSearch(String keyword){
        return new ApiResponse(true,"users",userRepository.getUserForTeacherSavingSearch(keyword));
    }


    public ApiResponse getNotification(String kafedraId) {

//        LocalDate localDate= LocalDate.of(2022,5,30);
        LocalDate localDate= LocalDate.now();
        Locale spanishLocale=new Locale("ru", "RU");
        String day = localDate.format(DateTimeFormatter.ofPattern("EEEE",spanishLocale));

        String dayId = DataBaseForTimeTable.daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst().get().getDays().get(0);

        Set<String> lessonsIds = DataBaseForTimeTable.cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());

        Set<String> teachersIds = new HashSet<>();

        for (String id : lessonsIds) {
            LessonXml lessonXml = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
            teachersIds.addAll(lessonXml.getTeacherIds());
        }


        Set<Teacher> teachers = new HashSet<>();
        for (String id : teachersIds) {
            Optional<Teacher> first = DataBaseForTimeTable.teachers.stream().filter(item -> item.getId().equals(id)).findFirst();
            first.ifPresent(teachers::add);
        }

        List<TeacherData> teacherData = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getEmail(),kafedraId);
            if (teacherData1!=null) teacherData.add(teacherData1);
        }

        return new ApiResponse(true,"teachers", teacherData);
    }

    public ApiResponse getTimeTable(String kafedraId) {

        LocalDate localDate= LocalDate.of(2022,6,1);
//        LocalDate localDate= LocalDate.now();
        Locale spanishLocale=new Locale("ru", "RU");
        String day = localDate.format(DateTimeFormatter.ofPattern("EEEE",spanishLocale));

        String dayId = DataBaseForTimeTable.daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst().get().getDays().get(0);

        Set<String> lessonsIds = DataBaseForTimeTable.cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());

        Set<String> teachersIds = new HashSet<>();

        for (String id : lessonsIds) {
            LessonXml lessonXml = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
            teachersIds.addAll(lessonXml.getTeacherIds());
        }


        Set<Teacher> teachers = new HashSet<>();
        for (String id : teachersIds) {
            Optional<Teacher> first = DataBaseForTimeTable.teachers.stream().filter(item -> item.getId().equals(id)).findFirst();
            first.ifPresent(teachers::add);
        }

        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();
        for (Teacher teacher : teachers) {
            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getEmail(),kafedraId);
            if (teacherData1!=null) {
                teacherData.add(teacherData1);
                List<Show> shows = new ArrayList<>();
                for (String id : lessonsIds) {
                    List<LessonXml> lessonXmls = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                    if (lessonXmls.size()!=0) {
//                        lists.add(lessonXmls);
                        Show show = new Show();
                        for (LessonXml xml : lessonXmls) {
                            Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                            Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                            for (String s : card.getClassroomIds()) {
                                ClassRoom room = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s)).findFirst().get();
                                show.setRoom(room.getName());
                                break;
                            }

                            show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                            show.setHourNumber(period.getPeriod());
                            show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
                            shows.add(show);
                        }
                    }
                }
                tables.add(new Table(teacherData1,shows));
//                shows.clear();
            }
        }



        return new ApiResponse(true,"teachers", tables);
    }

    public ApiResponse saveRoleUser(RoleUser dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            Set<Role> roles = new HashSet<>();

            for (String s : dto.getRoleId()) {
                Optional<Role> roleOptional = roleRepository.findById(s);
                roleOptional.ifPresent(roles::add);
            }
            user.setRoles(roles);
            userRepository.save(user);
        }
        else {
           return new ApiResponse(false,"not found user");
        }

        return null;
    }




    @Transactional
    public ApiResponse saving(MultipartHttpServletRequest request) throws IOException {
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
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            System.out.println(rows);
            System.out.println(rows.hasNext());
            while (rows.hasNext()){
                Row row = rows.next();
//                row.getCell(3).getStringCellValue()

                System.out.println(row.getCell(0));

                User user = new User();
                user.setFullName(row.getCell(0).getStringCellValue());
                user.setRFID(row.getCell(1).getStringCellValue());
                user.setLogin(row.getCell(2).getStringCellValue());
                user.setPassportNum(row.getCell(3).getStringCellValue());
                user.setPassword(passwordEncoder.encode(row.getCell(3).getStringCellValue()));
                user.setEmail(row.getCell(4).getStringCellValue());
                Optional<Role> optional = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                user.setRoles(new HashSet<>(Collections.singletonList(optional.get())));

                System.out.println(user);

//                userRepository.save(user);

                /**
                 *  full name
                 *  rfid
                 *  login
                 *  passport number
                 *  email
                 * **/


            }


            return new ApiResponse(true,"saved users");
        }catch (Exception e){
            return new ApiResponse(false,"error saved users");
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


    @Transactional
    public ApiResponse saveStudentFromSuperAdmin(StudentSaveDto dto) {
        User userByRFID = userRepository.findUserByRFID(dto.getRfid());
        if (userByRFID!=null){
            Student studentByUserId = studentRepository.findStudentByUserId(userByRFID.getId());
            if (studentByUserId!=null){
                Group groupByName = groupRepository.findGroupByName(dto.getGroup());
                if (groupByName!=null){

                    groupByName.setLevel(dto.getLevel());
                    groupRepository.save(groupByName);

                    userByRFID.setRFID(dto.getRfid());
                    userByRFID.setFullName(dto.getFullName());
                    userByRFID.setLogin(dto.getLogin());
                    userByRFID.setPassportNum(dto.getPassport());
                    userByRFID.setPassword(passwordEncoder.encode(dto.getPassword()));
                    userByRFID.setEnabled(true);
                    userByRFID.setAccountNonExpired(true);
                    userByRFID.setAccountNonLocked(true);
                    userByRFID.setCredentialsNonExpired(true);
                    userRepository.save(userByRFID);

                    studentByUserId.setGroup(groupByName);
                    studentByUserId.setUser(userByRFID);
                    studentRepository.save(studentByUserId);
                    return new ApiResponse(true,"saved student");
                }
                else {
                    Group group = new Group();
                    group.setName(dto.getGroup());
                    group.setLevel(dto.getLevel());

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



                    Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(group.getName().substring(0, 3));

                    if (facultyOptional.isPresent()){
                        group.setFaculty(facultyOptional.get());
                        Group save1 = groupRepository.save(group);
                        studentByUserId.setUser(userByRFID);
                        studentByUserId.setGroup(save1);
                        studentRepository.save(studentByUserId);
                        return new ApiResponse(true,"saved student");
                    }
                    else {
                        return new ApiResponse(false,"not fount faculty");
                    }

                }


            }
            else {
                Group groupByName = groupRepository.findGroupByName(dto.getGroup());
                if (groupByName!=null){
                    groupByName.setLevel(dto.getLevel());
                    groupRepository.save(groupByName);

                    userByRFID.setRFID(dto.getRfid());
                    userByRFID.setFullName(dto.getFullName());
                    userByRFID.setLogin(dto.getLogin());
                    userByRFID.setPassportNum(dto.getPassport());
                    userByRFID.setPassword(passwordEncoder.encode(dto.getPassword()));
                    userByRFID.setEnabled(true);
                    userByRFID.setAccountNonExpired(true);
                    userByRFID.setAccountNonLocked(true);
                    userByRFID.setCredentialsNonExpired(true);
                    userRepository.save(userByRFID);

                    Student student=new Student();
                    student.setGroup(groupByName);
                    student.setUser(userByRFID);
                    studentRepository.save(student);
                    return new ApiResponse(true,"saved student");
                }
                else {

                    userByRFID.setRFID(dto.getRfid());
                    userByRFID.setFullName(dto.getFullName());
                    userByRFID.setLogin(dto.getLogin());
                    userByRFID.setPassportNum(dto.getPassport());
                    userByRFID.setPassword(passwordEncoder.encode(dto.getPassword()));
                    userByRFID.setEnabled(true);
                    userByRFID.setAccountNonExpired(true);
                    userByRFID.setAccountNonLocked(true);
                    userByRFID.setCredentialsNonExpired(true);
                    userRepository.save(userByRFID);


                    Group group = new Group();
                    group.setName(dto.getGroup());
                    group.setLevel(dto.getLevel());

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



                    Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(group.getName().substring(0, 3));

                    if (facultyOptional.isPresent()){
                        group.setFaculty(facultyOptional.get());
                        Group save1 = groupRepository.save(group);
                        Student student = new Student();
                        student.setUser(userByRFID);
                        student.setGroup(save1);
                        studentRepository.save(student);
                        return new ApiResponse(true,"saved student");
                    }
                    else {
                        return new ApiResponse(false,"not fount faculty");
                    }

                }
            }
        }
        else {
            return new ApiResponse(false,"Not fount user by rfid");
        }
    }
}
