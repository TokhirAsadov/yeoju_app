package uz.yeoju.yeoju_app.repository.kafedra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.PositionEdit;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraMudiriForRektorTeacherPage;
import uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics.UserForRoomStatistics;

import java.util.List;
import java.util.Optional;

public interface KafedraMudirRepository extends JpaRepository<KafedraMudiri, String> {

    Optional<KafedraMudiri> findKafedraMudiriByKafedraId(String kafedra_id);

    @Query(value = "select u.id,u.fullName,u.email from KafedraMudiri km\n" +
            "join Kafedra k on km.kafedra_id = k.id\n" +
            "join users u on km.user_id = u.id\n" +
            "where kafedra_id=:id",nativeQuery = true)
    KafedraMudiriForRektorTeacherPage getKafedraMudiriForRektorTeacherPage(@Param("id") String id);

    Optional<KafedraMudiri> findKafedraMudiriByUserId(String user_id);

    @Query(value = "select :id as id,:teacherId as teacherId",nativeQuery = true)
    PositionEdit getPositionEdit(@Param("id") String id,@Param("teacherId") String teacherId);

    @Query(value = "select u.id,u.fullName,u.RFID,u.passportNum as passport from users u join Teacher T on u.id = T.user_id where T.kafedra_id=:kafedraId",nativeQuery = true)
    List<UserForRoomStatistics> getTeachersStatisticsForKafedraDashboard(@Param("kafedraId") String kafedraId);
}
