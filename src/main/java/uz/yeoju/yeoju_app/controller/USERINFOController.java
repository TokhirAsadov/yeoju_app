package uz.yeoju.yeoju_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.USERINFO;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.exceptions.PageSizeException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.UserInfoAndUserAndStudentDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.USERINFOService;
import uz.yeoju.yeoju_app.utills.AppConstants;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/userinfo")
@RequiredArgsConstructor
public class USERINFOController {

    public final USERINFOService userinfoService;
    public final UserRepository userRepository;


    @PostMapping("/uploadUserInfoAndUser")
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        System.out.println(" ----------------------------- 1 1 1 ------------------------ --");
        ApiResponse apiResponse = userinfoService.saving(request);
        System.out.println(" ----------------------------- ------------------------ --");
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/createUser")
    public HttpEntity<?> createNewFaculty(@RequestBody UserInfoAndUserAndStudentDto dto){
        return ResponseEntity.status(201).body(userinfoService.createUserInfo(dto));
    }

    @PostMapping("/uploadUserInfoAndTeacher")
    public HttpEntity<?> uploadUserInfoAndTeacher(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        System.out.println(" ----------------------------- 1 1 1 ------------------------ --");
        ApiResponse apiResponse = userinfoService.savingTeachers(request);
        System.out.println(" ----------------------------- ------------------------ --");
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

//    @GetMapping("/allByPageable")
//    public HttpEntity<?> byPageable(
//            @RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER)Integer page,
//            @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE)Integer size
//    ) throws PageSizeException {
//        ApiResponse pageable = userinfoService.byPageable(page, size);
//        Page<USERINFO> pageableObj = (Page<USERINFO>) pageable.getObj();
//
////        System.out.println("Page GET" + pageableObj.getContent() );
//
//        for (USERINFO userinfo : pageableObj) {
//            User user = new User();
//            user.setUserId(userinfo.getUSERID());
//            user.setFullName(userinfo.getName()+" "+userinfo.getLastname());
//            user.setRFID(userinfo.getCardNo());
//
//            userRepository.saveOrUpdate(user);
//            System.out.println("OK");
//        }
//        return ResponseEntity.ok("ok");
//    }
//
//
//    @GetMapping("/allUserInfos")
//    public HttpEntity<?> allFaculties(){
//        return ResponseEntity.ok(userinfoService.findAll());
//    }
//
//    @GetMapping("/getUserInfoById/{id}")
//    public HttpEntity<?> getFacultyById(@PathVariable String id){
//        return ResponseEntity.ok(userinfoService.findById(id));
//    }

//    @PostMapping("/createFaculty")
//    public HttpEntity<?> createNewFaculty(@RequestBody FacultyDto dto){
//        return ResponseEntity.status(201).body(userinfoService.saveOrUpdate(dto));
//    }
//
//    @PostMapping("/updateFaculty")
//    public HttpEntity<?> updateFaculty(@RequestBody FacultyDto dto){
//        return ResponseEntity.status(202).body(userinfoService.saveOrUpdate(dto));
//    }
//
//    @DeleteMapping("/deleteFaculty/{id}")
//    public HttpEntity<?> deleteFaculty(@PathVariable String id){
//        return ResponseEntity.status(204).body(userinfoService.deleteById(id));
//    }

}
