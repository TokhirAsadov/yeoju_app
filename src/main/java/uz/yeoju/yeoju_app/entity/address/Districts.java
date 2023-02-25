package uz.yeoju.yeoju_app.entity.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Districts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Regions region;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_oz")
    private String nameOz;

    @Column(name = "name_ru")
    private String nameRu;

    public Districts(String nameUz, String nameOz, String nameRu) {
        this.nameUz = nameUz;
        this.nameOz = nameOz;
        this.nameRu = nameRu;
    }
}
