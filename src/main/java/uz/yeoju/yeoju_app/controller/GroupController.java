package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor; 
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanGroupUpdateDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.GroupService;

import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/group")
@RequiredArgsConstructor
public class GroupController {

    public final GroupService groupService;



    @GetMapping("/getGroupsByFacultiesIds")
    public HttpEntity<?> getGroupsByFacultiesIds(@CurrentUser User user,@RequestParam("educationType") String educationType,@RequestParam("course") Integer course,@RequestParam("facultiesIds") List<String> facultiesIds){
        ApiResponse response = groupService.getGroupsByFacultiesIds(facultiesIds,course,educationType);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/getStudentStatisticsForDeanOneWeek/{groupId}")
    public HttpEntity<?> getStudentStatisticsForDeanOneWeek(
//            @CurrentUser User user,
            @PathVariable("groupId") String groupId,
            @RequestParam("educationYearId") String educationYearId,
            @RequestParam("weekday") Integer weekday,
            @RequestParam("week") Integer week,
            @RequestParam("year") Integer year

    ){
        ApiResponse response = groupService.getStudentStatisticsForDeanOneWeek(groupId,educationYearId,weekday,week,year);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/changeActiveOfGroup/{groupId}")
    public HttpEntity<?> changeActiveOfGroup(@CurrentUser User user,@PathVariable("groupId") String groupId){
        ApiResponse response = groupService.changeActiveOfGroup(groupId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }


    @GetMapping("/changeGroupsLevel")
    public HttpEntity<?> changeGroupsLevel(){
        return ResponseEntity.ok(groupService.changeGroupsLevel());
    }

    @GetMapping("/getGroupsForKafedraMudiri")
    public HttpEntity<?> getGroupsForKafedraMudiri(
            @CurrentUser User user,
            @RequestParam(name = "lang",required = false) String lang,
            @RequestParam(name = "eduType",required = false) String eduType,
            @RequestParam(name = "level",required = false) Integer level
    ){
        return ResponseEntity.ok(groupService.getGroupsForKafedraMudiri(lang,eduType,level));
    }


    @GetMapping("/getStudentsOfGroupWithTodayStatisticsAndScoreForJournal/{educationYearId}")
    public HttpEntity<?> getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(
            @CurrentUser User user,
            @PathVariable("educationYearId") String educationYearId,
            @RequestParam("groupName") String groupName
    ){
        return ResponseEntity.ok(groupService.getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(educationYearId,groupName));
    }

    @GetMapping("/getGroupsAndLessonsOfWeek")
    public HttpEntity<?> getGroupsAndLessonsOfWeek(@CurrentUser User user){
        return ResponseEntity.ok(groupService.getGroupsAndLessonsOfWeek());
    }

    @GetMapping("/getSubjectOfGroup/{group}")
    public HttpEntity<?> getSubjectOfGroup(@PathVariable("group") String group){
        return ResponseEntity.ok(groupService.getSubjectOfGroup(group));
    }

    @GetMapping("/getGroupNameByUserId")
    public HttpEntity<?> getGroupNameByUserId(@CurrentUser User user){
        return ResponseEntity.ok(groupService.getGroupNameByUserId(user.getId()));
    }

    @GetMapping("/createGroupsByGroupNamesAndFacultyId")
    public HttpEntity<?> createGroupsByGroupNamesAndFacultyId(
            @RequestParam("courseLevel") Integer courseLevel,
            @RequestParam("names")List<String> names,
            @RequestParam("facultyId") String facultyId
    ){
        return ResponseEntity.ok(groupService.createGroupsByGroupNamesAndFacultyId(courseLevel,names,facultyId));
    }

    @GetMapping("/allGroups")
    public HttpEntity<?> allGroups(){
        return ResponseEntity.ok(groupService.findAll());
    }

    @GetMapping("/getGroupByIdOrName/{id}")
    public HttpEntity<?> getGroupByIdOrName(
            @PathVariable String id,
            @RequestParam String name
    ){
        return id!=null ? ResponseEntity.ok(groupService.findById(id))
                :
                ResponseEntity.ok(groupService.findGroupByName(name));
    }

    @PostMapping("/createGroups")
    public HttpEntity<?> createGroups(@RequestBody DekanGroupUpdateDto dto){
        ApiResponse apiResponse = groupService.updateGroups(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }

    @PutMapping("/updateGroups")
    public HttpEntity<?> updateGroups(@RequestBody DekanGroupUpdateDto dto){
        ApiResponse apiResponse = groupService.updateGroups(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 203 : 409).body(apiResponse);
    }

    @PostMapping("/createGroup")
    public HttpEntity<?> createNewGroup(@RequestBody GroupDto dto){
        return ResponseEntity.status(201).body(groupService.saveOrUpdate(dto));
    }

    @PostMapping("/updateGroup")
    public HttpEntity<?> updateGroup(@RequestBody GroupDto dto){
        return ResponseEntity.status(202).body(groupService.saveOrUpdate(dto));
    }

    @DeleteMapping("/deleteGroup/{id}")
    public HttpEntity<?> deleteGroup(@PathVariable String id){
        return ResponseEntity.status(204).body(groupService.deleteById(id));
    }

    @GetMapping("/findGroupsByLevel/{level}")
    public HttpEntity<?> findGroupsByLevel(@PathVariable Integer level){
        return ResponseEntity.ok(groupService.findGroupsByLevel(level));
    }
    @GetMapping("/findGroupsByFacultyId/{faculty_id}")
    public HttpEntity<?> findGroupsByFacultyId(@PathVariable String faculty_id){
        return ResponseEntity.ok(groupService.findGroupsByFacultyId(faculty_id));
    }

}
