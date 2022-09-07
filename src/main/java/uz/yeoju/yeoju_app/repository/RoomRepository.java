package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.admin.Room;

public interface RoomRepository extends JpaRepository<Room,String> {

    boolean existsRoomByName(String name);

}
