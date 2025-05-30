package uz.yeoju.yeoju_app.repository.moduleV2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.TopicFileOfLineV2;
import uz.yeoju.yeoju_app.payload.resDto.module.GetFileOfLine;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface TopicFileOfLineV2Repository extends JpaRepository<TopicFileOfLineV2,String> {
    Optional<TopicFileOfLineV2> findTopicFileOfLineV2ByName(String name);

    @Query(value = "select t.id,t.name,t.type,t.fileType,t.url from TopicFileOfLineV2 t join TopicFileOfLineV2_LessonModule t_l on t.id = t_l.TopicFileOfLineV2_id where t_l.lessons_id=?1 order by t.createdAt",nativeQuery = true)
    Set<GetFileOfLine> getFilesByLessonModuleId(String lessonModuleId);

    List<TopicFileOfLineV2> findAllByModulesId(String modules_id);
}
