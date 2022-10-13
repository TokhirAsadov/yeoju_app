package uz.yeoju.yeoju_app.service.useServices;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;
import uz.yeoju.yeoju_app.entity.attachment.AttachmentContent;
import uz.yeoju.yeoju_app.entity.attachment.UserPhoto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AttachmentContentRepo;
import uz.yeoju.yeoju_app.repository.AttachmentRepo;
import uz.yeoju.yeoju_app.repository.UserPhotoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentContentRepo attachmentContentRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPhotoRepo userPhotoRepo;

    public ApiResponse uploadFileOrFilesToDatabase(MultipartHttpServletRequest request) throws IOException {

        int checkCount = 0;
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                boolean existsByOriginalName = attachmentRepo.existsByOriginalName(file.getOriginalFilename());
                if (existsByOriginalName) {
                    return new ApiResponse(false, "The file already exists!");
                }
                Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), file.getContentType(),createFileUrl(Objects.requireNonNull(file.getOriginalFilename())));
                System.out.println(attachment+" <===========================================");
                Attachment savedAttachment = attachmentRepo.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent(file.getBytes(), savedAttachment);
                attachmentContentRepo.save(attachmentContent);

                ++checkCount;
                // o'zimiz uchun ekranga chiqardim, tushinish uchun.
                System.out.println(file.getOriginalFilename() + " ==> saved!");
            }
        }
        return checkCount == 1 ? new ApiResponse(true, "File is saved successfully!") :
                checkCount > 1 ? new ApiResponse(true, "Files are saved successfully!") :
                        new ApiResponse(false, "Error!");
    }

    @SneakyThrows
    public ApiResponse saving(MultipartHttpServletRequest request, String userId){
        Attachment saving = saving(request);

        Optional<User> userOptional = userRepository.findById(userId);

        UserPhoto save = userPhotoRepo.save(new UserPhoto(userOptional.get(), saving, true));

        return new ApiResponse(true,"saved photo",save);
    }
    public Attachment saving(MultipartHttpServletRequest request) throws IOException {

        System.out.println("kirdi ==================================================== 111");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            System.out.println(" while ============================================== ");
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                boolean existsByOriginalName = attachmentRepo.existsByOriginalName(file.getOriginalFilename());
                if (existsByOriginalName) {
                    return null;
                }
//                Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), file.getContentType(),createFileUrl(Objects.requireNonNull(file.getOriginalFilename())));
                Attachment savedAttachment = attachmentToFile(file);
                System.out.println("saqladi ==================================================== 222");
                AttachmentContent attachmentContent = new AttachmentContent(file.getBytes(), savedAttachment);
                attachmentContentRepo.save(attachmentContent);

                System.out.println(file.getOriginalFilename() + " ==> saved!");

                return savedAttachment;
            }
        }

        System.out.println("saving ========================================== 555555");

        return null;

    }


        private String createFileUrl(String originalFilename) {
        String name = UUID.randomUUID().toString();
        String[] split = originalFilename.split("\\.");
        String contentType = split[split.length - 1];
        name = name + "." + contentType;
        return name;
    }


    public Attachment attachmentToFile(MultipartFile file) {
        System.out.println(file.getOriginalFilename()+" <--- ====================================================");
        System.out.println(file.getContentType()+" <--- ====================================================");
        return attachmentRepo.save(Attachment.builder()
                .originalName(file.getOriginalFilename())
                .fileName(createFileUrl(Objects.requireNonNull(file.getOriginalFilename())))
                .size((Long) file.getSize())
                .contentType(file.getContentType())
                //RASMNI PAPKALAR ICHIDAN TOPISH UCHUN YOL YASAB BERADIGANNI PATH GA BERDIM
                .build());
    }


    public ApiResponse downloadFileByIdFromDb(String id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepo.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();

                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + attachment.getFileName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
                return new ApiResponse(true, "Success");
            } else
                return new ApiResponse(false, "AttachmentContent not found!");
        }
        return new ApiResponse(false, "Attachment not found!");
    }
}
