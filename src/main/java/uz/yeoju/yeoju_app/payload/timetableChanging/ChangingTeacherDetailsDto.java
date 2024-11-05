package uz.yeoju.yeoju_app.payload.timetableChanging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChangingTeacherDetailsDto {
    public String id;
    public String shortName;
}
