package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStats {
    private boolean success;
    private String message;
    private int totalAttended;
    private int totalNotAttended;
}
