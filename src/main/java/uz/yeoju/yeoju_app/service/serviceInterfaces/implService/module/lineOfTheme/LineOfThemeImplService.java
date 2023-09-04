package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.lineOfTheme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.module.LineOfPlan;
import uz.yeoju.yeoju_app.entity.module.PlanOfSubject;
import uz.yeoju.yeoju_app.entity.module.ThemeOfSubject;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.CreateThemeAndLine;
import uz.yeoju.yeoju_app.payload.module.UpdateThemeAndLine;
import uz.yeoju.yeoju_app.payload.resDto.module.GetListOfPlan;
import uz.yeoju.yeoju_app.payload.resDto.module.GetOldThemesForCreateLine;
import uz.yeoju.yeoju_app.repository.module.LineOfPlanRepository;
import uz.yeoju.yeoju_app.repository.module.PlanOfSubjectRepository;
import uz.yeoju.yeoju_app.repository.module.ThemeOfSubjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LineOfThemeImplService implements LineOfThemeService{
    private final LineOfPlanRepository lineRepository;
    private final ThemeOfSubjectRepository themeRepository;
    private final PlanOfSubjectRepository planRepository;


    @Override
    public ApiResponse getAllQueueByPlanId(String planId) {
        List<GetListOfPlan> queue = lineRepository.getLines(planId);
        return new ApiResponse(true,"queues",queue);
    }

    @Override
    public ApiResponse createLineAndTheme(CreateThemeAndLine dto) {
        if (dto.getIsCreate()) {
            Optional<PlanOfSubject> planOptional = planRepository.findById(dto.getPlanId());
            if (planOptional.isPresent()) {
                PlanOfSubject plan = planOptional.get();
                ThemeOfSubject theme = new ThemeOfSubject();
                theme.setLesson(plan.getSubject());
                theme.setName(dto.getNewTheme());
                themeRepository.saveAndFlush(theme);

                LineOfPlan line = new LineOfPlan();
                line.setPlanOfSubject(plan);
                line.setQueue(dto.getQueue());
                line.setTheme(theme);
                lineRepository.save(line);
                return new ApiResponse(true,"line and theme created successfully");
            }
            else {
                return new ApiResponse(false,"not found plan by id: "+dto.getPlanId());
            }
        }
        else {
            Optional<ThemeOfSubject> themeOptional = themeRepository.findById(dto.getOldThemeId());
            if (themeOptional.isPresent()) {
                Optional<PlanOfSubject> planOptional = planRepository.findById(dto.getPlanId());
                if (planOptional.isPresent()) {
                    PlanOfSubject plan = planOptional.get();
                    ThemeOfSubject theme = themeOptional.get();
                    LineOfPlan line = new LineOfPlan();
                    line.setQueue(dto.getQueue());
                    line.setPlanOfSubject(plan);
                    line.setTheme(theme);
                    lineRepository.save(line);
                    return new ApiResponse(true, "line created successfully");
                }
                else {
                    return new ApiResponse(false,"not found plan by id: "+dto.getPlanId());
                }
            }
            else {
                return new ApiResponse(false,"not found theme by id: "+dto.getOldThemeId());
            }
        }
    }

    @Override
    public ApiResponse updateLineAndTheme(UpdateThemeAndLine dto) {
        if (dto.getIsUpdate()){ // true
            Optional<ThemeOfSubject> optionalThemeOfSubject = themeRepository.findById(dto.getThemeId());
            if (optionalThemeOfSubject.isPresent()){
                ThemeOfSubject theme = optionalThemeOfSubject.get();
                theme.setName(dto.getThemeName());
                themeRepository.save(theme);
                return new ApiResponse(true,"Theme was updated successfully");
            }
            else {
                return new ApiResponse(false,"not found theme by id : "+dto.getThemeId());
            }
        }
        else { // false
            Optional<LineOfPlan> optionalLineOfPlan = lineRepository.findById(dto.getLineId());
            if (optionalLineOfPlan.isPresent()) {
                LineOfPlan line = optionalLineOfPlan.get();
                Optional<ThemeOfSubject> optionalThemeOfSubject = themeRepository.findById(dto.getOldThemeId());
                if (optionalThemeOfSubject.isPresent()) {
                    ThemeOfSubject oldTheme = optionalThemeOfSubject.get();
                    line.setTheme(oldTheme);
                    lineRepository.save(line);
                    return new ApiResponse(true,"theme was changed successfully");
                }
                else {
                    return new ApiResponse(false,"not found old theme by id : "+dto.getOldThemeId());
                }
            }
            else {
                return new ApiResponse(false,"not found line by id : "+dto.getLineId());
            }
        }
    }

    @Override
    public ApiResponse getOldThemesForCreateLine(String ownerId, String subjectId) {
        Set<GetOldThemesForCreateLine> oldThemes = themeRepository.getOldThemesForCreateLine(ownerId, subjectId);
        return new ApiResponse(true,"old themes",oldThemes);
    }

    @Override
    public Boolean isMultiUsedTheme(String themeId) {
        return lineRepository.countUsedOfTheme(themeId)!=1;
    }


}
