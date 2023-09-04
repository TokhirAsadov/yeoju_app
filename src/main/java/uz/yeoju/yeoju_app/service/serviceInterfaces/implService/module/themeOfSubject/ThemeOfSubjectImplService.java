package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.module.ThemeOfSubjectRepository;

@Service
@RequiredArgsConstructor
public class ThemeOfSubjectImplService implements ThemeOfSubjectService{
    private final ThemeOfSubjectRepository themeRepository;
}
