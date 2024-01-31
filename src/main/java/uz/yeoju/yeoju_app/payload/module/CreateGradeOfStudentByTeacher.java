package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradeOfStudentByTeacher {
    private String id;
    private Float grade;
    private Timestamp time;
    private String description;
    private String studentId;
    private String subjectId;
    private String educationYearId;
}
