package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.OwnerDto;
import uz.yeoju.yeoju_app.payload.section.SectionDto;
import uz.yeoju.yeoju_app.payload.section.SectionDtoV2;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.section.SectionDtoV3;
import uz.yeoju.yeoju_app.repository.PositionRepository;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService implements FacultyImplService<SectionDto> {
    public final SectionRepository sectionRepository;
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final PositionRepository positionRepository;

    public ApiResponse getSection3ById(String id){
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        if (sectionOptional.isPresent()){
            Section section = sectionOptional.get();
            System.out.println(section+" 45465v ---");
//            Set<String> roleSet = new HashSet<>();
//            Set<String> positionSet = new HashSet<>();
//            section.getRoles().stream().map(role -> roleSet.add(role.getRoleName()));
//            section.getPositions().stream().map(position -> positionSet.add(position.getUserPositionName()));
            return new ApiResponse(
                    true,
                    "by id",
                    new SectionDtoV3(
                            section.getId(),
                            section.getName(),
                            section.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()),
                            section.getPositions().stream().map(Position::getUserPositionName).collect(Collectors.toSet()),
                            new OwnerDto(section.getOwner().getId(),section.getOwner().getFullName()),
                            section.getRoom(),
                            section.getPhone()
                    )
            );
        }
        else {
            return new ApiResponse(false,"not fount section");
        }
    }

    public ApiResponse getSectionById(String id){
        Optional<Section> sectionOptional = sectionRepository.findById(id);
        if (sectionOptional.isPresent()){
            Section section = sectionOptional.get();
            System.out.println(section+" 45465v ---");
//            Set<String> roleSet = new HashSet<>();
//            Set<String> positionSet = new HashSet<>();
//            section.getRoles().stream().map(role -> roleSet.add(role.getRoleName()));
//            section.getPositions().stream().map(position -> positionSet.add(position.getUserPositionName()));
            return new ApiResponse(
                    true,
                    "by id",
                    new SectionDtoV2(
                            section.getId(),
                            section.getName(),
                            section.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()),
                            section.getPositions().stream().map(Position::getUserPositionName).collect(Collectors.toSet())
//                            section.getOwner().getId(),
//                            section.getRoom(),
//                            section.getPhone()
                    )
            );
        }
        else {
            return new ApiResponse(false,"not fount section");
        }
    }


    public ApiResponse getSectionDatas(String userId){
        return new ApiResponse(true,"all section data",sectionRepository.getSectionDatas(userId));
    }

    public ApiResponse getSectionDatasByUserId(String userId){
        return new ApiResponse(true,"all section data",sectionRepository.getSectionDatasByUserId(userId));
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all section",
                sectionRepository.findAll()
//                sectionRepository.findAll().stream().map(this::generateSectionDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        Optional<Section> optional = sectionRepository.findById(id);
        return sectionRepository
                .findById(id)
                .map(section -> new ApiResponse(true, "Fount section by id", section))
                .orElseGet(() -> new ApiResponse(false, "Not fount section by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Section section = sectionRepository.getById(id);
        return new ApiResponse(true, "Fount section by id", section);
    }
    @Override
    public ApiResponse saveOrUpdate(SectionDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }
    public ApiResponse save(SectionDto dto){
        if (!sectionRepository.existsSectionByName(dto.getName())){
            Section section = generateSection(dto);
            section.setId(null);
            sectionRepository.save(section);
            return new ApiResponse(true,"new section saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved section! Please, enter other section userPositionName!"
            );
        }
    }

    public Section generateSection(SectionDto dto) {
        return new Section(dto.getId(),dto.getName());
    }
    public SectionDto generateSectionDto(Section section) {
        if (section!=null)
            return new SectionDto(section.getId(), section.getName());
        else return null;
    }

    public ApiResponse update(SectionDto dto){
        Optional<Section> optional = sectionRepository.findById(dto.getId());
        if (optional.isPresent()){
            Section section = optional.get();
            Section sectionByName = sectionRepository.getSectionByName(dto.getName());
            if (sectionByName!=null) {
                if (
                        Objects.equals(sectionByName.getId(), section.getId())
                                ||
                                !sectionRepository.existsSectionByName(dto.getName())
                ) {
                    section.setName(dto.getName());
                    sectionRepository.save(section);
                    return new ApiResponse(true, "section updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved section! Please, enter other section userPositionName!.."
                    );
                }
            }else {
                if (!sectionRepository.existsSectionByName(dto.getName())) {
                    section.setName(dto.getName());
                    sectionRepository.save(section);
                    return new ApiResponse(true, "section updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved section! Please, enter other section userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount section"
            );
        }
    }
    @Override
    public ApiResponse deleteById(String id) {
        if (sectionRepository.findById(id).isPresent()) {
            sectionRepository.deleteById(id);
            return new ApiResponse(true,"Section deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount section!");
        }
    }

    public ApiResponse getStaffComeCountTodayStatistics(String id,Boolean s) {
        return new ApiResponse(true,"statistics",s ? sectionRepository.getStaffComeCountTodayStatistics(id) : sectionRepository.getStaffComeCountTodayStatistics());
    }

    public ApiResponse getStatisticsForSectionDashboard(Integer index, String sectionId) {

        if (index!=0){
            List<NoComeStatistics> noComeStatisticsList = sectionRepository.getStatisticsForSectionDashboardNoCome(sectionId);
            return new ApiResponse(true,"no come",noComeStatisticsList);
        }
        else {
            List<ComeStatistics> comeStatistics = sectionRepository.getStatisticsForSectionDashboardCome(sectionId);
            return new ApiResponse(true,"come",comeStatistics);
        }

    }


    public ApiResponse getStatisticsForDekan(User user) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = sectionRepository.getStaffsOfDekan31(user.getId());
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = sectionRepository.getStaffsOfDekan30(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = sectionRepository.getStaffsOfDekan29(user.getId());
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = sectionRepository.getStaffsOfDekan28(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }


    public ApiResponse getStatistics(User user) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = sectionRepository.getStaffsOfKafedra31(user.getId());
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = sectionRepository.getStaffsOfKafedra30(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = sectionRepository.getStaffsOfKafedra29(user.getId());
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = sectionRepository.getStaffsOfKafedra28(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }

    public ApiResponse getStatisticsForRektor(String sectionId, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<??? 31",sectionRepository.getDateForRektor31(date,sectionId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<??? 30",sectionRepository.getDateForRektor30(date,sectionId));
        }else if (maxDay==29){
            return new ApiResponse(true,"<??? 29",sectionRepository.getDateForRektor29(date,sectionId));
        }else {
            return new ApiResponse(true,"<??? 28",sectionRepository.getDateForRektor28(date,sectionId));
        }
    }

    public ApiResponse saveOrUpdate(SectionDtoV2 dto) {
        if (dto.getId() == null){
            return save(dto);
        }
        else {
            return update(dto);
        }

    }

    private ApiResponse update(SectionDtoV2 dto) {
        Optional<Section> optional = sectionRepository.findById(dto.getId());
        if (optional.isPresent()){

            Section section = optional.get();
            Section kafedraByName = sectionRepository.getSectionByName(dto.getName());
            if (kafedraByName !=null) {
                if ( Objects.equals(kafedraByName.getId(), section.getId())   ) {

                    Optional<User> userOptional = userRepository.findById(dto.getOwnerId());

                    if (userOptional.isPresent()) {

                        section.setOwner(userOptional.get());
                        section.setRoom(dto.getRoom());
                        section.setPhone(dto.getPhone());
                        section.setName(dto.getName());

                        Set<Role> roleSet = new HashSet<>();
                        Set<Position> positionSet = new HashSet<>();
                        dto.getRoles().forEach(role -> {
                            if (roleRepository.findRoleByRoleName(role).isPresent())
                                roleSet.add(roleRepository.findRoleByRoleName(role).get());
                        });
                        dto.getPositions().forEach(position -> {
                            if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                        });

                        section.setRoles(roleSet);
                        section.setPositions(positionSet);

                        sectionRepository.save(section);
                        return new ApiResponse(true, section.getName() + " section updated successfully!..");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! did not update section! Please, choose other owner!"
                        );
                    }
                } else {
                    return new ApiResponse(
                            false,
                            "error! not saved section! Please, enter other section name!.."
                    );
                }
            }
            else {
                if (!sectionRepository.existsSectionByName(dto.getName())){
                    Optional<User> userOptional = userRepository.findById(dto.getOwnerId());
                    if (userOptional.isPresent()) {
                        section.setOwner(userOptional.get());
                        section.setRoom(dto.getRoom());
                        section.setPhone(dto.getPhone());
                        section.setName(dto.getName());

                        Set<Role> roleSet = new HashSet<>();
                        Set<Position> positionSet = new HashSet<>();
                        dto.getRoles().forEach(role -> {
                            if (roleRepository.findRoleByRoleName(role).isPresent())
                                roleSet.add(roleRepository.findRoleByRoleName(role).get());
                        });
                        dto.getPositions().forEach(position -> {
                            if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                        });

                        section.setRoles(roleSet);
                        section.setPositions(positionSet);

                        sectionRepository.save(section);
                        return new ApiResponse(true, section.getName() + " section updated successfully!..");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! not saved section! Please, choose other owner!"
                        );
                    }
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! not saved section! Please, enter other section!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount section"
            );
        }
    }

    private ApiResponse save(SectionDtoV2 dto) {
        if (!sectionRepository.existsSectionByName(dto.getName())){
            Section section = generateSection(dto);
            if (section!=null){
                sectionRepository.saveAndFlush(section);
                return new ApiResponse(true,"new section saved successfully!...");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! not saved section! Please, choose other owner!"
                );
            }
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved Section! Please, enter other section name!"
            );
        }
    }

    private Section generateSection(SectionDtoV2 dto) {

        Set<Role> roles = new HashSet<>();
        Set<Position> positions = new HashSet<>();
        Optional<User> userOptional = userRepository.findById(dto.getOwnerId());
        if (userOptional.isPresent()) {
            dto.getRoles().forEach(role -> {
                Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                roleOptional.ifPresent(roles::add);
            });
            dto.getPositions().forEach(position -> {
                Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                positionOptional.ifPresent(positions::add);
            });

            return new Section(
                    dto.getId(),
                    dto.getName(),
                    userOptional.get(),
                    dto.getRoom(),
                    dto.getPhone(),
                    roles,
                    positions
            );
        }
        else {
            return null;
        }

    }
}
