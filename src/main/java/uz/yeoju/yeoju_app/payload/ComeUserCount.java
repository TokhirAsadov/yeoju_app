package uz.yeoju.yeoju_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComeUserCount {
    private long come;
    private long all;
    private long cause;

    public ComeUserCount(long come, long all) {
        this.come = come;
        this.all = all;
    }
}
