package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.finalGrade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.module.FinalGradeOfStudent;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.FinalGradeCreatorDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinalGradeOfStudentImplService implements FinalGradeOfStudentService {
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;
    private final VedimostRepository vedimostRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse createFinalGrades(FinalGradeCreatorDto dto) {
        boolean exists = vedimostRepository.existsById(dto.vedimostId);
        if (exists) {
            Vedimost vedimost = vedimostRepository.getById(dto.vedimostId);
            Set<FinalGradeOfStudent> grades = new HashSet<>();
            dto.data.forEach(d -> {
                boolean existsStudent = userRepository.existsById(d.studentId);
                if (existsStudent) {
                    grades.add(new FinalGradeOfStudent(Math.round(d.grade * 100) / 100.0, userRepository.findById(d.studentId).get(), vedimost));
                }
                else {
                    throw new UserNotFoundException("Student was not found by id " +d.studentId);
                }
            });
            finalGradeOfStudentRepository.saveAll(grades);
            vedimost.setCondition(VedimostCondition.DONE);
            vedimost.setTimeClose(new Timestamp(System.currentTimeMillis()));
            vedimostRepository.save(vedimost);
            return new ApiResponse(true, "All grades created and vedimost is closed.");
        }
        else {
            throw new UserNotFoundException("Vedimost was not found by id " +dto.vedimostId);
        }
    }
}
