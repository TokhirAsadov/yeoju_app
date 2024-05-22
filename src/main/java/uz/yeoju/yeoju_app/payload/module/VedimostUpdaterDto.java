package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VedimostUpdaterDto {
    public String id;
//    public Integer level;
    public Timestamp deadline;
//    public Timestamp timeClose;
    public String condition;
    public String teacherId;
    public String lessonId;
    public String educationYearId;
    public String groupId;

    public VedimostUpdaterDto(/*Integer level,*/ Timestamp deadline, /*Timestamp timeClose,*/ String condition, String teacherId, String lessonId, String educationYearId, String groupId) {
//        this.level = level;
        this.deadline = deadline;
//        this.timeClose = timeClose;
        this.condition = condition;
        this.teacherId = teacherId;
        this.lessonId = lessonId;
        this.educationYearId = educationYearId;
        this.groupId = groupId;
    }
}
