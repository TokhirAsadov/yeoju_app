package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Faculty getFacultyByName(String name);

    Optional<Faculty> findFacultyByName(String name);

    boolean existsFacultyByName(String name);

    Optional<Faculty> findFacultyByShortName(String shortName);


    //todo----------------------------------------------
    @Query(value = "select f.id as value,f.shortName as label, f.name  from Faculty f \n",nativeQuery = true)
    List<UserForTeacherSaveItem> getFacultyForDekanatSaved();

    //  @Query(value = "select f.id as value,f.shortName as label, f.name  from Faculty f\n" +
    //            "left join Dekanat_Faculty DF on f.id = DF.faculties_id\n" +
    //            "where DF.faculties_id is null",nativeQuery = true)
    //    List<UserForTeacherSaveItem> getFacultyForDekanatSaved();
}
