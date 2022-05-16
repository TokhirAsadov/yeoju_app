package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.StudentImplService;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentImplService<StudentDto> {
    public final StudentRepository studentRepository;
    private final UserService userService;
    private final EducationFormService formService;
    private final EducationTypeService typeService;
    private final EduLanService lanService;
    private final FacultyService facultyService;
    //private final GroupService userService;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all students",
                studentRepository.findAll().stream().map(this::generateStudentDto).collect(Collectors.toSet())
        );
    }

    private StudentDto generateStudentDto(Student student) {
        return new StudentDto(
                student.getId(),
                userService.generateUserDto(student.getUser()),
                generateGroupDto(student.getGroup()),
                formService.generateEduFormDto(student.getEducationForm()),
                typeService.generateEduTypeDto(student.getEducationType()),
                lanService.generateEduLanDto(student.getEducationLanguage()),
                student.getPassportSerial(),
                student.getBornYear(),
                student.getDescription(),
                student.getEnrollmentYear(),
                student.getCitizenship()
        );
    }

    private GroupDto generateGroupDto(Group group) {
        return new GroupDto(
               group.getId(),
               group.getName(),
               group.getLevel(),
               facultyService.generateFacultyDto(group.getFaculty())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return studentRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount student by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount student by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Student student = studentRepository.getById(id);
        return new ApiResponse(true, "Fount student by id", student);
    }

    @Override
    public ApiResponse saveOrUpdate(StudentDto studentDto) {
        if (studentDto.getId()==null){
            return save(studentDto);
        }
        else {
            return update(studentDto);
        }
    }

    public ApiResponse update(StudentDto dto){
        Optional<Student> optional = studentRepository.findById(dto.getId());
        if (optional.isPresent()){
            Student student = optional.get();
            Student studentByUserId = studentRepository.findStudentByUserId(dto.getUserDto().getId());
            if (studentByUserId !=null) {
                if (
                        Objects.equals(studentByUserId.getId(), student.getId())
                                ||
                                !studentRepository.existsStudentByUserId(dto.getUserDto().getId())
                ) {
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setEducationForm(formService.generateEduForm(dto.getEduFormDto()));
                    student.setEducationType(typeService.generateEduType(dto.getEduTypeDto()));
                    student.setEducationLanguage(lanService.generateEduLan(dto.getEduLanDto()));
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true, "student updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! did not save student!"
                    );
                }
            }
            else {
                if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())){
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setEducationForm(formService.generateEduForm(dto.getEduFormDto()));
                    student.setEducationType(typeService.generateEduType(dto.getEduTypeDto()));
                    student.setEducationLanguage(lanService.generateEduLan(dto.getEduLanDto()));
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true,"student updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! did not save student!"
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount student"
            );
        }
    }

    private Group generateGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getLevel(),
                facultyService.generateFaculty(groupDto.getFacultyDto())
        );
    }

    public ApiResponse save(StudentDto dto){
        if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())){
            Student student = generateStudent(dto);
            studentRepository.saveAndFlush(student);
            return new ApiResponse(true,"new student saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! did not save student!"
            );
        }
    }

    private Student generateStudent(StudentDto dto) {
        return new Student(
                userService.generateUser(dto.getUserDto()),
                generateGroup(dto.getGroupDto()),
                formService.generateEduForm(dto.getEduFormDto()),
                typeService.generateEduType(dto.getEduTypeDto()),
                lanService.generateEduLan(dto.getEduLanDto()),
                dto.getPassportSerial(),
                dto.getBornYear(),
                dto.getDescription(),
                dto.getEnrollmentYear(),
                dto.getCitizenship()
        );
    }

    @Override
    public ApiResponse deleteById(String id) {
        if (studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            return new ApiResponse(true,"student deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount student!");
        }
    }

    @Override
    public ApiResponse findStudentByUserId(String user_id) {
        Student studentByUserId = studentRepository.findStudentByUserId(user_id);
        if (studentByUserId!=null){
            return new ApiResponse(
                    true,
                    "fount student by user id",
                    generateStudentDto(studentByUserId)
                    );
        }
        else {
            return new ApiResponse(
                    false,
                    "not fount student by user id"
            );
        }
    }

    @Override
    public ApiResponse findStudentsByGroupId(String group_id) {
        return new ApiResponse(
                true,
                "List of all students by group id",
                studentRepository.findStudentsByGroupId(group_id)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByEducationFormId(String educationForm_id) {
        return new ApiResponse(
                true,
                "List of all students by education form id",
                studentRepository.findStudentsByEducationFormId(educationForm_id)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByEducationTypeId(String educationType_id) {
        return new ApiResponse(
                true,
                "List of all students by education type id",
                studentRepository.findStudentsByEducationTypeId(educationType_id)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByEducationLanguageId(String educationLanguage_id) {
        return new ApiResponse(
                true,
                "List of all students by education language id",
                studentRepository.findStudentsByEducationLanguageId(educationLanguage_id)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentByPassportSerial(String passportSerial) {
        Student studentByPassportSerial = studentRepository.findStudentByPassportSerial(passportSerial);
        if (studentByPassportSerial !=null){
            return new ApiResponse(
                    true,
                    "fount student by Passport Serial",
                    generateStudentDto(studentByPassportSerial)
            );
        }
        else {
            return new ApiResponse(
                    false,
                    "not fount student by Passport Serial"
            );
        }
    }

    @Override
    public ApiResponse findStudentsByBornYear(Timestamp bornYear) {
        return new ApiResponse(
                true,
                "List of all students by born year",
                studentRepository.findStudentsByBornYear(bornYear)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByEnrollmentYear(Timestamp enrollmentYear) {
        return new ApiResponse(
                true,
                "List of all students by enrollment year",
                studentRepository.findStudentsByEnrollmentYear(enrollmentYear)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findStudentsByCitizenship(String citizenship) {
        return new ApiResponse(
                true,
                "List of all students by citizenship",
                studentRepository.findStudentsByCitizenship(citizenship)
                        .stream().map(this::generateStudentDto)
                        .collect(Collectors.toSet())
        );
    }
}
