package uz.yeoju.yeoju_app.payload.module;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
public class VedimostCreaterDto {
//    public Integer level;
    public Timestamp deadline;
//    public Timestamp timeClose;
    public String condition;
    public String teacherId;
    public String lessonId;
    public String educationYearId;
    public Set<String> groupsIds;

    public VedimostCreaterDto(/*Integer level,*/ Timestamp deadline, /*Timestamp timeClose, */String condition, String teacherId, String lessonId, String educationYearId, Set<String> groupsIds) {
//        this.level = level;
        this.deadline = deadline;
//        this.timeClose = timeClose;
        this.condition = condition;
        this.teacherId = teacherId;
        this.lessonId = lessonId;
        this.educationYearId = educationYearId;
        this.groupsIds = groupsIds;
    }
}
