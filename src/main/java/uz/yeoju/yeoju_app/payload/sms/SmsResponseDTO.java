package uz.yeoju.yeoju_app.payload.sms;

import lombok.Value;

@Value
public class SmsResponseDTO {
    int statusCode;
    String response;
}
