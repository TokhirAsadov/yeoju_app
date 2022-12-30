package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.PhoneNumber;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentPhones;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.KafedraMudiriPhonesForRektorTeacherPage;

import java.util.List;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {
    PhoneNumber getPhoneNumberByAndPhoneNumber(String phoneNumber);
    boolean existsPhoneNumberByPhoneNumber(String phoneNumber);

    List<PhoneNumber> findPhoneNumbersByUserId(String user_id);


    @Query(value = "select id,phoneNumber,phoneType,hasTg,hasInstagram,hasFacebook from PhoneNumber p_n where p_n.user_id=:userId",nativeQuery = true)
    List<StudentPhones> getPhoneNumberUserId(@Param("userId") String userId);

    @Query(value = "select pn.phoneNumber from PhoneNumber pn join users u on pn.user_id = u.id join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id join Faculty F on F.id = g.faculty_id join Dekan_Faculty DF on F.id = DF.faculties_id\n" +
            "join Dekan D on DF.Dekan_id = D.id join users u2 on D.user_id = u2.id where u2.id = :id",nativeQuery = true)
    List<String> getPhoneNumberForSendSmsToAllByDekanId(@Param("id") String id);


    @Query(value = "select pn.phoneNumber from PhoneNumber pn join users u on pn.user_id = u.id join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id join Faculty F on F.id = g.faculty_id join Dekan_Faculty DF on F.id = DF.faculties_id\n" +
            "join Dekan D on DF.Dekan_id = D.id join users u2 on D.user_id = u2.id where u2.id = :id and g.level= :level",nativeQuery = true)
    List<String> getPhoneNumberForSendSmsToCourseByDekanId(@Param("id") String id,@Param("level") Integer level);

    @Query(value = "select pn.phoneNumber from PhoneNumber pn join users u on pn.user_id = u.id join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id join Faculty F on F.id = g.faculty_id join Dekan_Faculty DF on F.id = DF.faculties_id\n" +
            "join Dekan D on DF.Dekan_id = D.id join users u2 on D.user_id = u2.id where u2.id = :id and g.name= :group",nativeQuery = true)
    List<String> getPhoneNumberForSendSmsToGroupByDekanId(@Param("id") String id,@Param("group") String group);

    @Query(value = "select pn.phoneNumber from PhoneNumber pn\n" +
            "join users u on pn.user_id = u.id\n" +
            "join Student S on u.id = S.user_id\n" +
            "join groups g on g.id = S.group_id\n" +
            "join Faculty F on F.id = g.faculty_id\n" +
            "join Dekan_Faculty DF on F.id = DF.faculties_id\n" +
            "join Dekan D on DF.Dekan_id = D.id\n" +
            "join users u2 on D.user_id = u2.id\n" +
            "where u2.id = :id and pn.user_id= :userId",nativeQuery = true)
    List<String> getPhoneNumberForSendSmsToSingleStudentByDekanId(@Param("id") String id,@Param("userId") String userId);

    @Query(value = "select id,phoneType,phoneNumber from PhoneNumber pn where pn.user_id=:id",nativeQuery = true)
    List<KafedraMudiriPhonesForRektorTeacherPage> getKafedraMudiriPhonesForRektorTeacherPage(@Param("id") String id);
}
