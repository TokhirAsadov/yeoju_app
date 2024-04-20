package uz.yeoju.yeoju_app.controller.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.address.Address2Dto;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address.AddressService;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/createForUser")
    public HttpEntity<?> createForUser(@CurrentUser User user, @RequestBody AddressDto dto){
       return ResponseEntity.ok(addressService.saveForUser(user,dto));
    }

    @PostMapping("/createUser")
    public HttpEntity<?> createUser(@CurrentUser User user, @RequestBody Address2Dto dto){
        return ResponseEntity.ok(addressService.save(user,dto));
    }

    @GetMapping("/findAll")
    public HttpEntity<?> findAll(){
        return ResponseEntity.ok(addressService.findAll());
    }

    @GetMapping("/findById/{id}")
    public HttpEntity<?> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping("/regions")
    public HttpEntity<?> getRegions(){
        return ResponseEntity.ok(addressService.getRegions());
    }

    @GetMapping("/districtsByRegionId/{regionId}")
    public HttpEntity<?> getDistrictsByRegionId(@PathVariable(name = "regionId") Long regionId){
        return ResponseEntity.ok(addressService.getDistrictsByRegionId(regionId));
    }

    @GetMapping("/villagesByDistrictId/{districtId}")
    public HttpEntity<?> getVillagesByDistrictId(@PathVariable(name = "districtId") Long districtId){
        return ResponseEntity.ok(addressService.getVillagesByDistrictId(districtId));
    }
    @PostMapping("/uploadAddressWithLogin")//getUserForTeacherSaving
    public HttpEntity<?> uploadAddressWithLogin(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = addressService.saveFromAttachmentWithLogin(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/uploadAddress")//getUserForTeacherSaving
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = addressService.saveFromAttachment(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getMapStatistics")
    public HttpEntity<?> getMapStatistics(){
        return ResponseEntity.ok(addressService.getMapStatistics());
    }

    @GetMapping("/getDekanatsForStatistics")
    public HttpEntity<?> getDekanatsForStatistics(){
        return ResponseEntity.ok(addressService.getDekanatsForStatistics());
    }

    @GetMapping("/getFacultiesForStatistics")
    public HttpEntity<?> getFacultiesForStatistics(){
        return ResponseEntity.ok(addressService.getFacultiesForStatistics());
    }

    @GetMapping("/getDekanatStatistics")
    public HttpEntity<?> getDekanatStatistics(@RequestParam(name = "dekanatId") String dekanatId){
        return ResponseEntity.ok(addressService.getDekanatStatistics(dekanatId));
    }

    @GetMapping("/getFacultiesByDekanatId")
    public HttpEntity<?> getFacultiesByDekanatId(@RequestParam(name = "dekanatId") String dekanatId){
        return ResponseEntity.ok(addressService.getFacultiesByDekanatId(dekanatId));
    }

    @GetMapping("/getDekanatStatisticsByFacultyId")
    public HttpEntity<?> getDekanatStatisticsByFacultyId(@RequestParam(name = "facultyId") String facultyId){
        return ResponseEntity.ok(addressService.getDekanatStatisticsByFacultyId(facultyId));
    }

    @GetMapping("/getDekanatStatisticsByFacultyAndEduType")
    public HttpEntity<?> getDekanatStatisticsByFacultyAndEduType(@RequestParam(name = "facultyId") String facultyId, @RequestParam("eduType") String eduType){
        return ResponseEntity.ok(addressService.getDekanatStatisticsByFacultyAndEduType(facultyId,eduType));
    }
    @GetMapping("/getDekanatStatisticsByFacultyAndEduTypeAndEduLang")
    public HttpEntity<?> getDekanatStatisticsByFacultyAndEduTypeAndEduLang(@RequestParam(name = "facultyId") String facultyId, @RequestParam("eduType") String eduType, @RequestParam("eduLang") String eduLang){
        return ResponseEntity.ok(addressService.getDekanatStatisticsByFacultyAndEduTypeAndEduLang(facultyId,eduType,eduLang));
    }

    @GetMapping("/getStatistics")
    public HttpEntity<?> getStatistics(
            @RequestParam(name = "eduType",required = false) String eduType,
            @RequestParam(name = "level",required = false) Integer level,
            @RequestParam(name = "facultyId",required = false) String facultyId,
            @RequestParam(name = "eduLang",required = false) String eduLang
    ){
        if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                level != null
                        &&
                facultyId != null && !facultyId.equals("")
                        &&
                 eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndLevelAndFacultyIdAndEduLang(eduType, level, facultyId, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level != null
                        &&
                        facultyId != null && !facultyId.equals("")
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndLevelAndFacultyId(eduType, level, facultyId);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level == null
                        &&
                        facultyId != null && !facultyId.equals("")
                        &&
                        eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndFacultyIdAndEduLang(eduType, facultyId, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level != null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndLevelAndEduLang(eduType, level, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level == null
                        &&
                        facultyId != null && !facultyId.equals("")
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndFacultyId(eduType, facultyId);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level != null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndLevel(eduType, level);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level == null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduTypeAndEduLang(eduType, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                eduType != null && !Objects.equals(eduLang, "")
                        &&
                        level == null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduType(eduType);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                    level != null
                    &&
                    facultyId != null && !facultyId.equals("")
                    &&
                    eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByLevelAndFacultyIdAndEduLang(level, facultyId, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                level != null
                        &&
                        facultyId != null && !facultyId.equals("")
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByLevelAndFacultyId(level, facultyId);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                level != null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByLevelAndEduLang(level, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                level != null
                        &&
                        (facultyId == null || facultyId.equals(""))
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getStatisticsByLevel(level);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                facultyId != null && !facultyId.equals("")
                        &&
                eduLang != null && !eduLang.equals("")
        ){
            ApiResponse apiResponse = addressService.getStatisticsByFacultyIdAndEduLang(facultyId, eduLang);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                facultyId != null && !facultyId.equals("")
                        &&
                        (eduLang == null || eduLang.equals(""))
        ){
            ApiResponse apiResponse = addressService.getDekanatStatisticsByFacultyId(facultyId);
            return ResponseEntity.ok(apiResponse);
        }
        else if (
                        eduLang != null
        ){
            ApiResponse apiResponse = addressService.getStatisticsByEduLang(eduLang);
            return ResponseEntity.ok(apiResponse);
        }


        return ResponseEntity.ok(new ApiResponse(false,"error eduType= "+eduType+", facultyId= "+facultyId+", level= "+level+", eduLang= "+eduLang ));
    }
}
