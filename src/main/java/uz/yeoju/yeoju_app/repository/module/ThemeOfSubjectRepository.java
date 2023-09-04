package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubject;
import uz.yeoju.yeoju_app.payload.resDto.module.GetListOfPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetOldThemesForCreateLine;
import uz.yeoju.yeoju_app.payload.resDto.module.GetThemeOfSubject;

import java.util.Set;

public interface ThemeOfSubjectRepository extends JpaRepository<ThemeOfSubject,String> {
    @Query(value = "select id as themeId, name as themeName from ThemeOfSubject where createdBy=?1 and lesson_id=?2 order by createdAt desc",nativeQuery = true)
    Set<GetOldThemesForCreateLine> getOldThemesForCreateLine(String ownerId,String subjectId);

    @Query(value = "select id, name,lesson_id as subjectId from ThemeOfSubject where id=?1",nativeQuery = true)
    GetThemeOfSubject getThemeById(String id);
}
