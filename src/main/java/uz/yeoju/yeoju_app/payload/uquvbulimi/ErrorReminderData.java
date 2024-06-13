package uz.yeoju.yeoju_app.payload.uquvbulimi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReminderData {
    public String id;
    public String error;
    public Timestamp timestamp;
}
