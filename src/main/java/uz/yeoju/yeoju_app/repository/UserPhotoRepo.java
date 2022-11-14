package uz.yeoju.yeoju_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.yeoju.yeoju_app.entity.attachment.UserPhoto;
import uz.yeoju.yeoju_app.payload.resDto.attachment.UserPhotoRes;

import java.util.List;

public interface UserPhotoRepo extends JpaRepository<UserPhoto, String> {
    List<UserPhoto> findUserPhotosByUserId(String user_id);
    List<UserPhoto> findUserPhotosByActive(boolean active);
    UserPhoto findUserPhotoByActive(boolean active);
    boolean existsUserPhotoByUserId(String user_id);
    boolean existsUserPhotoByActive(boolean active);

    @Query(value = "select Top 1 a.id,a.originalName,a.size,a.contentType from UserPhoto u_p join Attachment a on u_p.attachment_id = a.id where u_p.user_id=:userId order by u_p.createdAt desc ",nativeQuery = true)
    UserPhotoRes getUserPhotoRes(@Param("userId") String userId);
}
