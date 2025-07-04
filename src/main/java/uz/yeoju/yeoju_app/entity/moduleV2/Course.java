package uz.yeoju.yeoju_app.entity.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course extends AbsEntity {
    private String title;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private PlanOfSubjectV2 plan;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Module> modules = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "test_id")
    private Test finalTest;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Faculty> faculties;

    public  Course(String title, PlanOfSubjectV2 plan, Set<Faculty> faculties) {
        this.title = title;
        this.plan = plan;
        this.faculties = faculties;
    }
}
