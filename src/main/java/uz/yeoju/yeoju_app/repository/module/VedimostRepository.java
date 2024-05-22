package uz.yeoju.yeoju_app.repository.module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetVedimostOfKafedra;

import java.util.Set;

public interface VedimostRepository extends JpaRepository<Vedimost, String> {


    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Teacher t on t.user_id=v.teacher_id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where t.kafedra_id=?1 and v.educationYear_id=?2 order by u.fullName",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostOfKafedra(String kafedraId,String educationYearId);

    @Query(value = "select \n" +
            "    v.id,\n" +
            "    v.level,\n" +
            "    v.deadline,\n" +
            "    v.timeClose,\n" +
            "    v.condition,\n" +
            "    u.fullName as teacher,\n" +
            "    l.name as lesson,\n" +
            "    g.name as groupName\n" +
            "    from Vedimost v\n" +
            "    join users u on v.teacher_id = u.id\n" +
            "    join Lesson l on l.id=v.lesson_id\n" +
            "    join groups g on v.group_id = g.id\n" +
            "where v.teacher_id=?1 and v.educationYear_id=?2 order by v.createdAt",nativeQuery = true)
    Set<GetVedimostOfKafedra> getVedimostByTeacherId(String teacherId,String educationYearId);
}
