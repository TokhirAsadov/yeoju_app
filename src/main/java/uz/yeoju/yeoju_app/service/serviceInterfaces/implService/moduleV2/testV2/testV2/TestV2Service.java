package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.moduleV2.testV2.testV2;

import org.springframework.data.domain.Pageable;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Creator;
import uz.yeoju.yeoju_app.payload.moduleV2.testV2.TestV2Updater;

import javax.validation.Valid;

public interface TestV2Service {
    ApiResponse create(@Valid TestV2Creator creator);
    ApiResponse findByStudentIdAndEducationYearIdV2(String studentId, String educationYearId);
    ApiResponse findByGroupIdAndEducationYearIdV2(String groupId, String educationYearId);
    boolean delete(String id);
    ApiResponse findAll(Pageable pageable);
    ApiResponse findById(String id);
    ApiResponse update(TestV2Updater updater);
}
