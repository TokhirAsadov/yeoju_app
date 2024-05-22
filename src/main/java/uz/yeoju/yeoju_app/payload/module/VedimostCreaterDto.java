package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VedimostCreaterDto {
    public String id;
    public Integer level;
    public Timestamp deadline;
    public Timestamp timeClose;
    public VedimostCondition condition;
    public String teacherId;
    public String lessonId;
    public String educationYearId;
    public Set<String> groupsIds;
}
