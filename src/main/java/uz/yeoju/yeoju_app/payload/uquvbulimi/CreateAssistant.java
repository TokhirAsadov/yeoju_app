package uz.yeoju.yeoju_app.payload.uquvbulimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssistant {
    private String userId;
    private String roleName;
}
