package uz.yeoju.yeoju_app.payload.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectAddressDto {
    private String id;
    private String userId;
    private Boolean isConstant;
    private String villageName;
    private String address;
}
