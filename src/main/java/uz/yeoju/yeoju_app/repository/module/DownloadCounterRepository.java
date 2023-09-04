package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.DownloadCounter;
import uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter.GetDownloadCountAndFileName;
import uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter.GetLineAndThemeData;
import uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter.GetPlanIdAndStudentId;

import java.util.Optional;
import java.util.Set;

public interface DownloadCounterRepository extends JpaRepository<DownloadCounter,String> {

    Boolean existsDownloadCounterByUserIdAndTopicId(String user_id, String topic_id);
    Optional<DownloadCounter> findDownloadCounterByUserIdAndTopicId(String user_id, String topic_id);

    @Query(value = "select p.id as planId, ?5 as studentId from PlanOfSubject p join PlanOfSubject_groups pg on p.id = pg.PlanOfSubject_id where p.user_id=?1 and p.educationYear_id=?2 and pg.groups_id=?3 and p.subject_id=?4",nativeQuery = true)
    Set<GetPlanIdAndStudentId> getPlanIdAndStudentId(String teacherId,String educationYearId,String groupsId,String subjectId,String studentId);

    @Query(value = "select lp.id as lineId,lp.queue,ts.id as themeId, ts.name as themeName,?2 as studentId from LineOfPlan lp join ThemeOfSubject ts on lp.theme_id = ts.id\n" +
            "    where lp.planOfSubject_id=?1 order by lp.queue",nativeQuery = true)
    Set<GetLineAndThemeData> getLineAndThemeData(String planId,String studentId);

    @Query(value = "select DC.updatedAt as lastDownloadTime, DC.count,tl.name as fileName,tl.id as fileId from  TopicFileOfLine_LineOfPlan tl_l join TopicFileOfLine tl on tl_l.TopicFileOfLine_id = tl.id join DownloadCounter DC on tl.id = DC.topic_id\n" +
            "    where tl_l.lineOfPlans_id=?1 and DC.user_id=?2",nativeQuery = true)
    Set<GetDownloadCountAndFileName> getDownloadCountAndFileName(String lineId,String studentId);


}
