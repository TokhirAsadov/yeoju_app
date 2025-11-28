package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.table;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfDekanat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.DekanatRepository;
import uz.yeoju.yeoju_app.repository.kafedra.TableOfDekanatRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DekanatTableImplService implements DekanatTableService{

    private final TableOfDekanatRepository repository;
    private final DekanatRepository dekanatRepository;

    @Override
    public ApiResponse saveFileToSystem(User user, MultipartHttpServletRequest request, Integer year, String month,String dekanatId, String id) {
        Path folder = Paths.get("tables");

        if (Files.exists(folder)) {
            // file exist dekanat
            System.out.println("File exists");
        }
        if (Files.notExists(folder)) {
            // file is not exist
            System.out.println("file is not exist");
            File f = new File("tables");
            f.mkdir();
        }
        if (id==null || id.equals("")) {
            Boolean exists = repository.existsByYearAndMonthAndDekanatId(year, month, dekanatId);
            if (!exists) {
                Path yearPath = Paths.get(String.valueOf(year));
                if (Files.exists(yearPath)) {
                    // file exist
                    System.out.println("File exists");
                }
                if (Files.notExists(yearPath)) {
                    // file is not exist
                    System.out.println("file is not exist");
                    File f = new File(String.valueOf(year));
                    f.mkdir();
                }

                Path path = Paths.get("tables\\" + year + "\\" + month);

                if (Files.notExists(path)) {
                    File f = new File("tables\\" + year + "\\" + month);
                    f.mkdirs();
                }


                Iterator<String> fileNames = request.getFileNames();
                while (fileNames.hasNext()) {
                    MultipartFile file = request.getFile(fileNames.next());
                    if (file != null) {
                        System.out.println(file.getOriginalFilename());
                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                        String name = String.valueOf(year) + "-" + month + "_" + UUID.randomUUID();
                        Path filePath = Paths.get("tables\\" + year + "\\" + month + "\\" + name + substring);
                        try {
                            Files.copy(file.getInputStream(), filePath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        TableOfDekanat table = new TableOfDekanat();
                        table.setYear(year);
                        table.setMonth(month);
                        table.setFileName(name);
                        table.setContentType(file.getContentType());
                        table.setFileType(substring);
                        Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dekanatId);
                        if (optionalDekanat.isPresent()) {
                            Dekanat dekanat = optionalDekanat.get();
                            if (dekanat.getOwner().getId().equals(user.getId())) {
                                table.setDekanat(optionalDekanat.get());
                                repository.save(table);
                                return new ApiResponse(true, "Table was saved successfully.");
                            } else {
                                try {
                                    FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return new ApiResponse(false, "🚷❌Siz qoida buzarlik qilyapsiz❌🚷. Siz " + dekanat.getName() + " dekanat dekani emassiz!..");
                            }
                        } else {
                            try {
                                FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return new ApiResponse(false, "not found dekanat by id!..");
                        }

                    } else {
                        return new ApiResponse(false, "error. file is empty");
                    }
                }
                return new ApiResponse(false, "error. file is empty");
            }
            else {
                return new ApiResponse(false, "File already exists");
            }
        }
        else {
            Optional<TableOfDekanat> optional = repository.findById(id);
            if (optional.isPresent()) {
                TableOfDekanat table = optional.get();
                try {
                    FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + table.getFileName() + table.getFileType()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Path yearPath = Paths.get(String.valueOf(year));
                if (Files.exists(yearPath)) {
                    // file exist
                    System.out.println("File exists");
                }
                if (Files.notExists(yearPath)) {
                    // file is not exist
                    System.out.println("file is not exist");
                    File f = new File(String.valueOf(year));
                    f.mkdir();
                }

                Path path = Paths.get("tables\\" + year + "\\" + month);

                if (Files.notExists(path)) {
                    File f = new File("tables\\" + year + "\\" + month);
                    f.mkdirs();
                }



                Iterator<String> fileNames = request.getFileNames();
                while (fileNames.hasNext()) {
                    MultipartFile file = request.getFile(fileNames.next());
                    if (file != null) {
                        System.out.println(file.getOriginalFilename());
                        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
                        String name = String.valueOf(year) + "-" + month + "_" + UUID.randomUUID();
                        Path filePath = Paths.get("tables\\" + year + "\\" + month + "\\" + name + substring);
                        try {
                            Files.copy(file.getInputStream(), filePath);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        table.setYear(year);
                        table.setMonth(month);
                        table.setFileName(name);
                        table.setContentType(file.getContentType());
                        table.setFileType(substring);
                        Optional<Dekanat> optionalDekanat = dekanatRepository.findById(dekanatId);
                        if (optionalDekanat.isPresent()) {
                            Dekanat dekanat = optionalDekanat.get();
                            if (dekanat.getOwner().getId().equals(user.getId())) {
                                table.setDekanat(optionalDekanat.get());
                                repository.save(table);
                                return new ApiResponse(true, "Table was updated successfully.");
                            } else {
                                try {
                                    FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return new ApiResponse(false, "🚷❌Siz qoida buzarlik qilyapsiz❌🚷. Siz " + dekanat.getName() + " dekanat dekani emassiz!..");
                            }
                        } else {
                            try {
                                FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return new ApiResponse(false, "not found dekanat by id!..");
                        }

                    }
                    else {
                        return new ApiResponse(false, "error. file is empty");
                    }
                }
                return new ApiResponse(false, "error. file is empty");
            }
            else {
                return new ApiResponse(false, "File is not found.");
            }
        }
    }

    @Override
    public ApiResponse findByDekanatId(String dekanatId) {
        return new ApiResponse(true,"all tables of dekanat by dekanatId",repository.findAllByDekanatIdOrderByCreatedAtDesc(dekanatId));
    }

    @Override
    public ApiResponse findByName(String name) {
        return new ApiResponse(true,"table of kafedra by fileName",repository.findByFileName(name));
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileName) throws IOException {

        TableOfDekanat table = repository.findByFileName(fileName);
        if (table == null){
            return null;
        }

        byte[] images = Files.readAllBytes(new File("tables\\"+table.getYear()+"\\"+table.getMonth()+"\\"+fileName+table.getFileType()).toPath());
        return images;
    }

}
