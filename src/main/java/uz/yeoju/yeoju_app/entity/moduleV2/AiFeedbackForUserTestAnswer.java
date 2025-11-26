package uz.yeoju.yeoju_app.entity.moduleV2;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

//@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiFeedbackForUserTestAnswer {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @CreationTimestamp
    @Column(updatable = false)
    @NotNull
    private Timestamp createdAt;

    private Integer score; // Ballar
    private String feedback; // Fikr-mulohaza

    @OneToOne
    private UserTestAnswer userTestAnswer; // Foydalanuvchi javobi
}
