package uz.yeoju.yeoju_app.service.useServices;

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
import java.util.Objects;
import java.util.Optional;
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
    private Role generateRole(RoleDto dto) {
        return new Role(
                dto.getId(),
                dto.getRoleName(),
                sectionService.generateSection(dto.getSectionDto())
        );
    }

    @Override
    public ApiResponse findById(UUID id) {
        Optional<Role> optional = roleRepository.findById(id);
        return optional
                .map(role -> new ApiResponse(true, "Fount role by id", generateRoleDto(role)))
                .orElseGet(() -> new ApiResponse(false, "Not fount role by id."));
    }

    @Override
    public ApiResponse getById(UUID id) {
        return new ApiResponse(
                true,
                "Fount role by id",
                generateRoleDto(roleRepository.getById(id))
        );
    }

    @Override
    public ApiResponse saveOrUpdate(RoleDto roleDto) {
        if (roleDto.getId()==null){
            return save(roleDto);
        }
        else {
            return update(roleDto);
        }
    }

    public ApiResponse save(RoleDto dto){
        if (!roleRepository.existsRoleByRoleNameAndSection(dto.getRoleName(),sectionService.generateSection(dto.getSectionDto()))){
            Role role = generateRole(dto);
            roleRepository.saveAndFlush(role);
            return new ApiResponse(true,"new role saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved role! Please, enter other role name or choose other section!"
            );
        }
    }

    public ApiResponse update(RoleDto dto){
        Optional<Role> optional = roleRepository.findById(dto.getId());
        if (optional.isPresent()){
            Role role = optional.get();
            Optional<Role> optionalRole = roleRepository
                    .findRoleByRoleNameAndSection(
                            dto.getRoleName(),
                            sectionService.generateSection(dto.getSectionDto())
                    );
            if (
                    Objects.equals(optionalRole.get().getId(), role.getId())
                            ||
                    !roleRepository
                            .existsRoleByRoleNameAndSection(
                                    dto.getRoleName(),sectionService.generateSection(dto.getSectionDto())
                            )
            ){
                role.setRoleName(dto.getRoleName());
                role.setSection(sectionService.generateSection(dto.getSectionDto()));
                roleRepository.save(role);
                return new ApiResponse(true,"role updated successfully!..");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! nor saved role! Please, enter other role name or choose other section."
                );
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount role"
            );
        }
    }

    @Override
    public ApiResponse deleteById(UUID id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            roleRepository.deleteById(id);
            return new ApiResponse(true,optional.get().getRoleName()+" role delete successfully");
        }
        return new ApiResponse(false,"Error! Not fount role.");
    }

    @Override
    public ApiResponse findRolesByRoleName(String roleName) {
        return new ApiResponse(
                true,
                "List of roles by role_name",
                roleRepository
                        .findAllByRoleName(roleName)
                        .stream()
                        .map(this::generateRoleDto)
                        .collect(Collectors.toList())
                );
    }

    @Override
    public ApiResponse findRolesBySection(SectionDto sectionDto) {

        return null;
    }
}
