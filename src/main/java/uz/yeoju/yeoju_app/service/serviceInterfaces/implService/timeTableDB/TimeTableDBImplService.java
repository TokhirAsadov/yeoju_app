package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTableDB;

import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.EducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.timetableDB.CardDB;
import uz.yeoju.yeoju_app.entity.timetableDB.GroupConnectSubject;
import uz.yeoju.yeoju_app.entity.timetableDB.LessonDB;
import uz.yeoju.yeoju_app.entity.timetableDB.TeacherConnectSubject;
import uz.yeoju.yeoju_app.exceptions.UserNotFoundException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.educationYear.GroupsLessonCount;
import uz.yeoju.yeoju_app.payload.educationYear.WeekRestDtoForDean;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentStatisticsWithWeekOfEduYear;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.Table;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherData;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.TeacherStatisticsOfWeekday;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.LessonRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.educationYear.WeekOfEducationYearRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.CardDBRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.GroupConnectSubjectRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.LessonDBRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.TeacherConnectSubjectRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearImplService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@Service
public class TimeTableDBImplService implements TimeTableDBService {


    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    TeacherConnectSubjectRepository teacherConnectSubjectRepository;

    @Autowired
    GroupConnectSubjectRepository groupConnectSubjectRepository;

    @Autowired
    WeekOfEducationYearRepository weekOfEducationYearRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    LessonDBRepository lessonDBRepository;

    @Autowired
    CardDBRepository cardDBRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EducationYearRepository educationYearRepository;

    @Autowired
    DekanRepository dekanRepository;


    public static final List<Period> periods = new ArrayList<>();
    public static final List<DaysDef> daysDefs = new ArrayList<>();
    public static final List<WeeksDef> weeksDefs = new ArrayList<>();
    public static final List<TermsDef> termsDefs = new ArrayList<>();
    public static final List<Subject> subjects = new ArrayList<>();
    public static final List<Teacher> teachers = new ArrayList<>();
    public static final List<ClassRoom> classRooms = new ArrayList<>();
    public static final List<Grade> grades = new ArrayList<>();
    public static final List<Class> classes = new ArrayList<>();
    public static final List<GroupXml> groups = new ArrayList<>();
    public static final List<LessonXml> lessons = new ArrayList<>();
    public static final List<Card> cards = new ArrayList<>();




    //for medical
    public static final List<Period> periodsMed = new ArrayList<>();
    public static final List<DaysDef> daysDefsMed = new ArrayList<>();
    public static final List<WeeksDef> weeksDefsMed = new ArrayList<>();
    public static final List<TermsDef> termsDefsMed = new ArrayList<>();
    public static final List<Subject> subjectsMed = new ArrayList<>();
    public static final List<Teacher> teachersMed = new ArrayList<>();
    public static final List<ClassRoom> classRoomsMed = new ArrayList<>();
    public static final List<Grade> gradesMed = new ArrayList<>();
    public static final List<uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class> classesMed = new ArrayList<>();
    public static final List<GroupXml> groupsMed = new ArrayList<>();
    public static final List<LessonXml> lessonsMed = new ArrayList<>();
    public static final List<Card> cardsMed = new ArrayList<>();

    @Override
    public void getTimeTableByWeek(Integer year, Integer week) {
        clearTimeTable();

        String xmlFile = year+"/"+week+".xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(TimeTableDBImplService::readPeriod);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(TimeTableDBImplService::readDaysDef);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(TimeTableDBImplService::readWeeksDef);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(TimeTableDBImplService::readTermsDefs);
        rootNode.getChild("subjects").getChildren("subject").forEach(TimeTableDBImplService::readSubject);
        rootNode.getChild("teachers").getChildren("teacher").forEach(TimeTableDBImplService::readTeacher);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(TimeTableDBImplService::readClassroom);
        rootNode.getChild("grades").getChildren("grade").forEach(TimeTableDBImplService::readGrade);
        rootNode.getChild("classes").getChildren("class").forEach(TimeTableDBImplService::readClass);
        rootNode.getChild("groups").getChildren("group").forEach(TimeTableDBImplService::readGroup);
        rootNode.getChild("lessons").getChildren("lesson").forEach(TimeTableDBImplService::readLesson);
        rootNode.getChild("cards").getChildren("card").forEach(TimeTableDBImplService::readCard);
    }

    @Override
    public void getTimeTableByWeekMed(Integer year, Integer week) {
        clearTimeTableMed();

        String xmlFile = year+"/"+week+"med.xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(TimeTableDBImplService::readPeriodMed);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(TimeTableDBImplService::readDaysDefMed);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(TimeTableDBImplService::readWeeksDefMed);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(TimeTableDBImplService::readTermsDefsMed);
        rootNode.getChild("subjects").getChildren("subject").forEach(TimeTableDBImplService::readSubjectMed);
        rootNode.getChild("teachers").getChildren("teacher").forEach(TimeTableDBImplService::readTeacherMed);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(TimeTableDBImplService::readClassroomMed);
        rootNode.getChild("grades").getChildren("grade").forEach(TimeTableDBImplService::readGradeMed);
        rootNode.getChild("classes").getChildren("class").forEach(TimeTableDBImplService::readClassMed);
        rootNode.getChild("groups").getChildren("group").forEach(TimeTableDBImplService::readGroupMed);
        rootNode.getChild("lessons").getChildren("lesson").forEach(TimeTableDBImplService::readLessonMed);
        rootNode.getChild("cards").getChildren("card").forEach(TimeTableDBImplService::readCardMed);
    }

    @Override
    @Transactional
    public ApiResponseTwoObj generateNewTimeTableToDB(String educationYearId,Integer year, Integer week) {
        getTimeTableByWeek(year, week);

        Boolean b = weekOfEducationYearRepository.existsBySortNumberAndYear(week, year);
        if (!b){
            throw new UserNotFoundException("File of "+week+" does not exist!");
        }

        // create subjects from .xml to database
        subjects.forEach(s -> {
            boolean existsLessonByName = lessonRepository.existsLessonByName(s.getName());
            if (!existsLessonByName) {
                Lesson lesson = new Lesson(s.getName());
                lessonRepository.save(lesson);
            }
        });

        teachers.forEach(t -> {
            lessons.stream().filter(l->l.getTeacherIds().contains(t.getId())).collect(Collectors.toSet()).forEach(l-> {
                TeacherConnectSubject teacherConnectSubject = new TeacherConnectSubject();
                if(year ==2023 && week<44){
                    Optional<User> optionalUser = userRepository.findUserByPassportNum(t.getEmail());
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        teacherConnectSubject.setUser(user);
                        Subject subject = subjects.stream().filter(s -> s.getId().equals(l.getSubjectId())).findFirst().get();
                        Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
                        teacherConnectSubject.setLesson(lessonByName);

                        Set<Group> groupSet = new HashSet<>();
                        l.getClassIds().forEach(i -> {
                            Class aClass = classes.stream().filter(c -> c.getId().equals(i)).findFirst().get();
                            Group groupByName = groupRepository.findGroupByName(aClass.getName());
                            if (groupByName != null) {
                                groupSet.add(groupByName);
                            }
                        });
                        teacherConnectSubject.setGroups(groupSet);
                        Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
                        optionalWeekOfEducationYear.ifPresent(teacherConnectSubject::setWeeks);

                        teacherConnectSubjectRepository.save(teacherConnectSubject);
                    }
                    else {
                        System.out.println(t.getEmail());
                    }
                }
                else {
                    Optional<User> optionalUser = userRepository.findUserByLogin(t.getShortName());

                    if (optionalUser.isPresent()) {
                    try {
//                        User user = optionalUser.orElseThrow(()-> new UserNotFoundException(t.getName()+" not found teacher by id: "+t.getShortName()+"."));

                        User user = optionalUser.get();
                        teacherConnectSubject.setUser(user);
                        Subject subject = subjects.stream().filter(s -> s.getId().equals(l.getSubjectId())).findFirst().get();
                        Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
                        teacherConnectSubject.setLesson(lessonByName);

                        Set<Group> groupSet = new HashSet<>();
                        l.getClassIds().forEach(i -> {
                            Class aClass = classes.stream().filter(c -> c.getId().equals(i)).findFirst().get();
                            Group groupByName = groupRepository.findGroupByName(aClass.getName());
                            if (groupByName != null) {
                                groupSet.add(groupByName);
                            }
                        });
                        teacherConnectSubject.setGroups(groupSet);
                        Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
                        optionalWeekOfEducationYear.ifPresent(teacherConnectSubject::setWeeks);

                        teacherConnectSubjectRepository.save(teacherConnectSubject);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    }
                    else {
                        System.out.println(t.getShortName());
//                        throw new Exception(t.getShortName()+" not found teacher by id: "+t.getShortName());
                    }
                }
            });
        });


        classes.forEach(group ->{
            Group groupByName = groupRepository.findGroupByName(group.getName());
            if (groupByName != null){
                Boolean existsGroupConnectSubjectByGroupIdAndEducationYearId = groupConnectSubjectRepository.existsGroupConnectSubjectByGroupIdAndEducationYearId(groupByName.getId(), educationYearId);
                if (existsGroupConnectSubjectByGroupIdAndEducationYearId){
                    Optional<GroupConnectSubject> optional = groupConnectSubjectRepository.findGroupConnectSubjectByGroupIdAndEducationYearId(groupByName.getId(), educationYearId);
                    if (optional.isPresent()) {
                        GroupConnectSubject groupConnectSubject = optional.get();
                        Set<String> ids = new HashSet<String>();
                        lessons.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                        Set<Lesson> lessonSet = new HashSet<Lesson>(groupConnectSubject.getLessons());
                        ids.forEach(i -> {
                            Subject ss = subjects.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                            Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                            lessonSet.add(lessonByName);
                        });
                        groupConnectSubject.setLessons(lessonSet);
                        groupConnectSubjectRepository.save(groupConnectSubject);
                    }
                    else {
                        Optional<EducationYear> yearOptional = educationYearRepository.findById(educationYearId);
                        if (yearOptional.isPresent()) {
                            EducationYear educationYear = yearOptional.get();
                            GroupConnectSubject groupConnectSubject = new GroupConnectSubject();
                            groupConnectSubject.setEducationYear(educationYear);
                            groupConnectSubject.setGroup(groupByName);
                            Set<String> ids = new HashSet<String>();
                            lessons.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                            Set<Lesson> lessonSet = new HashSet<Lesson>();
                            ids.forEach(i -> {
                                Subject ss = subjects.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                                Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                                lessonSet.add(lessonByName);
                            });
                            groupConnectSubject.setLessons(lessonSet);
                            groupConnectSubjectRepository.save(groupConnectSubject);
                        }
                    }
                }
                else {
                    Optional<EducationYear> yearOptional = educationYearRepository.findById(educationYearId);
                    if (yearOptional.isPresent()) {
                        EducationYear educationYear = yearOptional.get();
                        GroupConnectSubject groupConnectSubject = new GroupConnectSubject();
                        groupConnectSubject.setEducationYear(educationYear);
                        groupConnectSubject.setGroup(groupByName);
                        Set<String> ids = new HashSet<String>();
                        lessons.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                        Set<Lesson> lessonSet = new HashSet<Lesson>();
                        ids.forEach(i -> {
                            Subject ss = subjects.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                            Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                            lessonSet.add(lessonByName);
                        });
                        groupConnectSubject.setLessons(lessonSet);
                        groupConnectSubjectRepository.save(groupConnectSubject);
                    }
                }
            }
        });

        // create LessonDB from .xml to database
        lessons.forEach(lesson -> {

            LessonDB lessonDB = new LessonDB();

            Subject subject = subjects.stream().filter(s -> s.getId().equals(lesson.getSubjectId())).findFirst().get();
            Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
            lessonDB.setSubject(lessonByName);
            Set<User> users = new HashSet<>();
            for (String teacherId : lesson.getTeacherIds()) {
                for (Teacher teacher : teachers) {
                    if (teacher.getId().equals(teacherId)){

                        if(year ==2023 && week<44){
                            Optional<User> optionalUser = userRepository.findUserByPassportNum(teacher.getEmail());
                            if (optionalUser.isPresent()) {
                                User user = optionalUser.get();
                                users.add(user);
                            }
                            break;
                        }
                        else {
                            Optional<User> optionalUser = userRepository.findUserByLogin(teacher.getShortName());
                            if (optionalUser.isPresent()) {
                                User user = optionalUser.get();
                                users.add(user);
                            }
                            break;
                        }

                    }
                }
            }
            lessonDB.setTeachers(users);
            Set<Group> groupSet = new HashSet<>();
            if (lesson.getClassIds().get(0).length()!=0) {
                lesson.getClassIds().forEach(classId -> {
                    Class aClass = classes.stream().filter(c -> c.getId().equals(classId)).findFirst().get();
//                    boolean existsGroupByName = groupRepository.existsGroupByName(aClass.getName());
                    Group groupByName = groupRepository.findGroupByName(aClass.getName());
                    if(groupByName != null) {
                        groupSet.add(groupByName);
                    }
                });
            }



            lessonDB.setGroups(groupSet);
            Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
            optionalWeekOfEducationYear.ifPresent(lessonDB::setWeekOfEducationYear);
            lessonDBRepository.saveAndFlush(lessonDB);
            WeekOfEducationYear weekOfEducationYear = optionalWeekOfEducationYear.get();

            Set<Card> cardSet = cards.stream().filter(card -> card.getLessonId().equals(lesson.getId())).collect(Collectors.toSet());
            cardSet.forEach(card -> {

                CardDB cardDB = new CardDB();
                cardDB.setLesson(lessonDB);
                cardDB.setWeekOfEducationYear(weekOfEducationYear);
                Period period = periods.stream().filter(p-> p.getName().equals(card.getPeriod())).findFirst().get();
                cardDB.setBetweenDuringDate(period.getStartTime()+" - "+period.getEndTime());
                cardDB.setPeriod(period.getPeriod());
                System.out.println(card+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().get(0).length()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().size()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().isEmpty()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                if(card.getClassroomIds().get(0).length()!=0){
                    ClassRoom room = classRooms.stream().filter(classRoom -> classRoom.getId().equals(card.getClassroomIds().get(0))).findFirst().get();
                    cardDB.setClassroom(room.getName());
                }
                for (DaysDef daysDef : daysDefs) {
                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                            cardDB.setDay(1);
                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                            cardDB.setDay(2);
                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                            cardDB.setDay(3);
                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                            cardDB.setDay(4);
                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                            cardDB.setDay(5);
                        if (daysDef.getDays().get(0).equals("000001"))
                            cardDB.setDay(6);
                        cardDB.setDayName(daysDef.getName()+"-"+daysDef.getShortName());
                        break;
                    }
                }
                cardDBRepository.save(cardDB);

            });

        });


        return new ApiResponseTwoObj(true,"success message");
    }

    @Override
    public ApiResponseTwoObj generateNewTimeTableToDBMed(String educationYearId, Integer year, Integer week) {

        getTimeTableByWeekMed(year, week);

        Boolean b = weekOfEducationYearRepository.existsBySortNumberAndYear(week, year);
        if (!b){
            throw new UserNotFoundException("File of "+week+" does not exist!");
        }

        // create subjects from .xml to database
        subjectsMed.forEach(s -> {
            boolean existsLessonByName = lessonRepository.existsLessonByName(s.getName());
            if (!existsLessonByName) {
                Lesson lesson = new Lesson(s.getName());
                lessonRepository.save(lesson);
            }
        });

        teachersMed.forEach(t -> {
            lessonsMed.stream().filter(l->l.getTeacherIds().contains(t.getId())).collect(Collectors.toSet()).forEach(l-> {
                TeacherConnectSubject teacherConnectSubject = new TeacherConnectSubject();
                if(year ==2023 && week<44){
                    Optional<User> optionalUser = userRepository.findUserByPassportNum(t.getEmail());
                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        teacherConnectSubject.setUser(user);
                        Subject subject = subjectsMed.stream().filter(s -> s.getId().equals(l.getSubjectId())).findFirst().get();
                        Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
                        teacherConnectSubject.setLesson(lessonByName);

                        Set<Group> groupSet = new HashSet<>();
                        l.getClassIds().forEach(i -> {
                            Class aClass = classesMed.stream().filter(c -> c.getId().equals(i)).findFirst().get();
                            Group groupByName = groupRepository.findGroupByName(aClass.getName());
                            if (groupByName != null) {
                                groupSet.add(groupByName);
                            }
                        });
                        teacherConnectSubject.setGroups(groupSet);
                        Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
                        optionalWeekOfEducationYear.ifPresent(teacherConnectSubject::setWeeks);

                        teacherConnectSubjectRepository.save(teacherConnectSubject);
                    }
                    else {
                        System.out.println(t.getEmail());
                    }
                }
                else {
                    Optional<User> optionalUser = userRepository.findUserByLogin(t.getShortName());

                    if (optionalUser.isPresent()) {
                    try {
//                        User user = optionalUser.orElseThrow(()-> new UserNotFoundException(t.getName()+" not found teacher by id: "+t.getShortName()+"."));

                            User user = optionalUser.get();

                            teacherConnectSubject.setUser(user);
                            Subject subject = subjectsMed.stream().filter(s -> s.getId().equals(l.getSubjectId())).findFirst().get();
                            Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
                            teacherConnectSubject.setLesson(lessonByName);

                            Set<Group> groupSet = new HashSet<>();
                            l.getClassIds().forEach(i -> {
                                Class aClass = classesMed.stream().filter(c -> c.getId().equals(i)).findFirst().get();
                                Group groupByName = groupRepository.findGroupByName(aClass.getName());
                                if (groupByName != null) {
                                    groupSet.add(groupByName);
                                }
                            });
                            teacherConnectSubject.setGroups(groupSet);
                            Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
                            optionalWeekOfEducationYear.ifPresent(teacherConnectSubject::setWeeks);

                            teacherConnectSubjectRepository.save(teacherConnectSubject);
//                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    }
                    else {
//                        System.out.println(t.getShortName());
//                        throw new Exception(t.getShortName()+" not found teacher by id: "+t.getShortName());
                    }
                }
            });
        });


        classesMed.forEach(group ->{
            Group groupByName = groupRepository.findGroupByName(group.getName());
            if (groupByName != null){
                Boolean existsGroupConnectSubjectByGroupIdAndEducationYearId = groupConnectSubjectRepository.existsGroupConnectSubjectByGroupIdAndEducationYearId(groupByName.getId(), educationYearId);
                if (existsGroupConnectSubjectByGroupIdAndEducationYearId){
                    Optional<GroupConnectSubject> optional = groupConnectSubjectRepository.findGroupConnectSubjectByGroupIdAndEducationYearId(groupByName.getId(), educationYearId);
                    if (optional.isPresent()) {
                        GroupConnectSubject groupConnectSubject = optional.get();
                        Set<String> ids = new HashSet<String>();
                        lessonsMed.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                        Set<Lesson> lessonSet = new HashSet<Lesson>(groupConnectSubject.getLessons());
                        ids.forEach(i -> {
                            Subject ss = subjectsMed.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                            Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                            lessonSet.add(lessonByName);
                        });
                        groupConnectSubject.setLessons(lessonSet);
                        groupConnectSubjectRepository.save(groupConnectSubject);
                    }
                    else {
                        Optional<EducationYear> yearOptional = educationYearRepository.findById(educationYearId);
                        if (yearOptional.isPresent()) {
                            EducationYear educationYear = yearOptional.get();
                            GroupConnectSubject groupConnectSubject = new GroupConnectSubject();
                            groupConnectSubject.setEducationYear(educationYear);
                            groupConnectSubject.setGroup(groupByName);
                            Set<String> ids = new HashSet<String>();
                            lessonsMed.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                            Set<Lesson> lessonSet = new HashSet<Lesson>();
                            ids.forEach(i -> {
                                Subject ss = subjectsMed.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                                Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                                lessonSet.add(lessonByName);
                            });
                            groupConnectSubject.setLessons(lessonSet);
                            groupConnectSubjectRepository.save(groupConnectSubject);
                        }
                    }
                }
                else {
                    Optional<EducationYear> yearOptional = educationYearRepository.findById(educationYearId);
                    if (yearOptional.isPresent()) {
                        EducationYear educationYear = yearOptional.get();
                        GroupConnectSubject groupConnectSubject = new GroupConnectSubject();
                        groupConnectSubject.setEducationYear(educationYear);
                        groupConnectSubject.setGroup(groupByName);
                        Set<String> ids = new HashSet<String>();
                        lessonsMed.stream().filter(l -> l.getClassIds().contains(group.getId())).collect(Collectors.toSet()).forEach(l->ids.add(l.getSubjectId()));
                        Set<Lesson> lessonSet = new HashSet<Lesson>();
                        ids.forEach(i -> {
                            Subject ss = subjectsMed.stream().filter(s -> s.getId().equals(i)).findFirst().get();
                            Lesson lessonByName = lessonRepository.getLessonByName(ss.getName());
                            lessonSet.add(lessonByName);
                        });
                        groupConnectSubject.setLessons(lessonSet);
                        groupConnectSubjectRepository.save(groupConnectSubject);
                    }
                }
            }
        });

        // create LessonDB from .xml to database
        lessonsMed.forEach(lesson -> {

            LessonDB lessonDB = new LessonDB();

            Subject subject = subjectsMed.stream().filter(s -> s.getId().equals(lesson.getSubjectId())).findFirst().get();
            Lesson lessonByName = lessonRepository.getLessonByName(subject.getName());
            lessonDB.setSubject(lessonByName);
            Set<User> users = new HashSet<>();
            for (String teacherId : lesson.getTeacherIds()) {
                for (Teacher teacher : teachersMed) {
                    if (teacher.getId().equals(teacherId)){

                        if(year ==2023 && week<44){
                            Optional<User> optionalUser = userRepository.findUserByPassportNum(teacher.getEmail());
                            if (optionalUser.isPresent()) {
                                User user = optionalUser.get();
                                users.add(user);
                            }
                            break;
                        }
                        else {
                            Optional<User> optionalUser = userRepository.findUserByLogin(teacher.getShortName());
                            if (optionalUser.isPresent()) {
                                User user = optionalUser.get();
                                users.add(user);
                            }
                            break;
                        }

                    }
                }
            }
            lessonDB.setTeachers(users);
            Set<Group> groupSet = new HashSet<>();
            if (lesson.getClassIds().get(0).length()!=0) {
                lesson.getClassIds().forEach(classId -> {
                    Class aClass = classesMed.stream().filter(c -> c.getId().equals(classId)).findFirst().get();
//                    boolean existsGroupByName = groupRepository.existsGroupByName(aClass.getName());
                    Group groupByName = groupRepository.findGroupByName(aClass.getName());
                    if(groupByName != null) {
                        groupSet.add(groupByName);
                    }
                });
            }



            lessonDB.setGroups(groupSet);
            Optional<WeekOfEducationYear> optionalWeekOfEducationYear = weekOfEducationYearRepository.findWeekOfEducationBySortNumberAndYear(week, year);
            optionalWeekOfEducationYear.ifPresent(lessonDB::setWeekOfEducationYear);
            lessonDBRepository.saveAndFlush(lessonDB);
            WeekOfEducationYear weekOfEducationYear = optionalWeekOfEducationYear.get();

            Set<Card> cardSet = cardsMed.stream().filter(card -> card.getLessonId().equals(lesson.getId())).collect(Collectors.toSet());
            cardSet.forEach(card -> {

                CardDB cardDB = new CardDB();
                cardDB.setLesson(lessonDB);
                cardDB.setWeekOfEducationYear(weekOfEducationYear);
                Period period = periodsMed.stream().filter(p-> p.getName().equals(card.getPeriod())).findFirst().get();
                cardDB.setBetweenDuringDate(period.getStartTime()+" - "+period.getEndTime());
                cardDB.setPeriod(period.getPeriod());
                System.out.println(card+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().get(0).length()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().size()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println(card.getClassroomIds().isEmpty()+"-----------------------------------------------------------------------------------------------------------------------------------------------------------");
                if(card.getClassroomIds().get(0).length()!=0){
                    ClassRoom room = classRoomsMed.stream().filter(classRoom -> classRoom.getId().equals(card.getClassroomIds().get(0))).findFirst().get();
                    cardDB.setClassroom(room.getName());
                }
                for (DaysDef daysDef : daysDefsMed) {
                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                            cardDB.setDay(1);
                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                            cardDB.setDay(2);
                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                            cardDB.setDay(3);
                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                            cardDB.setDay(4);
                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                            cardDB.setDay(5);
                        if (daysDef.getDays().get(0).equals("000001"))
                            cardDB.setDay(6);
                        cardDB.setDayName(daysDef.getName()+"-"+daysDef.getShortName());
                        break;
                    }
                }
                cardDBRepository.save(cardDB);

            });

        });

        return new ApiResponseTwoObj(true,"success message");
    }


    public List<Show> timeTable(String groupName){
        List<Show> shows= new ArrayList<>();
        for (Card card : cards) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : classes) {// guruh
                            if (aClass.getId().equals(classId) && aClass.getName().equals(groupName)){
                                Show show = new Show();
                                List<String> teachers1 = new ArrayList<>();
                                List<Teacher> teachers2 = new ArrayList<>();
                                for (String classroomId : card.getClassroomIds()) {
                                    for (ClassRoom room : classRooms) {// xona
                                        if (room.getId().equals(classroomId)){
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
                                }
                                for (Period period : periods) {
                                    if (period.getName().equals(card.getPeriod())){
                                        show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                        show.setHourNumber(period.getPeriod());
                                        break;
                                    }
                                }
                                for (DaysDef daysDef : daysDefs) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
                                        if (daysDef.getDays().get(0).equals("000001"))
                                            show.setDayNumber(6);
                                        show.setDaysName(daysDef.getName());
                                        break;
                                    }
                                }
                                for (Subject subject : subjects) {
                                    if (subject.getId().equals(lesson.getSubjectId())){
                                        show.setLessonName(subject.getName());
                                        break;
                                    }
                                }
                                for (String teacherId : lesson.getTeacherIds()) {
                                    for (Teacher teacher : teachers) {
                                        if (teacher.getId().equals(teacherId)){
                                            teachers2.add(teacher);
                                            teachers1.add(teacher.getFirstName()+" "+teacher.getLastName());
                                            break;
                                        }
                                    }
                                }
                                show.setTeachers(teachers2);
                                show.setTeacherName(teachers1);
                                shows.add(show);
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    public Set<Show> timeTable(Set<Card> cardSet){
        Set<Show> shows= new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : classes) {// guruh
                            Show show = new Show();
                            List<String> teachers1 = new ArrayList<>();
                            List<Teacher> teachers2 = new ArrayList<>();
                            for (String classroomId : card.getClassroomIds()) {
                                for (ClassRoom room : classRooms) {// xona
                                    if (room.getId().equals(classroomId)){
                                        show.setRoom(room.getName());
                                        break;
                                    }
                                }
                            }
                            for (Period period : periods) {
                                if (period.getName().equals(card.getPeriod())){
                                    show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                    show.setHourNumber(period.getPeriod());
                                    break;
                                }
                            }
                            for (DaysDef daysDef : daysDefs) {
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    System.out.println(daysDef.getDays().get(0)+" < --------------day number--------------");
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        show.setDayNumber(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        show.setDayNumber(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        show.setDayNumber(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        show.setDayNumber(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        show.setDayNumber(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
                                        show.setDayNumber(6);
                                    show.setDaysName(daysDef.getShortName());
                                    break;
                                }
                            }
                            for (Subject subject : subjects) {
                                if (subject.getId().equals(lesson.getSubjectId())){
                                    show.setLessonName(subject.getName());
                                    break;
                                }
                            }
                            for (String teacherId : lesson.getTeacherIds()) {
                                for (Teacher teacher : teachers) {
                                    if (teacher.getId().equals(teacherId)){
                                        teachers2.add(teacher);
                                        teachers1.add(teacher.getFirstName()+" "+teacher.getLastName());
                                        break;
                                    }
                                }
                            }

                            show.setTeachers(teachers2);
                            show.setTeacherName(teachers1);
                            shows.add(show);
                            break;

                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    public Set<Show> timeTable2(Set<Card> cardSet,Integer year,Integer week){
        Set<Show> shows= new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : classes) {// guruh
                            Show show = new Show();
                            List<String> teachers1 = new ArrayList<>();
                            List<Teacher> teachers2 = new ArrayList<>();
                            for (String classroomId : card.getClassroomIds()) {
                                for (ClassRoom room : classRooms) {// xona
                                    if (room.getId().equals(classroomId)){
                                        show.setRoom(room.getName());
                                        break;
                                    }
                                }
                            }
                            for (Period period : periods) {
                                if (period.getName().equals(card.getPeriod())){
                                    show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                    show.setHourNumber(period.getPeriod());
                                    break;
                                }
                            }
                            for (DaysDef daysDef : daysDefs) {
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        show.setDayNumber(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        show.setDayNumber(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        show.setDayNumber(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        show.setDayNumber(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        show.setDayNumber(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
                                        show.setDayNumber(6);
                                    show.setDaysName(daysDef.getShortName());
                                    break;
                                }
                            }
                            for (Subject subject : subjects) {
                                if (subject.getId().equals(lesson.getSubjectId())){
                                    show.setLessonName(subject.getName());
                                    break;
                                }
                            }
                            for (String teacherId : lesson.getTeacherIds()) {
                                for (Teacher teacher : teachers) {
                                    if (teacher.getId().equals(teacherId)){
                                        teachers2.add(teacher);
                                        teachers1.add(teacher.getFirstName()+" "+teacher.getLastName());
                                        break;
                                    }
                                }
                            }
                            if (teachers2 !=null && teachers2.size() > 0) {
                                if(year ==2023 && week<44){
                                    show.setKafedraId(userRepository.getKafedraIdByUserPassport(teachers2.get(0).getEmail()));
                                }
                                else {
                                    show.setKafedraId(userRepository.getKafedraIdByUserPassportLogin(teachers2.get(0).getShortName()));
                                }
                            }
                            show.setTeachers(teachers2);
                            show.setTeacherName(teachers1);
                            shows.add(show);
                            break;

                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    //====================================  clear  ==========================================================
    public void clearTimeTable(){
        periods.clear();
        daysDefs.clear();
        weeksDefs.clear();
        termsDefs.clear();
        subjects.clear();
        teachers.clear();
        classRooms.clear();
        classes.clear();
        groups.clear();
        lessons.clear();
        cards.clear();
    }

    public void clearTimeTableMed(){
        periodsMed.clear();
        daysDefsMed.clear();
        weeksDefsMed.clear();
        termsDefsMed.clear();
        subjectsMed.clear();
        teachersMed.clear();
        classRoomsMed.clear();
        classesMed.clear();
        groupsMed.clear();
        lessonsMed.clear();
        cardsMed.clear();
    }

    //====================================  Period  ==========================================================
    public static void readPeriod(Element employeeNode)
    {
        periods.add(
                new Period(
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        employeeNode.getAttributeValue("starttime"),
                        employeeNode.getAttributeValue("endtime")
                )
        );

        //Country
//        System.out.println("country : " + employeeNode.getChild("country").getText());
//        /**Read Department Content*/
//        employeeNode.getChildren("department").forEach( HowToGetItemFromXmlApplication::readDepartmentNode );
    }

    public static void readPeriodMed(Element employeeNode)
    {
        periodsMed.add(
                new Period(
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("short")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        employeeNode.getAttributeValue("starttime"),
                        employeeNode.getAttributeValue("endtime")
                )
        );

        //Country
//        System.out.println("country : " + employeeNode.getChild("country").getText());
//        /**Read Department Content*/
//        employeeNode.getChildren("department").forEach( HowToGetItemFromXmlApplication::readDepartmentNode );
    }
    //====================================  DaysDef  ==========================================================
    public static void readDaysDef(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("days");
        List<String> array = array(days);
        daysDefs.add(
                new DaysDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }

    public static void readDaysDefMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("days");
        List<String> array = array(days);
        daysDefsMed.add(
                new DaysDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  WeeksDef  ==========================================================
    public static void readWeeksDef(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("weeks");
        List<String> array = array(days);
        weeksDefs.add(
                new WeeksDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }

    public static void readWeeksDefMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("weeks");
        List<String> array = array(days);
        weeksDefsMed.add(
                new WeeksDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  TermsDefs  ==========================================================
    public static void readTermsDefs(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("terms");
        List<String> array = array(days);
        termsDefs.add(
                new TermsDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    public static void readTermsDefsMed(Element employeeNode)
    {
        String days = employeeNode.getAttributeValue("terms");
        List<String> array = array(days);
        termsDefsMed.add(
                new TermsDef(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        array
                )
        );
    }
    //====================================  Subject  ==========================================================
    public static void readSubject(Element employeeNode)
    {
        subjects.add(
                new Subject(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );

    }
    public static void readSubjectMed(Element employeeNode)
    {
        subjectsMed.add(
                new Subject(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );

    }
    //====================================  Teacher  ==========================================================
    public static void readTeacher(Element employeeNode)
    {
        teachers.add(
                new Teacher(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("firstname"),
                        employeeNode.getAttributeValue("lastname"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("gender"),
                        employeeNode.getAttributeValue("email"),
                        employeeNode.getAttributeValue("mobile"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readTeacherMed(Element employeeNode)
    {
        teachersMed.add(
                new Teacher(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("firstname"),
                        employeeNode.getAttributeValue("lastname"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("gender"),
                        employeeNode.getAttributeValue("email"),
                        employeeNode.getAttributeValue("mobile"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Classrooms  ==========================================================
    public static void readClassroom(Element employeeNode)
    {
        classRooms.add(
                new ClassRoom(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readClassroomMed(Element employeeNode)
    {
        classRoomsMed.add(
                new ClassRoom(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Grade  ==========================================================
    public static void readGrade(Element employeeNode)
    {
        grades.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }

    public static void readGradeMed(Element employeeNode)
    {
        gradesMed.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }
    //====================================  Class  ==========================================================
    public static void readClass(Element employeeNode)
    {
        classes.add(
                new Class(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("teacherid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        employeeNode.getAttributeValue("grade"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readClassMed(Element employeeNode)
    {
        classesMed.add(
                new uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("teacherid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        employeeNode.getAttributeValue("grade"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Group  ==========================================================
    public static void readGroup(Element employeeNode)
    {
        groups.add(
                new GroupXml(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("classid"),
                        array(employeeNode.getAttributeValue("studentids")),
                        employeeNode.getAttributeValue("entireclass"),
                        employeeNode.getAttributeValue("divisiontag")
                )
        );
    }

    public static void readGroupMed(Element employeeNode)
    {
        groupsMed.add(
                new GroupXml(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("classid"),
                        array(employeeNode.getAttributeValue("studentids")),
                        employeeNode.getAttributeValue("entireclass"),
                        employeeNode.getAttributeValue("divisiontag")
                )
        );
    }
    //====================================  Lesson  ==========================================================
    public static void readLesson(Element employeeNode)
    {
        lessons.add(
                new LessonXml(
                        employeeNode.getAttributeValue("id"),
                        array(employeeNode.getAttributeValue("classids")),
                        employeeNode.getAttributeValue("subjectid"),
                        employeeNode.getAttributeValue("periodspercard"),
                        employeeNode.getAttributeValue("periodsperweek"),
                        array(employeeNode.getAttributeValue("teacherids")),
                        array(employeeNode.getAttributeValue("groupids")),
                        employeeNode.getAttributeValue("seminargroup"),
                        employeeNode.getAttributeValue("termsdefid"),
                        employeeNode.getAttributeValue("weeksdefid"),
                        employeeNode.getAttributeValue("daysdefid"),
                        employeeNode.getAttributeValue("capacity"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readLessonMed(Element employeeNode)
    {
        lessonsMed.add(
                new LessonXml(
                        employeeNode.getAttributeValue("id"),
                        array(employeeNode.getAttributeValue("classids")),
                        employeeNode.getAttributeValue("subjectid"),
                        employeeNode.getAttributeValue("periodspercard"),
                        employeeNode.getAttributeValue("periodsperweek"),
                        array(employeeNode.getAttributeValue("teacherids")),
                        array(employeeNode.getAttributeValue("groupids")),
                        employeeNode.getAttributeValue("seminargroup"),
                        employeeNode.getAttributeValue("termsdefid"),
                        employeeNode.getAttributeValue("weeksdefid"),
                        employeeNode.getAttributeValue("daysdefid"),
                        employeeNode.getAttributeValue("capacity"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }
    //====================================  Cards  ==========================================================
    public static void readCard(Element employeeNode)
    {
        cards.add(
                new Card(
                        employeeNode.getAttributeValue("lessonid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        array(employeeNode.getAttributeValue("weeks")),
                        array(employeeNode.getAttributeValue("terms")),
                        array(employeeNode.getAttributeValue("days"))
                )
        );
    }

    public static void readCardMed(Element employeeNode)
    {
        cardsMed.add(
                new Card(
                        employeeNode.getAttributeValue("lessonid"),
                        array(employeeNode.getAttributeValue("classroomids")),
                        Integer.valueOf(employeeNode.getAttributeValue("period")),
                        array(employeeNode.getAttributeValue("weeks")),
                        array(employeeNode.getAttributeValue("terms")),
                        array(employeeNode.getAttributeValue("days"))
                )
        );
    }
    public static List<String> array(String str){
        boolean has = true;
        List<String> arr = new ArrayList<>();

        while (has){
            int index = str.indexOf(',');
            if (index == -1) {
                arr.add(str);
                has = false;
            }
            else {
                arr.add(str.substring(0,index));
                str = str.substring(index+1);
            }
        }

        return arr;
    }
}
