package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;

import java.util.Calendar;
import java.util.List;

public interface KafedraTeachers29 {

    String getId();

    @Value("#{@kafedraMudirRepository.getKafedraMudiriForRektorTeacherPage(target.id)}")
    KafedraMudiriForRektorTeacherPage getKafedraMudiri();

    @Value("#{@kafedraRepository.getStatisticsForKafedraDashboardNoCome(target.id)}")
    List<NoComeStatistics> getNoComeTeachers();

    @Value("#{@kafedraRepository.getStatisticsForKafedraDashboardComeForRektor(target.id)}")
    List<NoComeStatistics> getComeTeachers();

    @Value("#{@kafedraRepository.getTeachersOfKafedraForRektor29(target.id)}")
    List<GetTeachersOfKafedra29> getAllTeachers();

//    @Value("#{@kafedraMudiriService.getStatisticsForRektorTeacherPage(target.id)}")
//    ApiResponse getAllTeachers();

//    default ApiResponse getAllTeachers() {
//            KafedraMudiriService kafedraMudiriService;
//            return kafedraMudiriService.getStatisticsForRektorTeacherPage(getId());
//        };

}
