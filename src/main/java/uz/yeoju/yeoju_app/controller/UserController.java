package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserDto;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/user")
@RequiredArgsConstructor
public class UserController {

    public final UserService userService;
    public final UserRepository userRepository;



    @GetMapping("/getUserFields")
    public HttpEntity<?> getUserFields(@CurrentUser User user){
        return ResponseEntity.ok(userRepository.getUserFields(user.getId()));
    }

    @GetMapping("/studentAllData/{userId}")
    public HttpEntity<?> studentAllData(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userRepository.getStudentDataByUserId(userId));
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
                                        if (daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
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
}
