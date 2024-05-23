package uz.yeoju.yeoju_app.payload.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalGradeCreatorDto {
    public String vedimostId;
    public Set<FinalGradeCreatorChildDto> data;
}
