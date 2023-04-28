//package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.attachment;
//
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import uz.yeoju.yeoju_app.entity.attachment.Attachment;
//import uz.yeoju.yeoju_app.exceptions.RestException;
//import uz.yeoju.yeoju_app.payload.ApiResult;
//import uz.yeoju.yeoju_app.utills.constants.Message;
//import uz.yeoju.yeoju_app.utills.constants.Rest;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class AttachmentServer {
//    private final AttachmentRepository attachmentRepository;
//
//    @Transactional
//    public ApiResult<List<FileDTO>> uploadFile(MultipartHttpServletRequest request) {
//        Iterator<String> fileNames = request.getFileNames();
//        List<FileDTO> fileDTOS = new ArrayList<>();
//        while (fileNames.hasNext()) {
//            MultipartFile file = request.getFile(fileNames.next());
//            if (file != null) {
//                checkFileContent(Objects.requireNonNull(file.getContentType()));
//
//                //FILEDAN ATTACHMENTNI OLADIGAN METOHD
//                Attachment attachment = attachmentToFile(file);
//
//                //FILEDTO LISTGA HAMMA SAQLAGAN FILERNI SAQLAYABMIZ RESPONSE UCHUN
//                fileDTOS.add(toFileDto(attachment));
//
//                // BU METHOD PATH YASAB BERADI. YA'NI FILENI PAPKALAR ICHIGA TAXLAB SAQLAYMIZ
//                Path path = createPath(attachment.getFileName());
//
//                // BERILGAN PATH GA KO'RSATILGAN YO'LGA FILENI BYTE[] NI SAQLAYDI.SAQLOLMASA EXCEPTIONGA OTILADI
//                fileSaveSystem(file, path);
//            }
//        }
//        return ApiResult.success(fileDTOS);
//    }
//
//    public FileDTO toFileDto(Attachment attachment) {
//        return FileDTO.builder()
//                .id(attachment.getId())
//                .name(attachment.getOriginalName())
//                .url(attachment.getFileName())
//                .size(Long.valueOf(attachment.getSize()))
//                .build();
//
//    }
//
//
//    @Override
//    public void getFile(UUID id, HttpServletResponse resp) {
//        //FILENI TOPIB OLIB KELISH TOPILMASA THROW QILAMIZ
//        File file = findFile(id);
//
//        // RESPONSEGA OSHA FILE NI OLIB BERVORAMIZ
//        downloadForResponse(file, resp);
//    }
//
//
//    @Override
//    public ApiResult<String> delete(UUID id) {
//        //AGAR FILE TOPILMASA THROW GA OTAMIZ
//        ifFileNotFoundThrow(id);
//
//        attachmentRepository.deleteById(id);
//        return ApiResult.success(Message.SUCCESSFULLY + Message.DELETE);
//    }
//
//
//    public void checkFileContent(String contentType) {
//        if (!contentType.startsWith("image/"))
//            throw RestException.restThrow(Message.FILE_IS_FORBIDDEN, HttpStatus.BAD_REQUEST);
//    }
//
//    //MULTIPART FILENI SAVE QILIB ATTACHMENT QAYTARDIM
//    public Attachment attachmentToFile(MultipartFile file) {
//        return attachmentRepository.saveOrUpdate(Attachment.builder()
//                .originalName(file.getOriginalFilename())
//                .fileName(createFileUrl(Objects.requireNonNull(file.getOriginalFilename())))
//                .size((int) file.getSize())
//                //RASMNI PAPKALAR ICHIDAN TOPISH UCHUN YOL YASAB BERADIGANNI PATH GA BERDIM
//                .path(collectFolder())
//                .build());
//    }
//
//    // XAR BIR FILEGA UNIQUE NAME YASAB BERADIGAN METHOD
//    private String createFileUrl(String originalFilename) {
//        String name = UUID.randomUUID().toString();
//        String[] split = originalFilename.split("\\.");
//        String contentType = split[split.length - 1];
//        name = name + "." + contentType;
//        return name;
//    }
//
//    //PAPKALARNI YO'LINI TAXLAB YOZIB BERADI
//    public String collectFolder() {
//        DateFormat dateFormat = new SimpleDateFormat("MMMMMMM");
//        Calendar calendar = Calendar.getInstance();
//        String year = calendar.get(Calendar.YEAR) + "";
//        String month = dateFormat.format(new Date());
//        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
//        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
//        return Rest.FILE_PATH + "/" + year + "/" + month + "/" + day + "/" + hour;
//    }
//
//    // BERILGAN YOL BOYICHA PAPKALARNI OCHIB BERADI
//    private void createFolder(String folder) {
//        File file = new File(folder);
//        file.mkdirs();
//    }
//
//    public Path createPath(String fileName) {
//
//        //PAPKALARNI YO'LINI TAXLAB YOZIB BERADI   EX:  =>  D:\PDP\ECMA\AI
//        String folder = collectFolder();
//
//        // BERILGAN YOL BOYICHA PAPKALARNI OCHIB BERADI
//        createFolder(folder);
//
//        String pathString = folder + "/" + fileName;
//        return Paths.get(pathString);
//    }
//
//    public void fileSaveSystem(MultipartFile file, Path path) {
//        try {
//            Files.copy(file.getInputStream(), path);
//        } catch (IOException e) {
//            //FILE_GET_INPUTSTREM bundle qoshmadim hali
//            throw RestException.restThrow(MessageService.getMessage("FILE_GET_INPUTSTREM"), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    private void ifFileNotFoundThrow(UUID id) {
//        if (!attachmentRepository.existsById(id))
//            throw RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST);
//    }
//
//    private File findFile(UUID id) {
//        Attachment attachment = attachmentRepository.findById(id).orElseThrow(() -> RestException.restThrow(Message.ID_NOT_FOUND, HttpStatus.BAD_REQUEST));
//        return new File(attachment.getPath(), attachment.getFileName());
//    }
//
//    private void downloadForResponse(File file, HttpServletResponse resp) {
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            FileCopyUtils.copy(fileInputStream, resp.getOutputStream());
//        } catch (FileNotFoundException e) {
//            throw RestException.restThrow("FileInputStream(attachment.getName())", HttpStatus.BAD_REQUEST);
//        } catch (IOException e) {
//            throw RestException.restThrow("response.getOutputStream()", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}
