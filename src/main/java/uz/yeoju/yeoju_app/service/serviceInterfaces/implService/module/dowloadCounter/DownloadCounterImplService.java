package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.dowloadCounter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.module.DownloadCounterRepository;

@Service
@RequiredArgsConstructor
public class DownloadCounterImplService implements DownloadCounterService{
    private final DownloadCounterRepository downloadRepository;

    @Override
    public ApiResponse getDownloadCountOfStudent(String teacherId, String educationYearId, String groupId, String subjectId, String studentId) {
        return new ApiResponse(true,"counter",downloadRepository.getPlanIdAndStudentId(teacherId, educationYearId, groupId, subjectId, studentId));
    }
}
