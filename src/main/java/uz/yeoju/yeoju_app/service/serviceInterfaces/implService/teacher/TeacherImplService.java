package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.Teacher;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.payload.teacher.TeacherSaveDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.useServices.LessonService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeacherImplService implements TeacherService{

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KafedraRepository kafedraRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private LessonService lessonService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all teachers",teacherRepository.findAll());
    }

    @Override
    public ApiResponse save(TeacherSaveDto dto) {
        System.out.println(dto+" <-------- ");
        Optional<User> userOptional = userRepository.findById(dto.getUserId());
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(dto.getKafedraId());
        System.out.println(dto.toString()+" ------------------------------- [[[[[[[[[[[[[[[[[[");
        Set<Lesson> lessons = new HashSet<>();
        for (LessonDto lessonDto : dto.getLessonDtos()) {
            Optional<Lesson> lessonOptional = lessonRepository.findById(lessonDto.getId());
            lessonOptional.ifPresent(lessons::add);
        }
        System.out.println(lessons.toString()+" *************************  lessons  *************************** ");
        Optional<Position> positionOptional = positionRepository.findById(Long.valueOf(dto.getPositionId()));
        System.out.println(positionOptional.get().toString()+" ***********************  positionOptional  ***************************** ");
        User user = userOptional.get();
        user.setEnabled(true);
        user.setAccountNonLocked(true);
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
        userRepository.save(user);
        System.out.println(user.toString()+" ,,,,,,,,,,,,,,,,,,,,,,,,,,,user,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        Teacher teacher = new Teacher();
        teacher.setKafedra(kafedraOptional.get());
        teacher.setUser(userOptional.get());
        teacher.setSubjects(lessons);
        teacher.setWorkerStatus(dto.getWorkerStatus());
        System.out.println(teacher.toString()+" ,,,,,,,,,,,,,,,,,,,,,,,,,teacher 1,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        teacherRepository.save(teacher);
        System.out.println(teacher.toString()+" ,,,,,,,,,,,,,,,,,,,,,,,,,,,,teacher 2,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        return new ApiResponse(true,"saved teacher",teacher);
    }

    @Override
    public ApiResponse changeTeacherPosition(String userId, Long positionId) {
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Position> positionOptional = positionRepository.findById(positionId);
        if (userOptional.isPresent()){
            if (positionOptional.isPresent()){
                User user = userOptional.get();
                user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
                userRepository.save(user);
                return new ApiResponse(true,"changed position");
            }
            else {
                return new ApiResponse(false,"not fount position");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }


}
