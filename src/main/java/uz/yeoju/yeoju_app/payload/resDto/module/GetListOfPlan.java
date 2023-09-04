package uz.yeoju.yeoju_app.payload.resDto.module;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetListOfPlan {
    String getId();
    Integer getQueue();
    String getThemeId();

    @Value("#{@themeOfSubjectRepository.getThemeById(target.themeId)}")
    GetThemeOfSubject getTheme();

    @Value("#{@topicFileOfLineRepository.getFilesByLineId(target.id)}")
    Set<GetFileOfLine> getFiles();
}
