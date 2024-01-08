package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.dekanat.DekanGroupUpdateDto;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;
import uz.yeoju.yeoju_app.payload.uquvbulimi.GroupAndLessonsOfWeek;
import uz.yeoju.yeoju_app.payload.uquvbulimi.LessonDataForWeek;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GroupImplService;

import java.util.*;
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

    public ApiResponse getStudentStatisticsForDeanOneWeek(String groupId,String educationYearId,Integer weekday,Integer week,Integer year){
        return new ApiResponse(true,"students",groupRepository.getStudentStatisticsForDeanOneWeekNEW(educationYearId,groupId,year, week,weekday ));
    }

    public ApiResponse getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(String educationYearId,String groupName){
        return new ApiResponse(true,"students",groupRepository.getStudentsOfGroupWithTodayStatisticsAndScoreForJournal(educationYearId,groupName));
    }


    public ApiResponse getGroupsAndLessonsOfWeek(){

//        String dayId = DataBaseForTimeTable.daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(day)).findFirst().get().getDays().get(0);
//
//        Set<String> lessonsIds = DataBaseForTimeTable.cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());

        Set<Class> classes = new HashSet<>(DataBaseForTimeTable.classes);

        Set<GroupAndLessonsOfWeek> groupsData = new HashSet<>();

        for (Class aClass : classes) {
            GroupAndLessonsOfWeek groupAndLessonsOfWeek = new GroupAndLessonsOfWeek();
            groupAndLessonsOfWeek.setGroup(aClass.getName());

            Set<LessonXml> lessonXmls = DataBaseForTimeTable.lessons.stream().filter(i -> i.getClassIds().contains(aClass.getId())).collect(Collectors.toSet());

            List<LessonDataForWeek> lessonDataForWeekList = new ArrayList<>();

            for (LessonXml lessonXml : lessonXmls) {
                Optional<Card> first = DataBaseForTimeTable.cards.stream().filter(i -> i.getLessonId().equals(lessonXml.getId())).findFirst();
                LessonDataForWeek lessonDataForWeek = new LessonDataForWeek();

                if (first.isPresent()){
                    Card card = first.get();
                    lessonDataForWeek.setHourPariod(card.getPeriod()); // hour pariod 1,2,...,11,12

                    for (String classroomId : card.getClassroomIds()) {
                        Optional<ClassRoom> first1 = DataBaseForTimeTable.classRooms.stream().filter(i -> i.getId().equals(classroomId)).findFirst();
                        first1.ifPresent(classRoom -> lessonDataForWeek.setRoom(classRoom.getName())); // dars buladigan xona A-108 va h.k.
                    }

                    for (String day : card.getDays()) {
                        Optional<DaysDef> first1 = DataBaseForTimeTable.daysDefs.stream().filter(i -> i.getDays().contains(day) && !i.getShortName().equals("X") && !i.getShortName().equals("E")).findFirst();
                        first1.ifPresent(daysDef -> lessonDataForWeek.setWeekDay(daysDef.getShortName())); // hafta kuni dushanba,seshanba,....
                    }
                    lessonDataForWeekList.add(lessonDataForWeek);
                }


            }

            groupAndLessonsOfWeek.setLessons(lessonDataForWeekList);

            groupsData.add(groupAndLessonsOfWeek);
        }




        return new ApiResponse(true,"group data",groupsData);
    }









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
            Group group = generateGroup2(dto);
            groupRepository.saveAndFlush(group);
            return new ApiResponse(true,"new group saved successfully!...");
        }
        else {
            Group groupByName = groupRepository.findGroupByName(dto.getName());
            if (!groupByName.getActive()) {
                groupByName.setActive(true);
                groupRepository.saveAndFlush(groupByName);
                return new ApiResponse(true, dto.getName()+" old group was been active successfully!...");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! did not saveOrUpdate group! Please, enter other group userPositionName!"
                );
            }
        }
    }

    @Deprecated
    public Group generateGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getLevel(),
                facultyService.generateFaculty(groupDto.getFacultyDto())
        );
    }

    public Group generateGroup2(GroupDto dto) {

        Group group = new Group();
        group.setId(dto.getId());
        group.setName(dto.getName());
        group.setLevel(dto.getLevel());
        group.setEducationType(eduTypeRepo.findById(dto.getEduTypeDto().getId()).get());
        group.setEducationForm(eduFormRepo.findById(dto.getEduFormDto().getId()).get());
        group.setEducationLanguage(eduLanRepo.findById(dto.getEduLanDto().getId()).get());
        group.setFaculty(facultyRepository.findById(dto.getFacultyDto().getId()).get());

        return group;
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
        Set<String> response = new HashSet<>();
        for (LessonXml lessonXml : lessonXmlList) {
            String name = DataBaseForTimeTable.subjects.stream().filter(item -> Objects.equals(item.getId(), lessonXml.getSubjectId())).findFirst().get().getName();
            response.add(name);
        }
        return new ApiResponse(true,"subjects",response);
    }

    public ApiResponse updateGroups(DekanGroupUpdateDto dto) {
        System.out.println(dto.toString());
        System.out.println(dto.getId()!=null);
        System.out.println( !Objects.equals(dto.getId(), ""));
        System.out.println( dto.getId()!=null && !Objects.equals(dto.getId(), ""));

        if (dto.getId()!=null && !Objects.equals(dto.getId(), "")){
            return updateGroup(dto);
        }
        else {
            return saveGroup(dto);
        }

//        return new ApiResponse(false,"error.. please. connect with programmers.");
    }

    public ApiResponse saveGroup(DekanGroupUpdateDto dto){

            boolean groupByName = groupRepository.existsGroupByName(dto.getName());

            if (!groupByName) {
                EducationForm form = eduFormRepo.getEducationFormByName(dto.getForm());
                EducationLanguage language = eduLanRepo.getEducationLanguageByName(dto.getLanguage());
                EducationType type = eduTypeRepo.getEducationTypeByName(dto.getType());

                Group group = new Group();
                group.setName(dto.getName());
                group.setLevel(dto.getLevel());
                group.setEducationForm(form);
                group.setEducationType(type);
                group.setEducationLanguage(language);

                Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(dto.getFaculty());
                if (facultyOptional.isPresent()) {
                    group.setFaculty(facultyOptional.get());
                } else {
                    return new ApiResponse(false, "Not fount Faculty");
                }

                groupRepository.save(group);
                return new ApiResponse(true, dto.getName() + " edited successfully..");
            }
            else {
                return new ApiResponse(false, dto.getName() + " already exists.. Enter other group name, please..");
            }

    }

    public ApiResponse updateGroup(DekanGroupUpdateDto dto){
        Optional<Group> groupOptional = groupRepository.findById(dto.getId());

        if (groupOptional.isPresent()) {
            Group group = groupOptional.get();
            Group groupByName = groupRepository.findGroupByName(dto.getName());

            if (groupByName == null) {
                EducationForm form = eduFormRepo.getEducationFormByName(dto.getForm());
                EducationLanguage language = eduLanRepo.getEducationLanguageByName(dto.getLanguage());
                EducationType type = eduTypeRepo.getEducationTypeByName(dto.getType());

                group.setLevel(dto.getLevel());
                group.setName(dto.getName());

                System.out.println(form.toString());

                group.setEducationForm(form);
                group.setEducationType(type);
                group.setEducationLanguage(language);

                Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(dto.getFaculty());
                if (facultyOptional.isPresent()) {
                    group.setFaculty(facultyOptional.get());
                } else {
                    return new ApiResponse(false, "Not fount Faculty");
                }

                groupRepository.save(group);
                return new ApiResponse(true, dto.getName() + " edited successfully..");
            }
            else {

                if (Objects.equals(groupByName.getId(), dto.getId())){

                    EducationForm form = eduFormRepo.getEducationFormByName(dto.getForm());
                    EducationLanguage language = eduLanRepo.getEducationLanguageByName(dto.getLanguage());
                    EducationType type = eduTypeRepo.getEducationTypeByName(dto.getType());

                    group.setLevel(dto.getLevel());
                    group.setName(dto.getName());

                    System.out.println(form.toString());

                    group.setEducationForm(form);
                    group.setEducationType(type);
                    group.setEducationLanguage(language);

                    Optional<Faculty> facultyOptional = facultyRepository.findFacultyByShortName(dto.getFaculty());
                    if (facultyOptional.isPresent()) {
                        group.setFaculty(facultyOptional.get());
                    } else {
                        return new ApiResponse(false, "Not fount Faculty");
                    }

                    groupRepository.save(group);
                    return new ApiResponse(true, dto.getName() + " edited successfully..");

                }
                else {
                    return new ApiResponse(false, dto.getName() + " already exists.. Enter other group name, please..");
                }
            }
        }
        else {
            return new ApiResponse(false, dto.getName() + " not fount group");
        }
    }


    public ApiResponse getGroupsForKafedraMudiri( String lang, String eduType, Integer level) {
        if (level !=null && eduType !=null && lang !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithLangAndEduTypeAndLevel(lang,eduType,level));
        }
        else if (level !=null && eduType !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithEduTypeAndLevel(eduType,level));
        }
        else if (level !=null && lang !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithLangAndLevel(lang,level));
        }
        else if (level !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithLevel(level));
        }
        else if (eduType !=null && lang !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithLangAndEduType(lang,eduType));
        }
        else if (eduType !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithEduType(eduType));
        }
        else if (lang !=null){
            return new ApiResponse(true,"groups for kafedra mudiri",groupRepository.getGroupsForKafedraMudiriWithLang(lang));
        }
        return new ApiResponse(false,"empty any fields");
    }

    public ApiResponse changeGroupsLevel() {
        List<Group> groupList = groupRepository.findAll();

        groupList.forEach(group -> {
            group.setLevel(group.getLevel()+1);
            groupRepository.save(group);
        });

        return new ApiResponse(true,"change levels successfully");
    }

    public ApiResponse changeActiveOfGroup(String groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setActive(false);
            groupRepository.save(group);
            return new ApiResponse(true,"deleted group successfully");
        }
        else {
            return new ApiResponse(false,"not found group by id : "+groupId);
        }
    }

    public ApiResponse getGroupsByFacultiesIds(List<String> facultiesIds,Integer course,String educationType) {

        Set<GroupForStudent> groups = new HashSet<>();
        facultiesIds.forEach(facultiesId -> {
            Set<GroupForStudent> groupsByFacultiesIds = groupRepository.getGroupsByFacultiesIds(educationType, course, facultiesId);
            groups.addAll(groupsByFacultiesIds);
        });
        return new ApiResponse(true,"groups",groups);
    }
}
