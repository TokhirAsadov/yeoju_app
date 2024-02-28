package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildOfMultiDynamicAttendance2Dto {
    public Integer section;
    public Boolean isCome;
    public String studentId;
    public String room;
}
