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
    private UserDto userDto;
    private PhoneTypeDto phoneTypeDto;
    private boolean hasTg;
    private boolean hasInstagram;
    private boolean hasFacebook;
    private boolean active;

    public PhoneNumberDto(String id, String phoneNumber, UserDto userDto, PhoneTypeDto phoneTypeDto) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userDto = userDto;
        this.phoneTypeDto = phoneTypeDto;
    }

    public PhoneNumberDto(String id, String phoneNumber, UserDto userDto, PhoneTypeDto phoneTypeDto, boolean hasTg, boolean hasInstagram, boolean hasFacebook) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userDto = userDto;
        this.phoneTypeDto = phoneTypeDto;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
    }
}
