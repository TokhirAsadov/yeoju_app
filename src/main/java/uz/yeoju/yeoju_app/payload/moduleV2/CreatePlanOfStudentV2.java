package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePlanOfStudentV2 {
    public String id;
    public String educationYearId;
    public String eduType;
    public String eduLang;
    public String teacherId;
    public String subjectId;
    public Integer level;
}
