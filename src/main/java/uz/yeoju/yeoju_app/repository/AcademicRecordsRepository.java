package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.dekanat.AcademicRecords;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.GetAllRecordsOfGroup;

import java.util.List;

public interface AcademicRecordsRepository extends JpaRepository<AcademicRecords, Long> {
    List<AcademicRecords> findAllByQaydRaqami(String qaydRaqami);


    @Query(value = "select u.id as userId, u.login from Student s\n" +
            "join users u on s.user_id = u.id\n" +
            "where s.group_id=?1;",nativeQuery = true)
    List<GetAllRecordsOfGroup> getAllRecordsByGroupId(String groupId);
}
