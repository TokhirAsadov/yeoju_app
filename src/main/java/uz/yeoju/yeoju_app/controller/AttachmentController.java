package uz.yeoju.yeoju_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.useServices.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/v1/attachment")
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


    @GetMapping("/download/{id}")
    public HttpEntity<?> downloadFileByIdFromDatabase(@PathVariable String id, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse = attachmentService.downloadFileByIdFromDb(id, response);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }



}
