package uz.yeoju.yeoju_app.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.test.entity.EnableTeacherControlTest;
import uz.yeoju.yeoju_app.test.payload.restDto.GetEnableTeachersByKafedraIdRestDto;

import java.util.Set;

public interface EnableTeacherControlTestRepository extends JpaRepository<EnableTeacherControlTest,String> {

    @Query(value = "select e.id,u.id as teacherId,u.fullName as teacherName from EnableTeacherControlTest e join users u on e.teacher_id = u.id join Teacher t on u.id = t.user_id where t.kafedra_id=?1 order by e.createdAt",nativeQuery = true)
    Set<GetEnableTeachersByKafedraIdRestDto> getEnableTeachersByKafedraId(String kafedraId);
}
