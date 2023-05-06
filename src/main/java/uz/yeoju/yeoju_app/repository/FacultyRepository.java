package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultiesResDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.RolesAndPositionsForDekanatCRUD;
import uz.yeoju.yeoju_app.payload.resDto.faculty.DirectionOfFaculty;
import uz.yeoju.yeoju_app.payload.resDto.student.FacultyStatistic;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Faculty getFacultyByName(String name);

    Optional<Faculty> findFacultyByName(String name);

    boolean existsFacultyByName(String name);

    Optional<Faculty> findFacultyByShortName(String shortName);


    //todo----------------------------------------------
    @Query(value = "select f.id as value,f.shortName as label, f.name  from Faculty f \n",nativeQuery = true)
    List<UserForTeacherSaveItem> getFacultyForDekanatSaved();


    @Query(value = "select 1 as id",nativeQuery = true)
    RolesAndPositionsForDekanatCRUD getRolesAndPositionsForDekanatCRUD();
    @Query(value = "select f.id as value, f.shortName as label from Faculty f join Dekanat_Faculty DF on f.id = DF.faculties_id\n" +
            "    join Dekanat D on D.id = DF.Dekanat_id join Dekan D2 on D.id = D2.dekanat_id\n" +
            "   join EducationType et on D2.educationType_id = et.id where et.name=:educationName",nativeQuery = true)
    List<FacultiesResDto> getFacultiesForSelect(@Param("educationName") String educationName);

    @Query(value = "select g.id as value, g.name as label from groups g join EducationType et on g.educationType_id = et.id where g.faculty_id=:facultyId and et.name=:eduTypeName order by g.name desc",nativeQuery = true)
    List<FacultiesResDto> getGroupsForSelect(@Param("facultyId") String facultyId, @Param("eduTypeName") String eduTypeName);

    @Query(value = "select f.id,f.name,f.shortName,s.nameEn as school from Faculty f join School s on s.code=f.schoolCode order by s.nameEn",nativeQuery = true)
    List<DirectionOfFaculty> getDirectionsOfFaculty();

    //  @Query(value = "select f.id as value,f.shortName as label, f.name  from Faculty f\n" +
    //            "left join Dekanat_Faculty DF on f.id = DF.faculties_id\n" +
    //            "where DF.faculties_id is null",nativeQuery = true)
    //    List<UserForTeacherSaveItem> getFacultyForDekanatSaved();
}
