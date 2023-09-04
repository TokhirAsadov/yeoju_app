package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.TopicFileOfLine;
import uz.yeoju.yeoju_app.payload.resDto.module.GetFileOfLine;

import java.util.Optional;
import java.util.Set;

public interface TopicFileOfLineRepository extends JpaRepository<TopicFileOfLine,String> {

    Optional<TopicFileOfLine> findTopicFileOfLineByName(String name);

    @Query(value = "select t.id,t.name,t.type,t.fileType,t.url from TopicFileOfLine t join TopicFileOfLine_LineOfPlan t_l on t.id = t_l.TopicFileOfLine_id where t_l.lineOfPlans_id=?1 order by t.createdAt",nativeQuery = true)
    Set<GetFileOfLine> getFilesByLineId(String lineId);
}
