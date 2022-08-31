package uz.yeoju.yeoju_app.payload.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class SmsSendBodyDTO {
    @NotNull
    @JsonProperty("originator")
    String originator = "3700";
    @NotNull
    @JsonProperty("content")
    SmsSendContentDTO content;

}
