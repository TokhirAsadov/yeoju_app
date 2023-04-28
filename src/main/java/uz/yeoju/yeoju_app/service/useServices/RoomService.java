package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.admin.Room;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.RoomDto;
import uz.yeoju.yeoju_app.repository.RoomRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.RoomImplService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService implements RoomImplService<RoomDto> {
    private final RoomRepository roomRepository;


    public ApiResponse getRoomsForSelect(){
        return new ApiResponse(true,"rooms",roomRepository.getRoomsForSelect());
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All rooms",
                roomRepository.findAll().stream()
                        .map(this::generateRoomDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> RoomDto generateRoomDto(Room room) {
        return new RoomDto(
                room.getId(),
                room.getName()
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return new ApiResponse(true,"Element by id",generateRoomDto(roomRepository.findById(id).get()));
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(RoomDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            if (!roomRepository.existsRoomByName(dto.getName())){
                roomRepository.save(generateRoom(dto));
                return new ApiResponse(true,"saveOrUpdate room successfully!..");
            }
            return new ApiResponse(false,"error, sorry already exist name of room");
        }
        else { //update
            if (!roomRepository.existsRoomByName(dto.getName())){
                Optional<Room> roomOptional = roomRepository.findById(dto.getId());
                if (roomOptional.isPresent()) {
                    Room room = roomOptional.get();
                    room.setName(dto.getName());
                    roomRepository.save(room);
                    return new ApiResponse(true,"update room successfully!..");
                }
                return new ApiResponse(false,"error, not fount room");
            }
            return new ApiResponse(false,"error, sorry already exist name of room");
        }
    }

    private Room generateRoom(RoomDto dto) {
        return new Room(
                dto.getId(),
                dto.getName()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        roomRepository.deleteById(id);
        return new ApiResponse(true,"Room deleted successfully!..");
    }
}
