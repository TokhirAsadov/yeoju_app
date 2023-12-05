package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Teacher;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.payload.permissionDto.UserDto2;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.Table;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers28;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers29;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraTeachers31;
import uz.yeoju.yeoju_app.payload.resDto.rektor.staff.*;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.superAdmin.StudentSaveDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.secret.JwtProvider;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserImplService;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearImplService;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@Service
@RequiredArgsConstructor
public class UserService implements UserImplService<UserDto> {

    public final UserRepository userRepository;
    public final AddressUserRepository addressUserRepository;
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




    public static final List<Period> periods = new ArrayList<>();
    public static final List<DaysDef> daysDefs = new ArrayList<>();
    public static final List<WeeksDef> weeksDefs = new ArrayList<>();
    public static final List<TermsDef> termsDefs = new ArrayList<>();
    public static final List<Subject> subjects = new ArrayList<>();
    public static final List<Teacher> teachers = new ArrayList<>();
    public static final List<ClassRoom> classRooms = new ArrayList<>();
    public static final List<Grade> grades = new ArrayList<>();
    public static final List<Class> classes = new ArrayList<>();
    public static final List<GroupXml> groups = new ArrayList<>();
    public static final List<LessonXml> lessons = new ArrayList<>();
    public static final List<Card> cards = new ArrayList<>();


    //for medical
    public static final List<Period> periodsMed = new ArrayList<>();
    public static final List<DaysDef> daysDefsMed = new ArrayList<>();
    public static final List<WeeksDef> weeksDefsMed = new ArrayList<>();
    public static final List<TermsDef> termsDefsMed = new ArrayList<>();
    public static final List<Subject> subjectsMed = new ArrayList<>();
    public static final List<Teacher> teachersMed = new ArrayList<>();
    public static final List<ClassRoom> classRoomsMed = new ArrayList<>();
    public static final List<Grade> gradesMed = new ArrayList<>();
    public static final List<uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class> classesMed = new ArrayList<>();
    public static final List<GroupXml> groupsMed = new ArrayList<>();
    public static final List<LessonXml> lessonsMed = new ArrayList<>();
    public static final List<Card> cardsMed = new ArrayList<>();






    public void getTimeTableByWeek(Integer year, Integer week) {
        clearTimeTable();

        String xmlFile = year+"/"+week+".xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(UserService::readPeriod);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(UserService::readDaysDef);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(UserService::readWeeksDef);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(UserService::readTermsDefs);
        rootNode.getChild("subjects").getChildren("subject").forEach(UserService::readSubject);
        rootNode.getChild("teachers").getChildren("teacher").forEach(UserService::readTeacher);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(UserService::readClassroom);
        rootNode.getChild("grades").getChildren("grade").forEach(UserService::readGrade);
        rootNode.getChild("classes").getChildren("class").forEach(UserService::readClass);
        rootNode.getChild("groups").getChildren("group").forEach(UserService::readGroup);
        rootNode.getChild("lessons").getChildren("lesson").forEach(UserService::readLesson);
        rootNode.getChild("cards").getChildren("card").forEach(UserService::readCard);
    }


    public void getTimeTableByWeekMed(Integer year, Integer week) {
        clearTimeTableMed();

        String xmlFile = year+"/"+week+"med.xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(UserService::readPeriodMed);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(UserService::readDaysDefMed);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(UserService::readWeeksDefMed);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(UserService::readTermsDefsMed);
        rootNode.getChild("subjects").getChildren("subject").forEach(UserService::readSubjectMed);
        rootNode.getChild("teachers").getChildren("teacher").forEach(UserService::readTeacherMed);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(UserService::readClassroomMed);
        rootNode.getChild("grades").getChildren("grade").forEach(UserService::readGradeMed);
        rootNode.getChild("classes").getChildren("class").forEach(UserService::readClassMed);
        rootNode.getChild("groups").getChildren("group").forEach(UserService::readGroupMed);
        rootNode.getChild("lessons").getChildren("lesson").forEach(UserService::readLessonMed);
        rootNode.getChild("cards").getChildren("card").forEach(UserService::readCardMed);
    }


    public ResToken login(SignInDto signInDto){
        Authentication auth = manager.authenticate(new UsernamePasswordAuthenticationToken(
                signInDto.getLogin(),
                signInDto.getPassword()
        ));
        User user = (User) auth.getPrincipal();
        String token = provider.generateToken(user);
        return new ResToken(token);
    }


    public ApiResponse getStatisticsForRektorStaffPageDekan(String id) {
        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            SectionStaff31 sectionStaffsDataForRektorBySectionId31 = userRepository.getSectionStaffsDataForRektorBySectionId31(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId31);
        }
        else if (days==30){
            SectionStaff30 sectionStaffsDataForRektorBySectionId30 = userRepository.getSectionStaffsDataForRektorBySectionId30(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId30);
        }else if (maxDay==29){
            SectionStaff29 sectionStaffsDataForRektorBySectionId29 = userRepository.getSectionStaffsDataForRektorBySectionId29(id);
            return new ApiResponse(true,"show",sectionStaffsDataForRektorBySectionId29 );
        }else {
            SectionStaff28 sectionStaffsDataForRektorBySectionId28 = userRepository.getSectionStaffsDataForRektorBySectionId28(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId28);
        }

    }


    public ApiResponse getStatisticsForRektorStaffPage(String id) {
        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            SectionStaff31No sectionStaffsDataForRektorBySectionId31 = userRepository.getSectionStaffsDataForRektorBySectionId31No(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId31);
        }
        else if (days==30){
            SectionStaff30No sectionStaffsDataForRektorBySectionId30No = userRepository.getSectionStaffsDataForRektorBySectionId30No(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId30No);
        }else if (maxDay==29){
            SectionStaff29No sectionStaffsDataForRektorBySectionId29No = userRepository.getSectionStaffsDataForRektorBySectionId29No(id);
            return new ApiResponse(true,"show",sectionStaffsDataForRektorBySectionId29No );
        }else {
            SectionStaff28No sectionStaffsDataForRektorBySectionId28No = userRepository.getSectionStaffsDataForRektorBySectionId28No(id);
            return new ApiResponse(true,"show", sectionStaffsDataForRektorBySectionId28No);
        }

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
            Optional<AddressUser> addressUserOptional = addressUserRepository.findAddressUserByUserId(id);
            addressUserOptional.ifPresent(addressUser -> addressUserRepository.deleteById(addressUser.getId()));
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

    public ApiResponse getUserForTeacherSavingSearch2(String keyword){
        return new ApiResponse(true,"users",userRepository.getUserForTeacherSavingSearch2(keyword));
    }


    public ApiResponse getNotification(String kafedraId,Integer week,Integer year) {

//        LocalDate localDate= LocalDate.of(2023,1,31);
        getTimeTableByWeek(year,week);
        getTimeTableByWeekMed(year,week);

        LocalDate localDate= LocalDate.now();
        Locale spanishLocale=new Locale("ru", "RU");
        String day = localDate.format(DateTimeFormatter.ofPattern("EEEE",spanishLocale));

        System.out.println(day);

        System.out.println(daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst());
        System.out.println(daysDefs);

        String dayId = daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst().get().getDays().get(0);

        Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());

        Set<String> teachersIds = new HashSet<>();

        for (String id : lessonsIds) {
            LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
            teachersIds.addAll(lessonXml.getTeacherIds());
        }


        Set<Teacher> teachers1 = new HashSet<>();
        for (String id : teachersIds) {
            Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id)).findFirst();
            first.ifPresent(teachers1::add);
        }

        List<TeacherData> teacherData = new ArrayList<>();
        for (Teacher teacher : teachers1) {
            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getShortName(),kafedraId);
//            TeacherData teacherData1 = userRepository.getTeachersForRememberLogin(teacher.getEmail(),kafedraId);
            if (teacherData1!=null) teacherData.add(teacherData1);
        }

        //--------------------------- med ------------------------


        String dayIdMed = daysDefsMed.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst().get().getDays().get(0);

        Set<String> lessonsIdsMed = cardsMed.stream().filter(item -> item.getDays().contains(dayIdMed)).map(Card::getLessonId).collect(Collectors.toSet());

        Set<String> teachersIdsMed = new HashSet<>();

        for (String id : lessonsIdsMed) {
            LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
            teachersIdsMed.addAll(lessonXml.getTeacherIds());
        }


        Set<Teacher> teachersMed2 = new HashSet<>();
        for (String id : teachersIdsMed) {
            Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id)).findFirst();
            first.ifPresent(teachersMed2::add);
        }

        for (Teacher teacher : teachersMed2) {
            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getShortName(),kafedraId);
//            TeacherData teacherData1 = userRepository.getTeachersForRememberLogin(teacher.getEmail(),kafedraId);
            if (teacherData1!=null) teacherData.add(teacherData1);
        }

        return new ApiResponse(true,"teachers", teacherData);
    }

    public ApiResponse getTimeTableWithKafedraMudiriId(String kafedraMudiriId) {

//        LocalDate localDate= LocalDate.of(2023,2,1);
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
        List<Table> tables = new ArrayList<>();
        for (Teacher teacher : teachers) {
//            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getEmail(),kafedraMudiriId);
            TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraMudiriId(teacher.getEmail(), kafedraMudiriId);
//            TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraMudiriId(teacher.getEmail(), kafedraMudiriId);


            if (teacherData1!=null) {
                teacherData.add(teacherData1);
                List<Show> shows = new ArrayList<>();
                for (String id : lessonsIds) {
                    List<LessonXml> lessonXmls = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                    if (lessonXmls.size()!=0) {
                        for (LessonXml xml : lessonXmls) {
                            List<Card> collect = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                            for (Card card : collect) {
                                Show show = new Show();
//                                Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                for (String s : card.getClassroomIds()) {
                                    Optional<ClassRoom> roomOptional = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                                    if (roomOptional.isPresent()) {
                                        ClassRoom room = roomOptional.get();
                                        show.setRoom(room.getName());
                                        break;
                                    }
                                }
//                                LessonXml lessonXml = DataBaseForTimeTable.lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                List<String> classIds = xml.getClassIds();
                                List<String> stringList = new ArrayList<>();

                                for (String classId : classIds) {
                                    Class aClass = DataBaseForTimeTable.classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                    stringList.add(aClass.getName());
                                }

                                show.setGroups(stringList);

                                show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                show.setHourNumber(period.getPeriod());
                                show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
                                shows.add(show);
                            }

//                            Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
//                            Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
//                            for (String s : card.getClassroomIds()) {
//                                ClassRoom room = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s)).findFirst().get();
//                                show.setRoom(room.getName());
//                                break;
//                            }
//
//                            show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
//                            show.setHourNumber(period.getPeriod());
//                            show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
//                            shows.add(show);
                        }
                    }
                }
                tables.add(new Table(teacherData1,shows));
            }
        }



        return new ApiResponse(true,"teachers", tables);
    }


    public ApiResponse getTimeTableWithKafedraId(String kafedraId) {

//        LocalDate localDate= LocalDate.of(2023,2,1);
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
        List<Table> tables = new ArrayList<>();
        for (Teacher teacher : teachers) {
//            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getEmail(),kafedraId);
            TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraId(teacher.getEmail(),kafedraId);
//TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getEmail(),kafedraId);

            if (teacherData1!=null) {
                teacherData.add(teacherData1);
                List<Show> shows = new ArrayList<>();
                for (String id : lessonsIds) {
                    List<LessonXml> lessonXmls = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                    if (lessonXmls.size()!=0) {
                        for (LessonXml xml : lessonXmls) {
                            List<Card> collect = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                            for (Card card : collect) {
                                Show show = new Show();
//                                Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                for (String s : card.getClassroomIds()) {
                                    ClassRoom room = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s)).findFirst().get();
                                    show.setRoom(room.getName());
                                    break;
                                }
//                                LessonXml lessonXml = DataBaseForTimeTable.lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                List<String> classIds = xml.getClassIds();
                                List<String> stringList = new ArrayList<>();

                                for (String classId : classIds) {
                                    Class aClass = DataBaseForTimeTable.classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                    stringList.add(aClass.getName());
                                }

                                show.setGroups(stringList);

                                show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                show.setHourNumber(period.getPeriod());
                                show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
                                shows.add(show);
                            }

//                            Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
//                            Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
//                            for (String s : card.getClassroomIds()) {
//                                ClassRoom room = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s)).findFirst().get();
//                                show.setRoom(room.getName());
//                                break;
//                            }
//
//                            show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
//                            show.setHourNumber(period.getPeriod());
//                            show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
//                            shows.add(show);
                        }
                    }
                }
                tables.add(new Table(teacherData1,shows));
            }
        }



        return new ApiResponse(true,"teachers", tables);
    }

    public ApiResponse getTeacherTimeTable(User user){
        List<String> daysList =DataBaseForTimeTable.daysDefs.
                stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                .collect(Collectors.toSet()).stream().map(i-> i.getDays().get(0)).collect(Collectors.toList());
        Collections.sort(daysList, Collections.reverseOrder());
        List<Table> tables = new ArrayList<>();
        for (String s : daysList) {
            Set<String> lessonsIds = DataBaseForTimeTable.cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIds = new HashSet<>();
            for (String id : lessonsIds) {
                LessonXml lessonXml = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                teachersIds.addAll(lessonXml.getTeacherIds());
            }
            Set<Teacher> teachers = new HashSet<>();
            for (String id : teachersIds) {
                Optional<Teacher> first = DataBaseForTimeTable.teachers.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum()) ).findFirst();
                first.ifPresent(teachers::add);
            }
            List<TeacherData> teacherData = new ArrayList<>();
            for (Teacher teacher : teachers) {
                TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
                if (teacherData1!=null) {
                    teacherData.add(teacherData1);
                    List<Show> shows = new ArrayList<>();
                    for (String id : lessonsIds) {
                        List<LessonXml> lessonXmls = DataBaseForTimeTable.lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                        if (lessonXmls.size()!=0) {
    //                        lists.add(lessonXmls);
                            for (LessonXml xml : lessonXmls) {
                                List<Card> collect = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                for (Card card : collect) {
                                    Show show = new Show();

                                    Period period = DataBaseForTimeTable.periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                    for (String s2 : card.getClassroomIds()) {
                                        ClassRoom room = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                        show.setRoom(room.getName());
                                        break;
                                    }

                                    show.setLessonName(DataBaseForTimeTable.subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                    show.setHourNumber(period.getPeriod());
                                    show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

                                    List<String> classIds = xml.getClassIds();
                                    List<String> stringList = new ArrayList<>();

                                    for (String classId : classIds) {
                                        Class aClass = DataBaseForTimeTable.classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                        stringList.add(aClass.getName());
                                    }

                                    show.setGroups(stringList);


                                    show.setDaysName(
                                            DataBaseForTimeTable.daysDefs.
                                                    stream().filter(item -> item.getDays().contains(s))
                                                    .findFirst().get().getShortName()
                                    );
                                    System.out.println(show.toString()+" <- show "+s);

                                    shows.add(show);

                                }




                            }
                        }
                    }
                    tables.add(new Table(teacherData1,shows));
                }
            }
        }
        return new ApiResponse(false,"ishlayapdi",tables);
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

//                userRepository.saveOrUpdate(user);

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
                    userByRFID.setPassword(passwordEncoder.encode(dto.getPassport()));
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

    public ApiResponse getUserForRoomStatistics(String userId) {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek+ " <--------------------------------------------------------------------------------- ");
        return new ApiResponse(true,"week statistics", userRepository.getUserForWeekStatistics(userId));
    }


    public ApiResponse getUserForRoomStatisticsByWeek(String userId,Integer week,Integer year) {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek+ " <--------------------------------------------------------------------------------- ");
        return new ApiResponse(true,"week statistics", userRepository.getUserForWeekStatisticsByWeek(userId,week,year));
    }

    public ApiResponse getUserForRoomStatisticsByPassport(String passport) {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek+ " <--------------------------------------------------------------------------------- ");
        return new ApiResponse(true,"week statistics", userRepository.getUserForWeekStatisticsByPassport(passport));
    }

    public UserDto2 getUserFields(String id) {
        Optional<User> optional = userRepository.findById(id);
        return optional
                .map(this::generateUserDto2)
                .orElseGet(UserDto2::new);
    }

    public UserDto2 generateUserDto2(User user) {
        return new UserDto2(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getMiddleName(),
//                user.getLogin(),
//                user.getPassword(),
                user.getEmail(),
                user.getRoles().stream().map(roleService::generateRoleDto).collect(Collectors.toSet())
        );
    }

    public ApiResponse deleteUsers(List<String> ids) {
        ids.forEach(userRepository::deleteById);
        return new ApiResponse(true,"deleted users");
    }
    //====================================  clear  ==========================================================
    public void clearTimeTable(){
        periods.clear();
        daysDefs.clear();
        weeksDefs.clear();
        termsDefs.clear();
        subjects.clear();
        teachers.clear();
        classRooms.clear();
        classes.clear();
        groups.clear();
        lessons.clear();
        cards.clear();
    }

    public void clearTimeTableMed(){
        periodsMed.clear();
        daysDefsMed.clear();
        weeksDefsMed.clear();
        termsDefsMed.clear();
        subjectsMed.clear();
        teachersMed.clear();
        classRoomsMed.clear();
        classesMed.clear();
        groupsMed.clear();
        lessonsMed.clear();
        cardsMed.clear();
    }



    //====================================  Period  ==========================================================
    public static void readPeriod(Element employeeNode)
    {
        periods.add(
                new Period(
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        employeeNode.getAttributeValue("starttime"),
                        employeeNode.getAttributeValue("endtime")
                )
        );

        //Country
//        System.out.println("country : " + employeeNode.getChild("country").getText());
//        /**Read Department Content*/
//        employeeNode.getChildren("department").forEach( HowToGetItemFromXmlApplication::readDepartmentNode );
    }

    public static void readPeriodMed(Element employeeNode)
    {
        periodsMed.add(
                new Period(
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        employeeNode.getAttributeValue("starttime"),
                        employeeNode.getAttributeValue("endtime")
                )
        );

        //Country
//        System.out.println("country : " + employeeNode.getChild("country").getText());
//        /**Read Department Content*/
//        employeeNode.getChildren("department").forEach( HowToGetItemFromXmlApplication::readDepartmentNode );
    }
    //====================================  DaysDef  ==========================================================
    public static void readDaysDef(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("days");
        List<String> array = array(days);
        daysDefs.add(
                new DaysDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }

    public static void readDaysDefMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("days");
        List<String> array = array(days);
        daysDefsMed.add(
                new DaysDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  WeeksDef  ==========================================================
    public static void readWeeksDef(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("weeks");
        List<String> array = array(days);
        weeksDefs.add(
                new WeeksDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }

    public static void readWeeksDefMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("weeks");
        List<String> array = array(days);
        weeksDefsMed.add(
                new WeeksDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  TermsDefs  ==========================================================
    public static void readTermsDefs(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("terms");
        List<String> array = array(days);
        termsDefs.add(
                new TermsDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    public static void readTermsDefsMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("terms");
        List<String> array = array(days);
        termsDefsMed.add(
                new TermsDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  Subject  ==========================================================
    public static void readSubject(Element employeeNode)
    {
        subjects.add(
                new Subject(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );

    }
    public static void readSubjectMed(Element employeeNode)
    {
        subjectsMed.add(
                new Subject(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );

    }
    //====================================  Teacher  ==========================================================
    public static void readTeacher(Element employeeNode)
    {
        teachers.add(
                new Teacher(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("firstname"),
                        employeeNode.getAttributeValue("lastname"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("gender"),
                        employeeNode.getAttributeValue("email"),
                        employeeNode.getAttributeValue("mobile"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readTeacherMed(Element employeeNode)
    {
        teachersMed.add(
                new Teacher(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("firstname"),
                        employeeNode.getAttributeValue("lastname"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("gender"),
                        employeeNode.getAttributeValue("email"),
                        employeeNode.getAttributeValue("mobile"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Classrooms  ==========================================================
    public static void readClassroom(Element employeeNode)
    {
        classRooms.add(
                new ClassRoom(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readClassroomMed(Element employeeNode)
    {
        classRoomsMed.add(
                new ClassRoom(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Grade  ==========================================================
    public static void readGrade(Element employeeNode)
    {
        grades.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }

    public static void readGradeMed(Element employeeNode)
    {
        gradesMed.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }
    //====================================  Class  ==========================================================
    public static void readClass(Element employeeNode)
    {
        classes.add(
                new Class(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("teacherid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        employeeNode.getAttributeValue("grade"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readClassMed(Element employeeNode)
    {
        classesMed.add(
                new uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("teacherid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        employeeNode.getAttributeValue("grade"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Group  ==========================================================
    public static void readGroup(Element employeeNode)
    {
        groups.add(
                new GroupXml(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("classid"),
                        array(employeeNode.getAttributeValue("studentids")),
                        employeeNode.getAttributeValue("entireclass"),
                        employeeNode.getAttributeValue("divisiontag")
                )
        );
    }

    public static void readGroupMed(Element employeeNode)
    {
        groupsMed.add(
                new GroupXml(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("classid"),
                        array(employeeNode.getAttributeValue("studentids")),
                        employeeNode.getAttributeValue("entireclass"),
                        employeeNode.getAttributeValue("divisiontag")
                )
        );
    }
    //====================================  Lesson  ==========================================================
    public static void readLesson(Element employeeNode)
    {
        lessons.add(
                new LessonXml(
                        employeeNode.getAttributeValue("id"),
                        array(employeeNode.getAttributeValue("classids")),
                        employeeNode.getAttributeValue("subjectid"),
                        employeeNode.getAttributeValue("periodspercard"),
                        employeeNode.getAttributeValue("periodsperweek"),
                        array(employeeNode.getAttributeValue("teacherids")),
                        array(employeeNode.getAttributeValue("groupids")),
                        employeeNode.getAttributeValue("seminargroup"),
                        employeeNode.getAttributeValue("termsdefid"),
                        employeeNode.getAttributeValue("weeksdefid"),
                        employeeNode.getAttributeValue("daysdefid"),
                        employeeNode.getAttributeValue("capacity"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readLessonMed(Element employeeNode)
    {
        lessonsMed.add(
                new LessonXml(
                        employeeNode.getAttributeValue("id"),
                        array(employeeNode.getAttributeValue("classids")),
                        employeeNode.getAttributeValue("subjectid"),
                        employeeNode.getAttributeValue("periodspercard"),
                        employeeNode.getAttributeValue("periodsperweek"),
                        array(employeeNode.getAttributeValue("teacherids")),
                        array(employeeNode.getAttributeValue("groupids")),
                        employeeNode.getAttributeValue("seminargroup"),
                        employeeNode.getAttributeValue("termsdefid"),
                        employeeNode.getAttributeValue("weeksdefid"),
                        employeeNode.getAttributeValue("daysdefid"),
                        employeeNode.getAttributeValue("capacity"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Cards  ==========================================================
    public static void readCard(Element employeeNode)
    {
        cards.add(
                new Card(
                        employeeNode.getAttributeValue("lessonid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        array(employeeNode.getAttributeValue("weeks")),
                        array(employeeNode.getAttributeValue("terms")),
                        array(employeeNode.getAttributeValue("days"))
                )
        );
    }

    public static void readCardMed(Element employeeNode)
    {
        cardsMed.add(
                new Card(
                        employeeNode.getAttributeValue("lessonid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        array(employeeNode.getAttributeValue("weeks")),
                        array(employeeNode.getAttributeValue("terms")),
                        array(employeeNode.getAttributeValue("days"))
                )
        );
    }
    public static List<String> array(String str){
        boolean has = true;
        List<String> arr = new ArrayList<>();

        while (has){
            int index = str.indexOf(',');
            if (index == -1) {
                arr.add(str);
                has = false;
            }
            else {
                arr.add(str.substring(0,index));
                str = str.substring(index+1);
            }
        }

        return arr;
    }

}
