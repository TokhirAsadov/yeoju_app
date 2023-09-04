package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.LineOfPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetListOfPlan;

import java.util.List;

public interface LineOfPlanRepository extends JpaRepository<LineOfPlan,String> {

    List<LineOfPlan> getLineOfPlansByPlanOfSubjectIdOrderByQueue(String planOfSubject_id);

    @Query(value = "select id,queue,theme_id as themeId from LineOfPlan where planOfSubject_id=?1 order by queue asc",nativeQuery = true)
    List<GetListOfPlan> getLines(String planId);

    @Query(value = "select count(id) from LineOfPlan where theme_id=?1 group by theme_id",nativeQuery = true)
    Integer countUsedOfTheme(String themeId);
}
