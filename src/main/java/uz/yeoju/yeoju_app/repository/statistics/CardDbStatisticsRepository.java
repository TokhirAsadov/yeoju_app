package uz.yeoju.yeoju_app.repository.statistics;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.timetableDB.CardDbStatistics;

public interface CardDbStatisticsRepository extends JpaRepository<CardDbStatistics,String> {
}
