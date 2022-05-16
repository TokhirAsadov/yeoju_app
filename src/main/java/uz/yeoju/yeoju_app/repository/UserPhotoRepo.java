package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.yeoju.yeoju_app.entity.attachment.UserPhoto;

import java.util.List;

public interface UserPhotoRepo extends JpaRepository<UserPhoto, String> {
    List<UserPhoto> findUserPhotosByUserId(String user_id);
    List<UserPhoto> findUserPhotosByActive(boolean active);
    UserPhoto findUserPhotoByActive(boolean active);
    boolean existsUserPhotoByUserId(String user_id);
    boolean existsUserPhotoByActive(boolean active);
}
