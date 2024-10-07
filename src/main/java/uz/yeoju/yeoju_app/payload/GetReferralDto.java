package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetReferralDto {
    public String id;
    public Long queue;
    public Long numberOfDecision;
    public Date decisionDate;
    public Integer course;
    public Date fromDate;
    public Date toDate;
    public String dekanat;
    public Set<String> facultyNames;
    public Set<String> groupNames;
}
