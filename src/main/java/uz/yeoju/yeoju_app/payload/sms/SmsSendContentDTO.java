package uz.yeoju.yeoju_app.payload.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class SmsSendContentDTO {
    @NotNull
    @JsonProperty("text")
    String text;
}
