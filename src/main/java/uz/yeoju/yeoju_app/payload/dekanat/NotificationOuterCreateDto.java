package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationOuterCreateDto {
    public String id;
    public String dynamicSection;
    public String dekanatId;
    public String educationId;
    public Integer course;
    public Date fromDate;
    public Date toDate;
    public Set<String> facultiesId;

    public Set<String> groupsId;

    public NotificationOuterCreateDto(String dynamicSection, String dekanatId, String educationId, Integer course, Date fromDate, Date toDate, Set<String> facultiesId, Set<String> groupsId) {
        this.dynamicSection = dynamicSection;
        this.dekanatId = dekanatId;
        this.educationId = educationId;
        this.course = course;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.facultiesId = facultiesId;
        this.groupsId = groupsId;
    }

    public NotificationOuterCreateDto(String dekanatId, String educationId, Integer course, Date fromDate, Date toDate, Set<String> facultiesId, Set<String> groupsId) {
        this.dekanatId = dekanatId;
        this.educationId = educationId;
        this.course = course;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.facultiesId = facultiesId;
        this.groupsId = groupsId;
    }
}
