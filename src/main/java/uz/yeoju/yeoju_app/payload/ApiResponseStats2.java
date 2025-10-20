package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseStats2 {
    private boolean success;
    private String message;
    private String kafedraId; // Added for grouping
    private int totalAttended;
    private int totalNotAttended;
}
