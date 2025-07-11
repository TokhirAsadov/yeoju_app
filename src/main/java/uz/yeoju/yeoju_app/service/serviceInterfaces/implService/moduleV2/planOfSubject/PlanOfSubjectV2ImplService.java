package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.planOfSubject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.moduleV2.PlanOfSubjectV2;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.moduleV2.CourseGroupDetails;
import uz.yeoju.yeoju_app.payload.moduleV2.CoursesGroupDetails;
import uz.yeoju.yeoju_app.payload.moduleV2.CreatePlanOfStudentV2;
import uz.yeoju.yeoju_app.payload.resDto.module.GetPlansForTeacherSciences;
import uz.yeoju.yeoju_app.payload.resDto.moduleV2.GetExistsPlansV2;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.CourseRepository;
import uz.yeoju.yeoju_app.repository.moduleV2.PlanOfSubjectV2Repository;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanOfSubjectV2ImplService implements PlanOfSubjectV2Service {
    private final PlanOfSubjectV2Repository planRepository;
    private final EducationYearRepository educationYearRepository;
    private final EducationLanRepository educationLanRepository;
    private final EducationTypeRepository educationTypeRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponseTwoObj getPlansForTeacherSciences(String teacherId, String educationId, String subjectId, Integer groupLevel) {
        Set<GetExistsPlansV2> exists = planRepository.getExistsPlans(teacherId, educationId, subjectId, groupLevel);

        Set<GetPlansForTeacherSciences> plansForTeacherSciences = planRepository.getPlansForTeacherSciences(teacherId, educationId, subjectId, groupLevel);
        return new ApiResponseTwoObj(true,"plans for teacher",plansForTeacherSciences,exists);
    }

    @Override
    public ApiResponseTwoObj getAllPlansForTeacherSciences(String teacherId, String educationId) {
        Set<GetExistsPlansV2> exists = planRepository.getAllExistsPlans(teacherId, educationId);

        Set<GetPlansForTeacherSciences> plansForTeacherSciences = planRepository.getAllPlansForTeacherSciences(teacherId, educationId);
        return new ApiResponseTwoObj(true,"all plans for teacher",plansForTeacherSciences,exists);
    }

    @Override
    @Transactional
    public ApiResponse createPlan(User user, CreatePlanOfStudentV2 dto) {
        EducationLanguage educationLanguageByName = educationLanRepository.getEducationLanguageByName(dto.getEduLang());
        EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
        Lesson lesson = lessonRepository.getById(dto.getSubjectId());
        EducationYear educationYear = educationYearRepository.getById(dto.getEducationYearId());
        Optional<User> userOptional = userRepository.findById(dto.teacherId);
//        List<Group> groupList = groupRepository.findAllById(dto.getGroupsIds());

        if (!planRepository.existsByUserIdAndEducationYearIdAndSubjectIdAndLevelAndEducationLanguageIdAndEducationTypeId(
                dto.getTeacherId(),
                educationYear.getId(),
                lesson.getId(),
                dto.getLevel(),
                educationLanguageByName.getId(),
                educationTypeByName.getId()
        )) {
            PlanOfSubjectV2 plan = new PlanOfSubjectV2(
                    userOptional.get(),
                    educationYear,
                    lesson,
//                    new HashSet<>(groupList),
                    educationTypeByName,
                    educationLanguageByName,
                    dto.getLevel()
            );

            PlanOfSubjectV2 save = planRepository.save(plan);

//            Course course = new Course(lesson.getName(), save);
//            courseRepository.save(course);

            return new ApiResponse(true,"create plan successfully");
        }
        throw new UserNotFoundException("plan already exists");

    }


    @Override
    public ApiResponse getExistPlans(String teacherId, String educationId, String subjectId, Integer groupLevel) {
        Set<GetExistsPlansV2> exists = planRepository.getExistsPlans(teacherId, educationId, subjectId, groupLevel);

        return new ApiResponse(true,"exists plan successfully",exists);
    }


    @Override
    public ApiResponse getTeacherWIthSubjectForPlan(String id,String educationYearId) {
        return new ApiResponse(true,"teacher with subjects",planRepository.getTeacherWIthSubjectForPlan(id,educationYearId));
    }

    @Override
    public ApiResponse getPlansByKafedraId(String id, String kafedraId) {
        return new ApiResponse(true,"plans by kafedra id",planRepository.getPlansByKafedraId(id));
    }

    @Override
    public ApiResponse getAllDataOfPlanById(String planId) {
        String json = planRepository.getCourseGroupDetails(planId);
        System.out.println("JSON: " + json);
        try {
            return new ApiResponse(true,"All details",objectMapper.readValue(json, CoursesGroupDetails.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parse xatosi", e);
        }
    }

    @Override
    public ApiResponse getCourseDetailsByCourseId(String planId) {
        String json = planRepository.getCourseDetailsByCourseId(planId);
        System.out.println("JSON: " + json);
        try {
            return new ApiResponse(true,"All details",objectMapper.readValue(json, CourseGroupDetails.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON parse xatosi", e);
        }
    }

    @Override
    public ApiResponse getTeacherSubjects(String teacherId, String educationYearId) {
        return new ApiResponse(true, "teacher subjects",
                planRepository.getTeacherLessonByTeacherIdAndEducationYearId(teacherId, educationYearId));
    }

    @Override
    public ApiResponse getPlansBySubjectId(String lessonId, String educationYearId) {
        return new ApiResponse(true, "plans by subject id",
                planRepository.getPlansBySubjectId(lessonId, educationYearId));
    }
}
