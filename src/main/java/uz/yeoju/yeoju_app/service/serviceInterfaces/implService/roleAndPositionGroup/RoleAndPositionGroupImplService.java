package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.roleAndPositionGroup;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.RoleAndPositionGroupRepository;

@Service
@RequiredArgsConstructor
public class RoleAndPositionGroupImplService implements RoleAndPositionGroupService{
    private final RoleAndPositionGroupRepository repository;


    @Override
    public ApiResponse findAll() {
        return null;
    }
}
