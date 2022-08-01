package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.PhoneType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
    private String id;
    private String phoneNumber;
    private UserDto userDto;
    private PhoneType phoneType;
    private boolean hasTg;
    private boolean hasInstagram;
    private boolean hasFacebook;
    private boolean active;

    public PhoneNumberDto(String id, String phoneNumber, UserDto userDto, PhoneType phoneType) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userDto = userDto;
        this.phoneType = phoneType;
    }

    public PhoneNumberDto(String id, String phoneNumber, UserDto userDto, PhoneType phoneType, boolean hasTg, boolean hasInstagram, boolean hasFacebook) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.userDto = userDto;
        this.phoneType = phoneType;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
    }
}
