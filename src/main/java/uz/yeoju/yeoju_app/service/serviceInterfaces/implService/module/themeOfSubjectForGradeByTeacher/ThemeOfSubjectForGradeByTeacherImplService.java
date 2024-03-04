package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.themeOfSubjectForGradeByTeacher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.module.ThemeOfSubjectForGradeByTeacherRepository;

@Service
@RequiredArgsConstructor
public class ThemeOfSubjectForGradeByTeacherImplService implements ThemeOfSubjectForGradeByTeacherService{

    private final ThemeOfSubjectForGradeByTeacherRepository repository;

}
