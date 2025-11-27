package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.resDto.dekan.FacultiesResDto;
import uz.yeoju.yeoju_app.payload.resDto.dekan.ForDekanRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.dekan.GetFacultiesShortNameAndDekanEducationTypes;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.DekanatDataForDekan;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.EduTypeByDekanatId;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dekanat.FacultyOfDekanat;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ForKafedraRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.GetKafedraMudiriData;
import uz.yeoju.yeoju_app.payload.resDto.rektor.kafedraTeachers.monthly.*;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForDekanSave;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForSectionSave;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface DekanatRepository extends JpaRepository<Dekanat, String> {

    Boolean existsDekanatByName(String name);

    Dekanat findDekanatByName(String name);

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


    @Query(value = "select id from users where id=:id",nativeQuery = true)
    UserForDekanSave getUserForDekanSave(@Param("id") String id);

    @Query(value = "select id from users where id=:id", nativeQuery = true)
    UserForSectionSave getUserForSectionSave(@Param("id") String id);


    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyGroupForDean31 getDateForDean31(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyGroupForDean30 getDateForDean30(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyGroupForDean29 getDateForDean29(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select :id as id,  dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "       + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME)) as date",nativeQuery = true)
    MonthlyGroupForDean28 getDateForDean28(@Param("date") Date date, @Param("id") String id);

    @Query(value = "select et.id,et.name from EducationType et join Dekanat D on et.id = D.eduType_id where D.id=?1",nativeQuery = true)
    EduTypeByDekanatId getEduTypeByDekanatId(String id);

    @Query(value = "select Top 1 u.id, u.firstName, u.lastName, u.middleName, u.fullName from users u join Dekanat d on u.id=d.owner_id where d.id=?1", nativeQuery = true)
    GetKafedraMudiriData getDekanatOwner(String dekanatId);

    @Query(value = "select id as value, name as label from  Dekanat order by name",nativeQuery = true)
    List<FacultiesResDto> getDekanatsForSelect();

    @Query(value = "select u.id as value,u.fullName as label from Staff s join users u on u.id=s.user_id join users_Position uP on u.id = uP.users_id join Position P on uP.positions_id = P.id where s.dekanat_id=:dekanatId order by  P.degree,u.fullName",nativeQuery = true)
    List<FacultiesResDto> getStaffsForTableByDekanatId(@Param("dekanatId") String dekanatId);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    1 as rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "                  as date\n" +
            "from Staff s\n" +
            "         join users u on s.user_id = u.id\n" +
            "where s.dekanat_id =:id and u.id in :staffsIds",nativeQuery = true)
    List<GetTeachersForDekan31> getDateForTable31(@Param("id") String id, @Param("date") Date date,@Param("staffsIds") Set<String> staffsIds);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    1 as rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "                  as date\n" +
            "from Staff s\n" +
            "         join users u on s.user_id = u.id\n" +
            "where s.dekanat_id =:id and u.id in :staffsIds",nativeQuery = true)
    List<GetTeachersForDekan30> getDateForTable30(@Param("id") String id, @Param("date") Date date,@Param("staffsIds") Set<String> staffsIds);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    1 as rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "                  as date\n" +
            "from Staff s\n" +
            "         join users u on s.user_id = u.id\n" +
            "where s.dekanat_id =:id and u.id in :staffsIds",nativeQuery = true)
    List<GetTeachersForDekan29> getDateForTable29(@Param("id") String id, @Param("date") Date date,@Param("staffsIds") Set<String> staffsIds);

    @Query(value = "select\n" +
            "    u.fullName,\n" +
            "    u.id,\n" +
            "    u.passportNum as passport,\n" +
            "    u.login,\n" +
            "    u.RFID,\n" +
            "    u.email,\n" +
            "    1 as rate,\n" +
            "    dateadd(d,0, CAST(CAST(YEAR(:date) AS VARCHAR(4))\n" +
            "        + '/' + CAST(MONTH(:date) AS VARCHAR(2)) + '/01' AS DATETIME))\n" +
            "                  as date\n" +
            "from Staff s\n" +
            "         join users u on s.user_id = u.id\n" +
            "where s.dekanat_id =:id and u.id in :staffsIds",nativeQuery = true)
    List<GetTeachersForDekan28> getDateForTable28(@Param("id") String id, @Param("date") Date date,@Param("staffsIds") Set<String> staffsIds);

}
