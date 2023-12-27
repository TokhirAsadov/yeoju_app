package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.dekanat.queue.Queue;

public interface QueueRepository extends JpaRepository<Queue,String> {
}
