package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.dataOfLastActiveRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.uquvbulim.DataOfLastActive;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreateAssistant;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.uquvbulimi.DataOfLastActiveRepository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataOfLastActiveImplService implements DataOfLastActiveService {

    private final DataOfLastActiveRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all last active data",repository.findAll());
    }

    @Override
    public ApiResponse findByCreatorId(String id) {
        return new ApiResponse(true,"all last active data",repository.findById(id));
    }

    @Override
    public ApiResponse create(String passage) {
        repository.save(new DataOfLastActive(passage));
        return new ApiResponse(true,"saved successfully.");
    }

    @Override
    public ApiResponse getAssistants() {
        return new ApiResponse(true,"all assistants",repository.getAssistants());
    }

    @Override
    public ApiResponse createAssistant(CreateAssistant assistant) {
        Optional<User> optionalUser = userRepository.findById(assistant.getUserId());
        if (optionalUser.isPresent()){
            Optional<Role> optionalRole = roleRepository.findRoleByRoleName(assistant.getRoleName());
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                Set<Role> roles = user.getRoles();
                roles.add(optionalRole.get());
                user.setRoles(roles);
                userRepository.save(user);
                return new ApiResponse(true,"Assistant was added successful");
            }
            else {
                return new ApiResponse(false,"role was not found by name: "+assistant.getRoleName());
            }
        }
        else {
            return new ApiResponse(false,"user was not found by id: "+assistant.getUserId());
        }
    }

    @Override
    public ApiResponse deleteAssistant(String assistantId) {
        Optional<User> optionalUser = userRepository.findById(assistantId);
        if (optionalUser.isPresent()){
            Optional<Role> optionalRole = roleRepository.findRoleByRoleName("ROLE_MONITORING_ASSISTANT");
            if (optionalRole.isPresent()) {
                User user = optionalUser.get();
                Set<Role> roles = user.getRoles();
                roles.remove(optionalRole.get());
                user.setRoles(roles);
                userRepository.save(user);
                return new ApiResponse(true,"Assistant was deleted successful");
            }
            else {
                return new ApiResponse(false,"role was not found by name: ROLE_MONITORING_ASSISTANT");
            }
        }
        else {
            return new ApiResponse(false,"user was not found by id: "+assistantId);
        }
    }
}
