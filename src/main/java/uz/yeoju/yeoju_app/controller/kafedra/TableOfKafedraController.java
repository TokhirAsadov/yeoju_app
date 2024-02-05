package uz.yeoju.yeoju_app.controller.kafedra;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfKafedra;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.secret.CurrentUser;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.table.TableService;

import java.io.IOException;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/table")
@RequiredArgsConstructor
public class TableOfKafedraController {
    private final TableService service;

    @GetMapping("/uploadFromSystemUser")
    public HttpEntity<?> download(
            @RequestParam("fileName") String fileName
    ) throws IOException {

        ApiResponse response = service.findByName(fileName);
        if (response.isSuccess()) {
            TableOfKafedra table = (TableOfKafedra) response.getObj();

            byte[] imageData=service.downloadImageFromFileSystem(fileName);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(table.getContentType()))
                    .body(imageData);
        }
        else {
            return ResponseEntity.status(403).body(response);
        }

    }


}
