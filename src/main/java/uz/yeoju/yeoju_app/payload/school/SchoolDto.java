package uz.yeoju.yeoju_app.payload.school;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolDto {
    private String id;
    private Integer code;
    private String nameEn;
    private String nameRu;
    private String nameUz;
}
