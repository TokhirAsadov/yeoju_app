package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.school.School;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.DirectionDto;
import uz.yeoju.yeoju_app.payload.FacultyDto;
import uz.yeoju.yeoju_app.repository.FacultyRepository;
import uz.yeoju.yeoju_app.repository.SchoolRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.FacultyImplService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacultyService implements FacultyImplService<FacultyDto> {
    public final FacultyRepository facultyRepository;
    public final SchoolRepository schoolRepository;


    public ApiResponseTwoObj getFacultyForDekanatSaved(){
        return new ApiResponseTwoObj(true,"faculties",facultyRepository.getFacultyForDekanatSaved(),facultyRepository.getRolesAndPositionsForDekanatCRUD());
    }
    public ApiResponse createFacultiesByNames(List<String> namesOfFaculties){
        for (String namesOfFaculty : namesOfFaculties) {
            facultyRepository.save(new Faculty(namesOfFaculty));
        }
        return new ApiResponse(true,"Saved",facultyRepository.findAll());
    }

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all faculty",
                facultyRepository.findAll().stream().map(this::generateFacultyDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return facultyRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount faculty by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount faculty by id"));
    }

    @Override
    public ApiResponse getById(String id) {
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
            if (facultyByName!=null) {
                if (
                        Objects.equals(facultyByName.getId(), faculty.getId())
                                ||
                                !facultyRepository.existsFacultyByName(dto.getName())
                ) {
                    faculty.setName(dto.getName());
                    facultyRepository.save(faculty);
                    return new ApiResponse(true, "faculty updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved faculty! Please, enter other faculty userPositionName!.."
                    );
                }
            }
            else {
                if (!facultyRepository.existsFacultyByName(dto.getName())){
                    faculty.setName(dto.getName());
                    facultyRepository.save(faculty);
                    return new ApiResponse(true,"faculty updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved faculty! Please, enter other faculty userPositionName!.."
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

    public ApiResponse save(FacultyDto dto){
        if (!facultyRepository.existsFacultyByName(dto.getName())){
            Faculty faculty = generateFaculty(dto);
            facultyRepository.saveAndFlush(faculty);
            return new ApiResponse(true,"new faculty saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved faculty! Please, enter other faculty userPositionName!"
            );
        }
    }

    public Faculty generateFaculty(FacultyDto dto) {
        return new Faculty(dto.getName());
    }
    public FacultyDto generateFacultyDto(Faculty faculty) {
        return new FacultyDto(faculty.getId(),faculty.getName());
    }

    public FacultyDto generateFacultyDto2(Faculty faculty) {
        return new FacultyDto(faculty.getId(),faculty.getShortName());
    }



    @Override
    public ApiResponse deleteById(String id) {
        if (facultyRepository.findById(id).isPresent()) {
            facultyRepository.deleteById(id);
            return new ApiResponse(true,"faculty deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount faculty!");
        }
    }


    public ApiResponse getFacultiesForSelect(String educationName) {
        return new ApiResponse(true,"all faculties",facultyRepository.getFacultiesForSelect(educationName));
    }

    public ApiResponse getGroupsForSelect(String facultyId,String eduTypeName) {
        return new ApiResponse(true,"all groups",facultyRepository.getGroupsForSelect(facultyId,eduTypeName));
    }

    public ApiResponse getDirectionsOfFaculty() {
        return new ApiResponse(true,"directions of faculty",facultyRepository.getDirectionsOfFaculty());
    }

    public ApiResponse saveOrUpdateDirection(DirectionDto dto) {
        if (dto.getId()==null){
            return saveDirection(dto);
        }
        else {
            return updateDirection(dto);
        }
    }

    public ApiResponse saveDirection(DirectionDto dto){

        if (!facultyRepository.existsFacultyByName(dto.getName())){
            Optional<School> schoolOptional = schoolRepository.findSchoolByCode(dto.getSchoolCode());
            if (schoolOptional.isPresent()){
                Faculty faculty = new Faculty();
                faculty.setName(dto.getName());
                faculty.setShortName(dto.getShortName());
                faculty.setSchoolCode(dto.getSchoolCode());
                facultyRepository.saveAndFlush(faculty);
                return new ApiResponse(true,"new faculty saved successfully!...");
            }
            else {
                return new ApiResponse(
                        false,
                        "error! not fount school by school Code: "+dto.getSchoolCode()
                );
            }

        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved faculty! Please, enter other faculty userPositionName!"
            );
        }
    }


    public ApiResponse updateDirection(DirectionDto dto){
        Optional<Faculty> optional = facultyRepository.findById(dto.getId());
        if (optional.isPresent()){
            Faculty faculty = optional.get();
            Faculty facultyByName = facultyRepository.getFacultyByName(dto.getName());
            if (facultyByName!=null) {
                if (
                        Objects.equals(facultyByName.getId(), faculty.getId())
                                ||
                                !facultyRepository.existsFacultyByName(dto.getName())
                ) {
                    Optional<School> schoolOptional = schoolRepository.findSchoolByCode(dto.getSchoolCode());
                    if (schoolOptional.isPresent()){
                        faculty.setName(dto.getName());
                        faculty.setShortName(dto.getShortName());
                        faculty.setSchoolCode(dto.getSchoolCode());
                        facultyRepository.saveAndFlush(faculty);
                        return new ApiResponse(true, dto.getName()+" updated successfully!...");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! not fount school by school Code: "+dto.getSchoolCode()
                        );
                    }
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved faculty! Please, enter other faculty userPositionName!.."
                    );
                }
            }
            else {
                if (!facultyRepository.existsFacultyByName(dto.getName())){
                    Optional<School> schoolOptional = schoolRepository.findSchoolByCode(dto.getSchoolCode());
                    if (schoolOptional.isPresent()){
                        faculty.setName(dto.getName());
                        faculty.setShortName(dto.getShortName());
                        faculty.setSchoolCode(dto.getSchoolCode());
                        facultyRepository.saveAndFlush(faculty);
                        return new ApiResponse(true, dto.getName()+" updated successfully!...");
                    }
                    else {
                        return new ApiResponse(
                                false,
                                "error! not fount school by school Code: "+dto.getSchoolCode()
                        );
                    }
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved faculty! Please, enter other faculty userPositionName!.."
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


    public ApiResponse findAllShortName() {
        return new ApiResponse(
                true,
                "List of all faculty short names",
                facultyRepository.findAll().stream().map(this::generateFacultyDto2).collect(Collectors.toSet())
        );
    }
}
