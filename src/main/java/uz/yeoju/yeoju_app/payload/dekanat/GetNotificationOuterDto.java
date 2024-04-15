package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetNotificationOuterDto {
    public String id;
    public Long queue;
    public String dynamicSection;
    public String dekanat;
    public String educationYear;
    public Integer course;
    public Date fromDate;
    public Date toDate;
    public Set<String> facultyNames;
    public Set<String> groupNames;
}
