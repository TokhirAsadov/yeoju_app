package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.kafedra.ChangeKafedraNameDto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;
import uz.yeoju.yeoju_app.payload.kafedra.TeacherEditDto;

import java.util.Date;
import java.util.Set;

public interface KafedraMudiriService {

    ApiResponse findAll();
    ApiResponse save(KafedraMudiriSaving saving);

    ApiResponse getStatistics(User user);

    ApiResponse getStatistics(String kafedraId);

    ApiResponse getStatistics(User user, String userId,Date date);

    ApiResponse getStatisticsForRektorTeacherPage(String id);

    ApiResponse getStatisticsForRektor(String kafedraId, Date date);
    ApiResponse getStatisticsForTable(String kafedraId, Date date, Set<String> teachersIds);

    ApiResponse saveKafedra(DekanSave dekanSave);

    ApiResponse positionEdit(String id,String teacherId);

    ApiResponse changeTeacher(TeacherEditDto dto);

    ApiResponse deletedTeacherWithUserId(String id);

    ApiResponse changeRolesTeachers();

    ApiResponse getTeachersStatisticsForKafedraDashboard(String kafedraId);

    ApiResponse changeNameOfKafedra(ChangeKafedraNameDto dto);
    ApiResponse changeRoomOfKafedra(ChangeKafedraNameDto dto);
}
