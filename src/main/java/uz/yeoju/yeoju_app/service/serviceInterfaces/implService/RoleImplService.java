package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

import java.util.List;

public interface RoleImplService<T> extends MainService<T> {
    ApiResponse findRoleByRoleName(String roleName);
    ApiResponse findRolesBySection(SectionDto sectionDto);
}
