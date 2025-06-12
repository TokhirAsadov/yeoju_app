package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.module;

import fr.opensagres.poi.xwpf.converter.core.FileImageExtractor;
import fr.opensagres.poi.xwpf.converter.core.ImageManager;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLConverter;
import fr.opensagres.poi.xwpf.converter.xhtml.XHTMLOptions;

import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.entity.attachment.Attachment;
import uz.yeoju.yeoju_app.entity.attachment.AttachmentContent;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.ModuleAttachment;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.*;
import uz.yeoju.yeoju_app.repository.AttachmentContentRepo;
import uz.yeoju.yeoju_app.repository.AttachmentRepo;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleAttachmentRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.ModuleRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.TopicFileOfLineV2Repository;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModuleImplService implements ModuleService {
    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final TopicFileOfLineV2Repository topicFileOfLineV2Repository;
    private final AttachmentRepo attachmentRepository;
    private final AttachmentContentRepo attachmentContentRepository;
    private final ModuleAttachmentRepository moduleAttachmentRepository;

    public ModuleImplService(CourseRepository courseRepository, ModuleRepository moduleRepository, TopicFileOfLineV2Repository topicFileOfLineV2Repository, AttachmentRepo attachmentRepository, AttachmentContentRepo attachmentContentRepository, ModuleAttachmentRepository moduleAttachmentRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.topicFileOfLineV2Repository = topicFileOfLineV2Repository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.moduleAttachmentRepository = moduleAttachmentRepository;
    }

    @Override
    @Transactional
    public void createModule(ModuleCreator creator) {
        if (courseRepository.existsById(creator.courseId)){
            Module module = new Module(creator.title, creator.theme, courseRepository.findById(creator.courseId).get());
            moduleRepository.save(module);
        } else {
            throw new UserNotFoundException("Course did not found by id: "+creator.courseId);
        }
    }

    @Override
    @Transactional
    public ApiResponse updateModule(ModuleUpdater updater) {
        if (moduleRepository.existsById(updater.id)){
            Module module = moduleRepository.findById(updater.getId()).get();
            if (updater.getTitle()!=null && !updater.getTitle().isEmpty()) {
                module.setTitle(updater.getTitle());
            }
            if (updater.theme!=null && !updater.theme.isEmpty()) {
                module.setTheme(updater.getTheme());
            }
            Module save = moduleRepository.save(module);
            return new ApiResponse(true,"Module updated",save);
        } else {
            throw new UserNotFoundException("Module did not found by id: "+updater.id);
        }
    }

    @Override
    public boolean deleteModule(String moduleId) {
        if(moduleRepository.existsById(moduleId)){
            moduleRepository.deleteById(moduleId);
            return true;
        } else {
            throw new UserNotFoundException("Module did not found by id: "+moduleId);
        }
    }

    @Override
    public ApiResponse findAll(Pageable pageable) {
        return new ApiResponse(true,"Modules",moduleRepository.findAll(pageable));
    }

    @Override
    public ApiResponse findById(String id) {
        if (moduleRepository.existsById(id)) {
            ModuleResponse moduleResponse = new ModuleResponse();
            moduleResponse.setId(id);
            Module module = moduleRepository.findById(id).get();
            moduleResponse.setTitle(module.getTitle());
            moduleResponse.setTheme(module.getTheme());

            List<TopicFileOfLineV2Dto> topicFiles = new ArrayList<>();
            List<TopicFileOfLineV2> topics = topicFileOfLineV2Repository.findAllByModulesId(module.getId());
            boolean isTopicFilesEmpty = topics.isEmpty();
            if (!isTopicFilesEmpty&&topics.size()>0) {
                topics.forEach(tf -> {
                    topicFiles.add(new TopicFileOfLineV2Dto(
                            tf.getName(),
                            tf.getFileType(),
                            tf.getContentType(),
                            tf.getPackageName()
                    ));
                });
            }

            moduleResponse.setTopicFiles(topicFiles);
            return new ApiResponse(true,"Module is found by id: "+id,moduleResponse);
        }
        else {
            throw new UserNotFoundException("Module not found by id: "+id);
        }
    }

    @Override
    @Transactional
    public void uploadModuleFile(String moduleId, MultipartFile file) {
        if (moduleRepository.existsById(moduleId)) {

            try (XWPFDocument doc = new XWPFDocument(file.getInputStream())) {

                Module module = moduleRepository.getById(moduleId);
                module.setTheme(""); // Temporarily empty
                moduleRepository.save(module); // ID kerak bo'ladi

                // 2. Barcha rasmlarni bazaga saqlaymiz
                Map<String, String> imageIdToAttachmentUrl = new HashMap<>();

                List<XWPFPictureData> pictures = doc.getAllPictures();
                for (XWPFPictureData pictureData : pictures) {
                    byte[] imageBytes = pictureData.getData();

                    // 2.1 Attachment
                    Attachment attachment = new Attachment();
                    attachment.setOriginalName(pictureData.getFileName());
                    attachment.setSize((long) imageBytes.length);
                    attachment.setContentType(pictureData.getPackagePart().getContentType());
                    Attachment savedAttachment = attachmentRepository.save(attachment);

                    // 2.2 AttachmentContent
                    AttachmentContent content = new AttachmentContent();
                    content.setAttachment(savedAttachment);
                    content.setBytes(imageBytes);
                    attachmentContentRepository.save(content);

                    // 2.3 ModuleAttachment
                    ModuleAttachment moduleAttachment = new ModuleAttachment();
                    moduleAttachment.setModule(module);
                    moduleAttachment.setAttachment(savedAttachment);
                    moduleAttachmentRepository.save(moduleAttachment);

                    // 2.4 Generate image ID to URL mapping
                    String imageRelId = pictureData.getPackagePart().getPartName().getName(); // e.g. "/word/media/image1.png"
                    imageRelId = imageRelId.substring(imageRelId.lastIndexOf("/") + 1); // image1.png

                    // Customize your download URL pattern here
                    imageIdToAttachmentUrl.put(imageRelId, "/api/v1/desktop/attachment/download/" + savedAttachment.getId());
                }

                // 3. HTMLga konvertatsiya qilish (lekin faylga emas)
                File imageFolder = new File("uploads/images"); // dummy folder
                if (!imageFolder.exists()) imageFolder.mkdirs();

                XHTMLOptions options = XHTMLOptions.create();
                options.setExtractor(new FileImageExtractor(imageFolder)); // still required by converter
                options.setImageManager(new ImageManager(imageFolder, "images")); // dummy path

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                XHTMLConverter.getInstance().convert(doc, out, options);
                String htmlContent = out.toString(String.valueOf(StandardCharsets.UTF_8));

                // 4. Rasm `src` larini o‘zgartiramiz
                for (Map.Entry<String, String> entry : imageIdToAttachmentUrl.entrySet()) {
                    String oldSrc = "src=\"images\\" + entry.getKey();
                    String newSrc = "src=\"" + entry.getValue(); // Your actual download URL
                    System.out.println("Replacing: " + oldSrc + " with " + newSrc);
                    htmlContent = htmlContent.replace(oldSrc, newSrc);
                    System.out.println("-> "+htmlContent);
                    if (htmlContent.contains(newSrc)){
                        System.out.println("---------- YES ---------");
                    }

                    if (htmlContent.contains(oldSrc)){
                        System.out.println("---------- YES 22222 ---------");
                    }
                }

                // 5. Modulega HTML ni saqlaymiz
                module.setTheme(htmlContent);
                moduleRepository.save(module);
            }
            catch (Exception e) {
                throw new RuntimeException("Failed to upload module file: " + e.getMessage(), e);
            }

        }
        else {
            throw new UserNotFoundException("Module not found by id: " + moduleId);
        }
    }
}
