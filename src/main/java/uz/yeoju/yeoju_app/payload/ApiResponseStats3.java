package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStats3 {
    private boolean success;
    private String message;
    private int week;
    private int weekday;
    private int totalAttended;
    private int totalNotAttended;
}
