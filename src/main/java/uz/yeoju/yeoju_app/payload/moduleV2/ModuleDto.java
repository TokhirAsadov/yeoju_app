package uz.yeoju.yeoju_app.payload.moduleV2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.QuestionsV2Dto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDto {
    private String module_id;
    private String module_title;
    private String module_theme;
    private List<TopicFileOfLineV2Dto> topic_files;
    private CourseTestDto module_test;
    private List<QuestionsV2Dto> questions;
}