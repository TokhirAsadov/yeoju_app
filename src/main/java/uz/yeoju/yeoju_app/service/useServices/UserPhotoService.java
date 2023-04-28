package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.attachment.UserPhoto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserPhotoDto;
import uz.yeoju.yeoju_app.payload.UserPhotoSaveDto;
import uz.yeoju.yeoju_app.repository.UserPhotoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.UserPhotoImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserPhotoService implements UserPhotoImplService<UserPhotoDto> {
    public final UserPhotoRepo userPhotoRepo;
    public final UserService userService;
    public final UserRepository userRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all user photos",
                userPhotoRepo.findAll().stream().map(this::generateUserPhotoDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return userPhotoRepo
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount user photo by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not user photo sheet by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        UserPhoto userPhoto = userPhotoRepo.getById(id);
        return new ApiResponse(true, "Fount user photo by id", userPhoto);
    }

    @Override
    public ApiResponse saveOrUpdate(UserPhotoDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(UserPhotoDto dto){
        Optional<UserPhoto> optional = userPhotoRepo.findById(dto.getId());
        if (optional.isPresent()){
            UserPhoto userPhoto = optional.get();
            UserPhoto userPhotoByActive = userPhotoRepo.findUserPhotoByActive(true);
            if (userPhotoByActive !=null) {
                if (Objects.equals(userPhotoByActive.getId(), userPhoto.getId())) {
                    userPhoto.setUser(userService.generateUser(dto.getUserDto()));
                    userPhoto.setAttachment(dto.getAttachment());
                    userPhoto.setActive(dto.isActive());
                    userPhotoRepo.save(userPhoto);
                    return new ApiResponse(true, "user photo updated successfully!..");
                }
                else {
                    return new ApiResponse(true, "Error... You have user photo an active...");
                }
            }
            else {
                userPhoto.setUser(userService.generateUser(dto.getUserDto()));
                userPhoto.setAttachment(dto.getAttachment());
                userPhoto.setActive(dto.isActive());
                userPhotoRepo.save(userPhoto);
                return new ApiResponse(true, "user photo updated successfully!..");
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount user photo"
            );
        }
    }

    public ApiResponse save(UserPhotoDto dto){
        if (!userPhotoRepo.existsUserPhotoByUserId(dto.getUserDto().getId())){
            UserPhoto userPhoto = generateUserPhoto(dto);
            userPhotoRepo.saveAndFlush(userPhoto);
            return new ApiResponse(true,"new user photo saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate user photo! You have an active user photo...!"
            );
        }
    }

    public ApiResponse saving(UserPhotoSaveDto dto){
        if (!userPhotoRepo.existsUserPhotoByUserId(dto.getUserId())){
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            UserPhoto userPhoto = new UserPhoto();
            userPhoto.setUser(userOptional.get());
            userPhoto.setActive(dto.isActive());
            userPhoto.setAttachment(dto.getAttachment());
            userPhotoRepo.saveAndFlush(userPhoto);
            return new ApiResponse(true,"new user photo saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not saveOrUpdate user photo! You have an active user photo...!"
            );
        }
    }

    public UserPhoto generateUserPhoto(UserPhotoDto dto) {
        return new UserPhoto(
                userService.generateUser(dto.getUserDto()),
                dto.getAttachment(),
                dto.isActive()
        );
    }
    public UserPhotoDto generateUserPhotoDto(UserPhoto photo) {
        return new UserPhotoDto(
                photo.getId(),
                userService.generateUserDto(photo.getUser()),
                photo.getAttachment(),
                photo.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (userPhotoRepo.findById(id).isPresent()) {
            userPhotoRepo.deleteById(id);
            return new ApiResponse(true,"user photo deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount user photo!");
        }
    }


    @Override
    public ApiResponse findUserPhotosByUserId(String user_id) {
        return new ApiResponse(
                true,
                "List of all user photos by User_id",
                userPhotoRepo.findUserPhotosByUserId(user_id)
                        .stream()
                        .map(this::generateUserPhotoDto)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public ApiResponse findUserPhotosByActive(boolean active) {
        return new ApiResponse(
                true,
                "List of all user photos by active = "+active,
                userPhotoRepo.findUserPhotosByActive(active)
                        .stream()
                        .map(this::generateUserPhotoDto)
                        .collect(Collectors.toList())
        );
    }
}
