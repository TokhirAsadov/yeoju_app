package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.YeojuAppApplication;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;
import uz.yeoju.yeoju_app.payload.superAdmin.StudentSaveDto;
import uz.yeoju.yeoju_app.repository.UserRFIDRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.UserRFIDServer;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user")
@RequiredArgsConstructor
public class UserController {


    public final UserService userService;
    public final UserRFIDServer userRFIDService;
    public final UserRFIDServer getUserRFIDByRFID;
    public final UserRepository userRepository;
    public final PasswordEncoder passwordEncoder;

    @GetMapping("/getLoginByPassport/{passport}")
    public HttpEntity<?> getLoginByPassport(@PathVariable("passport") String passport){
        return ResponseEntity.ok(userRepository.getLoginByPassport(passport));
    }

    @GetMapping("/getUserCheckRoomStatistics")
    public HttpEntity<?> getUserCheckRoomStatistics(@CurrentUser User user){
//        Calendar c = Calendar.getInstance();
//        c.setTime(yourDate);
//        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
//        System.out.println(dayOfWeek+ " <--------------------------------------------------------------------------------- ");
        return ResponseEntity.ok(userService.getUserForRoomStatistics(user.getId()));
    }

    @GetMapping("/getTeacherStatisticsForTimeTable")
    public HttpEntity<?> getTeacherStatisticsForTimeTable(@CurrentUser User user,@RequestParam(name = "teacherId",required = true) String teacherId){
        return  ResponseEntity.ok(userService.getUserForRoomStatistics(teacherId));
    }

    @GetMapping("/getTeacherStatisticsForTimeTableByWeek")
    public HttpEntity<?> getTeacherStatisticsForTimeTableByWeek(
            @CurrentUser User user,
            @RequestParam(name = "teacherId",required = true) String teacherId,
            @RequestParam("week") Integer week,
            @RequestParam("year") Integer year
    ){
        return  ResponseEntity.ok(userService.getUserForRoomStatisticsByWeek(teacherId,week,year));
    }

    //@GetMapping("/getTeacherStatisticsForTimeTable")
    //    public HttpEntity<?> getTeacherStatisticsForTimeTable(@CurrentUser User user,@RequestParam(name = "passport",required = true) String passport){
    //        return  ResponseEntity.ok(userService.getUserForRoomStatisticsByPassport(passport));
    //    }


    @PostMapping("/changeLogin")
    public HttpEntity<?> changeLoginAndPassword(@CurrentUser User user,@RequestBody ChangeLoginAndPassword dto ){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setPassword(passwordEncoder.encode(dto.getPassword()));
            user1.setLogin(dto.getLogin());

            userRepository.save(user1);

            return ResponseEntity.ok(new ApiResponse(true,"changed login and password successfully.."));
        }
        else {
            return ResponseEntity.ok(new ApiResponse(false,"not fount user"));
        }
    }
    public final UserRFIDRepository userRFIDRepository;

    //todo-------------------------------------------------
    @PostMapping("/saveStudentFromSuperAdmin")
    public HttpEntity<?> saveStudentFromSuperAdmin(@RequestBody StudentSaveDto dto,@CurrentUser User user){
        return ResponseEntity.ok(userService.saveStudentFromSuperAdmin(dto));
    }


    @GetMapping("/getDataForStaffSaving")
    public HttpEntity<?> getDataForStaffSaving(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getDataForStaffSaving(user.getId()));
    }

    @PostMapping("/uploadUser")//getUserForTeacherSaving
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        ApiResponse apiResponse = userService.saving(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    //todo-------------------------------------------- ------------getSectionStaffDataForRektorBySectionId
    @GetMapping("/getSectionStaffDataForRektorBySectionId")//getSectionStaffDataForRektorBySectionId
    public HttpEntity<?> getSectionStaffDataForRektorBySectionId(@CurrentUser User user,@RequestParam("id") String id,@RequestParam("s") Boolean s){
         return ResponseEntity.ok(s ? userService.getStatisticsForRektorStaffPageDekan(id) : userService.getStatisticsForRektorStaffPage(id));
    }

    @GetMapping("/getSectionStaffDataForRektorBySectionIdNo")//getSectionStaffDataForRektorBySectionId
    public HttpEntity<?> getSectionStaffDataForRektorBySectionIdNo(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(userService.getStatisticsForRektorStaffPage(id));
    }


    // getSectionStaffDataForRektorBySectionId

    @GetMapping("/getKafedraTeachersDataForRektorByKafedraId")
    public HttpEntity<?> getKafedraTeachersDataForRektorByKafedraId(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(userService.getStatisticsForRektorTeacherPage(id));
    }
    @GetMapping("/rektorTeacher")
    public HttpEntity<?> getKafedraStatisticsForRektor(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getKafedraStatisticsForRektor());
    }

    @GetMapping("/rektorStaff")
    public HttpEntity<?> getStaffStatisticsForRektor(@CurrentUser User user,@RequestParam(name = "s",required = false) Boolean s){
        if (s) {
            return ResponseEntity.ok(userRepository.getStaffStatisticsForRektor());
        }
        else {
            return ResponseEntity.ok(userRepository.getStaffStatisticsForRektor2());
        }
    }

    @GetMapping("/rektorDashboardStaffs")
    public HttpEntity<?> getRektorDashboardStaffs(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getStaffDataForRektorDashboard());
    }
    @GetMapping("/rektorDashboardStudents")
    public HttpEntity<?> getRektorDashboardStudents(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getStudentDataForRektorDashboard());
    }
    @GetMapping("/rektorDashboardTeachers")
    public HttpEntity<?> getRektorDashboardTeachers(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getTeacherDataForRektorDashboard());
    }


    @GetMapping("/rektorDashboard")
    public HttpEntity<?> getRektorDashboard(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getRektorDashboard(user.getId()));
    }

    @GetMapping("/updateFullName")
    public HttpEntity<?> updateFullName(@CurrentUser User user, @RequestParam String fullName){
        user.setFullName(fullName);
        userRepository.save(user);
        return ResponseEntity.ok("updated");
    }


    @GetMapping("/updateCivil")
    public HttpEntity<?> updateCivil(@CurrentUser User user, @RequestParam String civil){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setCitizenship(civil);
            userRepository.save(user1);
        }
        return ResponseEntity.ok("updated");
    }


    @GetMapping("/updateNation")
    public HttpEntity<?> updateNation(@CurrentUser User user, @RequestParam String nation){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setNationality(nation);
            userRepository.save(user1);
        }
        return ResponseEntity.ok("updated");
    }

    @GetMapping("/updateEmail")
    public HttpEntity<?> updateEmail(
            @CurrentUser User user,
            @RequestParam(name = "email",required = false) String email,
            @RequestParam(name = "passport",required = false) String passport
    ){

        System.out.println(passport+" <- passport");
        System.out.println(email+" <- email");

        Optional<User> userOptional = userRepository.findById(user.getId());
        if(email!=null) {

            if (userOptional.isPresent()) {
                User user1 = userOptional.get();
                user1.setEmail(email);
                userRepository.save(user1);
            }
        }
        if (passport!=null){
            Optional<User> userPassportOptional = userRepository.findUserByPassportNum(passport);
            if (userPassportOptional.isPresent()) {
                if (userOptional.isPresent()) {
                    if (userPassportOptional.get().getId().equals(userOptional.get().getId())) {
                        User user1 = userOptional.get();
                        user1.setPassportNum(passport);
                        userRepository.save(user1);
                    }
                    else {
                        return ResponseEntity.ok(new ApiResponse(false,"error.. already exists "+passport+" passport number"));
                    }
                }
                else {
                    return ResponseEntity.ok(new ApiResponse(false,"not fount user"));
                }
            }
            else {
                if (userOptional.isPresent()) {
                    User user1 = userOptional.get();
                    user1.setPassportNum(passport);
                    userRepository.save(user1);
                }
                else {
                    return ResponseEntity.ok(new ApiResponse(false,"not fount user"));
                }
            }

        }
        return ResponseEntity.ok("updated");
    }

    @GetMapping("/updateBirth")
    public HttpEntity<?> updateBirth(@CurrentUser User user, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date birth){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setBornYear(birth);
            userRepository.save(user1);
        }
        return ResponseEntity.ok("updated");
    }

    @GetMapping("/notification")
    public HttpEntity<?> getNotification(@CurrentUser User user, @RequestParam("week") Integer week,@RequestParam("year") Integer year){
        return ResponseEntity.ok(userService.getNotification(user.getId(),week,year));
    }
//    @GetMapping("/notification")
//    public HttpEntity<?> getNotification(@RequestParam("kafedraId") String kafedraId){
//        return ResponseEntity.ok(userService.getNotification(kafedraId));
//    }

    @GetMapping("/getTimeTable")
    public HttpEntity<?> getTimeTable(@CurrentUser User user,@RequestParam(name = "kafedraId",required = false) String kafedraId){
        if(kafedraId==null){
            return ResponseEntity.ok(userService.getTimeTableWithKafedraMudiriId(user.getId()));
        }
        else {
            return ResponseEntity.ok(userService.getTimeTableWithKafedraId(kafedraId));
        }
    }

    @GetMapping("/getTeacherTimeTable")
    public HttpEntity<?> getTeacherTimeTable(@CurrentUser User user,@RequestParam(name = "t",required = false) String teacherId){

        if (teacherId==null) {
            return ResponseEntity.ok(userService.getTeacherTimeTable(user));
        }
        else {
            Optional<User> userOptional = userRepository.findById(teacherId);
            return userOptional.map(value -> ResponseEntity.ok(userService.getTeacherTimeTable(value))).orElseGet(() -> ResponseEntity.ok(new ApiResponse(false, "error... not fount user...")));
        }
    }

    @GetMapping("/getUserFields")
    public HttpEntity<?> getUserFields(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getUserFields(user.getId()));
    }

    @GetMapping("/studentAllData/{userId}")//studentAllData
    public HttpEntity<?> studentAllData(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userRepository.getStudentDataByUserId(userId));
    }
    @GetMapping("/studentAllDataForOwn")
    public HttpEntity<?> studentAllDataForOwn(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getStudentDataByUserId(user.getId()));
    }

    @GetMapping("/teacherAllData/{userId}")
    public HttpEntity<?> teacherAllData(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userRepository.getTeacherDataByUserId(userId));
    }

    @GetMapping("/teacherAllDataForOwn")
    public HttpEntity<?> teacherAllDataForOwn(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getTeacherDataByUserId(user.getId()));
    }


    @GetMapping("/passport")
    public HttpEntity<?> passport(){
        return ResponseEntity.ok(userService.passport());
    }


    @GetMapping("/search")
    public HttpEntity<?> getUserForSearch(@RequestParam("keyword") String keyword){
//        anyOne = "%"+ anyOne +"%";
        if (!Objects.equals(keyword, "")) {
            return ResponseEntity.ok(userService.getUserForSearch("%" + keyword + "%"));
        }
        else return ResponseEntity.ok(new ApiResponse(false,"Please, Send search word!.."));
    }

    @GetMapping("/timeTable/{groupName}")
    public HttpEntity<?> timeTableAPI(
            @PathVariable String groupName,
            @RequestParam(name = "day") Integer day
    ){
            List<Show> shows = timeTable(groupName);
        if (day==0) {
            ShowWeek showWeek = new ShowWeek();

            showWeek.setFirst(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeek.setSecond(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeek.setThird(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeek.setFourth(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeek.setFifth(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeek.setSixth(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeek.setSeventh(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeek.setEighth(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeek.setNinth(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeek.setTenth(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeek.setEleventh(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeek.setTwelfth(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));

            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeek));
        }
        else {
            Set<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toSet());
            return ResponseEntity.ok(new ApiResponse(true,"Time Table of Today",today));
        }
    }


    @GetMapping("/studentTimeTable/{groupName}")
    public HttpEntity<?> studentTimeTableAPI(
            @CurrentUser User user,
            @PathVariable String groupName,
            @RequestParam(name = "day") Integer day,
            @RequestParam(name = "s") Boolean s
    ){
        List<Show> shows = timeTable(groupName);
        if (day==0) {
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            showWeekNumberFields.setGet1(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));


//            List<Teacher> teacherList = new ArrayList<>();
            Set<Teacher> teacherList = new HashSet<>();
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();


            if (s) {

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });

                return ResponseEntity.ok(new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists));
            }
            else {

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                return ResponseEntity.ok(new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists));
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseTwoObj(true,"Time Table of Today",today));
        }
    }

    @GetMapping("/getTimeTableByRoom")
    public HttpEntity<?> getTimeTableByRoom(@CurrentUser User user,@RequestParam("r") String room){
        Optional<ClassRoom> first = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getName().equals(room)).findFirst();
        ClassRoom classRoom = first.get();

        Set<Card> cardSet = DataBaseForTimeTable.cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
        Set<Show> shows = timeTable(cardSet);

        Calendar c = Calendar.getInstance();
//        c.set(2023,2,24);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println(dayOfWeek+ " <--------------------------------------------------------------------------------- week day ");

        Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber().equals(dayOfWeek-1)).collect(Collectors.toSet());

        ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

        showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
        showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
        showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
        showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
        showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
        showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
        showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
        showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
        showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
        showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
        showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
        showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));

        List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();

        showWeekNumberFields.getGet1().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet2().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet3().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet4().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet5().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet6().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet7().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet8().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet9().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet10().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet11().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });
        showWeekNumberFields.getGet12().forEach(i -> {
            for (Teacher teacher : i.getTeachers()) {
                List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                lists.addAll(statisticsOfWeekdayList);
            }
        });

        return ResponseEntity.ok(new ApiResponseTwoObj(true,"time table room",showSet,lists));
    }

    @GetMapping("/getTimeTableByWeekOfYear")
    public HttpEntity<?> getTimeTableByWeekOfYear(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, month, day);
        int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        return ResponseEntity.ok(new ApiResponse(true,"week of year",weekOfYear));
    }


    public List<Show> timeTable(String groupName){
        List<Show> shows= new ArrayList<>();
        for (Card card : DataBaseForTimeTable.cards) {// jadvalning bitta kuni
            for (LessonXml lesson : DataBaseForTimeTable.lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : DataBaseForTimeTable.classes) {// guruh
                            if (aClass.getId().equals(classId) && aClass.getName().equals(groupName)){
                                Show show = new Show();
                                List<String> teachers = new ArrayList<>();
                                List<Teacher> teachers2 = new ArrayList<>();
                                for (String classroomId : card.getClassroomIds()) {
                                    for (ClassRoom room : DataBaseForTimeTable.classRooms) {// xona
                                        if (room.getId().equals(classroomId)){
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
                                }
                                for (Period period : DataBaseForTimeTable.periods) {
                                    if (period.getName().equals(card.getPeriod())){
                                        show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                        show.setHourNumber(period.getPeriod());
                                        break;
                                    }
                                }
                                for (DaysDef daysDef : DataBaseForTimeTable.daysDefs) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
                                        if (daysDef.getDays().get(0).equals("000001"))
                                            show.setDayNumber(6);
                                        show.setDaysName(daysDef.getName());
                                        break;
                                    }
                                }
                                for (Subject subject : DataBaseForTimeTable.subjects) {
                                    if (subject.getId().equals(lesson.getSubjectId())){
                                        show.setLessonName(subject.getName());
                                        break;
                                    }
                                }
                                for (String teacherId : lesson.getTeacherIds()) {
                                    for (Teacher teacher : DataBaseForTimeTable.teachers) {
                                        if (teacher.getId().equals(teacherId)){
                                            teachers2.add(teacher);
                                            teachers.add(teacher.getFirstName()+" "+teacher.getLastName());
                                            break;
                                        }
                                    }
                                }
                                show.setTeachers(teachers2);
                                show.setTeacherName(teachers);
                                shows.add(show);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    public Set<Show> timeTable(Set<Card> cardSet){
        Set<Show> shows= new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : DataBaseForTimeTable.lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : DataBaseForTimeTable.classes) {// guruh
                                Show show = new Show();
                                List<String> teachers = new ArrayList<>();
                                List<Teacher> teachers2 = new ArrayList<>();
                                for (String classroomId : card.getClassroomIds()) {
                                    for (ClassRoom room : DataBaseForTimeTable.classRooms) {// xona
                                        if (room.getId().equals(classroomId)){
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
                                }
                                for (Period period : DataBaseForTimeTable.periods) {
                                    if (period.getName().equals(card.getPeriod())){
                                        show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                        show.setHourNumber(period.getPeriod());
                                        break;
                                    }
                                }
                                for (DaysDef daysDef : DataBaseForTimeTable.daysDefs) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
                                        if (daysDef.getDays().get(0).equals("000001"))
                                            show.setDayNumber(6);
                                        show.setDaysName(daysDef.getShortName());
                                        break;
                                    }
                                }
                                for (Subject subject : DataBaseForTimeTable.subjects) {
                                    if (subject.getId().equals(lesson.getSubjectId())){
                                        show.setLessonName(subject.getName());
                                        break;
                                    }
                                }
                                for (String teacherId : lesson.getTeacherIds()) {
                                    for (Teacher teacher : DataBaseForTimeTable.teachers) {
                                        if (teacher.getId().equals(teacherId)){
                                            teachers2.add(teacher);
                                            teachers.add(teacher.getFirstName()+" "+teacher.getLastName());
                                            break;
                                        }
                                    }
                                }
                                show.setTeachers(teachers2);
                                show.setTeacherName(teachers);
                                shows.add(show);
                                break;

                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }


    @PostMapping("/userRole")
    public HttpEntity<?> roleUser(@RequestBody RoleUser dto) {
        return ResponseEntity.status(201).body(userService.saveRoleUser(dto));
    }
    //studentAllData

    @GetMapping("/me")
    public HttpEntity<?> me(@CurrentUser User user){
        return ResponseEntity.status(user!=null?200:409).body(new ApiResponse(user != null,"Ok"));
    }

    @GetMapping("/getAllUsers")
    public HttpEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }


    @PostMapping("/createUser")
    public HttpEntity<?> saveUser(@RequestBody UserDto dto) {
        return ResponseEntity.status(201).body(userService.saveOrUpdate(dto));
    }

    @GetMapping("/getUserById/{id}")
    public HttpEntity<?> getUserById(@PathVariable String  id){
            return ResponseEntity.ok(userService.findById(id));
    }
    @GetMapping("/getUserByEmail/{email}")
    public HttpEntity<?> getUserByEmail(@PathVariable String  email){
            return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PostMapping("/updateUser")
    public HttpEntity<?> updateFaculty(@RequestBody UserDto dto){
        return ResponseEntity.status(202).body(userService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteUser/{id}")
    public HttpEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.status(204).body(userService.deleteById(id));
    }

    @PostMapping("/deleteUsers")
    public HttpEntity<?> deleteUser(@RequestBody RemoveUsers users){
        return ResponseEntity.status(204).body(userService.deleteUsers(users.getIds()));
    }

    @PostMapping("/generateTeacherByRfid")
    public HttpEntity<?> generateTeacherByRfid(@RequestBody List<String> ids){
        return ResponseEntity.ok(userService.generateTeacherByRfid(ids));
    }

    @GetMapping("/getUserByRfid/{rfid}")
    public HttpEntity<?> getUserByRfid(@PathVariable("rfid") String rfid){
        return ResponseEntity.ok(userService.getUserByRFID(rfid));
    }

    @GetMapping("/getUserRfid/{rfid}")
    public HttpEntity<?> getUserRFIDByRfid(@PathVariable("rfid") String rfid){
        return ResponseEntity.ok(userRFIDRepository.getUserShareRFID(rfid));
    }
    @GetMapping("/getUserForTeacherSaving")
    public HttpEntity<?> getUserForTeacherSaving(@CurrentUser User user){
        return ResponseEntity.ok(userService.getItemsForTeacherSaving(user.getId()));
    }

    @GetMapping("/getUsersForUserRole")
    public HttpEntity<?> getUsersForUserRole(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.getUserForTeacherSavingSearch("%" + keyword + "%"));
    }

    @GetMapping("/getUsersForUserRole2")
    public HttpEntity<?> getUsersForUserRole2(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.getUserForTeacherSavingSearch2("%" + keyword + "%"));
    }

    @GetMapping("/getUserForTeacherSavingSearch")
    public HttpEntity<?> getUserForTeacherSaving(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.getUserForTeacherSavingSearch("%" + keyword + "%"));
    }

    @GetMapping("/workerStatus")
    public HttpEntity<?> getWorkerStatuses(){
        WorkerStatus[] values = WorkerStatus.values();
        return ResponseEntity.ok(values);
    }

    @GetMapping("/clearTable")
    public HttpEntity<?> clearTimeTable(){
        return ResponseEntity.ok(DataBaseForTimeTable.clearTimeTable());
    }

    @GetMapping("/timeTable")
    public void RestartTimeTableDataBase(){
        String xmlFile = "yeoju.xml";
        Document document = getSAXParsedDocument(xmlFile);
        System.out.println(document);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(System.out::println);
        rootNode.getChild("periods").getChildren("period").forEach(YeojuAppApplication::readPeriod);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(YeojuAppApplication::readDaysDef);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(YeojuAppApplication::readWeeksDef);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(YeojuAppApplication::readTermsDefs);
        rootNode.getChild("subjects").getChildren("subject").forEach(YeojuAppApplication::readSubject);
        rootNode.getChild("teachers").getChildren("teacher").forEach(YeojuAppApplication::readTeacher);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(YeojuAppApplication::readClassroom);
        rootNode.getChild("grades").getChildren("grade").forEach(YeojuAppApplication::readGrade);
        rootNode.getChild("classes").getChildren("class").forEach(YeojuAppApplication::readClass);
        rootNode.getChild("groups").getChildren("group").forEach(YeojuAppApplication::readGroup);
        rootNode.getChild("lessons").getChildren("lesson").forEach(YeojuAppApplication::readLesson);
        rootNode.getChild("cards").getChildren("card").forEach(YeojuAppApplication::readCard);

    }
}
