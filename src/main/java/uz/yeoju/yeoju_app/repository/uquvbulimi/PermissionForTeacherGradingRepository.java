package uz.yeoju.yeoju_app.repository.uquvbulimi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.enums.PPostStatus;
import uz.yeoju.yeoju_app.entity.uquvbulim.PermissionForTeacherGrading;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.PermissionForTeacherGradingResDto;

import java.util.List;


public interface PermissionForTeacherGradingRepository extends JpaRepository<PermissionForTeacherGrading,String> {


    Boolean existsPermissionForTeacherGradingByTeacherIdAndEducationYearIdAndSubjectIdAndGroupIdAndStatus(String teacher_id, String educationYear_id, String subject_id, String group_id, PPostStatus status);

    @Query(value = "select Top 1 u.id from users u join users_Role uR on u.id = uR.users_id join Role r on uR.roles_id = r.id where r.roleName='ROLE_MONITORING'",nativeQuery = true)
    String getUquvBulimBoshligi();



    @Query(value = "select id,educationYear_id as educationYearId,teacher_id as teacherId,group_id as groupId,subject_id as subjectId,status,deadline,createdAt,updatedAt from PermissionForTeacherGrading where educationYear_id=?1 and status=?2",nativeQuery = true)
    List<PermissionForTeacherGradingResDto> findAllByStatusAndEducationYearIdOrderByCreatedAt(String educationYearId, String status);

    @Query(value = "select id,educationYear_id as educationYearId,teacher_id as teacherId,group_id as groupId,subject_id as subjectId,status,deadline,createdAt,updatedAt from PermissionForTeacherGrading where status!='AT_PROCESS' order by updatedAt desc ",nativeQuery = true)
    List<PermissionForTeacherGradingResDto> getHistory();

    @Query(value = "select Top 1 id,educationYear_id as educationYearId,teacher_id as teacherId,group_id as groupId,subject_id as subjectId,status,deadline,createdAt,updatedAt from PermissionForTeacherGrading \n" +
            "      where status='CONFIRM' and educationYear_id=?1 and teacher_id=?2 and subject_id=?3 and group_id=?4 and getdate()<deadline order by updatedAt desc",nativeQuery = true)
    PermissionForTeacherGradingResDto getConfirmPermission(String educationYearId,String teacherId,String subjectId,String groupId);
}
