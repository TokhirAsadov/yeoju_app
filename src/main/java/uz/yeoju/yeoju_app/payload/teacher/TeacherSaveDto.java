package uz.yeoju.yeoju_app.payload.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.WorkerStatus;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.payload.PositionDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSaveDto {
    private String id;
    private String userId;
    private String kafedraId;
    private Set<LessonDto> lessonDtos;
    private Integer positionId;
    private WorkerStatus workerStatus;
    private Float rate;
}
