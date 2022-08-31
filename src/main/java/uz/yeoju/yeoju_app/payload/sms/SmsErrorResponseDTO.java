package uz.yeoju.yeoju_app.payload.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SmsErrorResponseDTO {
    @JsonProperty("error-code")
    private Integer errorCode;
    @JsonProperty("error-description")
    private String errorDescription;
}
