package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseTestDto;
import uz.yeoju.yeoju_app.payload.moduleV2.JsonObject;
import uz.yeoju.yeoju_app.payload.moduleV2.ModuleDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseV2Dto {
    private String course_id;
    private String course_title;
    private List<ModuleDtoV2> modules;
    private List<JsonObject> faculties;
    private TestV2Dto final_test;
}
