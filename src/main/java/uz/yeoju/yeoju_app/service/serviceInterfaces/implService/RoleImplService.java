package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import jdk.nashorn.internal.runtime.options.Option;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

import java.util.List;

public interface RoleImplService<T> extends MainService<T> {
    Option<T> findRoleByRoleName(String roleName);
    List<T> findRolesBySection(SectionDto sectionDto);
}
