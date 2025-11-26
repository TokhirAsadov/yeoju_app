package uz.yeoju.yeoju_app.payload.resDto.statistics;

import java.util.concurrent.ExecutionException;

public interface GetTotalClassroomAttendance {

    Integer getTotalAllStudent() throws ExecutionException, InterruptedException;
    Integer getTotalComeCount() throws ExecutionException, InterruptedException;
}
