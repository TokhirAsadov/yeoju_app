package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.resDto.dekan.ForDekanRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.dekan.GetFacultiesShortNameAndDekanEducationTypes;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentDataForEditedDekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.DekanatDataForDekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.FacultyOfDekanat;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ForKafedraRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;
import java.util.Set;

public interface DekanatRepository extends JpaRepository<Dekanat, String> {

    Boolean existsDekanatByName(String name);


    @Query(value = "select d.id,d.name from Dekanat d join Dekan D2 on d.id = D2.dekanat_id where D2.user_id=:id",nativeQuery = true)
    DekanatDataForDekan getDekanatDataForDekan(@Param("id") String id);


    //todo--------------------------------------------------------------
    @Query(value = "select F.shortName from Dekan d\n" +
            "join Dekanat D1 on d.dekanat_id=D1.id\n" +
            "join Dekanat_Faculty DF on D1.id = DF.Dekanat_id\n" +
            "join Faculty F on DF.faculties_id = F.id\n" +
            "where d.user_id=:id",nativeQuery = true)
    Set<String> getFacultiesShortnamesWithDekanId(@Param("id") String id);

    @Query(value = "select :id as id",nativeQuery = true)
    GetFacultiesShortNameAndDekanEducationTypes getFacultiesShortNameAndDekanEducationTypes(@Param("id") String id);



    @Query(value = "select u.id as value,u.fullName as label from users u\n" +
            "join users_Role uR on u.id = uR.users_id\n" +
            "join Role R2 on R2.id = uR.roles_id\n" +
            "left join Dekan D on u.id = D.user_id\n" +
            "where R2.roleName='ROLE_DEKAN' and D.user_id is null",nativeQuery = true)
    List<UserForTeacherSaveItem> getDekansForSaved();

    @Query(value = "select D2.id as value,D2.name as label from Dekan d\n" +
            "right join Dekanat D2 on D2.id = d.dekanat_id\n" +
            "where d.dekanat_id is null",nativeQuery = true)
    List<UserForTeacherSaveItem> getDekanatsForDekanSaved();

    @Query(value = "select 1 as id",nativeQuery = true)
    ForDekanRoleSettings getForDekanRoleSettings();

    @Query(value = "select 1 as id",nativeQuery = true)
    ForKafedraRoleSettings getForKafedraRoleSettings();

    @Query(value = "select f.id,f.name,f.shortName from Faculty f join Dekanat_Faculty DF on f.id = DF.faculties_id where DF.Dekanat_id=:dekanatId",nativeQuery = true)
    List<FacultyOfDekanat> getFacultiesOfDekanatByDekanatId(@Param("dekanatId") String dekanatId);
}
