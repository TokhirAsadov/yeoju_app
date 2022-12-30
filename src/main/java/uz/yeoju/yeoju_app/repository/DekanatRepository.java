package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.resDto.dekan.ForDekanRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ForKafedraRoleSettings;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;

import java.util.List;

public interface DekanatRepository extends JpaRepository<Dekanat, String> {

    Boolean existsDekanatByName(String name);

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
}
