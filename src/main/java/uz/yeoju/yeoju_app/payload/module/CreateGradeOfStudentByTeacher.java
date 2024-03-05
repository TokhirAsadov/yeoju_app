package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGradeOfStudentByTeacher {
    private String id;
    private String failGradeId;
//    @Size(max=6, message = "Name should have at least two characters")
    @DecimalMax(value = "6",message = "Maximum grade can be 6.")
    @DecimalMin(value = "0",message = "Minimum grade can be 0.")
    private Float grade;
    private Timestamp time;
    private String description;
    private String studentId;
    private String themeId;
    private String subjectId;
    private String educationYearId;

    public CreateGradeOfStudentByTeacher( Float grade, Timestamp time, String description, String studentId, String subjectId, String educationYearId) {
        this.grade = grade;
        this.time = time;
        this.description = description;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.educationYearId = educationYearId;
    }

    public CreateGradeOfStudentByTeacher(String id, Float grade, Timestamp time, String description, String studentId, String subjectId, String educationYearId) {
        this.id = id;
        this.grade = grade;
        this.time = time;
        this.description = description;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.educationYearId = educationYearId;
    }
}
