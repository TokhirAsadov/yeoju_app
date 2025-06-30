package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.Data;

import java.util.List;
@Data
public class ModuleDtoV2 {
    private String module_id;
    private String module_title;
    private String module_theme;
    private TestV2Dto module_test;
    private List<QuestionsV2Dto> questions;
}
