package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSaveWithEduType;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;
import uz.yeoju.yeoju_app.payload.dekanat.StudentChangeDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultyForDekan;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.repository.DekanatRepository;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.DekanService;
import uz.yeoju.yeoju_app.service.useServices.FacultyService;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/dekan")
@RequiredArgsConstructor
public class DekanController {
    private final DekanRepository dekanRepository;
    private final DekanatRepository dekanatRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DekanService dekanService;
    private final UserService userService;
    private final FacultyService facultyService;

    @GetMapping("/studentStatisticsWithWeekOfEduYear/{eduYearId}")
    public HttpEntity<?> getStudentStatisticsWithWeekOfEdu(@PathVariable String eduYearId,@RequestParam("facultyId") String facultyId,@RequestParam("eduType") String eduType,@RequestParam("eduTypeId") String eduTypeId){
        return ResponseEntity.ok(dekanRepository.getStudentStatisticsWithWeekOfEdu(eduYearId,facultyId,eduType,eduTypeId));
    }

    @GetMapping("/getStudentDataWithUserId/{id}")
    public HttpEntity<?> getStudentDataForEditedDekan(@CurrentUser User user,@PathVariable String id){
        return ResponseEntity.ok(dekanRepository.getStudentDataForEditedDekan(id));
    }

    @GetMapping("/getFacultiesShortnamesWithDekanId")
    public HttpEntity<?> getFacultiesShortnamesWithDekanId(@CurrentUser User user){
        return ResponseEntity.ok(dekanatRepository.getFacultiesShortNameAndDekanEducationTypes(user.getId()));
    }


    @PostMapping("/changeStudent")
    public HttpEntity<?> changeStudent(@RequestBody StudentChangeDto dto){
        return ResponseEntity.status(201).body(dekanService.changeStudent(dto));
    }

    @GetMapping("/getStudentForSettings")
    public HttpEntity<?> getStudentForSettings(@CurrentUser User user){
        return ResponseEntity.ok(dekanRepository.getStudentsForSettings(user.getId()));
    }

    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody DekanSaveWithEduType dto){
        return ResponseEntity.status(201).body(dekanService.saveDekan(dto));
    }

    @GetMapping("/getGroupByFacultyIdAndCourse")
    public HttpEntity<?> getGroupByFacultyIdAndCourse(@CurrentUser User user,@RequestParam("id") String id,@RequestParam("course") Integer course){
        return ResponseEntity.ok(dekanService.getGroupByFacultyIdAndCourse(id,course));
    }

    @GetMapping("/getTransferStudentsData")
    public HttpEntity<?> getTransferStudentsData(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(dekanService.getForStudentTransferData(id));
    }

    @GetMapping("/facultiesForStudentTransfer")
    public HttpEntity<?> getFacultiesForStudentTransfer(@CurrentUser User user){
        return ResponseEntity.ok(dekanService.getFacultiesForStudentTransfer());
    }

    @GetMapping("/getStudents")
    public HttpEntity<?> getStudents(@CurrentUser User user,@RequestParam("id") String id){
        return ResponseEntity.ok(dekanService.getStudents(id));
    }

    @GetMapping("/getGroupFails/{group}")
    public HttpEntity<?> getGroupFails(@PathVariable("group") String group){
        return ResponseEntity.ok(dekanRepository.getGroupFails(group));
    }

    @GetMapping("/getBadAndStudent")
    public HttpEntity<?> getBadAndStudent(@CurrentUser User user){
        FacultyForDekan facultyForDekan = dekanRepository.getFacultyForDekan(user.getId());
        return ResponseEntity.ok(dekanRepository.getBadAndStudent(facultyForDekan.getId()));
    }

    @GetMapping("/getUserSearchingForDekan/{searchParam}")//getGroupsNamesForDekanByFacultyIdAndLevel/
    public HttpEntity<?> getUserSearchingForDekan(@CurrentUser User user,@PathVariable("searchParam") String searchParam){
        System.out.println(dekanRepository.getUserSearchingForDekan(searchParam, user.getId()));
        System.out.println(dekanRepository.getUserSearchingForDekan2("%"+searchParam+"%", user.getId()) + " <--- ****** ");
        System.out.println(user.getId());
        return ResponseEntity.ok(dekanRepository.getUserSearchingForDekan2("%"+searchParam+"%", user.getId()));
    }

    @GetMapping("/getGroupsNamesForDekanByDekanId")///dekan/getGroupsNamesForDekanByDekanId
    public HttpEntity<?> getGroupsNamesForDekanByDekanId(@CurrentUser User user){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByDekanId(user.getId()));
    }

//getGroupsNamesForDekanByFacultyIdAndLevel
    //getGroupsNamesForDekanByFacultyIdAndLevel
    @GetMapping("/getGroupsNamesForDekanByFacultyIdAndLevel/{level}")//dekan/getGroupsNamesForDekanByFacultyIdAndLevel/
    public HttpEntity<?> getGroupsNamesForDekanByFacultyIdAndLevel(@CurrentUser User user,@PathVariable("level") Integer level){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByDekanIdAndLevel(user.getId(),level));
    }

    //getFacultiesFromDekanByUserId//getGroupsNamesForDekanByFacultyId
    @GetMapping("/getGroupsNamesForDekanByFacultyId") //getGroupsNamesForDekanByFacultyId
    public HttpEntity<?> getGroupsNamesForDekanByFacultyId(@CurrentUser User user){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByFacultyId(user.getId()));
    }

    @GetMapping("/getGroupsNamesForDekanByFacultyId/{facultyId}") //getGroupsNamesForDekanByFacultyId
    public HttpEntity<?> getGroupsNamesForDekanByFacultyId(@CurrentUser User user,@PathVariable("facultyId") String facultyId,@RequestParam(name = "dekanId",required = false) String dekanId){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesForDekanByFacultyId(dekanId==null ? user.getId() : dekanId,facultyId));
    }

    @GetMapping("/getGroupsNamesForRektorByFacultyId/{facultyId}") //getGroupsNamesForDekanByFacultyId
    public HttpEntity<?> getGroupsNamesByFacultyIdAndLevelAndEduType(@CurrentUser User user,@PathVariable("facultyId") String facultyId,@RequestParam(name = "course") Integer course,@RequestParam(name = "eduType") String eduType){
        return ResponseEntity.ok(dekanRepository.getGroupsNamesByFacultyIdAndLevelAndEduType(facultyId,course,eduType));
    }


    @GetMapping("/getFacultiesFromDekanByUserId")//getFacultiesFromDekanByUserId
    public HttpEntity<?> getFacultiesFromDekanByUserId(@CurrentUser User user){
//        Dekan byUserId = dekanRepository.getDekanByUserId(user.getId());
//        System.out.println(byUserId);
////        System.out.println(byUserId.getFaculties());
//
//        System.out.println(byUserId.getFaculties().toString());
//
//        DekanDto dekanDto = dekanService.generateDekanDto(byUserId);
//        System.out.println(dekanDto);


        return ResponseEntity.ok(new ApiResponse(true,"look",dekanRepository.getFacultyForDekan(user.getId())));
    }

    @GetMapping("/get")
    public HttpEntity<?> get(@CurrentUser User user,
                             @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm") Date startTime,
                             @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm") Date endTime
    ) {
        return ResponseEntity.ok(dekanRepository.getCourseStatisticsForDekanNEW(user.getId(),startTime,endTime));
    }
    @GetMapping("/getGroupStatistics")///dekan/getGroupsNamesForDekanByFacultyId
    public HttpEntity<?> getGroupStatistics(@CurrentUser User user,
                                            @RequestParam("startTime") @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm") Date startTime,
                                            @RequestParam("endTime")@DateTimeFormat(pattern = "yyyy.MM.dd HH:mm") Date endTime
    ) {
        System.out.println(user.getId());
        System.out.println(startTime);
        System.out.println(endTime);
        return ResponseEntity.ok(dekanRepository.getGroupsStatisticForDekanNEW(user.getId(),startTime,endTime));
    }

//    @GetMapping("/change")
//    public HttpEntity<?> changed(@RequestParam("userId") String userId,@RequestParam("dekanId") String dekanId){
//        Optional<User> userOptional = userRepository.findById(userId);
////        Optional<Dekanat> dekanatOptional = dekanatRepository.findById(dekanatId);
//        Optional<Dekan> dekanOptional = dekanRepository.findById(dekanId);
//
//        User user = userOptional.get();
//        user.setCredentialsNonExpired(true);
//        user.setAccountNonLocked(true);
//        user.setAccountNonExpired(true);
//        user.setEnabled(true);
//        user.setLogin(user.getFullName());
//        user.setPassword(passwordEncoder.encode("root123"));
//        Set<Role> userRoles = new HashSet<>();
//        Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_DEKAN");
//        roleOptional.ifPresent(userRoles::add);
//        user.setRoles(userRoles);
//
//        userRepository.saveOrUpdate(user);
//
//        Dekan dekan = dekanOptional.get();
//        dekan.setUser(userOptional.get());
//
//        dekanRepository.saveOrUpdate(dekan);
//        return ResponseEntity.ok(new ApiResponse(true,"ok"));
//    }
}
