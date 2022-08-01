package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddressUser extends AbsEntity {

    @ManyToOne
    private User user;

    private String country;
    private String region;
    private String area;
    private String address;

    private Boolean constant;
    private Boolean current;

    public AddressUser(User user, String country, String region, String area, String address) {
        this.user = user;
        this.country = country;
        this.region = region;
        this.area = area;
        this.address = address;
    }
}
