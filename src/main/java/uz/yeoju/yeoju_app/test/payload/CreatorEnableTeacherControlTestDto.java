package uz.yeoju.yeoju_app.test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatorEnableTeacherControlTestDto {
    public String id;
    public String teacherId;
    public Set<String> lessonsIds;

    public CreatorEnableTeacherControlTestDto(String teacherId, Set<String> lessonsIds) {
        this.teacherId = teacherId;
        this.lessonsIds = lessonsIds;
    }
}
