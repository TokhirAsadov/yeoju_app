package uz.yeoju.yeoju_app.entity.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.address.Villages;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AddressUser extends AbsEntity {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Villages village;

    private Boolean isConstant=false;
    private Boolean fromUzbekistan=true;


    // Uzbekiston dan boshqa davlatdan bulsa country, region, area, address kiritadi
    private String country;
    private String region;
    private String district;
    private String area;

    // umumiy address Uzbekiston uchun ham bowqa davlat uchun ham ishlatiladi
    private String address;


    public AddressUser(User user, String country, String region, String area, String address) {
        this.user = user;
        this.country = country;
        this.region = region;
        this.area = area;
        this.address = address;
    }

    public AddressUser(User user, String country, String region, String area, String address,Boolean isConstant) {
        this.user = user;
        this.isConstant = isConstant;
        this.country = country;
        this.region = region;
        this.area = area;
        this.address = address;
    }

    public AddressUser(String id, User user, String country, String region, String area, String address, Boolean isConstant) {
        super(id);
        this.user = user;
        this.country = country;
        this.region = region;
        this.area = area;
        this.address = address;
        this.isConstant = isConstant;
    }
}
