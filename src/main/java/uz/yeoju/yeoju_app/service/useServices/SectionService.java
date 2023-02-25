package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService implements FacultyImplService<SectionDto> {
    public final SectionRepository sectionRepository;


    public ApiResponse getSectionDatas(String userId){
        return new ApiResponse(true,"all section data",sectionRepository.getSectionDatas(userId));
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all section",
                sectionRepository.findAll().stream().map(this::generateSectionDto).collect(Collectors.toSet())
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

    public ApiResponse getStaffComeCountTodayStatistics(String id) {
        return new ApiResponse(true,"statistics",sectionRepository.getStaffComeCountTodayStatistics(id));
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
}
