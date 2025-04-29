package uz.yeoju.yeoju_app.controller.testServer;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.external.UserPatchUpdater;
import uz.yeoju.yeoju_app.payload.resDto.StudentDataForExternalApi;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/external")
public class ExternalController {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public ExternalController(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/students")
    HttpEntity<?> sendStudentsData() {
        Set<StudentDataForExternalApi> data = groupRepository.sendStudentsData();
        return ResponseEntity.ok(data);
    }

    @PatchMapping("/updatePatchStudent")
    HttpEntity<?> updatePatchStudent(@RequestBody UserPatchUpdater updater){
        Optional<User> optionalUser = userRepository.findUserByLogin(updater.login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (updater.firstName != null) {
                user.setFirstName(updater.firstName);
            }
            if (updater.lastName != null) {
                user.setLastName(updater.lastName);
            }
            if (updater.middleName != null) {
                user.setMiddleName(updater.middleName);
            }
            if (updater.password != null) {
                user.setPassword(updater.password);
            }
            userRepository.save(user);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(404).body(false);
        }
    }
}
