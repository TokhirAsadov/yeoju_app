package uz.yeoju.yeoju_app.payload.educationYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducationYearDto {
    private String id;
    private String name;
    private Date start;
    private Date end;
    private Set<String> weeksIds;
}
