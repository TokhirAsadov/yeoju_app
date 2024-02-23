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

    public EducationYearDto(String name, Date start, Date end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public EducationYearDto(String id, String name, Date start, Date end) {
        this.id = id;
        this.name = name;
        this.start = start;
        this.end = end;
    }
}
