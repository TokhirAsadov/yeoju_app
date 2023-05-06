package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.school;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.school.School;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.school.SchoolDto;
import uz.yeoju.yeoju_app.repository.SchoolRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService{

    private final SchoolRepository schoolRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all schools",schoolRepository.findAll().stream().map(this::generateSchoolDto));
    }

    @Override
    public ApiResponse saveOrUpdate(SchoolDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    @Override
    public ApiResponse findById(String id) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        return schoolOptional.map(school -> new ApiResponse(true, "send school by id", generateSchoolDto(school))).orElseGet(() -> new ApiResponse(false, "not fount school"));
    }

    @Override
    public ApiResponse deleteById(String id) {
        Optional<School> schoolOptional = schoolRepository.findById(id);
        if (schoolOptional.isPresent()){
            schoolRepository.deleteById(id);
            return new ApiResponse(true,"deleted school successfully.");
        }
        else {
            return new ApiResponse(false,"not fount id -> "+id);
        }
    }


    private ApiResponse update(SchoolDto dto) {

        Optional<School> schoolOptional = schoolRepository.findById(dto.getId());

        if (schoolOptional.isPresent()){

            School school = schoolOptional.get();
            Optional<School> schoolByNameEnOrNameRuOrNameUz = schoolRepository.findSchoolByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz());
            if (schoolByNameEnOrNameRuOrNameUz.isPresent()) {
                School school1 = schoolByNameEnOrNameRuOrNameUz.get();
                if (
                        Objects.equals(school1.getId(), school.getId())
                                ||
                                !schoolRepository.existsSchoolByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz())
                ) {

                    Optional<School> schoolOptional1 = schoolRepository.findSchoolByCode(dto.getCode());
                    if (schoolOptional1.isPresent()){
                        School school2 = schoolOptional1.get();
                        if (Objects.equals(school2.getId(), school.getId())){
                            school.setNameEn(dto.getNameEn());
                            school.setNameRu(dto.getNameRu());
                            school.setNameUz(dto.getNameUz());
                            schoolRepository.save(school);
                            return new ApiResponse(true,"school updated successfully!.");
                        }
                        else {
                            return new ApiResponse(false,"error.. already exists code "+dto.getCode());
                        }
                    }
                    else {
                        school.setCode(dto.getCode());
                        school.setNameEn(dto.getNameEn());
                        school.setNameRu(dto.getNameRu());
                        school.setNameUz(dto.getNameUz());
                        schoolRepository.save(school);
                        return new ApiResponse(true,"school updated successfully!.");
                    }

                } else {
                    return new ApiResponse(
                            false,
                            "error! not updated school! Please, enter other school name!.."
                    );
                }
            }
            else {
                Optional<School> schoolOptional1 = schoolRepository.findSchoolByCode(dto.getCode());
                if (schoolOptional1.isPresent()){
                    School school2 = schoolOptional1.get();
                    if (Objects.equals(school2.getId(), school.getId())){
                        school.setNameEn(dto.getNameEn());
                        school.setNameRu(dto.getNameRu());
                        school.setNameUz(dto.getNameUz());
                        schoolRepository.save(school);
                        return new ApiResponse(true,"school updated successfully!.");
                    }
                    else {
                        return new ApiResponse(false,"error.. already exists code "+dto.getCode());
                    }
                }
                else {
                    school.setCode(dto.getCode());
                    school.setNameEn(dto.getNameEn());
                    school.setNameRu(dto.getNameRu());
                    school.setNameUz(dto.getNameUz());
                    schoolRepository.save(school);
                    return new ApiResponse(true,"school updated successfully!.");
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount school"
            );
        }

    };

    private ApiResponse save(SchoolDto dto) {
        if (schoolRepository.existsSchoolByCode(dto.getCode())){
            return new ApiResponse(false,"error.. already exists "+dto.getCode()+" code.");
        } else if (schoolRepository.existsSchoolByNameEnOrNameRuOrNameUz(dto.getNameEn(), dto.getNameRu(), dto.getNameUz())) {
            return new ApiResponse(false,"error.. already exists "+dto.getNameEn()+" or "+dto.getNameRu()+" or "+dto.getNameUz());
        } else {
            School school = generateSchool(dto);
            schoolRepository.save(school);
            return new ApiResponse(true,"new school saved successfully!.");
        }
    }

    public School generateSchool(SchoolDto dto){
        return new School(
                dto.getId(),
                dto.getCode(),
                dto.getNameEn(),
                dto.getNameRu(),
                dto.getNameUz()
        );
    }

    private SchoolDto generateSchoolDto(School school) {
        return new SchoolDto(school.getId(),school.getCode(),school.getNameEn(),school.getNameRu(),school.getNameUz());
    }

}
