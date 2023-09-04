package uz.yeoju.yeoju_app.payload.resDto.module.downloadCounter;

import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface GetLineAndThemeData {
    String getLineId();
    Integer getQueue();
    String getThemeId();
    String getThemeName();
    String getStudentId();

    @Value("#{@downloadCounterRepository.getDownloadCountAndFileName(target.lineId,target.studentId)}")
    Set<GetDownloadCountAndFileName> getCounter();
}
