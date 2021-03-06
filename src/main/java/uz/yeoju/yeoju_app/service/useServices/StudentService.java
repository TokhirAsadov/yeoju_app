package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.enums.TeachStatus;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.GroupDto;
import uz.yeoju.yeoju_app.payload.StudentDto;
import uz.yeoju.yeoju_app.payload.payres.FacultyStatisticDto;
import uz.yeoju.yeoju_app.payload.payres.StudentFullNameAndAscAndDescDateDto;
import uz.yeoju.yeoju_app.payload.resDto.student.*;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.StudentImplService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService implements StudentImplService<StudentDto> {
    public final StudentRepository studentRepository;
    private final UserService userService;
    private final FacultyService facultyService;
    //private final GroupService userService;

    public final UserRepository userRepository;
    public final GroupRepository groupRepository;


    public ApiResponse getStudentWithRFIDForToday(String groupName){
        List<StudentWithRFID> list = studentRepository.getStudentWithRFID(groupName);

        List<StudentFullNameAndAscAndDescDateDto> ascAndDescDateDtos = new ArrayList<>();
        int id = 1;
        for (StudentWithRFID rfid : list) {
            StudentWithAscAndDescDate ascAndDescDate = studentRepository.getStudentWithAscAndDescDateForToday(rfid.getRFID());
            if (ascAndDescDate != null) {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                id,
                                rfid.getFullName(),
                                rfid.getRFID(),
                                ascAndDescDate.getTimeAsc(),
                                ascAndDescDate.getTimeDesc()
                        )
                );
            }
            else {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                id,
                                rfid.getFullName(),
                                rfid.getRFID()
                        )
                );
            }
            id++;
        }
        id = 1;
        return new ApiResponse(true,"send",ascAndDescDateDtos);

    }

    public ApiResponse getStudentWithRFID(String groupName,LocalDateTime dateFrom,LocalDateTime dateTo){
        List<StudentWithRFID> list = studentRepository.getStudentWithRFID(groupName);

        List<StudentFullNameAndAscAndDescDateDto> ascAndDescDateDtos = new ArrayList<>();
        int id = 1;
        for (StudentWithRFID rfid : list) {
            StudentWithAscAndDescDate ascAndDescDate = studentRepository.getStudentWithAscAndDescDate(rfid.getRFID(), dateFrom, dateTo);
            if (ascAndDescDate != null) {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                id,
                                rfid.getFullName(),
                                rfid.getRFID(),
                                ascAndDescDate.getTimeAsc(),
                                ascAndDescDate.getTimeDesc()
                        )
                );
            }
            else {
                ascAndDescDateDtos.add(
                        new StudentFullNameAndAscAndDescDateDto(
                                id,
                                rfid.getFullName(),
                                rfid.getRFID()
                        )
                );
            }
            id++;
        }
        id = 1;
        return new ApiResponse(true,"send",ascAndDescDateDtos);

    }

    public ApiResponse getStudentsByTimeIntervalAndLevelAndFacultyName(Integer timeInterval, Integer level, String facultyName){
        List<FacultyStatistic> statistics = studentRepository.getStudentsByTimeIntervalAndLevelAndFacultyName(timeInterval, level, facultyName);
        return new ApiResponse(true,"send",statistics);
    }


    public ApiResponse getGroupsStatisticByFacultyNameAndGroupLevel(Integer level,String facultyName, LocalDateTime dateFrom, LocalDateTime dateTo){
        List<FacultyStatistic> statistics = studentRepository.getGroupsStatisticByFacultyNameAndGroupLevel(level, facultyName, dateFrom, dateTo);
        return new ApiResponse(true,"send",statistics);
    }

    public ApiResponse getGroupsByLevelAndFacultyName(Integer level,String facultyName){
        List<String> groups = studentRepository.getGroupsByLevelAndFacultyName(level, facultyName);
        return new ApiResponse(true,"send",groups);
    }


    public ApiResponse getComeCountStudentAndFacultyName(Integer level, LocalDateTime dateFrom, LocalDateTime dateTo) {
        List<FacultyStatisticComing> comingList = studentRepository.getComeCountStudentAndFacultyName(level, dateFrom, dateTo);
        List<FacultyStatisticAll> allList = studentRepository.getAllCountStudentAndFaculty(level);
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatisticAll all : allList) {
            for (FacultyStatisticComing coming : comingList) {
                if (coming.getName().equals(all.getName())) {
                    FacultyStatisticDto dto = new FacultyStatisticDto(all.getName(), coming.getComeCount(), (all.getAllCount() - coming.getComeCount()));
                    dtos.add(dto);
                }
            }
        }
        return new ApiResponse(true, "send ------------", dtos);
    }

    public ApiResponse getAllCourses() {
        return new ApiResponse(true, "all getAllCourses", studentRepository.getAllCourses());
    }

    public ApiResponse getFacultyByCourseLevel(Integer level) {
        List<String> facultyByLevels = studentRepository.getFacultyByCourseLevel(level);
        return new ApiResponse(true, "send", facultyByLevels);
    }

    public ApiResponse getStudentCountByGroupAndFaculty(String facultyName, Integer level, LocalDateTime startTime, LocalDateTime endTime) {
        List<FacultyStatistic> statistics = studentRepository.getStudentCountByGroupAndFaculty(facultyName, level, startTime, endTime);
        return new ApiResponse(true, "send", statistics);
    }

    public ApiResponse getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(Integer level, Integer weekOrMonth) {
        long start = System.currentTimeMillis();
        System.out.println(level);
        System.out.println("Kirdi methodga");
        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAllByGroupLevelAndWeekOrMonth(level, weekOrMonth);

        System.out.println("DB dan keldi: " + (System.currentTimeMillis() - start));

        start=System.currentTimeMillis();
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatistic item : list) {
            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
            dtos.add(dto);
        }

        System.out.println("Method tugadi");
        System.out.println(System.currentTimeMillis() - start);

        return new ApiResponse(true, "send", dtos);
    }
    public ApiResponse getFacultyAndComingCountWithAllByGroupLevel(Integer level, LocalDateTime startTime, LocalDateTime endTime) {
        long start = System.currentTimeMillis();
        System.out.println(startTime);
        System.out.println(endTime);
        System.out.println(level);
        System.out.println("Kirdi methodga");
        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAllByGroupLevel(level, startTime, endTime);

        System.out.println("DB dan keldi: " + (System.currentTimeMillis() - start));

        start=System.currentTimeMillis();
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatistic item : list) {
            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
            dtos.add(dto);
        }

        System.out.println("Method tugadi");
        System.out.println(System.currentTimeMillis() - start);

        return new ApiResponse(true, "send", dtos);
    }

    public ApiResponse getFacultyAndComingCountWithAll(LocalDateTime startTime, LocalDateTime endTime) {
        List<FacultyStatistic> list = studentRepository.getFacultyAndComingCountWithAll(startTime, endTime);
        List<FacultyStatisticDto> dtos = new ArrayList<>();
        for (FacultyStatistic item : list) {
            FacultyStatisticDto dto = new FacultyStatisticDto(item.getName(), item.getComeCount(), (item.getAllCount() - item.getComeCount()));
            dtos.add(dto);
        }

        return new ApiResponse(true, "send", dtos);
    }

    public ApiResponse createStudentsByRfidAndGroupNames(List<String> rfid, List<String> groupNames) {
        System.out.println(rfid.size());
        System.out.println(groupNames.size());
        System.out.println("-----------------------------------------------------------------------------------------");

        for (int i = 0; i < rfid.size(); i++) {
            User userByRFID = userRepository.findUserByRFID(rfid.get(i));
            Group groupByName = groupRepository.findGroupByName(groupNames.get(i));

            Student student = new Student();
            student.setGroup(groupByName);
            student.setUser(userByRFID);
            student.setTeachStatus(TeachStatus.TEACHING);
            studentRepository.save(student);
            System.out.println(student);
        }

        return new ApiResponse(true, "success");
    }

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
                .map(faculty -> new ApiResponse(true, "Fount user by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount user by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Student student = studentRepository.getById(id);
        return new ApiResponse(true, "Fount user by id", student);
    }

    @Override
    public ApiResponse saveOrUpdate(StudentDto studentDto) {
        if (studentDto.getId() == null) {
            return save(studentDto);
        } else {
            return update(studentDto);
        }
    }

    public ApiResponse update(StudentDto dto) {
        Optional<Student> optional = studentRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Student student = optional.get();
            Student studentByUserId = studentRepository.findStudentByUserId(dto.getUserDto().getId());
            if (studentByUserId != null) {
                if (
                        Objects.equals(studentByUserId.getId(), student.getId())
                                ||
                                !studentRepository.existsStudentByUserId(dto.getUserDto().getId())
                ) {
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true, "user updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! did not save user!"
                    );
                }
            } else {
                if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())) {
                    student.setBornYear(dto.getBornYear());
                    student.setCitizenship(dto.getCitizenship());
                    student.setDescription(dto.getDescription());
                    student.setEnrollmentYear(dto.getEnrollmentYear());
                    student.setGroup(generateGroup(dto.getGroupDto()));
                    studentRepository.save(student);
                    return new ApiResponse(true, "user updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! did not save user!"
                    );
                }
            }
        } else {
            return new ApiResponse(
                    false,
                    "error... not fount user"
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

    public ApiResponse save(StudentDto dto) {
        if (!studentRepository.existsStudentByUserId(dto.getUserDto().getId())) {
            Student student = generateStudent(dto);
            studentRepository.saveAndFlush(student);
            return new ApiResponse(true, "new user saved successfully!...");
        } else {
            return new ApiResponse(
                    false,
                    "error! did not save user!"
            );
        }
    }

    private Student generateStudent(StudentDto dto) {
        return new Student(
                userService.generateUser(dto.getUserDto()),
                generateGroup(dto.getGroupDto()),
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
            return new ApiResponse(true, "user deleted successfully!..");
        } else {
            return new ApiResponse(false, "error... not fount user!");
        }
    }

    @Override
    public ApiResponse findStudentByUserId(String user_id) {
        Student studentByUserId = studentRepository.findStudentByUserId(user_id);
        if (studentByUserId != null) {
            return new ApiResponse(
                    true,
                    "fount user by user id",
                    generateStudentDto(studentByUserId)
            );
        } else {
            return new ApiResponse(
                    false,
                    "not fount user by user id"
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

//    @Override
//    public ApiResponse findStudentsByEducationFormId(String educationForm_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education form id",
//                studentRepository.findStudentsByEducationFormId(educationForm_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

//    @Override
//    public ApiResponse findStudentsByEducationTypeId(String educationType_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education type id",
//                studentRepository.findStudentsByEducationTypeId(educationType_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

//    @Override
//    public ApiResponse findStudentsByEducationLanguageId(String educationLanguage_id) {
//        return new ApiResponse(
//                true,
//                "List of all students by education language id",
//                studentRepository.findStudentsByEducationLanguageId(educationLanguage_id)
//                        .stream().map(this::generateStudentDto)
//                        .collect(Collectors.toSet())
//        );
//    }

    @Override
    public ApiResponse findStudentByPassportSerial(String passportSerial) {
        Student studentByPassportSerial = studentRepository.findStudentByPassportSerial(passportSerial);
        if (studentByPassportSerial != null) {
            return new ApiResponse(
                    true,
                    "fount user by Passport Serial",
                    generateStudentDto(studentByPassportSerial)
            );
        } else {
            return new ApiResponse(
                    false,
                    "not fount user by Passport Serial"
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
