package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.payload.module.VedimostUpdaterDto;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetVedimostOfKafedra;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.DataOfLeaders;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher.TeacherService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VedimostImplService implements VedimostService{
    private final VedimostRepository vedimostRepository;
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final EducationYearRepository educationYearRepository;
    private final GroupRepository groupRepository;
    private final TeacherService teacherService;

    @Override
    public ApiResponse findAllVedimosts() {
        return null;
    }

    @Override
    public ApiResponse createVedimost(VedimostCreaterDto dto) {
        boolean existsTeacher = userRepository.existsById(dto.teacherId);
        if (existsTeacher) {
            boolean existsLesson = lessonRepository.existsById(dto.lessonId);
            if (existsLesson) {
                boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
                if (existsEducationYear) {
                    dto.groupsIds.forEach(groupId -> {
                        groupRepository.findById(groupId).ifPresent(group -> {
                            Boolean bool = vedimostRepository.existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(dto.teacherId, dto.lessonId, groupId, dto.educationYearId);
                            if (!bool) {
                                ApiResponse dataOfLeaders = teacherService.getDataOfLeaders(userRepository.findById(dto.teacherId).orElse(null).getId(), group.getName());
                                if (dataOfLeaders.isSuccess()){
                                    DataOfLeaders obj = (DataOfLeaders) dataOfLeaders.getObj();
                                    Vedimost vedimost = new Vedimost();
                                    vedimost.setCourseLeader(obj.getCourseLeader());
                                    vedimost.setHeadOfDepartment(obj.getHeadOfDepartment());
                                    vedimost.setHeadOfAcademicAffair(obj.getHeadOfAcademicAffair());
                                    vedimost.setDirection(obj.getDirection());

                                    vedimost.setCourseLeader(obj.getCourseLeader());
                                    vedimost.setLevel(group.getLevel());
                                    vedimost.setDeadline(dto.deadline);
//                            vedimost.setTimeClose(dto.timeClose);
                                    vedimost.setCondition(VedimostCondition.valueOf(dto.condition));
                                    vedimost.setTeacher(userRepository.findById(dto.teacherId).orElse(null));
                                    vedimost.setLesson(lessonRepository.findById(dto.lessonId).orElse(null));
                                    vedimost.setGroup(group);
                                    vedimost.setEducationYear(educationYearRepository.findById(dto.educationYearId).orElse(null));
                                    vedimostRepository.save(vedimost);
                                }
                                else {
                                    throw new UserNotFoundException("Data about course leader has error. "+dataOfLeaders.getMessage());
                                }
                            }
                            else {
                                throw new UserNotFoundException(userRepository.findById(dto.teacherId).orElse(null).getFullName()+" ga " +group.getName()+" guruhi uchun "+lessonRepository.findById(dto.lessonId).orElse(null).getName()+" fanidan qaytnoma oldin yaratilgan.");
                            }
                        });
                    });
                }
                else {
                    throw new UserNotFoundException("Education year was not found by id " +dto.educationYearId);
                }
            }
            else {
                throw new UserNotFoundException("Lesson was not found by id " +dto.lessonId);
            }
        }
        else {
            throw new UserNotFoundException("Teacher was not found by id " +dto.teacherId);
        }
        return new ApiResponse(true, "Vedimost created successfully");
    }

    @Override
    public ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId) {
        return new ApiResponse(true,"All vedimosts by education year",vedimostRepository.getVedimostOfKafedra(kafedraId,educationYearId));
    }

    @Override
    public ApiResponse getVedimostByLessonId(String lessonId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts by lesson",educationYearId!=null ? vedimostRepository.getVedimostByLessonId(lessonId,educationYearId):vedimostRepository.getVedimostByLessonId(lessonId));
    }

    @Override
    public ApiResponse getVedimostByTeacherId(String teacherId, String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",educationYearId!=null ? vedimostRepository.getVedimostByTeacherId(teacherId,educationYearId):vedimostRepository.getVedimostByTeacherId(teacherId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndLessonId(String teacherId, String educationYearId, String lessonId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",educationYearId!=null ? vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearId(teacherId,educationYearId,lessonId):vedimostRepository.getVedimostByTeacherIdAndLessonId(teacherId,lessonId));
    }

    @Override
    public ApiResponse getAllVedimostByKafedra(String kafedraId) {
        return new ApiResponse(true,"All vedimosts",vedimostRepository.getAllVedimostOfKafedra(kafedraId));
    }

    @Override
    public ApiResponse getAllVedimostByTeacherId(String teacherId) {
        return new ApiResponse(true,"All teacher's vedimosts",vedimostRepository.getAllVedimostByTeacherId(teacherId));
    }

    @Override
    public ApiResponse updateVedimost(VedimostUpdaterDto dto) {
        boolean exists = vedimostRepository.existsById(dto.id);
        if (exists) {
            Vedimost vedimost = vedimostRepository.getById(dto.id);
            vedimost.setDeadline(dto.deadline);
//                            vedimost.setTimeClose(dto.timeClose);
            vedimost.setCondition(VedimostCondition.valueOf(dto.condition));

            if (!vedimost.getTeacher().getId().equals(dto.teacherId)) {
                boolean existsTeacher = userRepository.existsById(dto.teacherId);
                if (existsTeacher) {
                    vedimost.setTeacher(userRepository.findById(dto.teacherId).orElse(null));
                }
                else {
                    throw new UserNotFoundException("Teacher was not found by id " +dto.teacherId);
                }
            }
            if (!vedimost.getLesson().getId().equals(dto.lessonId)) {
                boolean existsLesson = lessonRepository.existsById(dto.lessonId);
                if (existsLesson) {
                    vedimost.setLesson(lessonRepository.findById(dto.lessonId).orElse(null));
                }
                else {
                    throw new UserNotFoundException("Lesson was not found by id " +dto.lessonId);
                }
            }
            if (!vedimost.getGroup().getId().equals(dto.groupId)) {
                boolean existsGroup = groupRepository.existsById(dto.groupId);
                if (existsGroup) {
                    vedimost.setLevel(Objects.requireNonNull(groupRepository.findById(dto.groupId).orElse(null)).getLevel());
                    vedimost.setGroup(groupRepository.findById(dto.groupId).orElse(null));
                }
                else {
                    throw new UserNotFoundException("Group was not found by id " +dto.groupId);
                }
            }
            if (!vedimost.getEducationYear().getId().equals(dto.educationYearId)) {
                boolean existsEducationYear = educationYearRepository.existsById(dto.educationYearId);
                if (existsEducationYear) {
                    vedimost.setEducationYear(educationYearRepository.findById(dto.educationYearId).orElse(null));
                }
                else {
                    throw new UserNotFoundException("Education year was not found by id " +dto.educationYearId);
                }
            }

            vedimostRepository.save(vedimost);
            return new ApiResponse(true,"Vedimost updated successfully");
        }
        else {
            return new ApiResponse(false,"Vedimost not found by id " +dto.id);
        }
    }

    @Override
    public ApiResponse deleteVedimostById(String id) {
        boolean exists = vedimostRepository.existsById(id);
        if (exists) {
            try {
                vedimostRepository.deleteById(id);
                return new ApiResponse(true,"Vedimost deleted successfully");
            }
            catch (Exception e) {
                throw new UserNotFoundException("Error!. Vedimost was not deleted");
            }

        }
        else {
            return new ApiResponse(false,"Vedimost not found by id " +id);
        }
    }

    @Override
    public ApiResponse getAllVedimostByGroupId(String groupId) {
        return new ApiResponse(true,"All vedimosts of group",vedimostRepository.getAllVedimostByGroupId(groupId));
    }

    @Override
    public ApiResponse getVedimostByGroupId(String groupId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts of group by education year",educationYearId!=null?vedimostRepository.getVedimostByGroupId(groupId,educationYearId):vedimostRepository.getVedimostByGroupId(groupId));
    }

    @Override
    public ApiResponse getVedimostByFacultyId(String facultyId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts of group by faculty id",educationYearId!=null?vedimostRepository.getVedimostByFacultyId(facultyId,educationYearId):vedimostRepository.getVedimostByFacultyId(facultyId));
    }

    @Override
    public ApiResponse getVedimostForBeingDone(String teacherId, String lessonId, String groupId, String educationYearId) {
        if (educationYearId!=null) {
            Boolean bool1 = vedimostRepository.existsVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(teacherId, lessonId, groupId, educationYearId);
            if (bool1) {
                GetVedimostOfKafedra vedimost = vedimostRepository.getVedimostByTeacherIdAndLessonIdAndGroupIdAndEducationYearId(teacherId, lessonId, groupId, educationYearId);
                return new ApiResponse(true, "Vedimost for teacher", vedimost);
            } else {
                return new ApiResponse(false, "Qaytnoma topilmadi iltimos kafedra mudiri bilan bog`laning.");
            }
        }
        else {
            Boolean bool1 = vedimostRepository.existsVedimostByTeacherIdAndLessonIdAndGroupId(teacherId, lessonId, groupId);
            if (bool1) {
                GetVedimostOfKafedra vedimost = vedimostRepository.getVedimostByTeacherIdAndLessonIdAndGroupId(teacherId, lessonId, groupId);
                return new ApiResponse(true, "Vedimost for teacher", vedimost);
            } else {
                return new ApiResponse(false, "Qaytnoma topilmadi iltimos kafedra mudiri bilan bog`laning.");
            }
        }
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String teacherId, String lessonId, String facultyId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts of group by faculty id",educationYearId!=null ? vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(teacherId,lessonId,facultyId,educationYearId):vedimostRepository.getVedimostByTeacherIdAndLessonIdAndFacultyId(teacherId,lessonId,facultyId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndFacultyId(String teacherId, String facultyId, String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",educationYearId!=null ? vedimostRepository.getVedimostByTeacherIdAndFacultyIdAndEducationId(teacherId,facultyId,educationYearId):vedimostRepository.getVedimostByTeacherIdAndFacultyId(teacherId,facultyId));
    }

    @Override
    public ApiResponse getVedimostByLessonIdAndFacultyId(String educationYearId, String lessonId, String facultyId) {
        return new ApiResponse(true,"All vedimosts by lesson and faculty",educationYearId!=null ? vedimostRepository.getVedimostByLessonIdAndFacultyId(lessonId,facultyId,facultyId):vedimostRepository.getVedimostByLessonIdAndFacultyId(lessonId,facultyId));
    }

    @Override
    public ApiResponse getVedimostByLessonIdAndGroupId(String educationYearId, String lessonId, String groupId) {
        return new ApiResponse(true,"All vedimosts by lesson and group",educationYearId!=null ? vedimostRepository.getVedimostByLessonIdAndGroupId(lessonId,groupId,educationYearId):vedimostRepository.getVedimostByLessonIdAndGroupId(lessonId,groupId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndGroupId(String teacherId, String educationYearId, String groupId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",educationYearId!=null ? vedimostRepository.getVedimostByTeacherIdAndGroupId(teacherId,groupId,educationYearId):vedimostRepository.getVedimostByTeacherIdAndGroupId(teacherId,groupId));
    }

    @Override
    public ApiResponse getVedimostByEducationYearId(String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",vedimostRepository.getVedimostByEducationYearId(educationYearId));
    }

    @Override
    public ApiResponse getLast50Vedimost() {
        return new ApiResponse(true,"Last 50 vedimosts",vedimostRepository.getLast50Vedimost());
    }
}
