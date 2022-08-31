package uz.yeoju.yeoju_app.payload.resDto.user;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.role.RoleResDto;

import java.util.List;

public interface UserResDto {

    String getId();
    String getFullName();
    String getRFID();
    String getPassport();

    @Value("#{@roleRepository.findByUserIdRoleName(target.id)}")
    List<RoleResDto> getRoleName();
}
