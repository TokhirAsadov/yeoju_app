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
    private String failGradeId;
    private Float grade;
    private Timestamp time;
    private String description;
    private String studentId;
    private String subjectId;
    private String educationYearId;

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
