package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.module.vedimost;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.module.Vedimost;
import uz.yeoju.yeoju_app.entity.module.VedimostCondition;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.module.VedimostCreaterDto;
import uz.yeoju.yeoju_app.payload.module.VedimostUpdaterDto;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetLessonsIdsWithTeachersIds;
import uz.yeoju.yeoju_app.payload.resDto.module.vedimost.GetVedimostOfKafedra;
import uz.yeoju.yeoju_app.payload.resDto.uquvbulim.DataOfLeaders;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.TeacherRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.module.FinalGradeOfStudentRepository;
import uz.yeoju.yeoju_app.repository.module.VedimostRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.teacher.TeacherService;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VedimostImplService implements VedimostService{
    private final VedimostRepository vedimostRepository;
    private final FinalGradeOfStudentRepository finalGradeOfStudentRepository;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;
    private final EducationYearRepository educationYearRepository;
    private final GroupRepository groupRepository;
    private final TeacherService teacherService;

    @Override
    public ApiResponse findAllVedimosts() {
        return null;
    }

    @Transactional
    @Override
    public ApiResponse createVedimost(VedimostCreaterDto dto) {
        boolean existsEducationYearId = educationYearRepository.existsById(dto.educationYearId);

        if (existsEducationYearId) {
            dto.groupsIds.forEach(groupId->{
                boolean existsGroup = groupRepository.existsById(groupId);
                if (existsGroup) {
                    Set<GetLessonsIdsWithTeachersIds> ids = vedimostRepository.getLessonsIdsWithTeachersIds(dto.educationYearId, groupId);
                    ids.forEach(id-> {
                        Boolean existsTeacher = teacherRepository.existsTeacherByUserId(id.getTeacherId());
                        if (existsTeacher) {
                            Boolean existsVedimost = vedimostRepository.existsVedimostByEducationYearIdAndLessonIdAndGroupId(dto.educationYearId, id.getLessonId(), groupId);
                            if (!existsVedimost) {
                                Group group = groupRepository.getById(groupId);
                                ApiResponse dataOfLeaders = teacherService.getDataOfLeaders(userRepository.findById(id.getTeacherId()).orElse(null).getId(), group.getName());
                                if (dataOfLeaders.isSuccess()) {
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
                                    vedimost.setCondition(VedimostCondition.OPEN);
                                    vedimost.setTeacher(userRepository.findById(id.getTeacherId()).orElse(null));
                                    vedimost.setLesson(lessonRepository.findById(id.getLessonId()).orElse(null));
                                    vedimost.setGroup(group);
                                    vedimost.setEducationYear(educationYearRepository.findById(dto.educationYearId).orElse(null));
                                    vedimostRepository.save(vedimost);
                                } else {
                                    throw new UserNotFoundException("Data about course leader has error. " + dataOfLeaders.getMessage());
                                }
                            }
                        }
                        else {
                            throw new UserNotFoundException("Teacher does not exist in Kafedra. Teacher name is " + userRepository.findById(id.getTeacherId()).orElse(null).getFullName());
                        }
                    });
                }
                else {
                    throw new UserNotFoundException("Group is not fount by id: "+groupId);
                }
            });
        }
        else {
            throw new UserNotFoundException("Education year is not fount by id: "+dto.educationYearId);
        }
        return new ApiResponse(true, "Vedimosts are created successfully!.");
    }

    @Override
    public ApiResponse getVedimostByKafedra(String kafedraId,String educationYearId) {
        return new ApiResponse(true,"All vedimosts by education year",vedimostRepository.getVedimostOfKafedra(kafedraId,educationYearId));
    }

    @Override
    public ApiResponse getVedimostByLessonId(String type,String dekanatOrKafedraId,String lessonId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts by lesson",type=="MONITORING" ? vedimostRepository.getVedimostByLessonId(lessonId,educationYearId):vedimostRepository.getVedimostByLessonId(lessonId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByTeacherId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherId(teacherId,educationYearId):vedimostRepository.getVedimostByTeacherId(teacherId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndLessonId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId, String lessonId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearId(teacherId,educationYearId,lessonId):vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearId(teacherId,educationYearId,lessonId,dekanatOrKafedraId));
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
    public ApiResponse getVedimostByGroupId(String type,String dekanatOrKafedraId,String groupId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts of group by education year",type=="MONITORING" ? vedimostRepository.getVedimostByGroupId(groupId,educationYearId):vedimostRepository.getVedimostByGroupId(groupId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByFacultyId(String type,String dekanatOrKafedraId,String facultyId, String educationYearId) {
        return new ApiResponse(true,"All vedimosts of group by faculty id",type=="MONITORING" ? vedimostRepository.getVedimostByFacultyId(facultyId,educationYearId):vedimostRepository.getVedimostByFacultyId(facultyId,educationYearId,dekanatOrKafedraId));
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
    public ApiResponse getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(String dekanatOrKafedraId,String teacherId, String lessonId, String facultyId, String educationYearId,String type) {
        return new ApiResponse(true,"All vedimosts of group by faculty id",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(teacherId,lessonId,facultyId,educationYearId,dekanatOrKafedraId):vedimostRepository.getVedimostByTeacherIdAndLessonIdAndEducationYearIdAndFacultyId(teacherId,lessonId,facultyId,educationYearId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndFacultyId(String type,String dekanatOrKafedraId,String teacherId, String facultyId, String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherIdAndFacultyIdAndEducationId(teacherId,facultyId,educationYearId):vedimostRepository.getVedimostByTeacherIdAndFacultyIdAndEducationId(teacherId,facultyId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByLessonIdAndFacultyId(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, String facultyId) {
        return new ApiResponse(true,"All vedimosts by lesson and faculty",type=="MONITORING" ? vedimostRepository.getVedimostByLessonIdAndFacultyId(lessonId,facultyId,educationYearId):vedimostRepository.getVedimostByLessonIdAndFacultyId(lessonId,facultyId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByLessonIdAndGroupId(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, String groupId) {
        return new ApiResponse(true,"All vedimosts by lesson and group",type=="MONITORING" ? vedimostRepository.getVedimostByLessonIdAndGroupId(lessonId,groupId,educationYearId):vedimostRepository.getVedimostByLessonIdAndGroupId(lessonId,groupId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndGroupId(String type,String dekanatOrKafedraId,String teacherId, String educationYearId, String groupId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherIdAndGroupId(teacherId,groupId,educationYearId):vedimostRepository.getVedimostByTeacherIdAndGroupId(teacherId,groupId,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByEducationYearId(String type,String dekanatOrKafedraId,String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ?vedimostRepository.getVedimostByEducationYearId(educationYearId):vedimostRepository.getVedimostByEducationYearId(educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getLast50Vedimost(String type,String dekanatOrKafedraId) {
        return new ApiResponse(true,"Last 50 vedimosts",type=="MONITORING" ?vedimostRepository.getLast50Vedimost():vedimostRepository.getLast50Vedimost(dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByLevel(String type,String dekanatOrKafedraId,Integer level, String educationYearId) {
        return new ApiResponse(true,"Vedimosts by level",type=="MONITORING" ? vedimostRepository.getVedimostByLevel(level,educationYearId):vedimostRepository.getVedimostByLevel(level,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByLessonIdAndLevel(String type,String dekanatOrKafedraId,String educationYearId, String lessonId, Integer level) {
        return new ApiResponse(true,"All vedimosts by lesson and course", type=="MONITORING" ? vedimostRepository.getVedimostByLessonIdAndLevel(lessonId,level,educationYearId):vedimostRepository.getVedimostByLessonIdAndLevel(lessonId,level,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByFacultyIdAndLevel(String type,String dekanatOrKafedraId,String educationYearId, String facultyId, Integer level) {
        return new ApiResponse(true,"All vedimosts by faculty and course", type=="MONITORING" ?  vedimostRepository.getVedimostByFacultyIdAndLevel(facultyId,level,educationYearId): vedimostRepository.getVedimostByFacultyIdAndLevel(facultyId,level,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getVedimostByTeacherIdAndLevel(String type,String dekanatOrKafedraId,String teacherId, Integer level, String educationYearId) {
        return new ApiResponse(true,"All teacher's vedimosts by education year",type=="MONITORING" ? vedimostRepository.getVedimostByTeacherIdAndLevel(teacherId,level,educationYearId):vedimostRepository.getVedimostByTeacherIdAndLevel(teacherId,level,educationYearId,dekanatOrKafedraId));
    }

    @Override
    public ApiResponse getDataAboutVedimostByDekanat(String dekanatId, String educationYearId,String condition) {
        return new ApiResponse(true,"All data about vedimosts in dekanat",condition==null ? vedimostRepository.getDataAboutVedimostByDekanat(dekanatId,educationYearId) : vedimostRepository.getDataAboutVedimostByDekanat(dekanatId,educationYearId,condition) );
    }

    @Override
    public ApiResponse getDataAboutVedimostByKafedra(String kafedraId, String educationYearId,String condition) {
        return new ApiResponse(true,"All data about vedimosts in kafedra",condition==null?vedimostRepository.getDataAboutVedimostByKafedra(kafedraId,educationYearId):vedimostRepository.getDataAboutVedimostByKafedra(kafedraId,educationYearId,condition));
    }
}
