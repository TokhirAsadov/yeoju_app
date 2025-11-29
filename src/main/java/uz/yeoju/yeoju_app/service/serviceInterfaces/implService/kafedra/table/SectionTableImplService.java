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



}
