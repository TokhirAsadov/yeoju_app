package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffMonitoringOneDay {
    private int comeInCount;
    private int notCome;
    private int causeCome;
}
