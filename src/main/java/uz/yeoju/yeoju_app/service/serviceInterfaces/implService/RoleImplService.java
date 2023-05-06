package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface RoleImplService<T> extends MainService<T> {
    ApiResponse findRolesByRoleName(String roleName);
}
