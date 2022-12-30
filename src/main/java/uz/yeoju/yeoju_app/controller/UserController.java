package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.YeojuAppApplication;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.RoleUser;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.payload.superAdmin.StudentSaveDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
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
    public final UserRepository userRepository;

    //todo-------------------------------------------------
    @PostMapping("/saveStudentFromSuperAdmin")
    public HttpEntity<?> saveStudentFromSuperAdmin(@RequestBody StudentSaveDto dto){
        return ResponseEntity.ok(userService.saveStudentFromSuperAdmin(dto));
    }


    @PostMapping("/uploadUser")
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        ApiResponse apiResponse = userService.saving(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping("/getKafedraTeachersDataForRektorByKafedraId")
    public HttpEntity<?> getKafedraTeachersDataForRektorByKafedraId(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(userService.getStatisticsForRektorTeacherPage(id));
    }
    @GetMapping("/rektorTeacher")
    public HttpEntity<?> getKafedraStatisticsForRektor(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getKafedraStatisticsForRektor());
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
    public HttpEntity<?> updateEmail(@CurrentUser User user, @RequestParam String email){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setEmail(email);
            userRepository.save(user1);
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
    public HttpEntity<?> getNotification(@CurrentUser User user){
        return ResponseEntity.ok(userService.getNotification(user.getId()));
    }
//    @GetMapping("/notification")
//    public HttpEntity<?> getNotification(@RequestParam("kafedraId") String kafedraId){
//        return ResponseEntity.ok(userService.getNotification(kafedraId));
//    }

    @GetMapping("/getTimeTable")
    public HttpEntity<?> getTimeTable(@CurrentUser User user){
        return ResponseEntity.ok(userService.getTimeTable(user.getId()));
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
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponse(true,"Time Table of Today",today));
        }
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
                                        if (daysDef.getDays().get(0).equals("100000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("010000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("001000"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("000100"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("000010"))
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
                                            teachers.add(teacher.getFirstName()+" "+teacher.getLastName());
                                            break;
                                        }
                                    }
                                }
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

    @PostMapping("/generateTeacherByRfid")
    public HttpEntity<?> generateTeacherByRfid(@RequestBody List<String> ids){
        return ResponseEntity.ok(userService.generateTeacherByRfid(ids));
    }

    @GetMapping("/getUserByRfid/{rfid}")
    public HttpEntity<?> getUserByRfid(@PathVariable("rfid") String rfid){
        return ResponseEntity.ok(userService.getUserByRFID(rfid));
    }

    @GetMapping("/getUserForTeacherSaving")
    public HttpEntity<?> getUserForTeacherSaving(@CurrentUser User user){
        return ResponseEntity.ok(userService.getItemsForTeacherSaving(user.getId()));
    }

    @GetMapping("/getUsersForUserRole")
    public HttpEntity<?> getUsersForUserRole(@RequestParam("keyword") String keyword){
        return ResponseEntity.ok(userService.getUserForTeacherSavingSearch("%" + keyword + "%"));
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
