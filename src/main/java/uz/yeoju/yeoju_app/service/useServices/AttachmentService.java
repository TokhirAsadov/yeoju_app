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
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekEduType;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.repository.AttachmentContentRepo;
import uz.yeoju.yeoju_app.repository.AttachmentRepo;
import uz.yeoju.yeoju_app.repository.UserPhotoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.educationYear.WeekOfEducationYearRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTableDB.TimeTableDBService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepo attachmentRepo;

    @Autowired
    AttachmentContentRepo attachmentContentRepo;

    @Autowired
    TimeTableDBService timeTableDBService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserPhotoRepo userPhotoRepo;

    @Autowired
    private EducationYearRepository educationYearRepository;

    @Autowired
    private WeekOfEducationYearRepository weekOfEducationYearRepository;

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




    public ApiResponse saveToFileSystem(MultipartHttpServletRequest request, String educationYearId, String filename, String year,Integer weekNumber, WeekEduType eduType, WeekType weekType,WeekType defaultOrMed, Date startWeek, Date endWeek) throws IOException {


        boolean existEducationYear = educationYearRepository.existsById(educationYearId);

        if (existEducationYear) {

            Path folder = Paths.get(year);

            if (Files.exists(folder)) {
                // file exist
                System.out.println("File exists");
            }

            if (Files.notExists(folder)) {
                // file is not exist
                System.out.println("file is not exist");
                File f = new File(year);
                f.mkdir();
            }

            Path path = defaultOrMed.equals(WeekType.DEFAULT) ? Paths.get(year + "\\" + filename + ".xml"): Paths.get(year + "\\" + filename + "med.xml");
            if (Files.exists(path)) {
                System.out.println(defaultOrMed.equals(WeekType.DEFAULT) ? filename + " already exists":filename + "med already exists");
                return defaultOrMed.equals(WeekType.DEFAULT) ? new ApiResponse(false, filename + " already exists"):new ApiResponse(false, filename + "med already exists");
            } else {
                Iterator<String> fileNames = request.getFileNames();
                List<String> fileIds = new ArrayList<>();
                while (fileNames.hasNext()) {
                    MultipartFile file = request.getFile(fileNames.next());
                    if (file != null) {

                        if (defaultOrMed.equals(WeekType.DEFAULT)){
                            EducationYear educationYear = educationYearRepository.getById(educationYearId);
                            Boolean existsSort = weekOfEducationYearRepository.existsBySortNumberAndYear(Integer.valueOf(filename), Integer.valueOf(year));
                            Boolean existsWeek = weekOfEducationYearRepository.existsByWeekNumberAndYear(weekNumber, Integer.valueOf(year));
                            if (existsSort){
                                return new ApiResponse(false, "Already exists week of year number : "+Integer.valueOf(filename) +" at "+year);
                            }
                            if (existsWeek){
                                return new ApiResponse(false, "Already exists week of education year : "+weekNumber +" at "+year);
                            }

                            WeekOfEducationYear weekOfEducationYear = new WeekOfEducationYear();
                            weekOfEducationYear.setYear(Integer.valueOf(year));
                            weekOfEducationYear.setWeekNumber(weekNumber);
                            weekOfEducationYear.setSortNumber(Integer.valueOf(filename));
                            weekOfEducationYear.setStart(startWeek);
                            weekOfEducationYear.setEnd(endWeek);
                            weekOfEducationYear.setType(weekType);
                            weekOfEducationYear.setEduType(eduType);
                            weekOfEducationYearRepository.saveAndFlush(weekOfEducationYear);

                            educationYear.getWeeks().add(weekOfEducationYear);
                            educationYear.setWeeks(educationYear.getWeeks());
                            educationYearRepository.save(educationYear);
                        }
                        else {
//                            EducationYear educationYear = educationYearRepository.getById(educationYearId);
                            Boolean existsSort = weekOfEducationYearRepository.existsBySortNumberAndYear(Integer.valueOf(filename), Integer.valueOf(year));
                            Boolean existsWeek = weekOfEducationYearRepository.existsByWeekNumberAndYear(weekNumber, Integer.valueOf(year));
                            if (!existsSort){
                                return new ApiResponse(false, "Error.. MED file was not uploaded. Please, Upload MED file!. with: "+Integer.valueOf(filename) +" at "+year);
                            }
                            if (!existsWeek){
                                return new ApiResponse(false, "Error.. MED file was not uploaded. Please, Upload MED file!. with: "+weekNumber +" at "+year);
                            }


                        }


                        Files.copy(file.getInputStream(), path);

                        ApiResponseTwoObj apiResponseTwoObj = defaultOrMed.equals(WeekType.DEFAULT) ? timeTableDBService.generateNewTimeTableToDB(educationYearId,Integer.valueOf(year), Integer.valueOf(filename)) : timeTableDBService.generateNewTimeTableToDBMed(educationYearId,Integer.valueOf(year), Integer.valueOf(filename));

                    }
                }
                return new ApiResponse(true, "Congratulation, " + filename + " - time table of week saved successfully", fileIds);
            }
        }
        else {
            return new ApiResponse(false, "Not fount education year with : "+educationYearId);
        }

    }



}
