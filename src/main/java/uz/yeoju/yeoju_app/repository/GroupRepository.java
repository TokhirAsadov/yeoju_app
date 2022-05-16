package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.Group;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group,String> {
   Group findGroupByName(String name);
   boolean existsGroupByName(String name);
   List<Group> findGroupsByLevel(Integer level);
   List<Group> findGroupsByFacultyId(String faculty_id);
}
