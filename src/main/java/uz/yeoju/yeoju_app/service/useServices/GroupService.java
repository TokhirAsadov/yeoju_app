package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.GroupXml;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.LessonXml;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GroupImplService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GroupService implements GroupImplService<GroupDto> {
    public final GroupRepository groupRepository;
    public final FacultyService facultyService;
    public final FacultyRepository facultyRepository;

    public final EducationLanRepository eduLanRepo;
    public final EducationFormRepository eduFormRepo;
    public final EducationTypeRepository eduTypeRepo;


    public ApiResponse getGroupNameByUserId(String userId){
        return new ApiResponse(true,"group",groupRepository.getGroupNameByUserId(userId));
    }



    public ApiResponse createGroupsByGroupNamesAndFacultyId(Integer courseLevel,List<String> names, String facultyId){

        for (String name : names) {
            Group group = new Group();
            group.setName(name);
            group.setLevel(courseLevel);

            if (name.charAt(name.length()-1) == 'U') group.setEducationLanguage(eduLanRepo.findEducationLanguageByName("UZBEK").get());
            if (name.charAt(name.length()-1) == 'R') group.setEducationLanguage(eduLanRepo.findById("RUSSIAN").get());
            if (name.charAt(name.length()-1) == 'E') group.setEducationLanguage(eduLanRepo.findById("ENGLISH").get());

            if (name.indexOf('-') == 3){
                group.setEducationType(eduTypeRepo.findEducationTypeByName("KUNDUZGI").get());
            }else {
                if (name.charAt(3) == 'P') group.setEducationType(eduTypeRepo.findById("SIRTQI").get());
                else group.setEducationType(eduTypeRepo.findById("KECHKI").get());
            }

            group.setFaculty(facultyRepository.findById(facultyId).get());
            groupRepository.save(group);
        }

        return new ApiResponse(true,"Saved");
    }

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
                            "error! nor saved group! Please, enter other group userPositionName!.."
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
                            "error! nor saved group! Please, enter other group userPositionName!.."
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
                    "error! did not save group! Please, enter other group userPositionName!"
            );
        }
    }

    public Group generateGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getLevel(),
                facultyService.generateFaculty(groupDto.getFacultyDto())
        );
    }

    public GroupDto generateGroupDto(Group group) {
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
                    "fount group by userPositionName",
                    generateGroupDto(groupByName)
            );
        }
        else {
            return new ApiResponse(
                    false,
                    "not fount group by userPositionName"
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


    //TODO ----> Time table from .xml file
    public ApiResponse getSubjectOfGroup(String group) {
        String id = DataBaseForTimeTable.classes.stream().filter(item -> Objects.equals(item.getName(), group)).findFirst().get().getId();
        List<LessonXml> lessonXmlList = DataBaseForTimeTable.lessons.stream().filter(item -> item.getClassIds().contains(id)).collect(Collectors.toList());
        List<String> response = new ArrayList<>();
        for (LessonXml lessonXml : lessonXmlList) {
            String name = DataBaseForTimeTable.subjects.stream().filter(item -> Objects.equals(item.getId(), lessonXml.getSubjectId())).findFirst().get().getName();
            response.add(name);
        }
        return new ApiResponse(true,"subjects",response);
    }
}
