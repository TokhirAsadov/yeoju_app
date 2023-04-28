package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.studentBall.StudentResult;
import uz.yeoju.yeoju_app.entity.studentBall.SubjectCredit;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.studentBall.ForBuildStudentResult;
import uz.yeoju.yeoju_app.payload.studentBall.StudentResultDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.StudentResultImplService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentResultService implements StudentResultImplService<StudentResultDto> {
    private final StudentResultRepository studentResultRepository;
    private final SubjectCreditService creditService;
    private final SubjectCreditRepository creditRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;


//TODO------------------------------------------------------------------------------------------
//TODO------------------------------------------------------------------------------------------
//TODO------------------------------------------------------------------------------------------
//    public ApiResponse multiStudentResults(List<ForBuildStudentResult> results){
//        results.forEach(item->{
//            System.out.println("FOREACH ITEM -> "+item);
//            System.out.println("id -> "+item.getStudentId());
//            User user = userRepository.getUserByLogin(item.getStudentId());
//            System.out.println("user -> "+user);
//            Lesson lesson = lessonRepository.getLessonByName(item.getSubjectName());
//            System.out.println("lesson -> "+lesson);
//            Group group = studentRepository.getGroupIdForStudentResult(item.getStudentId());
//            User userByLogin = userRepository.getUserByLogin(item.getStudentId());
//            System.out.println("item by login -> "+userByLogin);
//            System.out.println("group -> "+group);
//            if (userByLogin!=null) {
//                if (!creditRepository.existsSubjectCreditByLessonNameAndGroupName(lesson.getUserPositionName(), group.getUserPositionName())) {
//                    SubjectCredit credit = new SubjectCredit();
//                    credit.setLesson(lesson);
//                    credit.setGroup(group);
//                    credit.setCredit(item.getCredit());
//                    credit.setSemester(item.getSemester());
//                    credit.setYear(item.getYear());
//                    creditRepository.saveAndFlush(credit);
//                    System.out.println("CREDIT -> " + credit);
//                    StudentResult result = new StudentResult();
//                    result.setUser(user);
//                    result.setSubjectCredit(credit);
//                    result.setScore(item.getScore());
//                    studentResultRepository.saveAndFlush(result);
//                    System.out.println("RESULT -> " + result);
//                } else {
//                    SubjectCredit credit = creditRepository.getSubjectCreditByLessonNameAndGroupName(lesson.getUserPositionName(), group.getUserPositionName());
//                    if (credit != null) {
//                        StudentResult result = new StudentResult();
//                        result.setUser(user);
//                        result.setSubjectCredit(credit);
//                        result.setScore(item.getScore());
//                        studentResultRepository.saveAndFlush(result);
//                        System.out.println("RESULT BY ELSE -> " + result);
//                    } else {
//                        System.out.println("ERROR -> ...");
//                    }
//                }
//            }
//            else System.out.println("STUDENT IDS TULIQ EMAS");
//        });
//
//        return new ApiResponse(true,"saved");
//    }


    public ApiResponse getSubjectCreditsByGroupName(String login){
        return new ApiResponse(
                true,
                "all retake",
                studentResultRepository.getStudentResultsByUserLogin(login)
                        .stream()
                        .map(this::generateStudentResultDto)
                        .collect(Collectors.toSet())
        );
    }


    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All retake",
                studentResultRepository.findAll().stream()
                        .map(this::generateStudentResultDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> StudentResultDto generateStudentResultDto(StudentResult result) {
        return new StudentResultDto(
                result.getId(),
                userService.generateUserDto(result.getUser()),
                creditService.generateSubjectCreditDto(result.getSubjectCredit()),
                result.getScore()
        );
    }

    @Override
    public ApiResponse findById(Long id) {
        return null;
    }

    @Override
    public ApiResponse getById(Long id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(StudentResultDto dto) {

        if (dto.getId() == null){ //saveOrUpdate
            ApiResponse byId = userService.getById(dto.getUserDto().getId());
            if (byId.getObj()!=null) {
                ApiResponse creditServiceById = creditService.getById(dto.getSubjectCreditDto().getId());
                if (creditServiceById.getObj() != null){
                    studentResultRepository.save(generateStudentResult(dto));
                    return new ApiResponse(true,"saved");
                }
                return new ApiResponse(false,"not fount subject credit");
            }
            return new ApiResponse(false,"not fount user");
        }
        else { //update
            ApiResponse byId = userService.getById(dto.getUserDto().getId());
            if (byId.getObj()!=null) {
                ApiResponse creditServiceById = creditService.getById(dto.getSubjectCreditDto().getId());
                if (creditServiceById.getObj() != null){
                    studentResultRepository.save(generateStudentResult(dto));
                    return new ApiResponse(true,"updated");
                }
                return new ApiResponse(false,"not fount subject credit");
            }
            return new ApiResponse(false,"not fount user");
        }

    }

    public StudentResult generateStudentResult(StudentResultDto dto) {
        return new StudentResult(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                creditService.generateSubjectCredit(dto.getSubjectCreditDto()),
                dto.getScore()
        );
    }


    @Override
    public ApiResponse deleteById(Long id) {
        return null;
    }
}
