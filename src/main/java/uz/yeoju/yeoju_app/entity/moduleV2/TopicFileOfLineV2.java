package uz.yeoju.yeoju_app.entity.moduleV2;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.module.TopicFileType;
import uz.yeoju.yeoju_app.entity.temp.AbsEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicFileOfLineV2 extends AbsEntity {
    private String name;
    private String fileType;
    private String contentType;

    @Enumerated(EnumType.STRING)
    private TopicFileType type;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Module> modules;
    private String packageName;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<DownloadCounterV2> downloads = new ArrayList<>();

    private String url;
}
