package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiDynamicAttendance2Dto {
    public Integer year;
    public Integer week;
    public Integer day;
    public Set<ChildOfMultiDynamicAttendance2Dto> attendances;
}
