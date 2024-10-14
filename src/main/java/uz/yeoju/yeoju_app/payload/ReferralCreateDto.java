package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReferralCreateDto {
    public String id;
    public Long numberOfDecision;
    public Date decisionDate;
    public Integer course;
    public Date fromDate;
    public Date toDate;
    public String dekanatId;
    public Set<String> facultiesId;
    public Set<String> groupsId;
}
