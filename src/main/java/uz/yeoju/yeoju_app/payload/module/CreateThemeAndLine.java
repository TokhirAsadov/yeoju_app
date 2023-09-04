package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateThemeAndLine {
    private Boolean isCreate;
    private Integer queue;
    private String newTheme;
    private String planId;
    private String oldThemeId;
}
