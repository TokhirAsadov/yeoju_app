package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDto {

    private String id;
    private UserDto userDto;
    private KafedraDto kafedraDto;
    private Set<LessonDto> subjectDto;

}
