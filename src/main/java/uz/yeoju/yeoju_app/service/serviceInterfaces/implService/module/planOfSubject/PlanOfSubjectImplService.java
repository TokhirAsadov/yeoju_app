package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.planOfSubject;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.module.PlanOfSubject;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.module.CreatePlanOfStudent;
import uz.yeoju.yeoju_app.payload.resDto.module.GetExistsPlans;
import uz.yeoju.yeoju_app.payload.resDto.module.GetPlansForTeacherSciences;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.PlanOfSubjectRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlanOfSubjectImplService implements PlanOfSubjectService{
    private final PlanOfSubjectRepository planRepository;
    private final EducationYearRepository educationYearRepository;
    private final EducationLanRepository educationLanRepository;
    private final EducationTypeRepository educationTypeRepository;
    private final GroupRepository groupRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponseTwoObj getPlansForTeacherSciences(String teacherId, String educationId, String subjectId, Integer groupLevel) {
        Set<GetExistsPlans> exists = planRepository.getExistsPlans(teacherId, educationId, subjectId, groupLevel);

        Set<GetPlansForTeacherSciences> plansForTeacherSciences = planRepository.getPlansForTeacherSciences(teacherId, educationId, subjectId, groupLevel);
        return new ApiResponseTwoObj(true,"plans for teacher",plansForTeacherSciences,exists);
    }

    @Override
    public ApiResponseTwoObj getAllPlansForTeacherSciences(String teacherId, String educationId) {
        Set<GetExistsPlans> exists = planRepository.getAllExistsPlans(teacherId, educationId);

        Set<GetPlansForTeacherSciences> plansForTeacherSciences = planRepository.getAllPlansForTeacherSciences(teacherId, educationId);
        return new ApiResponseTwoObj(true,"all plans for teacher",plansForTeacherSciences,exists);
    }

    @Override
    public ApiResponse createPlan(User user, CreatePlanOfStudent dto) {

        System.out.println(dto.toString()+"-------------------------- 000000000000000000000--------------------------");

        EducationLanguage educationLanguageByName = educationLanRepository.getEducationLanguageByName(dto.getEduLang());
        EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
        Lesson lesson = lessonRepository.getById(dto.getSubjectId());
        EducationYear educationYear = educationYearRepository.getById(dto.getEducationYearId());
        List<Group> groupList = groupRepository.findAllById(dto.getGroupsIds());

        Set<Group> ids = new HashSet<>(groupList);

        System.out.println(ids+" -----------------set------------------------");


        PlanOfSubject plan = new PlanOfSubject(
                user,
                educationYear,
                lesson,
                ids,
                educationTypeByName,
                educationLanguageByName,
                dto.getLevel()
        );

        planRepository.save(plan);


        return new ApiResponse(true,"create plan successfully");
    }
    @Override
    public ApiResponse createPlanByKafedraMudiri(User user, CreatePlanOfStudent dto) {

        System.out.println(dto.toString()+"-------------------------- 000000000000000000000--------------------------");

        EducationLanguage educationLanguageByName = educationLanRepository.getEducationLanguageByName(dto.getEduLang());
        EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
        Lesson lesson = lessonRepository.getById(dto.getSubjectId());
        EducationYear educationYear = educationYearRepository.getById(dto.getEducationYearId());
        List<Group> groupList = groupRepository.findAllById(dto.getGroupsIds());
        Optional<User> optionalUser = userRepository.findById(dto.getTeacherId());



        Set<Group> ids = new HashSet<>(groupList);

        System.out.println(ids+" -----------------set------------------------");


        PlanOfSubject plan = new PlanOfSubject(
                optionalUser.get(),
                educationYear,
                lesson,
                ids,
                educationTypeByName,
                educationLanguageByName,
                dto.getLevel()
        );

        planRepository.save(plan);


        return new ApiResponse(true,"create plan successfully");
    }

    @Override
    public ApiResponse getExistPlans(String teacherId, String educationId, String subjectId, Integer groupLevel) {
        Set<GetExistsPlans> exists = planRepository.getExistsPlans(teacherId, educationId, subjectId, groupLevel);

        return new ApiResponse(true,"exists plan successfully",exists);
    }

    @Override
    public ApiResponse updatePlan(User user, CreatePlanOfStudent dto) {

        Optional<PlanOfSubject> optionalPlanOfSubject = planRepository.findById(dto.getId());
        if (optionalPlanOfSubject.isPresent()) {
            PlanOfSubject plan = optionalPlanOfSubject.get();
            List<Group> groupList = groupRepository.findAllById(dto.getGroupsIds());
            Set<Group> ids = new HashSet<>(groupList);
            System.out.println(ids+" -----------------set------------------------");
            plan.setGroups(ids);
//            EducationLanguage educationLanguageByName = educationLanRepository.getEducationLanguageByName(dto.getEduLang());
//            EducationType educationTypeByName = educationTypeRepository.getEducationTypeByName(dto.getEduType());
//            Lesson lesson = lessonRepository.getById(dto.getSubjectId());
//            EducationYear educationYear = educationYearRepository.getById(dto.getEducationId());
//            PlanOfSubject plan = new PlanOfSubject(
//                    user,
//                    educationYear,
//                    lesson,
//                    ids,
//                    educationTypeByName,
//                    educationLanguageByName,
//                    dto.getLevel()
//            );

            planRepository.save(plan);
            return new ApiResponse(true,"plan is updated successfully");
        }

        else {
            return new ApiResponse(false,"not found plan by id: "+dto.getId());
        }
    }

    @Override
    public ApiResponse getTeacherWIthSubjectForPlan(String id,String educationYearId) {
        return new ApiResponse(true,"teacher with subjects",planRepository.getTeacherWIthSubjectForPlan(id,educationYearId));
    }
}
