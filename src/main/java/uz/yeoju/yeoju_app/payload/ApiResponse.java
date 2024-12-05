package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Object obj;
    private Long totalElements;
    private Date time = new Date();

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ApiResponse(boolean success, String message, Object obj) {
        this.success = success;
        this.message = message;
        this.obj = obj;
    }

    public ApiResponse(boolean success, String message, Object obj, Long totalElements) {
        this.success = success;
        this.message = message;
        this.obj = obj;
        this.totalElements = totalElements;
    }
}
