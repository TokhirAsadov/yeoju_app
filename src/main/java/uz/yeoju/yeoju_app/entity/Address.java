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
public class Address extends AbsEntity {

    @ManyToOne
    private User user;

    private String country;
    private String region;
    private String district;
    private String streetAndOthers;

    private boolean constant;
    private boolean current;
}
