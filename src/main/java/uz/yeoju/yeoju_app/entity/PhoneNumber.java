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
public class PhoneNumber extends AbsEntity {

    private String phoneNumber;
    @ManyToOne
    private PhoneType phoneType;
    private boolean hasTg;
    private boolean hasInstagram;
    private boolean hasFacebook;

    private boolean active=true;

    public PhoneNumber(String id, String phoneNumber, PhoneType phoneType) {
        super(id);
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }

    public PhoneNumber(String phoneNumber, PhoneType phoneType, boolean hasTg, boolean hasInstagram, boolean hasFacebook) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
    }

    public PhoneNumber(String id, String phoneNumber, PhoneType phoneType, boolean hasTg, boolean hasInstagram, boolean hasFacebook) {
        super(id);
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
    }

    public PhoneNumber(String id, String phoneNumber, PhoneType phoneType, boolean hasTg, boolean hasInstagram, boolean hasFacebook, boolean active) {
        super(id);
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
        this.hasTg = hasTg;
        this.hasInstagram = hasInstagram;
        this.hasFacebook = hasFacebook;
        this.active = active;
    }
}
