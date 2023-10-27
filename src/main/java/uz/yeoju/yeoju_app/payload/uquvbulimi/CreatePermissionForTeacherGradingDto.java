package uz.yeoju.yeoju_app.payload.uquvbulimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePermissionForTeacherGradingDto {
    private String id;
    private String teacherId;
    private String subjectId;
    private PPostStatus status;
    private Date deadline;
    private String groupId;
    private String educationYearId;

    public CreatePermissionForTeacherGradingDto(String id, String teacherId, PPostStatus status) {
        this.id = id;
        this.teacherId = teacherId;
        this.status = status;
    }

    public CreatePermissionForTeacherGradingDto(String id, String teacherId, PPostStatus status, Date deadline) {
        this.id = id;
        this.teacherId = teacherId;
        this.status = status;
        this.deadline = deadline;
    }

    public CreatePermissionForTeacherGradingDto(String teacherId, PPostStatus status, Date deadline, String groupId) {
        this.teacherId = teacherId;
        this.status = status;
        this.deadline = deadline;
        this.groupId = groupId;
    }

    public CreatePermissionForTeacherGradingDto(String teacherId, String subjectId, PPostStatus status, Date deadline, String groupId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.status = status;
        this.deadline = deadline;
        this.groupId = groupId;
    }

    public CreatePermissionForTeacherGradingDto(String teacherId, String subjectId, PPostStatus status, Date deadline, String groupId, String educationYearId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
        this.status = status;
        this.deadline = deadline;
        this.groupId = groupId;
        this.educationYearId = educationYearId;
    }
}
