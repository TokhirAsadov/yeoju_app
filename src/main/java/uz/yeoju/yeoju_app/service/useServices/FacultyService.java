package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.repository.FacultyRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.GanderImplService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacultyService implements FacultyImplService<FacultyDto> {
    public final FacultyRepository facultyRepository;
    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"List of all faculty", facultyRepository.findAll());
    }

    @Override
    public ApiResponse findById(UUID id) {
        return facultyRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount faculty by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount faculty by id"));
    }

    @Override
    public ApiResponse getById(UUID id) {
        Faculty faculty = facultyRepository.getById(id);
        return new ApiResponse(true, "Fount faculty by id", faculty);
    }

    @Override
    public ApiResponse saveOrUpdate(FacultyDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(FacultyDto dto){
        Optional<Faculty> optional = facultyRepository.findById(dto.getId());
        if (optional.isPresent()){
            Faculty faculty = optional.get();
            Faculty facultyByName = facultyRepository.getFacultyByName(dto.getName());
            if (
                    Objects.equals(facultyByName.getId(), faculty.getId())
                    ||
                            !facultyRepository.existsFacultyByName(dto.getName())
            ){
                faculty.setName(dto.getName());
                facultyRepository.save(faculty);
                return new ApiResponse(true,"faculty updated successfully!..");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! nor saved faculty! Please, enter other faculty name!.."
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

    public ApiResponse save(FacultyDto dto){
        if (!facultyRepository.existsFacultyByName(dto.getName())){
            Faculty faculty = generateFaculty(dto);
            facultyRepository.saveAndFlush(faculty);
            return new ApiResponse(true,"new faculty saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved faculty! Please, enter other faculty name!"
            );
        }
    }

    public Faculty generateFaculty(FacultyDto dto) {
        return new Faculty(dto.getName());
    }
    public FacultyDto generateFacultyDto(Faculty faculty) {
        return new FacultyDto(faculty.getId(),faculty.getName());
    }


    @Override
    public ApiResponse deleteById(UUID id) {
        if (facultyRepository.findById(id).isPresent()) {
            facultyRepository.deleteById(id);
            return new ApiResponse(true,"faculty deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount faculty!");
        }
    }
}
