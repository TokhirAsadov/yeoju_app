package uz.yeoju.yeoju_app.payload.timetableChanging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChangingRoomOfLessonDetailsDto {
    public String teacherId;
    public String lessonId;

    // hafta kuni code 000001, 000010, 000100, 001000, 010000, 100000
    public String dayCode;

    //para
    public Integer period;
    //kun
    public Integer day;

    public Integer year;
    public Integer week;
    public WeekType type;

    //new room id
    public String roomId;
}
