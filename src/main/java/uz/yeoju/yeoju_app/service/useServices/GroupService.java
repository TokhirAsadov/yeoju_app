package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GroupImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService implements GroupImplService<GroupDto> {
    public final GroupRepository groupRepository;
    public final FacultyService facultyService;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all group",
                groupRepository.findAll().stream().map(this::generateGroupDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return groupRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount group by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount group by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Group group = groupRepository.getById(id);
        return new ApiResponse(true, "Fount group by id", group);
    }

    @Override
    public ApiResponse saveOrUpdate(GroupDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(GroupDto dto){
        Optional<Group> optional = groupRepository.findById(dto.getId());
        if (optional.isPresent()){
            Group group = optional.get();
            Group groupByName = groupRepository.findGroupByName(dto.getName());
            if (groupByName !=null) {
                if (
                        Objects.equals(groupByName.getId(), group.getId())
                                ||
                                !groupRepository.existsGroupByName(dto.getName())
                ) {
                    group.setName(dto.getName());
                    group.setLevel(dto.getLevel());
                    group.setFaculty(facultyService.generateFaculty(dto.getFacultyDto()));
                    groupRepository.save(group);
                    return new ApiResponse(true, "group updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved group! Please, enter other group name!.."
                    );
                }
            }
            else {
                if (!groupRepository.existsGroupByName(dto.getName())){
                    group.setName(dto.getName());
                    group.setLevel(dto.getLevel());
                    group.setFaculty(facultyService.generateFaculty(dto.getFacultyDto()));
                    groupRepository.save(group);
                    return new ApiResponse(true,"group updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved group! Please, enter other group name!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount group"
            );
        }
    }

    public ApiResponse save(GroupDto dto){
        if (!groupRepository.existsGroupByName(dto.getName())){
            Group group = generateGroup(dto);
            groupRepository.saveAndFlush(group);
            return new ApiResponse(true,"new group saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not save group! Please, enter other group name!"
            );
        }
    }

    private Group generateGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getLevel(),
                facultyService.generateFaculty(groupDto.getFacultyDto())
        );
    }

    private GroupDto generateGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getLevel(),
                facultyService.generateFacultyDto(group.getFaculty())
        );
    }

    @Override
    public ApiResponse deleteById(String id) {
        if (groupRepository.findById(id).isPresent()) {
            groupRepository.deleteById(id);
            return new ApiResponse(true,"group deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount group!");
        }
    }

    @Override
    public ApiResponse findGroupByName(String name) {
        Group groupByName = groupRepository.findGroupByName(name);
        if (groupByName !=null){
            return new ApiResponse(
                    true,
                    "fount group by name",
                    generateGroupDto(groupByName)
            );
        }
        else {
            return new ApiResponse(
                    false,
                    "not fount group by name"
            );
        }
    }

    @Override
    public ApiResponse findGroupsByLevel(Integer level) {
        return new ApiResponse(
                true,
                "List of all groups by level = " + level,
                groupRepository.findGroupsByLevel(level)
                        .stream().map(this::generateGroupDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findGroupsByFacultyId(String faculty_id) {
        return new ApiResponse(
                true,
                "List of all groups by faculty",
                groupRepository.findGroupsByFacultyId(faculty_id)
                        .stream().map(this::generateGroupDto)
                        .collect(Collectors.toSet())
        );
    }
}
