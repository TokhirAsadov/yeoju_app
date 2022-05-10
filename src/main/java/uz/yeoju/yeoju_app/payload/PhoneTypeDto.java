package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneTypeDto {
    private Long id;
    private String name;

    public PhoneTypeDto(String name) {
        this.name = name;
    }
}
