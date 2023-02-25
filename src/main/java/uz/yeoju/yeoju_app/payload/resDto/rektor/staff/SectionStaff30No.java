package uz.yeoju.yeoju_app.payload.resDto.rektor.staff;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraMudiriForRektorTeacherPage;

import java.util.List;

public interface SectionStaff30No {

    String getId();

    @Value("#{@staffRepository.getBossOfSectionForRektorStaffPage(target.id)}")
    KafedraMudiriForRektorTeacherPage getBoss();

    @Value("#{@staffRepository.getStatisticsForRektorStaffNoCome(target.id)}")
    List<NoComeStatistics> getNoComeStaff();

    @Value("#{@staffRepository.getStatisticsForRektorStaffComeForRektor(target.id)}")
    List<ComeStatistics> getComeStaff();

    @Value("#{@staffRepository.getStaffsOfSectionForRektor30(target.id)}")
    List<GetTeachersOfKafedra30> getAllStaffs();

//    default ApiResponse getAllTeachers() {
//            KafedraMudiriService kafedraMudiriService;
//            return kafedraMudiriService.getStatisticsForRektorTeacherPage(getId());
//        };

}
