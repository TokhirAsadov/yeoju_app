package uz.yeoju.yeoju_app.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.teacher.Teacher;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.TeacherRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.test.entity.EnableTeacherControlTest;
import uz.yeoju.yeoju_app.test.payload.CreatorEnableTeacherControlTestDto;
import uz.yeoju.yeoju_app.test.repository.EnableTeacherControlTestRepository;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class EnableTeacherControlTestImplService implements EnableTeacherControlTestService{
    private final EnableTeacherControlTestRepository repository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    @Override
    public ApiResponse findAllEnableTeacherByKafedraId(String kafkaId) {
        return new ApiResponse(true,"All teachers' list",repository.getEnableTeachersByKafedraId(kafkaId));
    }

    @Override
    public ApiResponse getAllTeacherLessonsByTeacherId(String teacherId) {
        return new ApiResponse(true,"Teacher's lessons",repository.getTeacherLessonsByTeacherId(teacherId));
    }

    @Override
    public ApiResponse createAndUpdateEnableTeacher(CreatorEnableTeacherControlTestDto dto) {
        if (dto.id==null || dto.id.isEmpty()){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    private ApiResponse save(CreatorEnableTeacherControlTestDto dto) {
        if (userRepository.existsById(dto.teacherId)) {
            if (teacherRepository.existsTeacherByUserId(dto.teacherId)) {
                Set<Lesson> lessons = new HashSet<>();
                dto.lessonsIds.forEach(lessonId -> {
                    if (lessonRepository.existsById(lessonId)) {
                        lessons.add(lessonRepository.findById(lessonId).get());
                    }
                    else {
                        lessons.clear();
                        throw new UserNotFoundException("Lesson not found by id: "+lessonId);
                    }
                });
                User teacher = userRepository.getById(dto.teacherId);
                try {
                    repository.save(new EnableTeacherControlTest(teacher,lessons));
                }
                catch (Exception e){
                    throw new UserNotFoundException("Error.. Data is not saved!?");
                }
                return new ApiResponse(true,"Successfully saved teacher");
            }
            else {
                throw new UserNotFoundException("Teacher not found by user id: "+dto.teacherId+".Firstly, you should create teacher. Show TEACHERS page");
            }
        }
        else {
            throw new UserNotFoundException("User not found by id: "+dto.teacherId);
        }
    }

    private ApiResponse update(CreatorEnableTeacherControlTestDto dto) {
        if (repository.existsById(dto.id)) {
            if (userRepository.existsById(dto.teacherId)) {
                if (teacherRepository.existsTeacherByUserId(dto.teacherId)) {
                    Set<Lesson> lessons = new HashSet<>();
                    dto.lessonsIds.forEach(lessonId -> {
                        if (lessonRepository.existsById(lessonId)) {
                            lessons.add(lessonRepository.findById(lessonId).get());
                        }
                        else {
                            lessons.clear();
                            throw new UserNotFoundException("Lesson not found by id: "+lessonId);
                        }
                    });
                    User teacher = userRepository.getById(dto.teacherId);
                    try {
                        EnableTeacherControlTest enableTeacherControlTest= repository.getById(dto.id);
                        enableTeacherControlTest.setTeacher(teacher);
                        enableTeacherControlTest.setLessons(lessons);
                        repository.save(enableTeacherControlTest);
                    }
                    catch (Exception e){
                        throw new UserNotFoundException("Error.. Data is not updated!?");
                    }
                    return new ApiResponse(true,"Successfully updated teacher");
                }
                else {
                    throw new UserNotFoundException("Teacher not found by user id: "+dto.teacherId+".Firstly, you should create teacher. Show TEACHERS page");
                }
            }
            else {
                throw new UserNotFoundException("User not found by id: "+dto.teacherId);
            }
        }
        else {
            throw new UserNotFoundException("Data not found by id: "+dto.id);
        }
    }

}
