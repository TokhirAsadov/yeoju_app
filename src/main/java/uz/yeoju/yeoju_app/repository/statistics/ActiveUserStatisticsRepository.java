package uz.yeoju.yeoju_app.repository.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.statistics.ActiveUserStatistics;

public interface ActiveUserStatisticsRepository extends JpaRepository<ActiveUserStatistics,String> {

    @Query(value = "SELECT Top 1 *\n" +
            "FROM ActiveUserStatistics\n" +
            "WHERE user_id = ?1 \n" +
            "   and CAST(createdAt AS DATE) = CAST(GETDATE() AS DATE)",nativeQuery = true)
    ActiveUserStatistics getActiveUserByUserIdAndToday(String userId);


    @Query(value = "SELECT CASE\n" +
            "           WHEN EXISTS (SELECT 1\n" +
            "                        FROM ActiveUserStatistics\n" +
            "                        WHERE user_id=?1 and CAST(createdAt AS DATE) = CAST(GETDATE() AS DATE))\n" +
            "               THEN 1\n" +
            "           ELSE 0\n" +
            "           END AS isDataCreatedToday",nativeQuery = true)
    Integer checkingDataForToday(String userId);
}
