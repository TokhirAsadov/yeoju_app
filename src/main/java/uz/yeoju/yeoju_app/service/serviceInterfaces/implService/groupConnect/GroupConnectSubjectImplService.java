package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.groupConnect;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.timetableDB.GroupConnectSubject;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.resDto.timeTableDB.StudentSubjectsByEduYearIdAndGroupAndStudentId;
import uz.yeoju.yeoju_app.repository.timetableDB.GroupConnectSubjectRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class GroupConnectSubjectImplService implements GroupConnectSubjectService {

    private final GroupConnectSubjectRepository groupConnectSubjectRepository;

    @Override
    public ApiResponseTwoObj getGroupsAndStatisticsByGroupName(String studentId, String groupName, String educationYearId) {

        Set<StudentSubjectsByEduYearIdAndGroupAndStudentId> subjects = groupConnectSubjectRepository.getSubjectsByEduYearIdAndGroupAndStudentId(groupName, educationYearId, studentId);

        return new ApiResponseTwoObj(true,"subjects with statistics",subjects);
    }

    @Override
    public ApiResponse getSubjectsOfTeacherByEducationId(String teacherId, String educationId) {
        return new ApiResponse(true,"subjects of teacher",groupConnectSubjectRepository.getSubjectsOfTeacherByEducationId(teacherId, educationId));
    }

    @Override
    public ApiResponse getGroupsOfTeacherByEducationId(String teacherId, String educationId,String subjectId) {
        return new ApiResponse(true,"groups of teacher",groupConnectSubjectRepository.getGroupsOfTeacherByEducationYearId(teacherId, educationId,subjectId));
    }

    @Override
    public ApiResponse getGroupsOfTeacherByEducationId(String teacherId, String educationId,String subjectId, String eduType) {
        return new ApiResponse(true,"groups of teacher",
                eduType.lastIndexOf("_") != -1
                        ?
                        groupConnectSubjectRepository.getGroupsOfTeacherByEducationYearId(teacherId, educationId,subjectId,eduType.substring(0,eduType.lastIndexOf("_")),eduType.substring(eduType.lastIndexOf("_")+1))
                :
                        groupConnectSubjectRepository.getGroupsOfTeacherByEducationYearId(teacherId, educationId,subjectId,eduType,eduType)
                );
    }

    @Override
    public ApiResponse getStatisticsOfGroupForTeacher(String educationId, String groupId, String subjectId) {


        return null; //new ApiResponse(true,"statistics of one group",groupConnectSubjectRepository.getStatisticsOfGroupForTeacher(educationId, groupId, subjectId));
    }
}
