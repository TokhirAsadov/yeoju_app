package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Section;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.SectionDto;
import uz.yeoju.yeoju_app.repository.SectionRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectionService implements FacultyImplService<SectionDto> {
    public final SectionRepository sectionRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all section",
                sectionRepository.findAll().stream().map(this::generateSectionDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        Optional<Section> optional = sectionRepository.findById(id);
        return sectionRepository
                .findById(id)
                .map(section -> new ApiResponse(true, "Fount section by id", section))
                .orElseGet(() -> new ApiResponse(false, "Not fount section by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Section section = sectionRepository.getById(id);
        return new ApiResponse(true, "Fount section by id", section);
    }
    @Override
    public ApiResponse saveOrUpdate(SectionDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }
    public ApiResponse save(SectionDto dto){
        if (!sectionRepository.existsSectionByName(dto.getName())){
            Section section = generateSection(dto);
            section.setId(null);
            sectionRepository.save(section);
            return new ApiResponse(true,"new section saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved section! Please, enter other section name!"
            );
        }
    }

    public Section generateSection(SectionDto dto) {
        return new Section(dto.getId(),dto.getName());
    }
    public SectionDto generateSectionDto(Section section) {
        if (section!=null)
            return new SectionDto(section.getId(), section.getName());
        else return null;
    }

    public ApiResponse update(SectionDto dto){
        Optional<Section> optional = sectionRepository.findById(dto.getId());
        if (optional.isPresent()){
            Section section = optional.get();
            Section sectionByName = sectionRepository.getSectionByName(dto.getName());
            if (sectionByName!=null) {
                if (
                        Objects.equals(sectionByName.getId(), section.getId())
                                ||
                                !sectionRepository.existsSectionByName(dto.getName())
                ) {
                    section.setName(dto.getName());
                    sectionRepository.save(section);
                    return new ApiResponse(true, "section updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved section! Please, enter other section name!.."
                    );
                }
            }else {
                if (!sectionRepository.existsSectionByName(dto.getName())) {
                    section.setName(dto.getName());
                    sectionRepository.save(section);
                    return new ApiResponse(true, "section updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved section! Please, enter other section name!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount section"
            );
        }
    }
    @Override
    public ApiResponse deleteById(String id) {
        if (sectionRepository.findById(id).isPresent()) {
            sectionRepository.deleteById(id);
            return new ApiResponse(true,"Section deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount section!");
        }
    }
}
