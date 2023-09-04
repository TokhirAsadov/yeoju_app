package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonNewDto {
    private String id;
    private String name;
    private String kafedraId;
//    private FacultyDto facultyDto;
    private boolean active=true;

    public LessonNewDto(String name, /*KafedraDto kafedraDto, FacultyDto facultyDto,*/ boolean active) {
        this.name = name;
//        this.kafedraDto = kafedraDto;
//        this.facultyDto = facultyDto;
        this.active = active;
    }
}
