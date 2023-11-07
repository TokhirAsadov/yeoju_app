package uz.yeoju.yeoju_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekEduType;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.useServices.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/attachment")
public class AttachmentController {


    @Autowired
    AttachmentService attachmentService;

    /**
     * Bitta file ni ham ko'p file larni ham database ga saqlovchi AttachmentService ni ichidagi methodga o'tvoradi.
     *
     * @param request MultipartHttpServletRequest
     * @return ApiResponse
     * @throws IOException exception
     */
    @PostMapping("/upload")
    public HttpEntity<?> uploadFileToDatabase(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.uploadFileOrFilesToDatabase(request);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/uploadPhoto")
    public HttpEntity<?> uploadPhotoForUser(MultipartHttpServletRequest request, @CurrentUser User user) throws IOException {
        ApiResponse apiResponse = attachmentService.saving(request,user.getId());
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/uploadPhotoByUserId")
    public HttpEntity<?> saving(MultipartHttpServletRequest request, @RequestParam("userId") String userId) throws IOException {
        ApiResponse apiResponse = attachmentService.saving(request,userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @GetMapping("/download/{id}")
    public HttpEntity<?> downloadFileByIdFromDatabase(@PathVariable String id, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse = attachmentService.downloadFileByIdFromDb(id, response);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



    @PostMapping("/upload/{educationYearId}/{filename}")
    public HttpEntity<?> uploadXml(
            MultipartHttpServletRequest request,
            @PathVariable("educationYearId") String educationYearId,
            @PathVariable("filename") String filename,
            @RequestParam("year") String year,
//            @RequestParam("sortNumber") Integer sortNumber,
            @RequestParam("weekNumber") Integer weekNumber,
            @RequestParam("eduType") WeekEduType eduType,
            @RequestParam("weekType") WeekType weekType,
            @RequestParam("defaultOrMed") WeekType defaultOrMed,
            @RequestParam("startWeek")  @DateTimeFormat(pattern = "dd.MM.yyyy") Date startWeek,
            @RequestParam("endWeek")  @DateTimeFormat(pattern = "dd.MM.yyyy") Date endWeek
            ) throws IOException {

        System.out.println(educationYearId);
        System.out.println(filename);
        System.out.println(year);
//        System.out.println(sortNumber);
        System.out.println(weekNumber);
        System.out.println(eduType.toString());
        System.out.println(weekType.toString());
        System.out.println(startWeek);
        System.out.println(endWeek);

        ApiResponse apiResponse = attachmentService.saveToFileSystem(request,educationYearId,filename,year,weekNumber,eduType,weekType,defaultOrMed,startWeek,endWeek);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
