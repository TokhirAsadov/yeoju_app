package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
    private String id;
    private String phoneNumber;
    private PhoneTypeDto phoneTypeDto;
    private boolean hasTg;
    private boolean hasInstagram;
    private boolean hasFacebook;

    public PhoneNumberDto(String phoneNumber, PhoneTypeDto phoneTypeDto, boolean hasTg, boolean hasInstagram, boolean hasFacebook) {
        this.phoneNumber = phoneNumber;
        this.phoneTypeDto = phoneTypeDto;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
    }
}
