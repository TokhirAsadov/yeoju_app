package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentGroupField;
import uz.yeoju.yeoju_app.payload.resDto.group.GroupForStudent;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,String> {
   Group findGroupByName(String name);
   boolean existsGroupByName(String name);
   List<Group> findGroupsByLevel(Integer level);
   List<Group> findGroupsByFacultyId(String faculty_id);

   @Query(value = "select g.id,g.level,g.name,F.name as facultyName,et.name as educationTypeName from Student s\n" +
           "join groups g on g.id = s.group_id\n" +
           "join Faculty F on F.id = g.faculty_id\n" +
           "join EducationType et on g.educationType_id = et.id\n" +
           "where s.user_id =:userId\n" +
           "group by  g.id, g.level, g.name, F.name, et.name",nativeQuery = true)
   StudentGroupField getGroupFieldByUserId(@Param("userId") String userId);

   @Query(value = "select g.id,g.name from groups g join Student S on g.id = S.group_id where S.user_id=:id",nativeQuery = true)
   GroupForStudent getGroupNameByUserId(@Param("id") String id);
}
