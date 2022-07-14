package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.EducationForm;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.EducationFormDto;
import uz.yeoju.yeoju_app.repository.EducationFormRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.EducationTypeImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationFormService implements EducationTypeImplService<EducationFormDto> {
    public final EducationFormRepository educationFormRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all education forms",
                educationFormRepository.findAll().stream().map(this::generateEduFormDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return educationFormRepository
                .findById(id)
                .map(educationType -> new ApiResponse(true, "Fount education form by id", generateEduFormDto(educationType) ))
                .orElseGet(() -> new ApiResponse(false, "Not fount education form by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        EducationForm educationForm = educationFormRepository.getById(id);
        return new ApiResponse(true, "Fount education form by id", educationForm);
    }

    @Override
    public ApiResponse saveOrUpdate(EducationFormDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(EducationFormDto dto){
        Optional<EducationForm> optional = educationFormRepository.findById(dto.getId());
        if (optional.isPresent()){
            EducationForm form = optional.get();
            EducationForm formByName = educationFormRepository.getEducationFormByName(dto.getName());
            if (formByName!=null) {
                if (
                        Objects.equals(formByName.getId(), form.getId())
                                ||
                                !educationFormRepository.existsEducationFormByName(dto.getName())
                ) {
                    form.setName(dto.getName());
                    educationFormRepository.save(form);
                    return new ApiResponse(true, "form updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved form! Please, enter other form userPositionName!.."
                    );
                }
            }
            else {
                if (!educationFormRepository.existsEducationFormByName(dto.getName())) {
                    form.setName(dto.getName());
                    educationFormRepository.save(form);
                    return new ApiResponse(true, "form updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved form! Please, enter other form userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount faculty"
            );
        }
    }

    public ApiResponse save(EducationFormDto dto){
        if (!educationFormRepository.existsEducationFormByName(dto.getName())){
            EducationForm form = generateEduForm(dto);
            educationFormRepository.saveAndFlush(form);
            return new ApiResponse(true,"new form saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved form! Please, enter other form userPositionName!"
            );
        }
    }

    public EducationForm generateEduForm(EducationFormDto dto) {
        return new EducationForm(dto.getName());
    }
    public EducationFormDto generateEduFormDto(EducationForm form) {
        return new EducationFormDto(form.getId(), form.getName());
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (educationFormRepository.findById(id).isPresent()) {
            educationFormRepository.deleteById(id);
            return new ApiResponse(true,"education form deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount education form!");
        }
    }
}
