package uz.yeoju.yeoju_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleWebClient {
    private String id;
    private String roleName;

    public RoleWebClient(String roleName) {
        this.roleName = roleName;
    }
}
