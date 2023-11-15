package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.uquvbulim.gradeChangedHistory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.module.GradeOfStudentByTeacher;
import uz.yeoju.yeoju_app.entity.uquvbulim.GradeChangedHistory;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.uquvbulimi.CreateGradeChangedHistoryDto;
import uz.yeoju.yeoju_app.repository.module.GradeOfStudentByTeacherRepository;
import uz.yeoju.yeoju_app.repository.uquvbulimi.GradeChangedHistoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeChangedHistoryImplService implements GradeChangedHistoryService{
    private final GradeChangedHistoryRepository historyRepository;
    private final GradeOfStudentByTeacherRepository gradeRepository;


    @Override
    public ApiResponse findAllHistory() {
        return new ApiResponse(true,"all history of grade",historyRepository.findAll());
    }

    @Override
    public ApiResponse findById(String id) {
        Optional<GradeChangedHistory> optionalGradeChangedHistory = historyRepository.findById(id);
        return optionalGradeChangedHistory.map(gradeChangedHistory -> new ApiResponse(true, "history by id", gradeChangedHistory)).orElseGet(() -> new ApiResponse(false, "not found history by id: " + id));
    }

    @Override
    public ApiResponse createHistory(CreateGradeChangedHistoryDto dto) {
        Optional<GradeOfStudentByTeacher> optionalGradeOfStudentByTeacher = gradeRepository.findById(dto.getGradeId());
        if (optionalGradeOfStudentByTeacher.isPresent()){
            GradeOfStudentByTeacher grade = optionalGradeOfStudentByTeacher.get();
            GradeChangedHistory history = new GradeChangedHistory();
            history.setGrade(grade);
            history.setOldGrade(grade.getGrade());
            history.setOldGrade(dto.getOldGrade());
            historyRepository.save(history);
            grade.setGrade(dto.getNewGrade());
            gradeRepository.save(grade);
            return new ApiResponse(true,"history was saved successfully");
        }
        else {
            return new ApiResponse(false, "not found grade by id: "+dto.getGradeId());
        }
    }
}
