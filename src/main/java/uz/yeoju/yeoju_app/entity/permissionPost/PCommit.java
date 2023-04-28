package uz.yeoju.yeoju_app.entity.permissionPost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PCommit extends AbsEntity {

    @Column(name = "content")
    private String content;

    public PCommit(String id, String content) {
        super(id);
        this.content = content;
    }
}
