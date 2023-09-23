package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanOfStudent {
    public String id;
    public String educationYearId;
    public String eduType;
    public String eduLang;
    public String teacherId;
    public String subjectId;
    public Integer level;
    public List<String> groupsIds;
}
