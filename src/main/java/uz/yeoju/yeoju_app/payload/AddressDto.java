package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private String id;
    private UserDto userDto;
    private String country;
    private String region;
    private String area;
    private String address;
    private Boolean constant;
    private Boolean asCurrent;

}
