package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.PositionDto;
import uz.yeoju.yeoju_app.repository.PositionRepository;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.PositionImplService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionService implements PositionImplService<PositionDto> {
    public final PositionRepository positionRepository;
    public final RoleRepository roleRepository;
    public final UserRepository userRepository;



    public ApiResponse build(){
        List<User> users = userRepository.findAll();
        for (User user : users) {
            Position position = positionRepository.getPositionByUserPositionName(user.getRoles().stream().findFirst().get().getRoleName());
            Set<Position> positions = new HashSet<>();
            positions.add(position);
            user.setPositions(positions);
            userRepository.save(user);
        }

        return new ApiResponse(true,"Saved", positionRepository.findAll());
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all faculty",
                positionRepository.findAll().stream().map(this::generatePositionDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(Long id) {
        return positionRepository
                .findById(id)
                .map(position -> new ApiResponse(true, "Fount position by id", position))
                .orElseGet(() -> new ApiResponse(false, "Not fount position by id"));
    }

    @Override
    public ApiResponse getById(Long id) {
        Position position = positionRepository.getById(id);
        return new ApiResponse(true, "Fount position by id", position);
    }

    @Override
    public ApiResponse saveOrUpdate(PositionDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(PositionDto dto){
        Optional<Position> optional = positionRepository.findById(dto.getId());
        if (optional.isPresent()){
            Position position = optional.get();
            Position positionName = positionRepository.getPositionByUserPositionName(dto.getUserPositionName());
            if (positionName !=null) {
                if (
                        Objects.equals(positionName.getId(), position.getId())
                                ||
                                !positionRepository.existsPositionByUserPositionName(dto.getUserPositionName())
                ) {
                    position.setUserPositionName(dto.getUserPositionName());
                    positionRepository.save(position);
                    return new ApiResponse(true, "position updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved position! Please, enter other position userPositionName!.."
                    );
                }
            }
            else {
                if (!positionRepository.existsPositionByUserPositionName(dto.getUserPositionName())){
                    position.setUserPositionName(dto.getUserPositionName());
                    positionRepository.save(position);
                    return new ApiResponse(true,"position updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved position! Please, enter other position userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount position"
            );
        }
    }

    public ApiResponse save(PositionDto dto){
        if (!positionRepository.existsPositionByUserPositionName(dto.getUserPositionName())){
            Position position = generatePosition(dto);
            positionRepository.saveAndFlush(position);
            return new ApiResponse(true,"new position saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved faculty! Please, enter other faculty userPositionName!"
            );
        }
    }

    public Position generatePosition(PositionDto dto) {
        return new Position(dto.getUserPositionName());
    }
    public PositionDto generatePositionDto(Position position) {
        return new PositionDto(position.getId(), position.getUserPositionName());
    }


    @Override
    public ApiResponse deleteById(Long id) {
        if (positionRepository.findById(id).isPresent()) {
            positionRepository.deleteById(id);
            return new ApiResponse(true,"position deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount position!");
        }
    }
}
