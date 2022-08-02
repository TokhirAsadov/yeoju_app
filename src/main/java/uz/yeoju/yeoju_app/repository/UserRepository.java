package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentData;
import uz.yeoju.yeoju_app.payload.resDto.search.SearchDto;
import uz.yeoju.yeoju_app.payload.resDto.user.UserResDto;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserByLoginOrEmailOrRFID(String login, String email, String RFID);
    User getUserByLogin(String login);
    User findUserByEmail(String email);
    User findUserByRFID(String RFID);

    boolean existsUserByLoginOrEmailOrRFID(String login, String email, String RFID);

    @Query(value = "select u.id,\n" +
            "       u.fullName,\n" +
            "       u.RFID,\n" +
            "       r.roleName\n" +
            "from users u\n" +
            "join users_Role ur on u.id = ur.users_id\n" +
            "join Role r  on r.id = ur.roles_id\n" +
            "where r.roleName = :roleName",nativeQuery = true)
    List<UserResDto> returnTeachers(@Param("roleName") String roleName);

    @Query(value = "select id,fullName,rfid from users where id=:userId",nativeQuery = true)
    List<UserResDto> findByUserIdAndRole(@Param("userId") String userId);

    @Query(value = "select count(*) from users\n" +
            "join users_Role uR on users.id = uR.users_id\n" +
            "join Role R2 on uR.roles_id = R2.id\n" +
            "where R2.id=:roleId",nativeQuery = true)
    Long countUsersByRoleId(@Param("roleId") String roleId);
    List<User> findUsersByRFID(String RFID);

    @Query(value = "select u.id as userId, u.fullName,u.login,u.passportNum as passport,r.roleName from users u\n" +
            "left join Student S on u.id = S.user_id\n" +
            "         join users_Role uR on u.id = uR.users_id\n" +
            "         join Role r on uR.roles_id = r.id\n" +
            "where login like :keyword or fullName like :keyword\n" +
            "or S.passportSerial like :keyword",nativeQuery = true)
    List<SearchDto> getUserForSearch(@Param("keyword") String keyword);

    User getUserByPassportNum(String passportNum);

    @Query(value = "select id,login,fullName,bornYear,citizenship,nationality,passportNum from users where id=:userId",nativeQuery = true)
    StudentData getStudentDataByUserId(@Param("userId") String userId);

}
