package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private UUID id;
    private String name;
    private FacultyDto facultyDto;
    public GroupDto(String name) {
        this.name = name;
    }

    public GroupDto(String name, FacultyDto facultyDto) {
        this.name = name;
        this.facultyDto = facultyDto;
    }
}
