package uz.yeoju.yeoju_app.payload.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class SmsSendRequestDTO {
    @NotNull
    @JsonProperty("sms")
    SmsSendBodyDTO sms;
    @NotNull
    @JsonProperty("messages")
    List<SmsSendMessagesDTO> messages;
}
