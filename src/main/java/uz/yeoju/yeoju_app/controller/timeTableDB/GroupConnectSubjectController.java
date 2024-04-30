package uz.yeoju.yeoju_app.controller.timeTableDB;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.repository.timetableDB.GroupConnectSubjectRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.groupConnect.GroupConnectSubjectService;

import java.util.Objects;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/groupConnect")
@RequiredArgsConstructor
public class GroupConnectSubjectController {
    private final GroupConnectSubjectService groupConnectSubjectService;
    private final GroupConnectSubjectRepository groupConnectSubjectRepository;

    @GetMapping("/getLinesForStudent/{educationYearId}")
    public HttpEntity<?> getLinesForStudent(
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("groupId") String groupId,
            @RequestParam("subjectId") String subjectId,
            @RequestParam("teacherId") String teacherId
    ){
        return ResponseEntity.ok(groupConnectSubjectRepository.getLinesForStudent(educationYearId,groupId,subjectId,teacherId));
    }

    @GetMapping("/getLessonForGroup/{educationYearId}")
    public HttpEntity<?> getLessonForGroupByEducationYearIdAndGroupName(
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("groupName") String groupName
    ){
        return ResponseEntity.ok(groupConnectSubjectRepository.getLessonForGroupByEducationYearIdAndGroupName(educationYearId, groupName));
    }

    @GetMapping("/getGradesByLesson/{educationYearId}/{studentId}")
    public HttpEntity<?> getGradesByLesson(
            @PathVariable("educationYearId") String educationYearId,
            @PathVariable("studentId") String studentId,
            @RequestParam("groupName") String groupName
    ){
        return ResponseEntity.ok(groupConnectSubjectRepository.getLessonForGroupByEducationYearIdAndGroupNameForGetGrades(educationYearId, groupName,studentId));
    }


    @GetMapping("/subjectsWithStatistics/{studentId}")
    public HttpEntity<?> getSubjectsWithStatistics(
            @PathVariable String studentId,
            @RequestParam(name = "groupName") String groupName,
            @RequestParam(name = "educationId") String educationId
    ){
        return ResponseEntity.ok(groupConnectSubjectService.getGroupsAndStatisticsByGroupName(studentId, groupName, educationId));
    }

    @GetMapping("/subjectsOfTeacher/{teacherId}")
    public HttpEntity<?> getSubjectsOfTeacher(
            @PathVariable String teacherId,
            @RequestParam(name = "educationId") String educationId
    ){
        return ResponseEntity.ok(groupConnectSubjectService.getSubjectsOfTeacherByEducationId(teacherId, educationId));
    }
    @GetMapping("/groupsOfTeacher/{teacherId}")
    public HttpEntity<?> getGroupsOfTeacher(
            @PathVariable String teacherId,
            @RequestParam(name = "educationId") String educationId,
            @RequestParam(name = "subjectId") String subjectId,
            @RequestParam(name = "eduType") String eduType
    ){
        return ResponseEntity.ok(
                Objects.equals(eduType, "ALL")
                        ?
                        groupConnectSubjectService.getGroupsOfTeacherByEducationId(teacherId, educationId,subjectId)
                        :
                      groupConnectSubjectService.getGroupsOfTeacherByEducationId(teacherId, educationId,subjectId,eduType)
        );
    }


    @GetMapping("/getStatisticsOfGroupForTeacherForToday/{educationId}/{groupId}")
    public Flux<?> getSubjectsOfTeacher(
            @CurrentUser User user,
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "subjectId") String subjectId,
            @RequestParam(name = "year") String year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day
    ){
        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(educationId, groupId, subjectId,user.getId(),year,week,day));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }


//    getSubjectsByEduYearIdAndGroupAndStudentIdNEW

    @GetMapping("/getStatisticsOfStudentForTeacher/{educationId}/{groupId}")
    public Flux<?> getStatisticsOfStudentForTeacher(
            @CurrentUser User user,
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "subjectId") String subjectId,
            @RequestParam(name = "studentId") String studentId
    ){
        return Flux.just(groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentIdNEW(studentId, groupId,educationId, subjectId));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }

    @GetMapping("/getSectionsAndRooms/{educationId}/{groupId}")
    public Flux<?> getSectionsAndRooms(
            @CurrentUser User user,
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "subjectId") String subjectId,
            @RequestParam(name = "year") String year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day
    ){
        return Flux.just(groupConnectSubjectRepository.getSectionsAndRooms(groupId,educationId, subjectId,year,week,day,user.getId()));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }

    @GetMapping("/getStatisticsOfGroupForTeacherForTodayWithMax/{educationId}/{groupId}")
    public Flux<?> getSubjectsOfTeacherWithMax(
            @CurrentUser User user,
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "subjectId") String subjectId,
            @RequestParam(name = "year") String year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day
    ){
        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForTeacherWithMax(educationId, groupId, subjectId,user.getId(),year,week,day));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }

    @GetMapping("/getStatisticsOfGroupForTeacher/{educationId}/{groupId}")
    public Flux<?> getSubjectsOfTeacher(
            @CurrentUser User user,
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "subjectId") String subjectId
    ){
        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForTeacherWithMax(educationId, groupId, subjectId,user.getId()));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }

    @GetMapping("/getStatisticsOfGroupForTeacherByDay/{subjectId}/{groupId}")
    public Flux<?> getStatisticsOfGroupForTeacherByDay(
            @CurrentUser User user,
            @PathVariable(name = "subjectId") String subjectId,
            @PathVariable(name = "groupId") String groupId,
            @RequestParam(name = "year") Integer year,
            @RequestParam(name = "week") Integer week,
            @RequestParam(name = "day") Integer day
    ){
        System.out.println(user.getId());
        System.out.println(subjectId);
        System.out.println(groupId);
        System.out.println(year);
        System.out.println(week);
        System.out.println(day);
        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForTeacherByDay(user.getId(), subjectId, groupId, year, week, day));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }


//    @GetMapping(path = "/getStatisticsOfGroupForTeacher2/{educationId}/{groupId}",produces = MediaType.APPLICATION_JSON_VALUE)
//    public Flux<?> getSubjectsOfTeacher2(
//            @PathVariable(name = "educationId") String educationId,
//            @PathVariable(name = "groupId") String groupId,
//            @RequestParam(name = "subjectId") String subjectId
//    ){
//
//        return Flux.interval(Duration.ofSeconds(1))
//                .publishOn(Schedulers.boundedElastic())
//                .map(sequence -> ServerSentEvent.<Set<StatisticsOfGroupForTeacherForToday>>builder().id(String.valueOf(sequence))
//                        .event("statistics-teacher-event").data(groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(educationId, groupId, subjectId))
//                        .build());
//
////        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(educationId, groupId, subjectId));
////        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
////        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//    }






    @GetMapping("/getStatisticsOfGroupForDean/{educationId}/{groupId}")
    public Flux<?> getStatisticsOfGroupForDean(
            @PathVariable(name = "educationId") String educationId,
            @PathVariable(name = "groupId") String groupId
    ){
        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForDean(educationId, groupId));
//        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
    }

//    @GetMapping("/getStatisticsOfGroupForDean2/{educationId}/{groupId}")
//    public Flux<?> getStatisticsOfGroupForDean2(
//            @PathVariable(name = "educationId") String educationId,
//            @PathVariable(name = "groupId") String groupId
//    ){
//
//        return Flux.interval(Duration.ofSeconds(1))
//                .publishOn(Schedulers.boundedElastic())
//                .map(sequence -> ServerSentEvent.<Set<StatisticsOfGroupForDean>>builder().id(String.valueOf(sequence))
//                        .event("statistics-dean-event").data(groupConnectSubjectRepository.getStatisticsOfGroupForDean(educationId, groupId))
//                        .build());
//
////        return Flux.just(groupConnectSubjectRepository.getStatisticsOfGroupForDean(educationId, groupId));
////        return Flux.just(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
////        return ResponseEntity.ok(groupConnectSubjectService.getStatisticsOfGroupForTeacher(educationId,groupId,subjectId));
//    }

}
