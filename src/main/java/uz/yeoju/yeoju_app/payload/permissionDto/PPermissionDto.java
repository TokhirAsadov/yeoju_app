package uz.yeoju.yeoju_app.payload.permissionDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.permissionPost.PCommit;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PPermissionDto {
    private String id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String createdBy;
    private String updatedBy;

    private String content;
    private Date fromDate;
    private Date toDate;
    private String description;
    private PPostStatus status;
    private Set<PCommit> commits;
    private UserDto2 user;
}
