package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra;

import org.jaxen.util.SingletonList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.kafedra.ChangeKafedraNameDto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;
import uz.yeoju.yeoju_app.payload.kafedra.TeacherEditDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraMudirRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;

import java.util.*;

@Service
public class KafedraMudiriImplService implements KafedraMudiriService{

    @Autowired
    private KafedraMudirRepository kafedraMudirRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private KafedraRepository kafedraRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;





    @Override
    public ApiResponse getTeachersStatisticsForKafedraDashboard(String kafedraId) {


        return new ApiResponse(true,"dashboard", kafedraMudirRepository.getTeachersStatisticsForKafedraDashboard(kafedraId));
    }

    @Override
    public ApiResponse changeNameOfKafedra(ChangeKafedraNameDto dto) {
        boolean b = kafedraRepository.existsKafedraByNameEn(dto.name);
        Optional<Kafedra> optionalKafedra = kafedraRepository.findById(dto.id);
        if (b) {
            if (optionalKafedra.isPresent()) {
                Kafedra kafedra = optionalKafedra.get();
                Kafedra kafedraByNameEn = kafedraRepository.getKafedraByNameEn(dto.getName());
                if (kafedraByNameEn!=null){
                    if (Objects.equals(kafedra.getId(), kafedraByNameEn.getId())) {
                        return new ApiResponse(false,"O`zgartirish kiritilmadi.");
                    }
                    else {
                        return new ApiResponse(false,"Already exists kafedra by name :"+dto.getName());
                    }
                }

            }
            return new ApiResponse(false,"Already exists kafedra by name :"+dto.getName());
        }
        else {
            if (optionalKafedra.isPresent()){
                Kafedra kafedra = optionalKafedra.get();
                kafedra.setNameEn(dto.getName());
                kafedraRepository.save(kafedra);
                return new ApiResponse(true,"Name of Kafedra was changed successful :"+dto.getName());
            }
            else {
                return new ApiResponse(false,"Not fount kafedra by id :"+dto.getId());
            }
        }
    }

    @Override
    public ApiResponse changeRoomOfKafedra(ChangeKafedraNameDto dto) {
        Optional<Kafedra> optionalKafedra = kafedraRepository.findById(dto.getId());
        if (optionalKafedra.isPresent()) {
            Kafedra kafedra = optionalKafedra.get();
            kafedra.setRoom(dto.getName());
            kafedraRepository.save(kafedra);
            return new ApiResponse(true,"Room of kafedra was changed to: "+dto.getName());
        }
        return new ApiResponse(false,"Not found kafedra by id: "+dto.getId());
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"Barcha mudirlar ro`yxati",kafedraMudirRepository.findAll());
    }

    @Override
    @Transactional
    public ApiResponse saveKafedra(DekanSave dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());

        if (userOptional.isPresent()){
            Optional<Kafedra> dekanatOptional = kafedraRepository.findById(dto.getDekanatId());
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
//            user.setPassportNum("");
            userRepository.save(user);

            if (dekanatOptional.isPresent()){
                KafedraMudiri kafedraMudiri = new KafedraMudiri();
                kafedraMudiri.setKafedra(dekanatOptional.get());
                kafedraMudiri.setUser(userOptional.get());
                kafedraMudirRepository.saveAndFlush(kafedraMudiri);
                return new ApiResponse(true,"saved", kafedraMudiri);
            }
            else {
                return new ApiResponse(false,"not fount kafedra");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }

    @Override
    public ApiResponse positionEdit(String id,String teacherId) {
        return new ApiResponse(true,"position change",kafedraMudirRepository.getPositionEdit(id,teacherId));
    }

    @Transactional
    @Override
    public ApiResponse changeTeacher(TeacherEditDto dto) {
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

                    Position positionByUserPositionName = positionRepository.getPositionByUserPositionName(dto.getPosition());

                    if (positionByUserPositionName != null) {
                        user.setPositions(new HashSet<>(Collections.singletonList(positionByUserPositionName)));
                    } else {
                        return new ApiResponse(false, "not fount position");
                    }

                    user.setAccountNonLocked(true);
                    user.setAccountNonExpired(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);

                    System.out.println(user.toString());

                    userRepository.save(user);

                    /***    teacher    ***/
                    Teacher teacherByUserId = teacherRepository.getTeacherByUserId(dto.getId());

                    if (teacherByUserId != null) {

                        /***    subjects    ***/
                        Set<Lesson> lessons = new HashSet<>();
                        for (String subject : dto.getSubjects()) {
                            Lesson lessonByName = lessonRepository.getLessonByName(subject);
                            if (lessonByName == null) {
                                return new ApiResponse(false, "not fount subject");
                            }
                            lessons.add(lessonByName);
                        }
                        teacherByUserId.setSubjects(lessons);

                        /***    worker status - asosiy,soatbay,orindosh    ***/
                        String workStatus = dto.getWorkStatus();
                        WorkerStatus workerStatus = WorkerStatus.valueOf(workStatus);
                        teacherByUserId.setWorkerStatus(workerStatus);

                        /***    kafedra    ***/
                        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(dto.getKafedraId());
                        if (kafedraOptional.isPresent()) {
                            teacherByUserId.setKafedra(kafedraOptional.get());
                        } else {
                            return new ApiResponse(false, "not fount kafedra");
                        }

                        /***    user    ***/
                        teacherByUserId.setUser(user);

                        /***    rate    ***/
                        teacherByUserId.setRate(dto.getRate());

                        teacherRepository.save(teacherByUserId);

                        return new ApiResponse(true, "Updated " + user.getFullName() + " successfully!.");
                    } else {
                        return new ApiResponse(false, "not fount teacher");
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

    @Override
    public ApiResponse deletedTeacherWithUserId(String id) {
        Optional<User> userOptional = userRepository.findById(id);
        Optional<Role> role_user = roleRepository.findRoleByRoleName("ROLE_USER");
        User user = userOptional.get();
        user.setRoles(new HashSet<>(new SingletonList(role_user.get())));
        userRepository.save(user);

        Teacher teacherByUserId = teacherRepository.getTeacherByUserId(id);
        teacherRepository.deleteById(teacherByUserId.getId());
        return new ApiResponse(true,userOptional.get().getFullName()+" deleted teacher");
    }

    @Override
    public ApiResponse changeRolesTeachers() {

        List<Teacher> teacherList = teacherRepository.findAll();
        Optional<Role> role_teacher = roleRepository.findRoleByRoleName("ROLE_TEACHER");

        if (role_teacher.isPresent()){
            for (Teacher teacher : teacherList) {
                Optional<User> userOptional = userRepository.findById(teacher.getUser().getId());
                User user = userOptional.get();
                user.setRoles(new HashSet<>(new SingletonList(role_teacher.get())));
                userRepository.save(user);
            }

            return new ApiResponse(true,"changed roles");
        }

        return new ApiResponse(false,"Not fount role");
    }

    @Override
    public ApiResponse save(KafedraMudiriSaving saving) {

        Optional<User> userOptional = userRepository.findById(saving.getUserId());
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(saving.getKafedraId());

        if (userOptional.isPresent() && kafedraOptional.isPresent()){

            KafedraMudiri save = kafedraMudirRepository.save(new KafedraMudiri(userOptional.get(), kafedraOptional.get()));

            return new ApiResponse(true,"Saving successfully",save);
        }

        return new ApiResponse(false,"Error not fount user or kafedra");
    }




    @Override
    public ApiResponse getStatistics(User user) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = kafedraRepository.getTeachersOfKafedra31(user.getId());
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = kafedraRepository.getTeachersOfKafedra30(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = kafedraRepository.getTeachersOfKafedra29(user.getId());
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = kafedraRepository.getTeachersOfKafedra28(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }

    @Override
    public ApiResponse getStatistics(String kafedraId) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            List<GetTeachersOfKafedra31> teachersOfKafedra = kafedraRepository.getTeachersOfKafedra31(kafedraId);
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = kafedraRepository.getTeachersOfKafedra30(kafedraId);
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = kafedraRepository.getTeachersOfKafedra29(kafedraId);
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = kafedraRepository.getTeachersOfKafedra28(kafedraId);
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }

    @Override
    public ApiResponse getStatistics(User user,String userId ,Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            System.out.println(maxDay+"-------------------------------- max day");
            return new ApiResponse(true,"<???",kafedraRepository.getDate31(date,userId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<???",kafedraRepository.getDate30(date,userId));
        }else if (maxDay==29){
            System.out.println(maxDay+"-------------------------------- max day");
            return new ApiResponse(true,"<???",kafedraRepository.getDate29(date,userId));
        }else {
            System.out.println(maxDay+"-------------------------------- max day");
            return new ApiResponse(true,"<???",kafedraRepository.getDate28(date,userId));
        }

    }

    @Override
    public ApiResponse getStatisticsForRektor(String kafedraId ,Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<??? 31",kafedraRepository.getDateForRektor31(date,kafedraId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<??? 30",kafedraRepository.getDateForRektor30(date,kafedraId));
        }else if (maxDay==29){
            return new ApiResponse(true,"<??? 29",kafedraRepository.getDateForRektor29(date,kafedraId));
        }else {
            return new ApiResponse(true,"<??? 28",kafedraRepository.getDateForRektor28(date,kafedraId));
        }

    }

    @Override
    public ApiResponse getStatisticsForTable(String kafedraId, Date date, Set<String> teachersIds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<??? 31",kafedraRepository.getDateForTable31(kafedraId,date,teachersIds));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<??? 30",kafedraRepository.getDateForTable30(kafedraId,date,teachersIds));
        }else if (maxDay==29){
            return new ApiResponse(true,"<??? 29",kafedraRepository.getDateForTable29(kafedraId,date,teachersIds));
        }else {
            return new ApiResponse(true,"<??? 28",kafedraRepository.getDateForTable28(kafedraId,date,teachersIds));
        }
    }

    @Override
    public ApiResponse getStatisticsForRektorTeacherPage(String id) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = kafedraRepository.getTeachersOfKafedra31(id);
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = kafedraRepository.getTeachersOfKafedra30(id);
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = kafedraRepository.getTeachersOfKafedra29(id);
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = kafedraRepository.getTeachersOfKafedra28(id);
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }
}
