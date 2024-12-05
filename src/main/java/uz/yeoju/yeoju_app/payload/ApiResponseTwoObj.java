package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseTwoObj {
    private boolean success;
    private String message;
    private Object obj;
    private Object secondObj;
    private Date time = new Date();

    public ApiResponseTwoObj(boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public ApiResponseTwoObj(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponseTwoObj(boolean success, String message, Object obj, Object secondObj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
        this.secondObj = secondObj;
    }
}
