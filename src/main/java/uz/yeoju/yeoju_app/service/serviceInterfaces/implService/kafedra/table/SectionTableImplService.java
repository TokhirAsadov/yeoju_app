package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra.table;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.TableOfSection;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.repository.kafedra.TableOfSectionRepository;

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
public class SectionTableImplService implements SectionTableService{

    private final TableOfSectionRepository repository;
    private final SectionRepository sectionRepository;

    @Override
    public ApiResponse saveFileToSystem(User user, MultipartHttpServletRequest request, Integer year, String month,String sectionId, String id) {
        Path folder = Paths.get("tables");

        if (Files.exists(folder)) {
            // file exist
            System.out.println("File exists");
        }
        if (Files.notExists(folder)) {
            // file is not exist
            System.out.println("file is not exist");
            File f = new File("tables");
            f.mkdir();
        }
        if (id==null || id.equals("")) {
            Boolean exists = repository.existsByYearAndMonthAndSectionId(year, month, sectionId);
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

                        TableOfSection table = new TableOfSection();
                        table.setYear(year);
                        table.setMonth(month);
                        table.setFileName(name);
                        table.setContentType(file.getContentType());
                        table.setFileType(substring);
                        Optional<Section> optionalSection = sectionRepository.findById(sectionId);
                        if (optionalSection.isPresent()) {
                            Section section = optionalSection.get();
                            if (section.getOwner().getId().equals(user.getId())) {
                                table.setSection(optionalSection.get());
                                repository.save(table);
                                return new ApiResponse(true, "Table was saved successfully.");
                            } else {
                                try {
                                    FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return new ApiResponse(false, "🚷❌Siz qoida buzarlik qilyapsiz❌🚷. Siz " + section.getName() + " bo`limining bo`lim boshlig`i emassiz!..");
                            }
                        } else {
                            try {
                                FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return new ApiResponse(false, "not found section by id!..");
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
            Optional<TableOfSection> optional = repository.findById(id);
            if (optional.isPresent()) {
                TableOfSection table = optional.get();
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
                        Optional<Section> optionalSection = sectionRepository.findById(sectionId);
                        if (optionalSection.isPresent()) {
                            Section section = optionalSection.get();
                            if (section.getOwner().getId().equals(user.getId())) {
                                table.setSection(optionalSection.get());
                                repository.save(table);
                                return new ApiResponse(true, "Table was updated successfully.");
                            } else {
                                try {
                                    FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return new ApiResponse(false, "🚷❌Siz qoida buzarlik qilyapsiz❌🚷. Siz " + section.getName() + " bo`limining bo`lim boshlig`i emassiz!..");
                            }
                        } else {
                            try {
                                FileUtils.forceDelete(FileUtils.getFile("tables\\" + year + "\\" + month + "\\" + name + substring));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return new ApiResponse(false, "not found section by id!..");
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
    public ApiResponse findBySectionId(String sectionId) {
        return new ApiResponse(true,"all tables of section by sectionId",repository.findAllBySectionIdOrderByCreatedAtDesc(sectionId));
    }




}
