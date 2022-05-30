package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.Gandername;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GanderDto {
    private Long id;
    private Gandername ganderName;
}
