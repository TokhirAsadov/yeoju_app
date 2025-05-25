package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicFileOfLineV2Dto {
    public String name;
    public String fileType;
    public String contentType;
    public String packageName;
}
