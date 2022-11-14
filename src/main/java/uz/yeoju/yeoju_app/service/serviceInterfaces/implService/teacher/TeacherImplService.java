package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.Teacher;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
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
        Set<Lesson> lessons = dto.getLessonDtos().stream().map(i -> lessonService.generateLesson(i)).collect(Collectors.toSet());
        Optional<Position> positionOptional = positionRepository.findById(Long.valueOf(dto.getPositionId()));
        User user = userOptional.get();
        user.setPositions(new HashSet<>(Collections.singletonList(positionOptional.get())));
        userRepository.save(user);
        Teacher teacher = new Teacher();
        teacher.setKafedra(kafedraOptional.get());
        teacher.setUser(userOptional.get());
        teacher.setSubjects(lessons);
        teacher.setWorkerStatus(dto.getWorkerStatus());
        teacherRepository.save(teacher);
        return new ApiResponse(true,"saved teacher",teacher);
    }


}
