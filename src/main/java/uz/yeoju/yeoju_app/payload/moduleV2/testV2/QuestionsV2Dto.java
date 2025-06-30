package uz.yeoju.yeoju_app.payload.moduleV2.testV2;

import lombok.Data;

import java.util.List;

@Data
public class QuestionsV2Dto {
    public String id;
    public String type; // Savol turi (masalan, "single", "multiple", "text")
    public String questionText; // Savol matni
    public List<TestOptionV2Dto> options; // Savolga tegishli variantlar
}
