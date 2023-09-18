package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.LessonDto;
import uz.yeoju.yeoju_app.payload.LessonNewDto;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.LessonImplService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService implements LessonImplService<LessonDto> {
    public final LessonRepository lessonRepository;
    public final KafedraService kafedraService;


    public ApiResponse multiSaved(List<String> subjectNames){
        subjectNames.forEach(item -> lessonRepository.save(new Lesson(item)));
        return new ApiResponse(true,"saved subjects",lessonRepository.findAll().stream().filter(item -> subjectNames.contains(item.getName())).collect(Collectors.toSet()));
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all lessons",
                lessonRepository.findAll().stream().map(this::generateLessonDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return lessonRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount lesson by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount lesson by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Lesson lesson = lessonRepository.getById(id);
        return new ApiResponse(true, "Fount lesson by id", lesson);
    }

    @Deprecated
    public ApiResponse saveOrUpdate(LessonDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }


    public ApiResponse getAllLessonByKaferaOwner(User user) {
        return new ApiResponse(true, "all lesson by kafedra owner id",lessonRepository.getAllLessonByKaferaOwnerId(user.getId()));
    }
    public ApiResponse getAllLessonByKaferaOwnerId(String ownerId) {
        return new ApiResponse(true, "all lesson by kafedra owner id",lessonRepository.getAllLessonByKaferaOwnerId(ownerId));
    }
    public ApiResponse getAllLessonByKaferaId(String kafedraId) {
        return new ApiResponse(true, "all lesson by kafedra owner id",lessonRepository.getSubjectsByKafedraId(kafedraId));
    }

    public ApiResponse saveOrUpdate(User user,LessonNewDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse save(LessonNewDto dto){
        if (!lessonRepository.existsLessonByName(dto.getName())){
            Lesson lesson = generateLesson(dto);
            lessonRepository.saveAndFlush(lesson);
            return new ApiResponse(true,"new lesson saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved lesson! Please, enter other lesson "+dto.getName()+"!.."
            );
        }
    }
    public ApiResponse update(LessonNewDto dto){
        Optional<Lesson> optional = lessonRepository.findById(dto.getId());
        if (optional.isPresent()){
            Lesson lesson = optional.get();
            Lesson lessonByName = lessonRepository.getLessonByName(dto.getName());
            if (lessonByName !=null) {
                if (
                        Objects.equals(lessonByName.getId(), lesson.getId())
                                ||
                                !lessonRepository.existsLessonByName(dto.getName())
                ) {
                    lesson.setName(dto.getName());
//                    lesson.setKafedra(kafedraService.generateKafedra(dto.getKafedraDto()));
//                    lesson.setFaculty(positionService.generatePosition(dto.getFacultyDto()));
                    lesson.setActive(dto.isActive());
                    lessonRepository.save(lesson);
                    return new ApiResponse(true, "lesson updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! not update lesson! Please, enter other lesson "+dto.getName()+"!.."
                    );
                }
            }
            else {
                if (!lessonRepository.existsLessonByName(dto.getName())){
                    lesson.setName(dto.getName());
//                    lesson.setKafedra(kafedraService.generateKafedra(dto.getKafedraDto()));
//                    lesson.setFaculty(positionService.generatePosition(dto.getFacultyDto()));
                    lesson.setActive(dto.isActive());
                    lessonRepository.save(lesson);
                    return new ApiResponse(true,"lesson updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! not update lesson! Please, enter other lesson "+dto.getName()+"!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount lesson"
            );
        }
    }

    public ApiResponse update(LessonDto dto){
        Optional<Lesson> optional = lessonRepository.findById(dto.getId());
        if (optional.isPresent()){
            Lesson lesson = optional.get();
            Lesson lessonByName = lessonRepository.getLessonByName(dto.getName());
            if (lessonByName !=null) {
                if (
                        Objects.equals(lessonByName.getId(), lesson.getId())
                                ||
                                lessonRepository.existsLessonByName(dto.getName())
                ) {
                    lesson.setName(dto.getName());
//                    lesson.setKafedra(kafedraService.generateKafedra(dto.getKafedraDto()));
//                    lesson.setFaculty(positionService.generatePosition(dto.getFacultyDto()));
                    lesson.setActive(dto.isActive());
                    lessonRepository.save(lesson);
                    return new ApiResponse(true, "lesson updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved lesson! Please, enter other lesson userPositionName!.."
                    );
                }
            }
            else {
                if (lessonRepository.existsLessonByName(dto.getName())){
                    lesson.setName(dto.getName());
//                    lesson.setKafedra(kafedraService.generateKafedra(dto.getKafedraDto()));
//                    lesson.setFaculty(positionService.generatePosition(dto.getFacultyDto()));
                    lesson.setActive(dto.isActive());
                    lessonRepository.save(lesson);
                    return new ApiResponse(true,"lesson updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved lesson! Please, enter other lesson userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount lesson"
            );
        }
    }

    public ApiResponse save(LessonDto dto){
        if (!lessonRepository.existsLessonByName(dto.getName())){
            Lesson lesson = generateLesson(dto);
            lessonRepository.saveAndFlush(lesson);
            return new ApiResponse(true,"new lesson saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved lesson! Please, enter other lesson userPositionName!"
            );
        }
    }

    public Lesson generateLesson(LessonDto dto) {
        return new Lesson(
                dto.getId(),
                dto.getName(),
//                kafedraService.generateKafedra(dto.getKafedraDto()),
//                positionService.generatePosition(dto.getFacultyDto()),
                dto.isActive()
        );
    }
    public Lesson generateLesson(LessonNewDto dto) {
        return new Lesson(
                dto.getId(),
                dto.getName(),
                (Kafedra)kafedraService.findById(dto.getKafedraId()).getObj(),
//                positionService.generatePosition(dto.getFacultyDto()),
                dto.isActive()
        );
    }
    public LessonDto generateLessonDto(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getName(),
//                kafedraService.generateKafedraDto(lesson.getKafedra()),
//                positionService.generateFacultyDto(lesson.getFaculty()),
                lesson.isActive()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (lessonRepository.findById(id).isPresent()) {
            lessonRepository.deleteById(id);
            return new ApiResponse(true,"lesson deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount lesson!");
        }
    }

    public ApiResponse checkLessonNameAlreadyExists(String subjectName) {
        boolean existsLessonByName = lessonRepository.existsLessonByName(subjectName);
        if(existsLessonByName){
            return new ApiResponse(true,subjectName+" - subject already exists");
        }
        else {
            return new ApiResponse(false,subjectName+" - subject doesn't exist");
        }
    }
}
