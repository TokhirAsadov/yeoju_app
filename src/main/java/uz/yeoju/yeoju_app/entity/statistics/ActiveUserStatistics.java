package uz.yeoju.yeoju_app.entity.statistics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUserStatistics extends AbsEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    private Long count = 1L;

    public ActiveUserStatistics(User user) {
        this.user = user;
    }
}
