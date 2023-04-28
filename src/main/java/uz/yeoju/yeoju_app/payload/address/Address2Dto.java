package uz.yeoju.yeoju_app.payload.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address2Dto {
    private String id;
    private String userId;

    private Long villageId;

    private String country;
    private String region;
    private String area;

    private String address;

    private Boolean constant;
    private Boolean fromUzbekistan;

}
