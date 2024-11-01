package uz.yeoju.yeoju_app.payload.resDto.statistics;

public interface GetTotalClassroomAttendanceEveryGroup {
    Integer getTotalAllStudent();
    Integer getTotalComeCount();
    String getFacultyShortName();
    String getFacultyName();
    String getEduType();
    String getGroupName();
    Integer getCourse();
}
