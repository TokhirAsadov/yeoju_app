package uz.yeoju.yeoju_app.controller.moduleV2;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.yeoju.yeoju_app.controller.BaseUrl;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/images")
public class ImageController {

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            Path imagePath = Paths.get("uploads/images/images").resolve(imageName).normalize();
            Resource resource = (Resource) new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = Files.probeContentType(imagePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private final String IMAGE_DIRECTORY = "D:\\applications\\backend\\yeoju_app\\uploads\\images\\images"; // Rasm joylashgan papkani yo‘li

    @GetMapping("/v2/{imageName}")
    public ResponseEntity<Resource> getImageV2(@PathVariable String imageName) {
        try {
            // Fayl yo‘lini yaratish
            Path filePath = Paths.get(IMAGE_DIRECTORY + imageName);
            Resource resource = new UrlResource(filePath.toUri());

            // Fayl mavjudligini tekshirish
            if (resource.exists() && resource.isReadable()) {
                // Fayl turiga qarab MIME type aniqlash
                String contentType = "image/jpeg"; // Standart sifatida JPEG
                if (imageName.endsWith(".png")) {
                    contentType = "image/png";
                } else if (imageName.endsWith(".gif")) {
                    contentType = "image/gif";
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

