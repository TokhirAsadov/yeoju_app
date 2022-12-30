package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.payload.UserDto;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DekanDto {
    private String id;
    private UserDto userDto;
    private Set<FacultyDto> facultyDtos;

    public DekanDto(String id, UserDto userDto) {
        this.id = id;
        this.userDto = userDto;
    }

    public DekanDto(UserDto userDto, Set<FacultyDto> facultyDtos) {
        this.userDto = userDto;
        this.facultyDtos = facultyDtos;
    }
}
