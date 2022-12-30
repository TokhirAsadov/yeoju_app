package uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers;

import org.springframework.beans.factory.annotation.Value;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;

import java.util.Calendar;
import java.util.List;

public interface KafedraTeachers28 {

    String getId();

    @Value("#{@kafedraMudirRepository.getKafedraMudiriForRektorTeacherPage(target.id)}")
    KafedraMudiriForRektorTeacherPage getKafedraMudiri();

    @Value("#{@kafedraRepository.getStatisticsForKafedraDashboardNoCome(target.id)}")
    List<NoComeStatistics> getNoComeTeachers();

    @Value("#{@kafedraRepository.getStatisticsForKafedraDashboardComeForRektor(target.id)}")
    List<NoComeStatistics> getComeTeachers();

    @Value("#{@kafedraRepository.getTeachersOfKafedraForRektor28(target.id)}")
    List<GetTeachersOfKafedra28> getAllTeachers();


}
