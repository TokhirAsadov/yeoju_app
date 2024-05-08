package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.educationYear;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.educationYear.EducationYearDto;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.educationYear.WeekOfEducationYearRepository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducationYearImplService implements EducationYearService{
    private final EducationYearRepository educationYearRepository;
    private final WeekOfEducationYearRepository weekOfEducationYearRepository;

    @Override
    public ApiResponse getGroupsByFacultyId(String id) {
        return new ApiResponse(true,"groups by faculty id",educationYearRepository.getGroupsByFacultyId(id));
    }
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all education year",educationYearRepository.findAll());
    }

    @Override
    public ApiResponse findById(String id) {
        return new ApiResponse(true, "education of year by -> ",educationYearRepository.findById(id).get()
        );
    }

    @Override
    public ApiResponse saveOrUpdate(EducationYearDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    @Override
    public ApiResponse deletedById(String id) {
        Optional<EducationYear> yearOptional = educationYearRepository.findById(id);
        if (yearOptional.isPresent()){
            educationYearRepository.deleteById(id);
            return new ApiResponse(true,"deleted successfully.");
        }
        else {
            return new ApiResponse(false,"error.. not fount education year.");
        }
    }

    @Override
    public ApiResponse educationYearsForSelected() {
        return new ApiResponse(true,"education years for selected",educationYearRepository.getEducationYearsForSelected());
    }

    @Override
    public ApiResponse educationYearsForCRUD() {
        return new ApiResponse(true,"education years for CRUD",educationYearRepository.getEducationYearsForCRUD());
    }

    @Override
    public ApiResponse getSortNumberOfWeek(String educationYearId) {
        return new ApiResponse(true,"sort number of week",educationYearRepository.getSortNumberOfWeek(educationYearId));
    }

    private ApiResponse update(EducationYearDto dto) {
        Optional<EducationYear> yearOptional = educationYearRepository.findById(dto.getId());
        if (yearOptional.isPresent()){
            EducationYear educationYear = yearOptional.get();
            Optional<EducationYear> yearOptional1 = educationYearRepository.findEducationYearByName(dto.getName());
            if (yearOptional1.isPresent()){
                EducationYear educationYear1 = yearOptional1.get();
                if (Objects.equals(educationYear1.getId(), educationYear.getId())){
                    educationYear.setName(dto.getName());
                    educationYear.setStart(dto.getStart());
                    educationYear.setEnd(dto.getEnd());
                    if (dto.getWeeksIds()!=null && !dto.getWeeksIds().isEmpty()) {
                        educationYear.setWeeks(
                                new HashSet<>(weekOfEducationYearRepository.findAllById(dto.getWeeksIds()))
                        );
                    }
                    educationYearRepository.save(educationYear);
                    return new ApiResponse(true,"updated successfully.");
                }
                else {
                    return new ApiResponse(false,"Error.. Already exists "+dto.getName()+" name. Enter other name.");
                }
            }
            else {
                educationYear.setName(dto.getName());
                educationYear.setStart(dto.getStart());
                educationYear.setEnd(dto.getEnd());
                if (dto.getWeeksIds()!=null && !dto.getWeeksIds().isEmpty()) {
                    educationYear.setWeeks(
                            new HashSet<>(weekOfEducationYearRepository.findAllById(dto.getWeeksIds()))
                    );
                }
                educationYearRepository.save(educationYear);
                return new ApiResponse(true,"updated successfully.");
            }
        }
        else {
            return new ApiResponse(false,"not fount education year by id -> "+dto.getId());
        }
    }

    private ApiResponse save(EducationYearDto dto) {
        Optional<EducationYear> educationYearOptional = educationYearRepository.findEducationYearByName(dto.getName());
        if (!educationYearOptional.isPresent()) {
            EducationYear educationYear = new EducationYear();
            educationYear.setName(dto.getName());
            educationYear.setStart(dto.getStart());
            educationYear.setEnd(dto.getEnd());
            if (dto.getWeeksIds()!=null && !dto.getWeeksIds().isEmpty()) {
                educationYear.setWeeks(
                        new HashSet<>(weekOfEducationYearRepository.findAllById(dto.getWeeksIds()))
                );
            }

            educationYearRepository.save(educationYear);
            return new ApiResponse(true,"saved successfully.");
        }
        else {
            return new ApiResponse(false,"Error.. Already exists "+dto.getName()+" name. Enter other name.");
        }

    }
}
