package uz.yeoju.yeoju_app.entity.moduleV2.testV2;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import uz.yeoju.yeoju_app.entity.moduleV2.Course;
import uz.yeoju.yeoju_app.entity.moduleV2.Module;
import uz.yeoju.yeoju_app.entity.moduleV2.TestQuestion;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TestV2 {
    @Id
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

    private String title;

    private Integer singleChoiceCount = 0; // Single choice questions count
    private Integer singleChoiceBall = 0; // har bir single choice dagi tug`ri javob uchun ball
    private Integer multipleChoiceCount = 0; // Multiple choice questions count
    private Integer multipleChoiceBall = 0; // har bir multiple choice dagi tug`ri javob uchun ball
    private Integer writtenCount = 0; // Written questions count
    private Integer writtenBall = 0; // har bir written question uchun ball

    private Double passingBall = 60.0;

    // module testi uchun
    @OneToOne
    private Module module;

    // course testi uchun
    @OneToOne
    private Course course;

    public TestV2(String id, Timestamp createdAt, Timestamp updatedAt, String createdBy, String updatedBy, String title) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.title = title;
    }
}
