package uz.yeoju.yeoju_app.service.useServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;
import uz.yeoju.yeoju_app.entity.attachment.AttachmentContent;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AttachmentContentRepo;
import uz.yeoju.yeoju_app.repository.AttachmentRepo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentContentRepo attachmentContentRepo;


    /**
     * Bitta file ni ham ko'p file larni ham database ga saqlovchi method, agar bitta file ni qaytadan saqlashga urinilsa
     * unga ruxsat bermaydi, chunki originalName unique qilingan.
     *
     * @param request MultipartHttpServletRequest
     * @return ApiResponse
     * @throws IOException exception
     */
    public ApiResponse uploadFileOrFilesToDatabase(MultipartHttpServletRequest request) throws IOException {
        /**
         * checkCount ==> 0 bo'lsa file yo'q boladi va 409 qaytaradi, 1 bo'lsa bitta file saqlagan bo'ladi,
         * agar 1 dan katta nechi soni bo'lsa ushancha file saqlagan bo'ladi.
         */
        int checkCount = 0;
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                boolean existsByOriginalName = attachmentRepo.existsByOriginalName(file.getOriginalFilename());
                if (existsByOriginalName) {
                    return new ApiResponse(false, "The file already exists!");
                }
                Attachment attachment = new Attachment(file.getOriginalFilename(), file.getSize(), file.getContentType());
                Attachment savedAttachment = attachmentRepo.save(attachment);
                AttachmentContent attachmentContent = new AttachmentContent(file.getBytes(), savedAttachment);
                attachmentContentRepo.save(attachmentContent);
                /**
                 * nechta file saqlashiga qarab checkCount bittaga oshib boraveradi.
                 */
                ++checkCount;
                // o'zimiz uchun ekranga chiqardim, tushinish uchun.
                System.out.println(file.getOriginalFilename() + " ==> saved!");
            }
        }
        /**
         * nechta file saqlaganiga qarab javob qaytardim.
         */
        return checkCount == 1 ? new ApiResponse(true, "File is saved successfully!") :
                checkCount > 1 ? new ApiResponse(true, "Files are saved successfully!") :
                        new ApiResponse(false, "Error!");
    }


    public ApiResponse downloadFileByIdFromDb(String id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepo.findById(id);
        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> optionalAttachmentContent = attachmentContentRepo.findByAttachmentId(id);
            if (optionalAttachmentContent.isPresent()) {
                AttachmentContent attachmentContent = optionalAttachmentContent.get();

                response.setHeader("Content-Disposition", "attachment; filename=\""
                        + attachment.getOriginalName() + "\"");
                response.setContentType(attachment.getContentType());
                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());
                return new ApiResponse(true, "Success");
            } else
                return new ApiResponse(false, "AttachmentContent not found!");
        }
        return new ApiResponse(false, "Attachment not found!");
    }
}
