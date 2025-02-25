package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.dekanat.Diploma;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaRestDto;

import java.util.Set;

public interface DiplomaRepository extends JpaRepository<Diploma, String> {
    Boolean existsDiplomaByLogin(String login);
    Boolean existsDiplomaByDiplomId(String diplomId);
    Boolean existsDiplomaByDiplomRaqami(String diplomRaqami);

    Boolean existsDiplomaByLoginAndId(String login,String id);
    Boolean existsDiplomaByDiplomIdAndId(String diplomId, String id);
    Boolean existsDiplomaByDiplomRaqamiAndId(String diplomRaqami,String id);

    @Query(value = "select\n" +
            "    u.id as userId,\n" +
            "    u.fullName as fullName,\n" +
            "    u.login as login,\n" +
            "    d.id as diplomaId,\n" +
            "    d.diplomId as ID,\n" +
            "    d.diplomSeriya,\n" +
            "    d.diplomRaqami,\n" +
            "    d.fName,\n" +
            "    d.mName,\n" +
            "    d.lName,\n" +
            "    d.fNameEng,\n" +
            "    d.lNameEng,\n" +
            "    d.yonalishQisqa,\n" +
            "    d.yonalishUzb,\n" +
            "    d.yonalishEng,\n" +
            "    d.maktab,\n" +
            "    d.bachelorOf,\n" +
            "    d.imtiyoz,\n" +
            "    d.imtiyozEng\n" +
            "\n" +
            "    from groups g\n" +
            "join Student s on g.id = s.group_id\n" +
            "join users u on s.user_id = u.id\n" +
            "left join  Diploma d on u.login=d.login\n" +
            "where g.id=?1\n",nativeQuery = true)
    Set<DiplomaRestDto> findStudentsWithDiploma(String groupId);
}
