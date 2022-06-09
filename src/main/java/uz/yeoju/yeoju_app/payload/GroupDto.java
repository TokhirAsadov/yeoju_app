package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {
    private String id;
    private String name;
    private Integer level;
    private FacultyDto facultyDto;
    private EducationFormDto eduFormDto;
    private EducationTypeDto eduTypeDto;
    private EducationLanguageDto eduLanDto;

    public GroupDto(String name) {
        this.name = name;
    }

    public GroupDto(String name, FacultyDto facultyDto) {
        this.name = name;
        this.facultyDto = facultyDto;
    }

    public GroupDto(String id, String name, Integer level, FacultyDto facultyDto) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.facultyDto = facultyDto;
    }
}
