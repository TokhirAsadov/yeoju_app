package uz.yeoju.yeoju_app.service.useServices;

import jdk.nashorn.internal.runtime.options.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.RoleDto;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.RoleImplService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService implements RoleImplService<RoleDto> {
    public final RoleRepository roleRepository;
    public final SectionService sectionService;
    public final SectionRepository sectionRepository;


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All list of roles with their sections",
                roleRepository.findAll().stream().map(this::generateRoleDto).collect(Collectors.toSet())
                );
    }

    private RoleDto generateRoleDto(Role role) {
        return new RoleDto(
                role.getId(),
                role.getRoleName(),
                sectionService.generateSectionDto(role.getSection())
        );
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
