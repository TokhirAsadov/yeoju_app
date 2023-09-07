package uz.yeoju.yeoju_app.payload.kafedra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeKafedraNameDto {
    public String id;
    public String name;
}
