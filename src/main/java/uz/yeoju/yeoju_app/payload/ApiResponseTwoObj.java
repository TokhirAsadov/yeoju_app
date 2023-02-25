package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseTwoObj {
    private boolean success;
    private String message;
    private Object obj;
    private Object secondObj;

    public ApiResponseTwoObj(boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public ApiResponseTwoObj(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
