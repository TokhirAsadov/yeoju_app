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



}
