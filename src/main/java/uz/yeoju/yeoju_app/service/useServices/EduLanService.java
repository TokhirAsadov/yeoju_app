package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.EducationLanguage;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.EducationLanguageDto;
import uz.yeoju.yeoju_app.repository.EducationLanRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.EduLanImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EduLanService implements EduLanImplService<EducationLanguageDto> {
    public final EducationLanRepository educationLanRepository;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"List of all education languages", educationLanRepository.findAll());
    }

    @Override
    public ApiResponse findById(UUID id) {
        return educationLanRepository
                .findById(id)
                .map(educationLanguage -> new ApiResponse(true, "Fount education language by id", educationLanguage))
                .orElseGet(() -> new ApiResponse(false, "Not fount education language by id"));
    }

    @Override
    public ApiResponse getById(UUID id) {
        EducationLanguage educationLanguage = educationLanRepository.getById(id);
        return new ApiResponse(true, "Fount educationLanguage by id", educationLanguage);
    }

    @Override
    public ApiResponse saveOrUpdate(EducationLanguageDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(EducationLanguageDto dto){
        Optional<EducationLanguage> optional = educationLanRepository.findById(dto.getId());
        if (optional.isPresent()){
            EducationLanguage language = optional.get();
            EducationLanguage languageByName = educationLanRepository.getEducationLanguageByName(dto.getName());
            if (
                    Objects.equals(languageByName.getId(), language.getId())
                    ||
                            educationLanRepository.existsEducationLanguageByName(dto.getName())
            ){
                language.setName(dto.getName());
                educationLanRepository.save(language);
                return new ApiResponse(true,"language updated successfully!..");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! nor saved language! Please, enter other language name!.."
                );
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount faculty"
            );
        }
    }

    public ApiResponse save(EducationLanguageDto dto){
        if (!educationLanRepository.existsEducationLanguageByName(dto.getName())){
            EducationLanguage language = generateEduLan(dto);
            educationLanRepository.saveAndFlush(language);
            return new ApiResponse(true,"new language saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved language! Please, enter other language name!"
            );
        }
    }

    public EducationLanguage generateEduLan(EducationLanguageDto dto) {
        return new EducationLanguage(dto.getName());
    }
    public EducationLanguageDto generateEduLanDto(EducationLanguage language) {
        return new EducationLanguageDto(language.getId(),language.getName());
    }


    @Override
    public ApiResponse deleteById(UUID id) {
        if (educationLanRepository.findById(id).isPresent()) {
            educationLanRepository.deleteById(id);
            return new ApiResponse(true,"language deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount education language!");
        }
    }
}