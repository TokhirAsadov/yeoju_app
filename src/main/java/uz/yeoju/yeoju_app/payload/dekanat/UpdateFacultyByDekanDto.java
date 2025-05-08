package uz.yeoju.yeoju_app.payload.dekanat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateFacultyByDekanDto {
    public String id;
    public String name;
    public String nameEn;
    public String shortName;
    public String schoolCode;
}
