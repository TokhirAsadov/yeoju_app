package uz.yeoju.yeoju_app.payload.res.user;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.res.role.RoleResDto;

import java.util.List;

public interface UserResDto {

    String getId();
    String getFullName();
    String getRFID();

    @Value("#{@roleRepository.findByUserIdRoleName(target.id)}")
    List<RoleResDto> getRoleName();
}
