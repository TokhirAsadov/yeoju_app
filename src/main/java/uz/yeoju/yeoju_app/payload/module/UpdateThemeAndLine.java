package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateThemeAndLine {
    private Boolean isMulti;
    private Boolean isUpdate;
    private String lineId;
    private String themeId;
    private String themeName;
    private String oldThemeId;
}
