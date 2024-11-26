package uz.yeoju.yeoju_app.payload.timetableChanging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChangingTeacherOfLessonDetailsDto {
    // eski teacherning platformadagi id si
    public String teacherId;

    // .xml dagi id
    public String xmlTeacherId;
    public String newTeacherShortname;


    public String lessonId;


    //para
    public Integer period;
    //kun
    public Integer day;

    public Integer year;
    public Integer week;
    public WeekType type;

//    //new room id
//    public String roomId;
//    //new room
//    public String room;
}
