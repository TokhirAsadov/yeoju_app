package uz.yeoju.yeoju_app.repository.uquvbulimi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.uquvbulim.DataOfLastActive;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.DataAssistants;

import java.util.List;
import java.util.Set;

public interface DataOfLastActiveRepository extends JpaRepository<DataOfLastActive,String> {

    List<DataOfLastActive> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);


    @Query(value = "select u.id,u.fullName,u.firstName,u.lastName,u.middleName from users u join users_Role ur on u.id = ur.users_id\n" +
            "join Role r on ur.roles_id = r.id\n" +
            "where r.roleName='ROLE_MONITORING_ASSISTANT'",nativeQuery = true)
    Set<DataAssistants> getAssistants();
}
