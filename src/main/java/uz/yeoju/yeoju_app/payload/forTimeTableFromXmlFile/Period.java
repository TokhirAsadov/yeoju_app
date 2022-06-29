package uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Period {
    private Integer name;
    private Integer shortName;
    private Integer period;
    private String startTime;
    private String endTime;
}
