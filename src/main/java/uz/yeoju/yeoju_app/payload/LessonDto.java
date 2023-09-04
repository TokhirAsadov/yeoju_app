package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Deprecated
public class LessonDto {
    private String id;
    private String name;
//    private KafedraDto kafedraDto;
//    private FacultyDto facultyDto;
    private boolean active=true;

    public LessonDto(String name, /*KafedraDto kafedraDto, FacultyDto facultyDto,*/ boolean active) {
        this.name = name;
//        this.kafedraDto = kafedraDto;
//        this.facultyDto = facultyDto;
        this.active = active;
    }
}
