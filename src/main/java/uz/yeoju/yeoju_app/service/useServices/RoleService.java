package uz.yeoju.yeoju_app.service.useServices;

import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.RoleDto;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.RoleImplService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleImplService<RoleDto> {
    public final RoleRepository roleRepository;
    public final SectionService sectionService;
    public final SectionRepository sectionRepository;


    @Override
    public ApiResponse findAll() {
        return null;
    }

    @Override
    public ApiResponse findById(UUID id) {
        return null;
    }

    @Override
    public ApiResponse getById(UUID id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(RoleDto roleDto) {
        return null;
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        return null;
    }

    @Override
    public Option<RoleDto> findRoleByRoleName(String roleName) {
        return null;
    }

    @Override
    public List<RoleDto> findRolesBySection(SectionDto sectionDto) {
        return null;
    }
}
