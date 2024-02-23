package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.payload.resDto.address.*;
import uz.yeoju.yeoju_app.payload.resDto.admin.AddressForUserUpdate;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;

import java.util.List;
import java.util.Optional;

public interface AddressUserRepository extends JpaRepository<AddressUser,String> {

    Optional<AddressUser> findAddressUserByUserId(String user_id);

    @Query(value = "select Top 1 au.id,au.country,au.region,au.area,au.address,au.isConstant from AddressUser au where (au.user_id=:userId and au.isConstant=1) order by au.createdAt desc",nativeQuery = true)
    StudentAddress getAddressByUserId(@Param("userId") String userId);

    @Query(value = "select Top 1 au.id,au.country,au.region,au.area,au.address,au.isConstant from AddressUser au where (user_id=:userId and isConstant=0) order by createdAt desc",nativeQuery = true)
    StudentAddress getAddressCurrentByUserId(@Param("userId") String userId);

    @Query(value = "select count(u.id) as count,G.gandername as gander from users u join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id where AU.region=:region group by G.gandername,AU.region",nativeQuery = true)
    List<GanderStatistics> getGandersStatistics(@Param("region") String region);


    @Query(value = "select count(u.id) as count,et.name as eduType from users u join Student s on u.id = s.user_id  join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id where AU.region=:region group by AU.region,et.name",nativeQuery = true)
    List<EduTypeStatistics> getEduTypeStatistics(@Param("region") String region);

    @Query(value = "select count(*) as count,region  from AddressUser group by region order by region",nativeQuery = true)
    List<MapStatistics> getMapStatistics();


    @Query(value = "select id,name from Dekanat order by name",nativeQuery = true)
    List<DekanatsForStatistics> getDekanatsForStatistics();

    @Query(value = "select id,name from Faculty order by name",nativeQuery = true)
    List<DekanatsForStatistics> getFacultiesForStatistics();

    @Query(value = "select f.id,f.shortName,f.name from Faculty f join Dekanat_Faculty DF on f.id = DF.faculties_id where DF.Dekanat_id=:dekanatId",nativeQuery = true)
    List<FacultiesForStatistics> getFacultiesByDekanatId(@Param("dekanatId") String dekanatId);

    @Query(value = "select :id as id",nativeQuery = true)
    DekanatStatisticsWithRegion getDekanatStatistics(@Param("id") String id);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u\n" +
            "    join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join Dekanat_Faculty df on g2.faculty_id=df.faculties_id join Dekanat d on df.Dekanat_id = d.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  df.Dekanat_id=:dekanatId group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getDekanatStatisticsByDekanatId(@Param("dekanatId") String dekanatId);

    @Query(value = "select count(u.id) as count,G.gandername as gander,AU.region from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id where  g2.faculty_id=:facultyId group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getDekanatStatisticsByFacultyId(@Param("facultyId") String id);

    @Query(value = "select count(u.id) as count,G.gandername as gander,AU.region from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.faculty_id=:facultyId and et.name=:eduType group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getDekanatStatisticsByFacultyAndEduType(@Param("facultyId") String facultyId, @Param("eduType") String eduType);

    @Query(value = "select count(u.id) as count,G.gandername as gander,AU.region from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  g2.faculty_id=:facultyId and et.name=:eduType and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getDekanatStatisticsByFacultyAndEduTypeAndEduLang(@Param("facultyId") String facultyId, @Param("eduType") String eduType, @Param("eduLang") String eduLang);



//    todo--------------------------------------------------------------------------------------=========================================
    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  et.name=:eduType group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduType(@Param("eduType") String eduType);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  et.name=:eduType and g2.level=:level group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndLevel(@Param("eduType") String eduType,@Param("level") Integer level);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id  join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  et.name=:eduType and g2.level=:level and g2.faculty_id=:facultyId group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndLevelAndFacultyId(@Param("eduType") String eduType,@Param("level") Integer level,@Param("facultyId") String facultyId);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id  join EducationType et on g2.educationType_id = et.id   join EducationLanguage el on g2.educationLanguage_id = el.id  join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  et.name=:eduType and g2.level=:level and g2.faculty_id=:facultyId and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndLevelAndFacultyIdAndEduLang(@Param("eduType") String eduType,@Param("level") Integer level,@Param("facultyId") String facultyId,@Param("eduLang") String eduLang);


    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u\n" +
            "    join Student s on u.id = s.user_id\n" +
            "    join groups g2 on s.group_id = g2.id\n" +
            "    join EducationType et on g2.educationType_id = et.id\n" +
            "    join EducationLanguage el on g2.educationLanguage_id = el.id\n" +
            "    join  AddressUser AU on u.id = AU.user_id\n" +
            "    join Gander G on G.id = u.gander_id\n" +
            "where et.name=:eduType and g2.level=:level and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndLevelAndEduLang( @Param("eduType") String eduType,@Param("level") Integer level, @Param("eduLang") String eduLang);
    @Query(value = "select count(u.id) as count,G.gandername as gander,AU.region from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.faculty_id=:facultyId and et.name=:eduType group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndFacultyId(@Param("eduType") String eduType,@Param("facultyId") String facultyId );

    @Query(value = "select count(u.id) as count,G.gandername as gander,AU.region from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  g2.faculty_id=:facultyId and et.name=:eduType and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndFacultyIdAndEduLang( @Param("eduType") String eduType,@Param("facultyId") String facultyId, @Param("eduLang") String eduLang);


    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationType et on g2.educationType_id = et.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where  et.name=:eduType and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduTypeAndEduLang( @Param("eduType") String eduType, @Param("eduLang") String eduLang);


    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.level=:level group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByLevel(@Param("level") Integer level);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.level=:level and g2.faculty_id=:facultyId group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByLevelAndFacultyId(@Param("level") Integer level,@Param("facultyId") String facultyId);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.level=:level and g2.faculty_id=:facultyId and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByLevelAndFacultyIdAndEduLang(@Param("level") Integer level,@Param("facultyId") String facultyId,@Param("eduLang") String eduLang);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.level=:level and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByLevelAndEduLang(@Param("level") Integer level,@Param("eduLang") String eduLang);

    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where g2.faculty_id=:facultyId and el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByFacultyIdAndEduLang(@Param("facultyId") String facultyId,@Param("eduLang") String eduLang);


    @Query(value = "select count(u.id) as count,G.gandername as gander, AU.region  from users u join Student s on u.id = s.user_id join groups g2 on s.group_id = g2.id join EducationLanguage el on g2.educationLanguage_id = el.id join  AddressUser AU on u.id = AU.user_id join Gander G on G.id = u.gander_id\n" +
            "where el.name=:eduLang group by G.gandername,AU.region",nativeQuery = true)
    List<DStatisticsByDekanatId> getStatisticsByEduLang(@Param("eduLang") String eduLang);

    @Query(value = "select id,region,district,address from AddressUser where user_id=:userId",nativeQuery = true)
    AddressForUserUpdate getUserAddressForUpdateByUserId(@Param("userId") String userId);
}
