package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.yeoju.yeoju_app.entity.dekanat.queue.Queue;
import uz.yeoju.yeoju_app.entity.dekanat.queue.QueueStatusEnum;
import uz.yeoju.yeoju_app.payload.resDto.dekan.queue.GetQueueRestDto;

import java.util.Set;

public interface QueueRepository extends JpaRepository<Queue,String> {

//    Set<Queue> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);
    Set<Queue> findAllByCreatedByOrderByCreatedAtDesc(String createdBy);


    Set<Queue> findAllByStatusOrderByCalledAt(QueueStatusEnum status);

    @Query(value = "select dbo.existQueueForToday(?1)",nativeQuery = true)
    Boolean existQueueForToday(String studentId);

    @Query(value = "select dbo.GetMaxQueueOfToday()",nativeQuery = true)
    Integer getMaxQueueOfToday();

    @Query(value = "select * from dbo.getAllQueuesOfTodayForDean()",nativeQuery = true)
    Set<GetQueueRestDto> getAllQueuesOfTodayForDean();
}
