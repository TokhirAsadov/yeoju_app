package uz.yeoju.yeoju_app.entity.temp;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbsEntity {
    @Id
//    @GeneratedValue(generator="system-uuid")
//    @GenericGenerator(userPositionName="system-uuid", strategy = "uuid")
//    @GeneratedValue
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @Column(updatable = false)
    @NotNull
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;

    private Boolean active=true;
    private Boolean deleted=false;

    public AbsEntity(String id) {
        this.id = id;
    }


}
