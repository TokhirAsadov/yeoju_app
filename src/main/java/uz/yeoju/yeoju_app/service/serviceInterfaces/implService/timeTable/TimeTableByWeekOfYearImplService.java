package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.timetableDB.DailyTeacherMissedLesson;
import uz.yeoju.yeoju_app.payload.*;
import uz.yeoju.yeoju_app.payload.educationYear.GroupsLessonCount;
import uz.yeoju.yeoju_app.payload.educationYear.WeekRestDtoForDean;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import org.jdom2.Document;
import org.jdom2.Element;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentStatisticsWithWeekOfEduYear;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.*;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetLessonStatistics31;
import uz.yeoju.yeoju_app.payload.resDto.user.timeTableStatistics.*;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.RoomRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.DailyTeacherMissedLessonRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.IsoFields;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@Service
public class TimeTableByWeekOfYearImplService implements TimeTableByWeekOfYearService {


    private final UserRepository userRepository;


    private final EducationYearRepository educationYearRepository;


    private final DekanRepository dekanRepository;


    private final GroupRepository groupRepository;


    private final TimeTableByWeekOfYearImplService2 service2;


    private final KafedraRepository kafedraRepository;
    private final DailyTeacherMissedLessonRepository dailyTeacherMissedLessonRepository;


    public static final List<Period> periods = Collections.synchronizedList(new ArrayList<>());
    public static final List<DaysDef> daysDefs = Collections.synchronizedList(new ArrayList<>());
    public static final List<WeeksDef> weeksDefs = Collections.synchronizedList(new ArrayList<>());
    public static final List<TermsDef> termsDefs = Collections.synchronizedList(new ArrayList<>());
    public static final List<Subject> subjects = Collections.synchronizedList(new ArrayList<>());
    public static final List<Teacher> teachers = Collections.synchronizedList(new ArrayList<>());
    public static final List<ClassRoom> classRooms = Collections.synchronizedList(new ArrayList<>());
    public static final List<Grade> grades = Collections.synchronizedList(new ArrayList<>());
    public static final List<uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class> classes = Collections.synchronizedList(new ArrayList<>());
    public static final List<GroupXml> groups = Collections.synchronizedList(new ArrayList<>());
    public static final List<LessonXml> lessons = Collections.synchronizedList(new ArrayList<>());
    public static final List<Card> cards = Collections.synchronizedList(new ArrayList<>());


    //for medical
    public static final List<Period> periodsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<DaysDef> daysDefsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<WeeksDef> weeksDefsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<TermsDef> termsDefsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<Subject> subjectsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<Teacher> teachersMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<ClassRoom> classRoomsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<Grade> gradesMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class> classesMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<GroupXml> groupsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<LessonXml> lessonsMed = Collections.synchronizedList(new ArrayList<>());
    public static final List<Card> cardsMed = Collections.synchronizedList(new ArrayList<>());
    private final RoomRepository roomRepository;

    public TimeTableByWeekOfYearImplService(UserRepository userRepository, EducationYearRepository educationYearRepository, DekanRepository dekanRepository, GroupRepository groupRepository, TimeTableByWeekOfYearImplService2 service2, KafedraRepository kafedraRepository, DailyTeacherMissedLessonRepository dailyTeacherMissedLessonRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.educationYearRepository = educationYearRepository;
        this.dekanRepository = dekanRepository;
        this.groupRepository = groupRepository;
        this.service2 = service2;
        this.kafedraRepository = kafedraRepository;
        this.dailyTeacherMissedLessonRepository = dailyTeacherMissedLessonRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public void getTimeTableByWeek(Integer week) {

        clearTimeTable();

        String xmlFile = week + ".xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(TimeTableByWeekOfYearImplService::readPeriod);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(TimeTableByWeekOfYearImplService::readDaysDef);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(TimeTableByWeekOfYearImplService::readWeeksDef);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(TimeTableByWeekOfYearImplService::readTermsDefs);
        rootNode.getChild("subjects").getChildren("subject").forEach(TimeTableByWeekOfYearImplService::readSubject);
        rootNode.getChild("teachers").getChildren("teacher").forEach(TimeTableByWeekOfYearImplService::readTeacher);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(TimeTableByWeekOfYearImplService::readClassroom);
        rootNode.getChild("grades").getChildren("grade").forEach(TimeTableByWeekOfYearImplService::readGrade);
        rootNode.getChild("classes").getChildren("class").forEach(TimeTableByWeekOfYearImplService::readClass);
        rootNode.getChild("groups").getChildren("group").forEach(TimeTableByWeekOfYearImplService::readGroup);
        rootNode.getChild("lessons").getChildren("lesson").forEach(TimeTableByWeekOfYearImplService::readLesson);
        rootNode.getChild("cards").getChildren("card").forEach(TimeTableByWeekOfYearImplService::readCard);
    }

    @Override
    public void getTimeTableByWeek(Integer year, Integer week) {
        clearTimeTable();
        String xmlFile = year + "/" + week + ".xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();

        // Har bir tagni alohida future’da ishlatamiz
        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() ->
                rootNode.getChild("periods").getChildren("period")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readPeriod));

        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() ->
                rootNode.getChild("daysdefs").getChildren("daysdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readDaysDef));

        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() ->
                rootNode.getChild("weeksdefs").getChildren("weeksdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readWeeksDef));

        CompletableFuture<Void> f4 = CompletableFuture.runAsync(() ->
                rootNode.getChild("termsdefs").getChildren("termsdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readTermsDefs));

        CompletableFuture<Void> f5 = CompletableFuture.runAsync(() ->
                rootNode.getChild("subjects").getChildren("subject")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readSubject));

        CompletableFuture<Void> f6 = CompletableFuture.runAsync(() ->
                rootNode.getChild("teachers").getChildren("teacher")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readTeacher));

        CompletableFuture<Void> f7 = CompletableFuture.runAsync(() ->
                rootNode.getChild("classrooms").getChildren("classroom")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readClassroom));

        CompletableFuture<Void> f8 = CompletableFuture.runAsync(() ->
                rootNode.getChild("grades").getChildren("grade")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readGrade));

        CompletableFuture<Void> f9 = CompletableFuture.runAsync(() ->
                rootNode.getChild("classes").getChildren("class")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readClass));

        CompletableFuture<Void> f10 = CompletableFuture.runAsync(() ->
                rootNode.getChild("groups").getChildren("group")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readGroup));

        CompletableFuture<Void> f11 = CompletableFuture.runAsync(() ->
                rootNode.getChild("lessons").getChildren("lesson")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readLesson));

        CompletableFuture<Void> f12 = CompletableFuture.runAsync(() ->
                rootNode.getChild("cards").getChildren("card")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readCard));

        // barcha ishlar tugaguncha kutamiz
        CompletableFuture.allOf(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12).join();
    }

    @Override
    public void getTimeTableByWeekMed(Integer year, Integer week) {
        clearTimeTableMed();
        String xmlFile = year + "/" + week + "med.xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();

        CompletableFuture<Void> f1 = CompletableFuture.runAsync(() ->
                rootNode.getChild("periods").getChildren("period")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readPeriodMed));

        CompletableFuture<Void> f2 = CompletableFuture.runAsync(() ->
                rootNode.getChild("daysdefs").getChildren("daysdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readDaysDefMed));

        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() ->
                rootNode.getChild("weeksdefs").getChildren("weeksdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readWeeksDefMed));

        CompletableFuture<Void> f4 = CompletableFuture.runAsync(() ->
                rootNode.getChild("termsdefs").getChildren("termsdef")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readTermsDefsMed));

        CompletableFuture<Void> f5 = CompletableFuture.runAsync(() ->
                rootNode.getChild("subjects").getChildren("subject")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readSubjectMed));

        CompletableFuture<Void> f6 = CompletableFuture.runAsync(() ->
                rootNode.getChild("teachers").getChildren("teacher")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readTeacherMed));

        CompletableFuture<Void> f7 = CompletableFuture.runAsync(() ->
                rootNode.getChild("classrooms").getChildren("classroom")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readClassroomMed));

        CompletableFuture<Void> f8 = CompletableFuture.runAsync(() ->
                rootNode.getChild("grades").getChildren("grade")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readGradeMed));

        CompletableFuture<Void> f9 = CompletableFuture.runAsync(() ->
                rootNode.getChild("classes").getChildren("class")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readClassMed));

        CompletableFuture<Void> f10 = CompletableFuture.runAsync(() ->
                rootNode.getChild("groups").getChildren("group")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readGroupMed));

        CompletableFuture<Void> f11 = CompletableFuture.runAsync(() ->
                rootNode.getChild("lessons").getChildren("lesson")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readLessonMed));

        CompletableFuture<Void> f12 = CompletableFuture.runAsync(() ->
                rootNode.getChild("cards").getChildren("card")
                        .parallelStream()
                        .forEach(TimeTableByWeekOfYearImplService::readCardMed));

        CompletableFuture.allOf(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12).join();
    }

    @Override
    public ApiResponseTwoObj getStatisticsForDeanDashboard(String educationYearId, String eduType, String eduTypeId, String facultyId, String facultyShortName) {
        Set<WeekRestDtoForDean> weeks = Objects.equals(eduType, "SIRTQI") ? educationYearRepository.getWeeksByEduIdAndEduType(educationYearId, eduType) : Objects.equals(eduType, "KUNDUZGI") || Objects.equals(eduType, "KECHKI") ? educationYearRepository.getWeeksByEduIdAndEduType(educationYearId, "KUNDUZGI_KECHKI") : educationYearRepository.getWeeksByEduIdAndEduType(educationYearId, eduType);
        Set<String> get1 = new HashSet<String>();
        Set<String> get2 = new HashSet<String>();
        Set<String> get3 = new HashSet<String>();
        Set<String> get4 = new HashSet<String>();
        List<GroupsLessonCount> get11 = new ArrayList<>();
        List<GroupsLessonCount> get22 = new ArrayList<>();
        List<GroupsLessonCount> get33 = new ArrayList<>();
        List<GroupsLessonCount> get44 = new ArrayList<>();
        System.out.println(facultyShortName);
        System.out.println(weeks);
        if (Objects.equals(eduType, "SIRTQI")) {
            weeks.stream().filter(w -> w.getCourse() == 1).forEach(w -> {
                System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                    get1.add(c.getName());

                    GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                    groupsLessonCount.setWeek(w.getSortNumber());
                    groupsLessonCount.setGroupName(c.getName());

                    List<Integer> daysS = new ArrayList<>();

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                        cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).forEach(card -> {
                            for (DaysDef daysDef : daysDefs) {
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                    if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                        daysS.add(6);
                                    break;
                                }
                            }
                        });
                    });

                    groupsLessonCount.setCount(daysS);

                    get11.add(groupsLessonCount);

                });


            });
            weeks.stream().filter(w -> w.getCourse() == 2).forEach(w -> {
                System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                    get2.add(c.getName());

                    GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                    groupsLessonCount.setWeek(w.getSortNumber());
                    groupsLessonCount.setGroupName(c.getName());

                    List<Integer> daysS = new ArrayList<>();

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                    if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                        daysS.add(6);
                                    break;
                                }
                            }
                        }
                    });

                    groupsLessonCount.setCount(daysS);

                    get22.add(groupsLessonCount);

                });


            });
            weeks.stream().filter(w -> w.getCourse() == 3).forEach(w -> {
                System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                    get3.add(c.getName());

                    GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                    groupsLessonCount.setWeek(w.getSortNumber());
                    groupsLessonCount.setGroupName(c.getName());

                    List<Integer> daysS = new ArrayList<>();

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                    if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                        daysS.add(6);
                                    break;
                                }
                            }
                        }
                    });

                    groupsLessonCount.setCount(daysS);

                    get33.add(groupsLessonCount);

                });


            });
            weeks.stream().filter(w -> w.getCourse() == 4).forEach(w -> {
                System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                    get4.add(c.getName());

                    GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                    groupsLessonCount.setWeek(w.getSortNumber());
                    groupsLessonCount.setGroupName(c.getName());

                    List<Integer> daysS = new ArrayList<>();

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                    if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                        daysS.add(6);
                                    break;
                                }
                            }
                        }
                    });

                    groupsLessonCount.setCount(daysS);

                    get44.add(groupsLessonCount);

                });


            });

            System.out.println(get1);
            System.out.println(get1.toString());
            System.out.println(get2);
            System.out.println(get3);
            System.out.println(get4);

            List<StudentStatisticsWithWeekOfEduYear> years1 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, eduType, eduTypeId, get1.toString().length() > 0 ? get1.toString().substring(1, get1.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years2 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, eduType, eduTypeId, get2.toString().length() > 0 ? get2.toString().substring(1, get2.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years3 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, eduType, eduTypeId, get3.toString().length() > 0 ? get3.toString().substring(1, get3.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years4 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, eduType, eduTypeId, get4.toString().length() > 0 ? get4.toString().substring(1, get4.toString().length() - 1).replaceAll("\\s", "") : "");

            List<List<StudentStatisticsWithWeekOfEduYear>> years = Arrays.asList(years1, years2, years3, years4);
            List<List<GroupsLessonCount>> lessonsS = Arrays.asList(get11, get22, get33, get44);

            return new ApiResponseTwoObj(true, "statistics", years, lessonsS);
        }
        else {

            if (!Objects.equals(facultyShortName, "DNT") && !Objects.equals(facultyShortName, "MED")) {
                weeks.forEach(w -> {
                    System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                    getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                    classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                        Group groupByName = groupRepository.findGroupByName(c.getName());
                        if (groupByName != null) {

                            if (groupByName.getLevel() == 1) get1.add(c.getName());
                            if (groupByName.getLevel() == 2) get2.add(c.getName());
                            if (groupByName.getLevel() == 3) get3.add(c.getName());
                            if (groupByName.getLevel() == 4) get4.add(c.getName());

                            GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                            groupsLessonCount.setWeek(w.getSortNumber());
                            groupsLessonCount.setGroupName(c.getName());

                            List<Integer> daysS = new ArrayList<>();

                            lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                                cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).forEach(card -> {
                                    for (DaysDef daysDef : daysDefs) {
                                        if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                            if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                                daysS.add(1);
                                            if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                                daysS.add(2);
                                            if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                                daysS.add(3);
                                            if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                                daysS.add(4);
                                            if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                                daysS.add(5);
                                            if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                                daysS.add(6);
                                            break;
                                        }
                                    }
                                });
                            });

                            groupsLessonCount.setCount(daysS);


                            if (groupByName.getLevel() == 1) get11.add(groupsLessonCount);
                            if (groupByName.getLevel() == 2) get22.add(groupsLessonCount);
                            if (groupByName.getLevel() == 3) get33.add(groupsLessonCount);
                            if (groupByName.getLevel() == 4) get44.add(groupsLessonCount);

                        }
                    });

                });

                System.out.println("+++++++++++++++++ " + get11);
                System.out.println("+++++++++++++++++ " + get11);
                System.out.println("+++++++++++++++++ " + get11);
                System.out.println("+++++++++++++++++ " + get11);

                List<StudentStatisticsWithWeekOfEduYear> years1 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get1.toString().length() > 0 ? get1.toString().substring(1, get1.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years2 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get2.toString().length() > 0 ? get2.toString().substring(1, get2.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years3 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get3.toString().length() > 0 ? get3.toString().substring(1, get3.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years4 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get4.toString().length() > 0 ? get4.toString().substring(1, get4.toString().length() - 1).replaceAll("\\s", "") : "");

                List<List<StudentStatisticsWithWeekOfEduYear>> years = Arrays.asList(years1, years2, years3, years4);
                List<List<GroupsLessonCount>> lessonsS = Arrays.asList(get11, get22, get33, get44);

                System.out.println("========= " + years);

                return new ApiResponseTwoObj(true, "statistics", years, lessonsS);
            }
            else {

                weeks.forEach(w -> {
                    System.out.println((w.getStart().getYear() + 1900) + "------------------------------------MED");
                    getTimeTableByWeekMed(w.getStart().getYear() + 1900, w.getSortNumber());


                    classesMed.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                        Group groupByName = groupRepository.findGroupByName(c.getName());
                        if (groupByName != null) {

                            if (groupByName.getLevel() == 1) get1.add(c.getName());
                            if (groupByName.getLevel() == 2) get2.add(c.getName());
                            if (groupByName.getLevel() == 3) get3.add(c.getName());
                            if (groupByName.getLevel() == 4) get4.add(c.getName());

                            GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                            groupsLessonCount.setWeek(w.getSortNumber());
                            groupsLessonCount.setGroupName(c.getName());

                            List<Integer> daysS = new ArrayList<>();

                            lessonsMed.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml -> {
                                cardsMed.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).forEach(card -> {
                                    for (DaysDef daysDef : daysDefsMed) {
                                        if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                            if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                                daysS.add(1);
                                            if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                                daysS.add(2);
                                            if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                                daysS.add(3);
                                            if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                                daysS.add(4);
                                            if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                                daysS.add(5);
                                            if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                                daysS.add(6);
                                            break;
                                        }
                                    }
                                });
                            });

                            groupsLessonCount.setCount(daysS);


                            if (groupByName.getLevel() == 1) get11.add(groupsLessonCount);
                            if (groupByName.getLevel() == 2) get22.add(groupsLessonCount);
                            if (groupByName.getLevel() == 3) get33.add(groupsLessonCount);
                            if (groupByName.getLevel() == 4) get44.add(groupsLessonCount);

                        }
                    });

                });

                System.out.println("&&&&&&&&&& " + get11);
                System.out.println("&&&&&&&&&& " + get11);
                System.out.println("&&&&&&&&&& " + get11);
                System.out.println("&&&&&&&&&& " + get11);

                List<StudentStatisticsWithWeekOfEduYear> years1 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get1.toString().length() > 0 ? get1.toString().substring(1, get1.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years2 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get2.toString().length() > 0 ? get2.toString().substring(1, get2.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years3 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get3.toString().length() > 0 ? get3.toString().substring(1, get3.toString().length() - 1).replaceAll("\\s", "") : "");
                List<StudentStatisticsWithWeekOfEduYear> years4 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get4.toString().length() > 0 ? get4.toString().substring(1, get4.toString().length() - 1).replaceAll("\\s", "") : "");

                List<List<StudentStatisticsWithWeekOfEduYear>> years = Arrays.asList(years1, years2, years3, years4);
                List<List<GroupsLessonCount>> lessonsS = Arrays.asList(get11, get22, get33, get44);

                System.out.println("========= " + years);

                return new ApiResponseTwoObj(true, "statistics", years, lessonsS);
            }
//            return new ApiResponseTwoObj(false, "statistics");
        }
    }


    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer week, Integer day, Boolean s) {

        getTimeTableByWeek(week);

        List<Show> shows = timeTable(groupName);
        if (day == 0) {
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            showWeekNumberFields.setGet1(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));


//            List<Teacher> teacherList = new ArrayList<>();
            Set<Teacher> teacherList = new HashSet<>();
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();


            if (s) {

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });

                return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
            } else {

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserId(user.getId(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, lists);
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        } else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true, "Time Table of Today", today);
        }
    }

    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer year, Integer week, Integer day, Boolean s) {
        getTimeTableByWeek(year, week);

        List<Show> shows = timeTable(groupName);
        if (day == 0) {
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            showWeekNumberFields.setGet1(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));


//            List<Teacher> teacherList = new ArrayList<>();
            Set<Teacher> teacherList = new HashSet<>();
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();


            if (year == 2023 && week < 44) {
                if (s) {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassport(teacher.getEmail(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
                } else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, lists);
                }
            } else {
                if (s) {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
                } else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, lists);
                }
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        } else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true, "Time Table of Today", today);
        }
    }

    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(String userId, String groupName, Integer year, Integer week, Integer day, Boolean s) {
        System.out.println("------------------------------------------");
        getTimeTableByWeek(year, week);
        getTimeTableByWeekMed(year, week);

        List<Show> shows = timeTable(groupName);
        List<Show> showsMed = timeTableMed(groupName);
        if (day == 0) {
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            System.out.println("=========:::::::::::::::::: "+shows);

            if (!shows.isEmpty()) {
                showWeekNumberFields.setGet1(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
                showWeekNumberFields.setGet2(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
                showWeekNumberFields.setGet3(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
                showWeekNumberFields.setGet4(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
                showWeekNumberFields.setGet5(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
                showWeekNumberFields.setGet6(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
                showWeekNumberFields.setGet7(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
                showWeekNumberFields.setGet8(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
                showWeekNumberFields.setGet9(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
                showWeekNumberFields.setGet10(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
                showWeekNumberFields.setGet11(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
                showWeekNumberFields.setGet12(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));
            }
            else {
                System.out.println("++++++++++++++++++++ :::::::::: "+showsMed);
                showWeekNumberFields.setGet1(showsMed.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
                showWeekNumberFields.setGet2(showsMed.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
                showWeekNumberFields.setGet3(showsMed.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
                showWeekNumberFields.setGet4(showsMed.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
                showWeekNumberFields.setGet5(showsMed.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
                showWeekNumberFields.setGet6(showsMed.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
                showWeekNumberFields.setGet7(showsMed.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
                showWeekNumberFields.setGet8(showsMed.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
                showWeekNumberFields.setGet9(showsMed.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
                showWeekNumberFields.setGet10(showsMed.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
                showWeekNumberFields.setGet11(showsMed.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
                showWeekNumberFields.setGet12(showsMed.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));
            }

//            List<Teacher> teacherList = new ArrayList<>();
            Set<Teacher> teacherList = new HashSet<>();
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();


            if (s) {

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });

                return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
            }
            else {

                List<Set<StudentsDynamicAttendance>> slists = new ArrayList<>();

                showWeekNumberFields.getGet1().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);

                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    teacherList.addAll(i.getTeachers());
                    Set<StudentsDynamicAttendance> statisticsOfWeekdayList = educationYearRepository.getTimesForRoomStatisticsByUserIdUnionNEW(userId, i.getRoom(), year, week, i.getDayNumber(), i.getHourNumber());
                    slists.add(statisticsOfWeekdayList);
                });
                return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, slists);
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true, "Time Table of Today", today);
        }
    }


    @Override
    public ApiResponseTwoObj getTimesForRoomStatisticsByUserIdAndWeek(User user, String groupName, Integer year, Integer week, Integer day, Boolean s) {
        getTimeTableByWeek(year, week);

        List<Show> shows = timeTable(groupName);
        if (day == 0) {
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            showWeekNumberFields.setGet1(shows.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(shows.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(shows.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(shows.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(shows.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(shows.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(shows.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(shows.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(shows.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(shows.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(shows.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(shows.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));


//            List<Teacher> teacherList = new ArrayList<>();
            Set<Teacher> teacherList = new HashSet<>();
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();


            if (year == 2023 && week < 44) {
                if (s) {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
                } else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, lists);
                }
            } else {
                if (s) {
                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields, lists);
                } else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields, lists);
                }
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        } else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true, "Time Table of Today", today);
        }
    }


    public ApiResponse getTeacherTimeTable(User user, Integer week, Integer year) {
        return service2.getTeacherTimeTable(user, week, year);
//        return getTeacherTimeTable3(user, week, year);
    }

    //todo---------------  Frontendchi tugirlashi kerak. bu tez ishlayapdi.
    public ApiResponse getTeacherTimeTable3(User user, Integer week, Integer year) {

        // 1️⃣ XML fayllarni parallel o‘qib olish
        CompletableFuture<Void> normalFuture =
                CompletableFuture.runAsync(() -> getTimeTableByWeek(year, week));
        CompletableFuture<Void> medFuture =
                CompletableFuture.runAsync(() -> getTimeTableByWeekMed(year, week));
        CompletableFuture.allOf(normalFuture, medFuture).join();

        // 2️⃣ Oldindan Map yaratish (findFirst().get() o‘rniga)
        Map<String, LessonXml> lessonById = lessons.stream()
                .collect(Collectors.toMap(LessonXml::getId, l -> l));
        Map<String, Teacher> teacherById = teachers.stream()
                .collect(Collectors.toMap(Teacher::getId, t -> t));
        Map<String, ClassRoom> roomById = classRooms.stream()
                .collect(Collectors.toMap(ClassRoom::getId, r -> r));
        Map<String, Subject> subjectById = subjects.stream()
                .collect(Collectors.toMap(Subject::getId, s -> s));
        Map<String, Period> periodByName = periods.stream()
                .collect(Collectors.toMap(period -> period.getName().toString(), p -> p));

        // MED uchun
        Map<String, LessonXml> lessonMedById = lessonsMed.stream()
                .collect(Collectors.toMap(LessonXml::getId, l -> l));
        Map<String, Teacher> teacherMedById = teachersMed.stream()
                .collect(Collectors.toMap(Teacher::getId, t -> t));
        Map<String, ClassRoom> roomMedById = classRoomsMed.stream()
                .collect(Collectors.toMap(ClassRoom::getId, r -> r));
        Map<String, Subject> subjectMedById = subjectsMed.stream()
                .collect(Collectors.toMap(Subject::getId, s -> s));
        Map<String, Period> periodMedByName = periodsMed.stream()
                .collect(Collectors.toMap(period -> period.getName().toString(), p -> p));

        // 3️⃣ Kunlar ro‘yxatini tayyorlash
        List<String> daysList = daysDefs.stream()
                .filter(item -> !item.getName().equalsIgnoreCase("В любой день")
                        && !item.getName().equalsIgnoreCase("Каждый день"))
                .map(i -> i.getDays().get(0))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        List<String> daysListMed = daysDefsMed.stream()
                .filter(item -> !item.getName().equalsIgnoreCase("В любой день")
                        && !item.getName().equalsIgnoreCase("Каждый день"))
                .map(i -> i.getDays().get(0))
                .distinct()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());

        // 4️⃣ Parallel ishlash uchun Executor
        ExecutorService executor = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());

        // 5️⃣ Normal timetable-ni parallel hisoblash
        CompletableFuture<List<Table>> normalTablesFuture =
                CompletableFuture.supplyAsync(() ->
                                daysList.parallelStream()
                                        .map(day -> processDay(day, user,
                                                cards, lessonById, teacherById,
                                                roomById, subjectById,
                                                periodByName, classes, daysDefs,
                                                WeekType.DEFAULT))
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())
                        , executor);

        // 6️⃣ MED timetable-ni parallel hisoblash
        CompletableFuture<List<Table>> medTablesFuture =
                CompletableFuture.supplyAsync(() ->
                                daysListMed.parallelStream()
                                        .map(day -> processDay(day, user,
                                                cardsMed, lessonMedById, teacherMedById,
                                                roomMedById, subjectMedById,
                                                periodMedByName, classesMed, daysDefsMed,
                                                WeekType.MED))
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())
                        , executor);

        // 7️⃣ Natijalarni yig‘ish
        List<Table> tables = Stream.of(normalTablesFuture, medTablesFuture)
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        executor.shutdown();
        return new ApiResponse(true, "ishlayapti ok", tables);
    }

    /**
     * Bitta kun uchun barcha Table’larni qaytaradi
     */
    private List<Table> processDay(
            String dayCode,
            User user,
            List<Card> cardsList,
            Map<String, LessonXml> lessonById,
            Map<String, Teacher> teacherById,
            Map<String, ClassRoom> roomById,
            Map<String, Subject> subjectById,
            Map<String, Period> periodByName,
            List<Class> classList,
            List<DaysDef> daysDefsLocal,
            WeekType weekType
    ) {
        List<Table> result = new ArrayList<>();

        // Shu kunda bor darslar
        Set<String> lessonsIds = cardsList.stream()
                .filter(c -> c.getDays().contains(dayCode))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        // Darslardan o‘qituvchi ID-lari
        Set<String> teacherIds = lessonsIds.stream()
                .map(lessonById::get)
                .filter(Objects::nonNull)
                .flatMap(l -> l.getTeacherIds().stream())
                .collect(Collectors.toSet());

        // Faqat shu user loginiga mos o‘qituvchilar
        List<Teacher> teachersForDay = teacherIds.stream()
                .map(teacherById::get)
                .filter(t -> t != null && t.getShortName().equalsIgnoreCase(user.getLogin()))
                .collect(Collectors.toList());

        for (Teacher teacher : teachersForDay) {
            TeacherData teacherData = userRepository.getTeachersForRemember3Login(teacher.getShortName());
            if (teacherData == null) continue;

            List<Show> shows = new ArrayList<>();

            for (String lessonId : lessonsIds) {
                LessonXml xml = lessonById.get(lessonId);
                if (xml == null || !xml.getTeacherIds().contains(teacher.getId())) continue;

                List<Card> lessonCards = cardsList.stream()
                        .filter(c -> c.getLessonId().equals(xml.getId()) && c.getDays().contains(dayCode))
                        .collect(Collectors.toList());

                for (Card card : lessonCards) {
                    Show show = new Show();

                    Period period = periodByName.get(card.getPeriod());
                    if (period != null) {
                        show.setHourNumber(period.getPeriod());
                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                    }

                    // xonalar
                    if (!card.getClassroomIds().isEmpty()) {
                        String roomName = card.getClassroomIds().stream()
                                .map(roomById::get)
                                .filter(Objects::nonNull)
                                .map(ClassRoom::getName)
                                .findFirst()
                                .orElse(null);
                        show.setRoom(roomName);
                    }

                    // fan nomi
                    Subject subj = subjectById.get(xml.getSubjectId());
                    if (subj != null) show.setLessonName(subj.getName());

                    // guruhlar
                    List<String> groupNames = xml.getClassIds().stream()
                            .map(id -> classList.stream()
                                    .filter(c -> c.getId().equals(id))
                                    .map(Class::getName)
                                    .findFirst().orElse(null))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                    show.setGroups(groupNames);

                    // kun nomi
                    String shortName = daysDefsLocal.stream()
                            .filter(d -> d.getDays().contains(dayCode))
                            .map(DaysDef::getShortName)
                            .findFirst()
                            .orElse(dayCode);
                    show.setDaysName(shortName);
                    show.setDayCode(dayCode);
                    show.setType(weekType);

                    shows.add(show);
                }
            }
            if (!shows.isEmpty()) {
                result.add(new Table(teacherData, shows));
            }
        }
        return result;
    }


    //    @Override
    public ApiResponse getTeacherTimeTable2(User user, Integer week, Integer year) {

        Path path = Paths.get(year + "\\" + week + ".xml");

        LocalDate localDate = LocalDate.now();
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));
        System.out.println(dayName);
        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();


        if (Files.exists(path)) {
            getTimeTableByWeek(year, week);

            List<String> daysList = daysDefs
                    .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                    .collect(Collectors.toSet()).stream().map(i -> i.getDays().get(0)).collect(Collectors.toList());
            Collections.sort(daysList, Collections.reverseOrder());


            for (String s : daysList) {
                Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                    first.ifPresent(teachers1::add);
                }

                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();

                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        if (card.getClassroomIds().get(0) != null && card.getClassroomIds().get(0) != "") {
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }

                                        show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setLessonId(xml.getId());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());

                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();

                                        for (String classId : classIds) {
                                            Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }
                                        show.setGroups(stringList);
                                        show.setDaysName(
                                                daysDefs.
                                                        stream().filter(item -> item.getDays().contains(s))
                                                        .findFirst().get().getShortName()
                                        );
                                        show.setDayCode(s);
                                        show.setType(WeekType.DEFAULT);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            }

        }

        //for med
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);
            List<String> daysListMed = daysDefsMed
                    .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                    .collect(Collectors.toSet()).stream().map(i -> i.getDays().get(0)).collect(Collectors.toList());
            Collections.sort(daysListMed, Collections.reverseOrder());


            for (String s : daysListMed) {
                Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                    first.ifPresent(teachers1::add);
                }

                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();
                                        Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        for (String s2 : card.getClassroomIds()) {
                                            ClassRoom room = classRoomsMed.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                        show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();
                                        for (String classId : classIds) {
                                            Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }
                                        show.setGroups(stringList);
                                        show.setDaysName(
                                                daysDefsMed.
                                                        stream().filter(item -> item.getDays().contains(s))
                                                        .findFirst().get().getShortName()
                                        );
                                        show.setType(WeekType.MED);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            }

        }

        return new ApiResponse(true, "ishlayapdi ok", tables);
    }

    @Override
    public ApiResponse getTeacherTimeTableToday(User user, Integer week, Integer year) {

        Path path = Paths.get(year + "\\" + week + ".xml");

        LocalDate localDate = LocalDate.now();
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));
        System.out.println(dayName);
        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        if (Files.exists(path)) {

            getTimeTableByWeek(year, week);

            List<String> daysList = daysDefs
                    .stream().filter(item -> item.getName().equalsIgnoreCase(dayName) && !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                    .collect(Collectors.toSet()).stream().map(i -> i.getDays().get(0)).collect(Collectors.toList());
            Collections.sort(daysList, Collections.reverseOrder());

            if (year == 2023 && week < 44) {
                for (String s : daysList) {
                    Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                    Set<String> teachersIds = new HashSet<>();
                    for (String id : lessonsIds) {
                        LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                        teachersIds.addAll(lessonXml.getTeacherIds());
                    }
                    Set<Teacher> teachers1 = new HashSet<>();
                    System.out.println(teachersIds + "-------------------------- teacherIds");
                    for (String id : teachersIds) {
                        Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum())).findFirst();
                        first.ifPresent(teachers1::add);
                    }

                    System.out.println("teachers1 -> " + teachers1);
                    for (Teacher teacher : teachers1) {
                        TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                        if (teacherData1 != null) {
                            teacherData.add(teacherData1);
                            List<Show> shows = new ArrayList<>();
                            for (String id : lessonsIds) {
                                List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                                if (lessonXmls.size() != 0) {
                                    //                        lists.add(lessonXmls);
                                    for (LessonXml xml : lessonXmls) {
                                        List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                        for (Card card : collect) {
                                            Show show = new Show();

                                            Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                            System.out.println(card + "------------------------------------- *****************************************************************************");
                                            if (card.getClassroomIds().get(0) != null && card.getClassroomIds().get(0) != "") {
                                                for (String s2 : card.getClassroomIds()) {
                                                    ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                    show.setRoom(room.getName());
                                                    break;
                                                }
                                            }


                                            show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                            show.setHourNumber(period.getPeriod());
                                            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());

                                            List<String> classIds = xml.getClassIds();
                                            List<String> stringList = new ArrayList<>();

                                            for (String classId : classIds) {
                                                Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                                stringList.add(aClass.getName());
                                            }

                                            show.setGroups(stringList);


                                            show.setDaysName(
                                                    daysDefs.
                                                            stream().filter(item -> item.getDays().contains(s))
                                                            .findFirst().get().getShortName()
                                            );
                                            System.out.println(show.toString() + " <- show " + s);

                                            shows.add(show);

                                        }


                                    }
                                }
                            }
                            tables.add(new Table(teacherData1, shows));
                        }
                    }
                }
            } else {
                for (String s : daysList) {
                    Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                    Set<String> teachersIds = new HashSet<>();
                    for (String id : lessonsIds) {
                        LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                        teachersIds.addAll(lessonXml.getTeacherIds());
                    }
                    Set<Teacher> teachers1 = new HashSet<>();
                    System.out.println(teachersIds + "-------------------------- teacherIds");
                    for (String id : teachersIds) {
                        Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                        first.ifPresent(teachers1::add);
                    }

                    System.out.println("teachers1 -> " + teachers1);
                    for (Teacher teacher : teachers1) {
                        TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                        if (teacherData1 != null) {
                            teacherData.add(teacherData1);
                            List<Show> shows = new ArrayList<>();
                            for (String id : lessonsIds) {
                                List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                                if (lessonXmls.size() != 0) {
                                    //                        lists.add(lessonXmls);
                                    for (LessonXml xml : lessonXmls) {
                                        List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                        for (Card card : collect) {
                                            Show show = new Show();

                                            Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                            System.out.println(card + "------------------------------------- *****************************************************************************");
                                            if (card.getClassroomIds().get(0) != null && card.getClassroomIds().get(0) != "") {
                                                for (String s2 : card.getClassroomIds()) {
                                                    ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                    show.setRoom(room.getName());
                                                    break;
                                                }
                                            }

                                            show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                            show.setHourNumber(period.getPeriod());
                                            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());

                                            List<String> classIds = xml.getClassIds();
                                            List<String> stringList = new ArrayList<>();

                                            for (String classId : classIds) {
                                                Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                                stringList.add(aClass.getName());
                                            }
                                            show.setGroups(stringList);
                                            show.setDaysName(
                                                    daysDefs.
                                                            stream().filter(item -> item.getDays().contains(s))
                                                            .findFirst().get().getShortName()
                                            );
                                            System.out.println(show.toString() + " <- show " + s);
                                            shows.add(show);
                                        }
                                    }
                                }
                            }
                            tables.add(new Table(teacherData1, shows));
                        }
                    }
                }
            }

        }

        //for med
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);
            List<String> daysListMed = daysDefsMed
                    .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                    .collect(Collectors.toSet()).stream().map(i -> i.getDays().get(0)).collect(Collectors.toList());
            Collections.sort(daysListMed, Collections.reverseOrder());

            if (year == 2023 && week < 44) {
                for (String s : daysListMed) {
                    Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                    Set<String> teachersIds = new HashSet<>();
                    for (String id : lessonsIds) {
                        LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                        teachersIds.addAll(lessonXml.getTeacherIds());
                    }
                    Set<Teacher> teachers1 = new HashSet<>();
                    for (String id : teachersIds) {
                        Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum())).findFirst();
                        first.ifPresent(teachers1::add);
                    }

                    System.out.println("teachers1 -> " + teachers1);
                    for (Teacher teacher : teachers1) {
                        TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//                TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                        if (teacherData1 != null) {
                            teacherData.add(teacherData1);
                            List<Show> shows = new ArrayList<>();
                            for (String id : lessonsIds) {
                                List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                                if (lessonXmls.size() != 0) {
                                    //                        lists.add(lessonXmls);
                                    for (LessonXml xml : lessonXmls) {
                                        List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                        for (Card card : collect) {
                                            Show show = new Show();

                                            Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRoomsMed.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }

                                            show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                            show.setHourNumber(period.getPeriod());
                                            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());

                                            List<String> classIds = xml.getClassIds();
                                            List<String> stringList = new ArrayList<>();

                                            for (String classId : classIds) {
                                                Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                                stringList.add(aClass.getName());
                                            }

                                            show.setGroups(stringList);


                                            show.setDaysName(
                                                    daysDefsMed.
                                                            stream().filter(item -> item.getDays().contains(s))
                                                            .findFirst().get().getShortName()
                                            );
                                            System.out.println(show.toString() + " <- show " + s);

                                            shows.add(show);

                                        }


                                    }
                                }
                            }
                            tables.add(new Table(teacherData1, shows));
                        }
                    }
                }
            } else {
                for (String s : daysListMed) {
                    Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                    Set<String> teachersIds = new HashSet<>();
                    for (String id : lessonsIds) {
                        LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                        teachersIds.addAll(lessonXml.getTeacherIds());
                    }
                    Set<Teacher> teachers1 = new HashSet<>();
                    for (String id : teachersIds) {
                        Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                        first.ifPresent(teachers1::add);
                    }

                    System.out.println("teachers1 -> " + teachers1);
                    for (Teacher teacher : teachers1) {
                        TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                        if (teacherData1 != null) {
                            teacherData.add(teacherData1);
                            List<Show> shows = new ArrayList<>();
                            for (String id : lessonsIds) {
                                List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                                if (lessonXmls.size() != 0) {
                                    //                        lists.add(lessonXmls);
                                    for (LessonXml xml : lessonXmls) {
                                        List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                        for (Card card : collect) {
                                            Show show = new Show();
                                            Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRoomsMed.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                            show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                            show.setHourNumber(period.getPeriod());
                                            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                            List<String> classIds = xml.getClassIds();
                                            List<String> stringList = new ArrayList<>();
                                            for (String classId : classIds) {
                                                Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                                stringList.add(aClass.getName());
                                            }
                                            show.setGroups(stringList);
                                            show.setDaysName(
                                                    daysDefsMed.
                                                            stream().filter(item -> item.getDays().contains(s))
                                                            .findFirst().get().getShortName()
                                            );
                                            System.out.println(show.toString() + " <- show " + s);
                                            shows.add(show);
                                        }
                                    }
                                }
                            }
                            tables.add(new Table(teacherData1, shows));
                        }
                    }
                }
            }

        }
        return new ApiResponse(false, "ishlayapdi ok", tables);
    }

    //    @Override
    public ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday) {

        Path path = Paths.get(year + "\\" + week + ".xml");

        LocalDate localDate = LocalDate.of(year, month, day);
//        LocalDate localDate= LocalDate.now();
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));
        System.out.println(dayName);
        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        if (Files.exists(path)) {
            getTimeTableByWeek(year, week);
            String dayId = daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);
            System.out.println("dayId -> " + dayId);
            Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIds = new HashSet<>();
            System.out.println("lessonsIds -> " + lessonsIds);
            for (String id : lessonsIds) {
                LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                teachersIds.addAll(lessonXml.getTeacherIds());
            }
            Set<Teacher> teachers1 = new HashSet<>();
            System.out.println("teachersIds -> " + teachersIds);
            for (String id : teachersIds) {
                Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id)).findFirst();
                first.ifPresent(teachers1::add);
            }
            System.out.println("teachers1 -> " + teachers1);
            if (year == 2023 && week < 44) {
                for (Teacher teacher : teachers1) {
//            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getShortName(),kafedraId);
                    TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraId(teacher.getEmail(), kafedraId);

                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                                    for (Card card : collect) {
                                        Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        for (String s : card.getClassroomIds()) {
                                            Optional<ClassRoom> roomOptional = classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                                            if (roomOptional.isPresent()) {
                                                ClassRoom room = roomOptional.get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();

                                        for (String classId : classIds) {
                                            Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }

                                        show.setGroups(stringList);

                                        show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                        shows.add(show);
                                    }

                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            } else {
                for (Teacher teacher : teachers1) {
                    System.out.println(teacher.getShortName());
                    TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);

                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                                    for (Card card : collect) {
                                        Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        for (String s : card.getClassroomIds()) {
                                            Optional<ClassRoom> roomOptional = classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                                            if (roomOptional.isPresent()) {
                                                ClassRoom room = roomOptional.get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();

                                        for (String classId : classIds) {
                                            Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }

                                        show.setGroups(stringList);

                                        show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                        shows.add(show);
                                    }

                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            }

        }

        //for med
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);
            String dayIdMed = daysDefsMed.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);
            Set<String> lessonsIdsMed = cardsMed.stream().filter(item -> item.getDays().contains(dayIdMed)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIdsMed = new HashSet<>();
            for (String id : lessonsIdsMed) {
                LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                teachersIdsMed.addAll(lessonXml.getTeacherIds());
            }
            Set<Teacher> teachers1Med = new HashSet<>();
            for (String id : teachersIdsMed) {
                Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id)).findFirst();
                first.ifPresent(teachers1Med::add);
            }

            if (year == 2023 && week < 44) {
                for (Teacher teacher : teachers1Med) {
//            TeacherData teacherData1 = userRepository.getTeachersForRemember(teacher.getEmail(),kafedraId);
                    TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraId(teacher.getEmail(), kafedraId);
//            TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getEmail(),kafedraId);
                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIdsMed) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayIdMed)).collect(Collectors.toList());
                                    for (Card card : collect) {
                                        Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                        Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        for (String s : card.getClassroomIds()) {
                                            Optional<ClassRoom> roomOptional = classRoomsMed.stream().filter(i -> i.getId().equals(s)).findFirst();
                                            if (roomOptional.isPresent()) {
                                                ClassRoom room = roomOptional.get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();

                                        for (String classId : classIds) {
                                            Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }

                                        show.setGroups(stringList);

                                        show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                        shows.add(show);
                                    }

                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            } else {
                for (Teacher teacher : teachers1Med) {
                    TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);
                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIdsMed) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size() != 0) {
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayIdMed)).collect(Collectors.toList());
                                    for (Card card : collect) {
                                        Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                        Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        for (String s : card.getClassroomIds()) {
                                            Optional<ClassRoom> roomOptional = classRoomsMed.stream().filter(i -> i.getId().equals(s)).findFirst();
                                            if (roomOptional.isPresent()) {
                                                ClassRoom room = roomOptional.get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                        List<String> classIds = xml.getClassIds();
                                        List<String> stringList = new ArrayList<>();

                                        for (String classId : classIds) {
                                            Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                            stringList.add(aClass.getName());
                                        }

                                        show.setGroups(stringList);

                                        show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                        shows.add(show);
                                    }

                                }
                            }
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }
            }
        }


        return new ApiResponseTwoObj(true, "teachers", tables, userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(kafedraId, year, week, weekday));
    }


    public ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra5(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday) {

        Path path = Paths.get(year + "\\" + week + ".xml");

        LocalDate localDate = LocalDate.of(year, month, day);
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));

        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust the thread pool size based on your system's capacity

        if (Files.exists(path)) {
            getTimeTableByWeek(year, week);

            String dayId = getDayId(dayName);

            Set<String> lessonsIds = getLessonsIdsForDay(dayId);
            Set<String> teachersIds = getTeacherIdsForLessons(lessonsIds);

            Set<Teacher> teachers = getTeachersByIds(teachersIds);

            // Process teachers in parallel
            List<Callable<Table>> tasks = teachers.stream()
                    .map(teacher -> (Callable<Table>) () -> processTeacherTable(teacher, user, kafedraId, lessonsIds, dayId))
                    .collect(Collectors.toList());

            try {
                List<Future<Table>> results = executorService.invokeAll(tasks);
                for (Future<Table> result : results) {
                    Table table = result.get();
                    if (table != null) {
                        tables.add(table);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        // For medical data
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);

            String dayIdMed = getDayId(dayName);

            Set<String> lessonsIdsMed = getLessonsIdsForDayMed(dayIdMed);
            Set<String> teachersIdsMed = getTeacherIdsForLessonsMed(lessonsIdsMed);

            Set<Teacher> teachersMed = getTeachersByIdsMed(teachersIdsMed);

            // Process medical teachers in parallel
            List<Callable<Table>> medTasks = teachersMed.stream()
                    .map(teacher -> (Callable<Table>) () -> processTeacherTableMed(teacher, user, kafedraId, lessonsIdsMed, dayIdMed))
                    .collect(Collectors.toList());

            try {
                List<Future<Table>> medResults = executorService.invokeAll(medTasks);
                for (Future<Table> result : medResults) {
                    Table table = result.get();
                    if (table != null) {
                        tables.add(table);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown(); // Shutdown the executor service after all tasks are completed

        return new ApiResponseTwoObj(true, "teachers", tables, userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(kafedraId, year, week, weekday));
    }

    private String getDayId(String dayName) {
        return daysDefs.stream()
                .filter(item -> item.getName().equalsIgnoreCase(dayName))
                .findFirst()
                .map(item -> item.getDays().get(0))
                .orElseThrow(() -> new IllegalArgumentException("Invalid day name"));
    }

    private Set<String> getLessonsIdsForDay(String dayId) {
        return cards.stream()
                .filter(item -> item.getDays().contains(dayId))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());
    }

    private Set<String> getTeacherIdsForLessons(Set<String> lessonsIds) {
        return lessonsIds.stream()
                .flatMap(id -> lessons.stream()
                        .filter(lesson -> lesson.getId().equals(id))
                        .flatMap(lesson -> lesson.getTeacherIds().stream()))
                .collect(Collectors.toSet());
    }

    private Set<Teacher> getTeachersByIds(Set<String> teachersIds) {
        return teachersIds.stream()
                .map(id -> teachers.stream().filter(teacher -> teacher.getId().equals(id)).findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    // Method to get Lesson IDs for a specific day for medical timetable
    private Set<String> getLessonsIdsForDayMed(String dayIdMed) {
        return cardsMed.stream()
                .filter(item -> item.getDays().contains(dayIdMed))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());
    }

    // Method to get Teacher IDs for specific lessons in the medical timetable
    private Set<String> getTeacherIdsForLessonsMed(Set<String> lessonsIdsMed) {
        return lessonsIdsMed.stream()
                .flatMap(id -> lessonsMed.stream()
                        .filter(lesson -> lesson.getId().equals(id))
                        .flatMap(lesson -> lesson.getTeacherIds().stream()))
                .collect(Collectors.toSet());
    }

    // Method to get Teachers by their IDs for the medical timetable
    private Set<Teacher> getTeachersByIdsMed(Set<String> teachersIdsMed) {
        return teachersIdsMed.stream()
                .map(id -> teachersMed.stream()
                        .filter(teacher -> teacher.getId().equals(id))
                        .findFirst()
                        .orElse(null)) // Return null if not found
                .filter(Objects::nonNull) // Ensure we only collect non-null teachers
                .collect(Collectors.toSet());
    }


    private Table processTeacherTable(Teacher teacher, User user, String kafedraId, Set<String> lessonsIds, String dayId) {
        TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);

        if (teacherData1 != null) {
            List<Show> shows = processTeacherLessons(teacher, lessonsIds, dayId);
            return new Table(teacherData1, shows);
        }
        return null;
    }

    private Table processTeacherTableMed(Teacher teacher, User user, String kafedraId, Set<String> lessonsIds, String dayId) {
        TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraId(teacher.getShortName(), kafedraId);
        if (teacherData1 != null) {
            List<Show> shows = processTeacherLessonsMed(teacher, lessonsIds, dayId);
            return new Table(teacherData1, shows);
        }
        return null;
    }

    // Process teacher lessons and generate show details for the general timetable
    private List<Show> processTeacherLessons(Teacher teacher, Set<String> lessonsIds, String dayId) {
        return lessonsIds.stream()
                .flatMap(id -> lessons.stream()
                        .filter(lesson -> lesson.getId().equals(id) && lesson.getTeacherIds().contains(teacher.getId()))
                        .flatMap(lesson -> cards.stream()
                                .filter(card -> card.getLessonId().equals(lesson.getId()) && card.getDays().contains(dayId))
                                .map(card -> createShow(card, lesson, teacher))))
                .collect(Collectors.toList());
    }

    // Process medical teacher lessons and generate show details for the medical timetable
    private List<Show> processTeacherLessonsMed(Teacher teacher, Set<String> lessonsIds, String dayId) {
        return lessonsIds.stream()
                .flatMap(id -> lessonsMed.stream()
                        .filter(lesson -> lesson.getId().equals(id) && lesson.getTeacherIds().contains(teacher.getId()))
                        .flatMap(lesson -> cardsMed.stream()
                                .filter(card -> card.getLessonId().equals(lesson.getId()) && card.getDays().contains(dayId))
                                .map(card -> createShowMed(card, lesson, teacher))))
                .collect(Collectors.toList());
    }

    private Show createShow(Card card, LessonXml xml, Teacher teacher) {
        Show show = new Show();
        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().orElse(null);
        if (period != null) {
            for (String s : card.getClassroomIds()) {
                Optional<ClassRoom> roomOptional = classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                roomOptional.ifPresent(room -> show.setRoom(room.getName()));
            }

            List<String> classIds = xml.getClassIds();
            List<String> stringList = classIds.stream()
                    .map(classId -> classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get().getName())
                    .collect(Collectors.toList());

            show.setGroups(stringList);
            show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
            show.setHourNumber(period.getPeriod());
            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
        }
        return show;
    }

    private Show createShowMed(Card card, LessonXml xml, Teacher teacher) {
        Show show = new Show();
        Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().orElse(null);
        if (period != null) {
            for (String s : card.getClassroomIds()) {
                Optional<ClassRoom> roomOptional = classRoomsMed.stream().filter(i -> i.getId().equals(s)).findFirst();
                roomOptional.ifPresent(room -> show.setRoom(room.getName()));
            }

            List<String> classIds = xml.getClassIds();
            List<String> stringList = classIds.stream()
                    .map(classId -> classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get().getName())
                    .collect(Collectors.toList());

            show.setGroups(stringList);
            show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
            show.setHourNumber(period.getPeriod());
            show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
        }
        return show;
    }



   /* public ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        Path path = Paths.get(year + "\\" + week + ".xml");
        LocalDate localDate = LocalDate.of(year, month, day);
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));

        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(4); // Use 4 threads (adjust based on your workload)
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        if (Files.exists(path)) {
            getTimeTableByWeek(year, week);
            String dayId = daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);

            Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIds = lessonsIds.stream()
                    .flatMap(id -> lessons.stream().filter(lesson -> lesson.getId().equals(id)).flatMap(lesson -> lesson.getTeacherIds().stream()))
                    .collect(Collectors.toSet());

            Set<Teacher> teachers1 = teachers.stream().filter(teacher -> teachersIds.contains(teacher.getId())).collect(Collectors.toSet());

            // Split work for parallel processing
            for (Teacher teacher : teachers1) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    TeacherData teacherData1 = (year == 2023 && week < 44) ?
                            userRepository.getTeachersForRememberWithKafedraId(teacher.getEmail(), kafedraId) :
                            userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);

                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            lessonXmls.forEach(xml -> {
                                List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                                collect.forEach(card -> {
                                    Show show = new Show();
                                    Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                    card.getClassroomIds().forEach(s -> {
                                        Optional<ClassRoom> roomOptional = classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                                        roomOptional.ifPresent(room -> show.setRoom(room.getName()));
                                    });
                                    List<String> classIds = xml.getClassIds();
                                    List<String> classNames = classIds.stream()
                                            .map(classId -> classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get().getName())
                                            .collect(Collectors.toList());
                                    show.setGroups(classNames);
                                    show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                    show.setHourNumber(period.getPeriod());
                                    show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                    shows.add(show);
                                });
                            });
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }, executorService);
                futures.add(future);
            }

            // Wait for all tasks to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        // Process 'med' section using a similar pattern
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);
            String dayIdMed = daysDefsMed.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);

            Set<String> lessonsIdsMed = cardsMed.stream().filter(item -> item.getDays().contains(dayIdMed)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIdsMed = lessonsIdsMed.stream()
                    .flatMap(id -> lessonsMed.stream().filter(lesson -> lesson.getId().equals(id)).flatMap(lesson -> lesson.getTeacherIds().stream()))
                    .collect(Collectors.toSet());

            Set<Teacher> teachers1Med = teachersMed.stream().filter(teacher -> teachersIdsMed.contains(teacher.getId())).collect(Collectors.toSet());

            for (Teacher teacher : teachers1Med) {
                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraId(teacher.getEmail(), kafedraId);
                    if (teacherData1 != null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIdsMed) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            lessonXmls.forEach(xml -> {
                                List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayIdMed)).collect(Collectors.toList());
                                collect.forEach(card -> {
                                    Show show = new Show();
                                    Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                    card.getClassroomIds().forEach(s -> {
                                        Optional<ClassRoom> roomOptional = classRoomsMed.stream().filter(i -> i.getId().equals(s)).findFirst();
                                        roomOptional.ifPresent(room -> show.setRoom(room.getName()));
                                    });
                                    List<String> classIds = xml.getClassIds();
                                    List<String> classNames = classIds.stream()
                                            .map(classId -> classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get().getName())
                                            .collect(Collectors.toList());
                                    show.setGroups(classNames);
                                    show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                    show.setHourNumber(period.getPeriod());
                                    show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                    shows.add(show);
                                });
                            });
                        }
                        tables.add(new Table(teacherData1, shows));
                    }
                }, executorService);
                futures.add(future);
            }

            // Wait for all tasks to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        executorService.shutdown(); // Don't forget to shutdown the executor

        // Return the final response
        return new ApiResponseTwoObj(true, "teachers", tables, userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(kafedraId, year, week, weekday));
    }*/

    //    @Override
    public ApiResponseTwoObj getTimeTableByRoomAndWeek(User user, String room, Integer weekday, Integer week, Integer year) {
        if (!room.startsWith("M")) {
            getTimeTableByWeek(year, week);
            getTimeTableByWeekMed(year, week);
            Optional<ClassRoom> first = classRooms.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            ClassRoom classRoom = first.get();
            Optional<ClassRoom> firstMed = classRoomsMed.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            ClassRoom classRoomMed = firstMed.orElse(null);
            Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
            Set<Show> shows = timeTable(cardSet);
            Set<Card> cardSetMed = classRoomMed != null ? cardsMed.stream().filter(i -> i.getClassroomIds().contains(classRoomMed.getId())).collect(Collectors.toSet()) : null;
            Set<Show> showsMed = cardSetMed != null ? timeTableMed(cardSetMed) : null;
            Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber() == weekday).collect(Collectors.toSet());
            Set<Show> showSetMed = showsMed != null ? showsMed.stream().filter(i -> i.getDayNumber() == weekday).collect(Collectors.toSet()) : new HashSet<>();
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();
            if (showSetMed != null) {
                showWeekNumberFields.setGet1(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet2(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet3(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet4(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet5(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );

                showWeekNumberFields.setGet6(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet7(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet8(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet9(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet10(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet11(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
                showWeekNumberFields.setGet12(
                        Stream.of(
                                showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()),
                                showSetMed.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList())
                        ).flatMap(Collection::stream).collect(Collectors.toList())
                );
            }
            else {
                showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
                showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
                showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
                showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
                showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
                showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
                showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
                showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
                showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
                showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
                showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
                showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));
            }
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();
            showWeekNumberFields.getGet1().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet2().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet3().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    System.out.println("###");
                    System.out.println(teacher.getShortName());
                    System.out.println(i.getRoom());
                    System.out.println(i.getDayNumber());
                    System.out.println(week);
                    System.out.println(year);
                    System.out.println(i.getHourNumber());
                    statisticsOfWeekdayList.forEach(System.out::println);
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet4().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet5().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet6().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet7().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet8().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet9().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet10().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet11().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet12().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            System.out.println("$$$");
            return new ApiResponseTwoObj(true, "time table room",
                    showSetMed.size() > 0 ?
                            Stream.of(
                                    showSet,
                                    showSetMed
                            ).flatMap(Collection::stream).collect(Collectors.toSet()) :
                            showSet
                    , lists);
        } else {
            getTimeTableByWeekMed(year, week);
            Optional<ClassRoom> first = classRoomsMed.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            ClassRoom classRoom = first.get();
            Set<Card> cardSet = cardsMed.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
            Set<Show> shows = timeTableMed(cardSet);
            Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber() == weekday).collect(Collectors.toSet());
            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();
            showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));
            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();
            showWeekNumberFields.getGet1().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet2().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet3().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet4().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet5().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet6().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet7().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet8().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet9().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet10().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet11().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            showWeekNumberFields.getGet12().forEach(i -> {
                for (Teacher teacher : i.getTeachers()) {
                    List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                    lists.addAll(statisticsOfWeekdayList);
                }
            });
            return new ApiResponseTwoObj(true, "time table room",
                    showSet
                    , lists);

        }
    }


    public ApiResponseTwoObj getTimeTableByRoomAndWeek5(User user, String room, Integer weekday, Integer week, Integer year) {
        // Fetch timetable data for normal and medical classes
        getTimeTableByWeek(year, week);
        getTimeTableByWeekMed(year, week);

        // Retrieve corresponding ClassRoom objects based on room name
        ClassRoom classRoom = findClassRoomByName(classRooms, room)
                .orElseThrow(() -> new IllegalArgumentException("Classroom not found"));

        Optional<ClassRoom> classRoomMed = findClassRoomByName(classRoomsMed, room);

        // Fetch sets of cards and shows for both normal and medical classes
        Set<Card> normalCards = getCardsByClassRoomId(cards, classRoom.getId());
        Set<Show> normalShows = filterShowsByWeekday(timeTable(normalCards), weekday);

        Set<Card> medicalCards = classRoomMed.map(med -> getCardsByClassRoomId(cardsMed, med.getId()))
                .orElse(Collections.emptySet());
        Set<Show> medicalShows = filterShowsByWeekday(timeTableMed(medicalCards), weekday);

        // Combine and populate ShowWeekNumberFields
        ShowWeekNumberFields showWeekNumberFields = createShowWeekNumberFields(normalShows, medicalShows);

        // Fetch teacher statistics for each show
        List<TeacherStatisticsOfWeekday> statistics = fetchAllTeacherStatistics(showWeekNumberFields, room, week, year);

        return new ApiResponseTwoObj(true, "", showWeekNumberFields, statistics);
    }

    // Helper method to retrieve a ClassRoom
    private Optional<ClassRoom> findClassRoomByName(List<ClassRoom> classRooms, String room) {
        String roomPrefix = room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room;
        return classRooms.stream()
                .filter(classRoom -> classRoom.getName().startsWith(roomPrefix))
                .findFirst();
    }

    // Helper method to fetch cards by classRoomId
    private Set<Card> getCardsByClassRoomId(List<Card> cards, String classRoomId) {
        return cards.stream()
                .filter(card -> card.getClassroomIds().contains(classRoomId))
                .collect(Collectors.toSet());
    }

    // Helper method to filter shows by weekday
    private Set<Show> filterShowsByWeekday(Set<Show> shows, Integer weekday) {
        return shows.stream()
                .filter(show -> show.getDayNumber().equals(weekday))
                .collect(Collectors.toSet());
    }

    // Helper method to filter shows by hour
    private List<Show> filterShowsByHour(Set<Show> shows, int hourNumber) {
        return shows.stream()
                .filter(show -> show.getHourNumber() == hourNumber)
                .collect(Collectors.toList());
    }

    // Method to create and populate ShowWeekNumberFields
    private ShowWeekNumberFields createShowWeekNumberFields(Set<Show> normalShows, Set<Show> medicalShows) {
        ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

        for (int hour = 1; hour <= 12; hour++) {
            List<Show> combinedShows = Stream.concat(
                    filterShowsByHour(normalShows, hour).stream(),
                    filterShowsByHour(medicalShows, hour).stream()
            ).collect(Collectors.toList());

            setShowFieldByHour(showWeekNumberFields, hour, combinedShows);
        }

        return showWeekNumberFields;
    }

    // Helper method to dynamically set fields in ShowWeekNumberFields
    private void setShowFieldByHour(ShowWeekNumberFields showWeekNumberFields, int hour, List<Show> shows) {
        switch (hour) {
            case 1:
                showWeekNumberFields.setGet1(shows);
                break;
            case 2:
                showWeekNumberFields.setGet2(shows);
                break;
            case 3:
                showWeekNumberFields.setGet3(shows);
                break;
            case 4:
                showWeekNumberFields.setGet4(shows);
                break;
            case 5:
                showWeekNumberFields.setGet5(shows);
                break;
            case 6:
                showWeekNumberFields.setGet6(shows);
                break;
            case 7:
                showWeekNumberFields.setGet7(shows);
                break;
            case 8:
                showWeekNumberFields.setGet8(shows);
                break;
            case 9:
                showWeekNumberFields.setGet9(shows);
                break;
            case 10:
                showWeekNumberFields.setGet10(shows);
                break;
            case 11:
                showWeekNumberFields.setGet11(shows);
                break;
            case 12:
                showWeekNumberFields.setGet12(shows);
                break;
            default:
                throw new IllegalArgumentException("Hour must be between 1 and 12");
        }
    }

    // Method to fetch all teacher statistics for the shows
    private List<TeacherStatisticsOfWeekday> fetchAllTeacherStatistics(ShowWeekNumberFields showWeekNumberFields, String room, int week, int year) {
        List<TeacherStatisticsOfWeekday> statistics = new ArrayList<>();

        // Combine all shows from the ShowWeekNumberFields and process them
        List<Show> allShows = getAllShows(showWeekNumberFields);

        // Iterate through all shows and collect teacher statistics
        allShows.forEach(show ->
                show.getTeachers().forEach(teacher ->
                        statistics.addAll(fetchTeacherStatistics(teacher, show, room, week, year))
                )
        );

        return statistics;
    }

    // Helper method to combine all shows from ShowWeekNumberFields
    private List<Show> getAllShows(ShowWeekNumberFields showWeekNumberFields) {
        List<Show> allShows = new ArrayList<>();
        allShows.addAll(showWeekNumberFields.getGet1());
        allShows.addAll(showWeekNumberFields.getGet2());
        allShows.addAll(showWeekNumberFields.getGet3());
        allShows.addAll(showWeekNumberFields.getGet4());
        allShows.addAll(showWeekNumberFields.getGet5());
        allShows.addAll(showWeekNumberFields.getGet6());
        allShows.addAll(showWeekNumberFields.getGet7());
        allShows.addAll(showWeekNumberFields.getGet8());
        allShows.addAll(showWeekNumberFields.getGet9());
        allShows.addAll(showWeekNumberFields.getGet10());
        allShows.addAll(showWeekNumberFields.getGet11());
        allShows.addAll(showWeekNumberFields.getGet12());

        return allShows;
    }


    // Helper method to fetch teacher statistics for a specific show and teacher
    private List<TeacherStatisticsOfWeekday> fetchTeacherStatistics(Teacher teacher, Show show, String room, int week, int year) {
        String adjustedRoom = Objects.equals(room, "A-612") ? "A-612%" :
                room.length() == 7 ? room : room.substring(0, room.indexOf("-") + 4);
        return userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(
                teacher.getShortName(),
                adjustedRoom,
                show.getDayNumber(),
                week,
                year,
                show.getHourNumber()
        );
    }


    //todo-----------------------------==============================================================================

//    @Override
    /*public ApiResponseTwoObj getTimeTableByAllRoomAndWeek5(User user,String building, Integer weekday, Integer week, Integer year,Boolean t) {
        Set<List<TeacherStatisticsOfWeekday2>> listsFather = new HashSet<>();
        Set<Set<Show>> showSetFather = new HashSet<>();
        if(!building.startsWith("M")) {
            getTimeTableByWeek(year, week);
            classRooms.stream().filter(i -> i.getName().startsWith(building)).sorted(Comparator.comparing(ClassRoom::getName)).collect(Collectors.toCollection(LinkedHashSet::new)).forEach(classRoom -> {
                Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
                Set<Show> shows = t ? timeTable(cardSet) : timeTable2(cardSet,year,week);
                Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet());
                ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

                showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
                showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
                showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
                showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
                showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
                showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
                showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
                showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
                showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
                showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
                showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
                showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));

                List<TeacherStatisticsOfWeekday2> lists = new ArrayList<>();


                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                listsFather.add(lists);
                showSetFather.add(showSet);
            });
        }
        else {
            getTimeTableByWeekMed(year, week);
            classRoomsMed.stream().filter(i -> i.getName().startsWith(building)).sorted(Comparator.comparing(ClassRoom::getName)).collect(Collectors.toCollection(LinkedHashSet::new)).forEach(classRoom -> {
                Set<Card> cardSet = cardsMed.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
                Set<Show> shows = t ? timeTableMed(cardSet) : timeTable2Med(cardSet,year,week);
                Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet());
                ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();
                showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
                showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
                showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
                showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
                showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
                showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
                showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
                showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
                showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
                showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
                showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
                showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));

                List<TeacherStatisticsOfWeekday2> lists = new ArrayList<>();

                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                        List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });

                listsFather.add(lists);
                showSetFather.add(showSet);
            });

        }
        return new ApiResponseTwoObj(true,"time table room of father",showSetFather,listsFather);
    }*/


    public ApiResponseTwoObj getTimeTableByAllRoomAndWeek(User user, String building, Integer weekday, Integer week, Integer year, Boolean t) {
        Set<List<TeacherStatisticsOfWeekday2>> listsFather = Collections.synchronizedSet(new HashSet<>());
        Set<Set<Show>> showSetFather = Collections.synchronizedSet(new HashSet<>());

        System.out.println("keldi 1");

        getTimeTableByWeek(year, week);
        getTimeTableByWeekMed(year, week);

        System.out.println("keldi");
        if (!building.startsWith("M")) {

            System.out.println(classRooms);

            classRooms.stream()
                    .filter(i -> i.getName().startsWith(building))
                    .sorted(Comparator.comparing(ClassRoom::getName))
                    .collect(Collectors.toCollection(LinkedHashSet::new))
                    .parallelStream()  // Use parallel stream for multi-threading
                    .forEach(classRoom -> {
                        Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
                        Set<Show> shows = t ? timeTable(cardSet) : timeTable2(cardSet, year, week);
                        Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber() == weekday).collect(Collectors.toSet());

                        ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();
                        populateShowWeekNumberFields(showWeekNumberFields, showSet);

                        List<TeacherStatisticsOfWeekday2> lists = new ArrayList<>();
                        for (int hour = 1; hour <= 12; hour++) {
                            List<Show> showsForHour = getShowsForHour(showWeekNumberFields, hour);
                            showsForHour.forEach(show -> {
                                for (Teacher teacher : show.getTeachers()) {
                                    List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(
                                            teacher.getShortName(),
                                            getRoomFormat(show.getRoom()),
                                            show.getDayNumber(),
                                            week,
                                            year,
                                            show.getHourNumber()
                                    );
                                    synchronized (lists) {
                                        lists.addAll(statisticsOfWeekdayList);
                                    }
                                }
                            });
                        }

                        listsFather.add(lists);
                        showSetFather.add(showSet);
                    });
        } else {
//            getTimeTableByWeekMed(year, week);
            classRoomsMed.stream()
                    .filter(i -> i.getName().startsWith(building))
                    .sorted(Comparator.comparing(ClassRoom::getName))
                    .collect(Collectors.toCollection(LinkedHashSet::new))
                    .forEach(classRoom -> {
                        Set<Card> cardSet = cardsMed.stream()
                                .filter(i -> i.getClassroomIds().contains(classRoom.getId()))
                                .collect(Collectors.toSet());
                        Set<Show> shows = t ? timeTableMed(cardSet) : timeTable2Med(cardSet, year, week);
                        Set<Show> showSet = shows.stream()
                                .filter(i -> i.getDayNumber() == weekday)
                                .collect(Collectors.toSet());

                        // Create and populate ShowWeekNumberFields using a loop
                        ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();
                        for (int hour = 1; hour <= 12; hour++) {
                            int finalHour = hour;
                            List<Show> showsForHour = showSet.stream()
                                    .filter(i -> i.getHourNumber() == finalHour)
                                    .collect(Collectors.toList());
                            setShowWeekNumberFields(showWeekNumberFields, finalHour, showsForHour);
                        }

                        // Collect statistics for each hour
                        List<TeacherStatisticsOfWeekday2> allStatistics = new ArrayList<>();
                        for (int hour = 1; hour <= 12; hour++) {
                            List<Show> showsForHour = getShowsForHour(showWeekNumberFields, hour);
                            collectTeacherStatistics(showsForHour, week, year, allStatistics);
                        }

                        listsFather.add(allStatistics);
                        showSetFather.add(showSet);
                    });
        }
        return new ApiResponseTwoObj(true, "time table room of father", showSetFather, listsFather);
    }


    private void collectTeacherStatistics(List<Show> shows, int week, int year, List<TeacherStatisticsOfWeekday2> allStatistics) {
        shows.forEach(show -> {
            for (Teacher teacher : show.getTeachers()) {
                String room = getFormattedRoom(show);
                List<TeacherStatisticsOfWeekday2> statistics = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(
                        teacher.getShortName(), room, show.getDayNumber(), week, year, show.getHourNumber());
                allStatistics.addAll(statistics);
            }
        });
    }

    // Helper method to populate ShowWeekNumberFields
    private void populateShowWeekNumberFields(ShowWeekNumberFields fields, Set<Show> showSet) {
        fields.setGet1(showSet.stream().filter(i -> i.getHourNumber() == 1).collect(Collectors.toList()));
        fields.setGet2(showSet.stream().filter(i -> i.getHourNumber() == 2).collect(Collectors.toList()));
        fields.setGet3(showSet.stream().filter(i -> i.getHourNumber() == 3).collect(Collectors.toList()));
        fields.setGet4(showSet.stream().filter(i -> i.getHourNumber() == 4).collect(Collectors.toList()));
        fields.setGet5(showSet.stream().filter(i -> i.getHourNumber() == 5).collect(Collectors.toList()));
        fields.setGet6(showSet.stream().filter(i -> i.getHourNumber() == 6).collect(Collectors.toList()));
        fields.setGet7(showSet.stream().filter(i -> i.getHourNumber() == 7).collect(Collectors.toList()));
        fields.setGet8(showSet.stream().filter(i -> i.getHourNumber() == 8).collect(Collectors.toList()));
        fields.setGet9(showSet.stream().filter(i -> i.getHourNumber() == 9).collect(Collectors.toList()));
        fields.setGet10(showSet.stream().filter(i -> i.getHourNumber() == 10).collect(Collectors.toList()));
        fields.setGet11(showSet.stream().filter(i -> i.getHourNumber() == 11).collect(Collectors.toList()));
        fields.setGet12(showSet.stream().filter(i -> i.getHourNumber() == 12).collect(Collectors.toList()));
        // Repeat for all 12 hours...
    }

    private void setShowWeekNumberFields(ShowWeekNumberFields fields, int hour, List<Show> shows) {
        switch (hour) {
            case 1:
                fields.setGet1(shows);
            case 2:
                fields.setGet2(shows);
            case 3:
                fields.setGet3(shows);
            case 4:
                fields.setGet4(shows);
            case 5:
                fields.setGet5(shows);
            case 6:
                fields.setGet6(shows);
            case 7:
                fields.setGet7(shows);
            case 8:
                fields.setGet8(shows);
            case 9:
                fields.setGet9(shows);
            case 10:
                fields.setGet10(shows);
            case 11:
                fields.setGet11(shows);
            case 12:
                fields.setGet12(shows);
        }
    }

    // Helper method to fetch shows for a specific hour
    private List<Show> getShowsForHour(ShowWeekNumberFields fields, int hour) {
        switch (hour) {
            case 1:
                return fields.getGet1();
            case 2:
                return fields.getGet2();
            case 3:
                return fields.getGet3();
            case 4:
                return fields.getGet4();
            case 5:
                return fields.getGet5();
            case 6:
                return fields.getGet6();
            case 7:
                return fields.getGet7();
            case 8:
                return fields.getGet8();
            case 9:
                return fields.getGet9();
            case 10:
                return fields.getGet10();
            case 11:
                return fields.getGet11();
            case 12:
                return fields.getGet12();
            default:
                return Collections.emptyList();
        }
    }

    // Helper method to format the room name
    private String getRoomFormat(String room) {
        if (Objects.equals(room, "A-612")) {
            return "A-612%";
        } else if (room.length() == 7) {
            return room;
        } else {
            return room.substring(0, room.indexOf("-") + 4);
        }
    }

    private String getFormattedRoom(Show show) {
        String room = show.getRoom();
        if (Objects.equals(room, "A-612")) {
            return "A-612%";
        }
        return room.length() == 7 ? room : room.substring(0, room.indexOf("-") + 4);
    }


//    @Override
    /*public ApiResponseTwoObj getTimeTableByAllRoomAndWeek25(User user, Integer weekday, Integer week, Integer year, Boolean t) {
        getTimeTableByWeek(year,week);

        Set<List<TeacherStatisticsOfWeekday>> listsFather = new HashSet<>();
        Set<Set<Show>> showSetFather = new HashSet<>();

        classRooms.forEach(classRoom -> {
            Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
            Set<Show> shows = t ? timeTable(cardSet) : timeTable2(cardSet,year,week);


            Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet());

            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            showWeekNumberFields.setGet1(showSet.stream().filter(item -> item.getHourNumber() == 1).collect(Collectors.toList()));
            showWeekNumberFields.setGet2(showSet.stream().filter(item -> item.getHourNumber() == 2).collect(Collectors.toList()));
            showWeekNumberFields.setGet3(showSet.stream().filter(item -> item.getHourNumber() == 3).collect(Collectors.toList()));
            showWeekNumberFields.setGet4(showSet.stream().filter(item -> item.getHourNumber() == 4).collect(Collectors.toList()));
            showWeekNumberFields.setGet5(showSet.stream().filter(item -> item.getHourNumber() == 5).collect(Collectors.toList()));
            showWeekNumberFields.setGet6(showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()));
            showWeekNumberFields.setGet7(showSet.stream().filter(item -> item.getHourNumber() == 7).collect(Collectors.toList()));
            showWeekNumberFields.setGet8(showSet.stream().filter(item -> item.getHourNumber() == 8).collect(Collectors.toList()));
            showWeekNumberFields.setGet9(showSet.stream().filter(item -> item.getHourNumber() == 9).collect(Collectors.toList()));
            showWeekNumberFields.setGet10(showSet.stream().filter(item -> item.getHourNumber() == 10).collect(Collectors.toList()));
            showWeekNumberFields.setGet11(showSet.stream().filter(item -> item.getHourNumber() == 11).collect(Collectors.toList()));
            showWeekNumberFields.setGet12(showSet.stream().filter(item -> item.getHourNumber() == 12).collect(Collectors.toList()));

            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();

                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });

            listsFather.add(lists);
            showSetFather.add(showSet);
        });

//        Set<Set<Show>> collect = showSetFather.stream().map(i -> i.stream().sorted(Comparator.comparing(Show::getRoom)).collect(Collectors.toSet())).collect(Collectors.toSet());


        return new ApiResponseTwoObj(true,"time table room of father",showSetFather,listsFather);
    }*/

    public ApiResponseTwoObj getTimeTableByAllRoomAndWeek2(User user, Integer weekday, Integer week, Integer year, Boolean t) {
        getTimeTableByWeek(year, week);

        Set<List<TeacherStatisticsOfWeekday>> listsFather = new HashSet<>();
        Set<Set<Show>> showSetFather = new HashSet<>();

        classRooms.forEach(classRoom -> {
            Set<Card> cardSet = cards.stream()
                    .filter(i -> i.getClassroomIds().contains(classRoom.getId()))
                    .collect(Collectors.toSet());

            Set<Show> shows = t ? timeTable(cardSet) : timeTable2(cardSet, year, week);
            Set<Show> showSet = shows.stream()
                    .filter(i -> i.getDayNumber() == weekday)
                    .collect(Collectors.toSet());

            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();

            // Fetch teacher statistics for each hour (1 to 12)
            for (int hour = 1; hour <= 12; hour++) {
                fetchTeacherStatisticsForHour(showSet, lists, hour, week, year);
            }

            listsFather.add(lists);
            showSetFather.add(showSet);
        });

        return new ApiResponseTwoObj(true, "Time table room of father", showSetFather, listsFather);
    }

    private void fetchTeacherStatisticsForHour(Set<Show> showSet, List<TeacherStatisticsOfWeekday> lists, int hour, int week, int year) {
        showSet.stream()
                .filter(show -> show.getHourNumber() == hour)
                .forEach(show -> show.getTeachers().forEach(teacher -> {
                    List<TeacherStatisticsOfWeekday> statistics = userRepository.getTimesForRoomStatisticsByPassportByWeekLogin(
                            teacher.getShortName(),
                            show.getRoom(),
                            show.getDayNumber(),
                            week,
                            year,
                            show.getHourNumber()
                    );
                    lists.addAll(statistics);
                }));
    }

    public List<Show> timeTable(String groupName) {
        List<Show> shows = new ArrayList<>();
        for (Card card : cards) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
                        for (uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class aClass : classes) {// guruh
                            if (aClass.getId().equals(classId) && aClass.getName().equals(groupName)) {
                                Show show = new Show();
                                List<String> teachers1 = new ArrayList<>();
                                List<Teacher> teachers2 = new ArrayList<>();
                                for (String classroomId : card.getClassroomIds()) {
                                    for (ClassRoom room : classRooms) {// xona
                                        if (room.getId().equals(classroomId)) {
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
                                }
                                for (Period period : periods) {
                                    if (period.getName().equals(card.getPeriod())) {
                                        show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                        show.setHourNumber(period.getPeriod());
                                        break;
                                    }
                                }
                                for (DaysDef daysDef : daysDefs) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                        if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
                                        if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                            show.setDayNumber(6);
                                        show.setDaysName(daysDef.getName());
                                        break;
                                    }
                                }
                                for (Subject subject : subjects) {
                                    if (subject.getId().equals(lesson.getSubjectId())) {
                                        show.setLessonName(subject.getName());
                                        break;
                                    }
                                }
                                for (String teacherId : lesson.getTeacherIds()) {
                                    for (Teacher teacher : teachers) {
                                        if (teacher.getId().equals(teacherId)) {
                                            teachers2.add(teacher);
                                            teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
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
    public List<Show> timeTableMed(String groupName) {
        List<Show> shows = new ArrayList<>();
        for (Card card : cardsMed) {// jadvalning bitta kuni
            for (LessonXml lesson : lessonsMed) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
                        for (uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class aClass : classesMed) {// guruh
                            if (aClass.getId().equals(classId) && aClass.getName().equals(groupName)) {
                                Show show = new Show();
                                List<String> teachers1 = new ArrayList<>();
                                List<Teacher> teachers2 = new ArrayList<>();
                                for (String classroomId : card.getClassroomIds()) {
                                    for (ClassRoom room : classRoomsMed) {// xona
                                        if (room.getId().equals(classroomId)) {
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
                                }
                                for (Period period : periodsMed) {
                                    if (period.getName().equals(card.getPeriod())) {
                                        show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                        show.setHourNumber(period.getPeriod());
                                        break;
                                    }
                                }
                                for (DaysDef daysDef : daysDefsMed) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                        if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            show.setDayNumber(1);
                                        if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            show.setDayNumber(2);
                                        if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            show.setDayNumber(3);
                                        if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            show.setDayNumber(4);
                                        if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            show.setDayNumber(5);
                                        if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                            show.setDayNumber(6);
                                        show.setDaysName(daysDef.getName());
                                        break;
                                    }
                                }
                                for (Subject subject : subjectsMed) {
                                    if (subject.getId().equals(lesson.getSubjectId())) {
                                        show.setLessonName(subject.getName());
                                        break;
                                    }
                                }
                                for (String teacherId : lesson.getTeacherIds()) {
                                    for (Teacher teacher : teachersMed) {
                                        if (teacher.getId().equals(teacherId)) {
                                            teachers2.add(teacher);
                                            teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
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

    public Set<Show> timeTable(Set<Card> cardSet) {
        Set<Show> shows = new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
                        Show show = new Show();
                        Set<Class> collect = classes.stream().filter(aClass -> aClass.getId().equals(classId)).collect(Collectors.toSet());
                        show.setGroups(collect.stream().map(Class::getName).collect(Collectors.toList()));
//                        for (Class aClass : classes) {// guruh
                        List<String> teachers1 = new ArrayList<>();
                        List<Teacher> teachers2 = new ArrayList<>();
                        for (String classroomId : card.getClassroomIds()) {
                            for (ClassRoom room : classRooms) {// xona
                                if (room.getId().equals(classroomId)) {
                                    show.setRoom(room.getName());
                                    break;
                                }
                            }
                        }
                        for (Period period : periods) {
                            if (period.getName().equals(card.getPeriod())) {
                                show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                show.setHourNumber(period.getPeriod());
                                break;
                            }
                        }
                        for (DaysDef daysDef : daysDefs) {
                            if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                System.out.println(daysDef.getDays().get(0) + " < --------------day number--------------");
                                if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                    show.setDayNumber(1);
                                if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                    show.setDayNumber(2);
                                if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                    show.setDayNumber(3);
                                if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                    show.setDayNumber(4);
                                if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                    show.setDayNumber(5);
                                if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                    show.setDayNumber(6);
                                show.setDaysName(daysDef.getShortName());
                                break;
                            }
                        }
                        for (Subject subject : subjects) {
                            if (subject.getId().equals(lesson.getSubjectId())) {
                                show.setLessonName(subject.getName());
                                break;
                            }
                        }
                        for (String teacherId : lesson.getTeacherIds()) {
                            for (Teacher teacher : teachers) {
                                if (teacher.getId().equals(teacherId)) {
                                    teachers2.add(teacher);
                                    teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
                                    break;
                                }
                            }
                        }

                        show.setTeachers(teachers2);
                        show.setTeacherName(teachers1);
                        shows.add(show);
//                        break;

//                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    public Set<Show> timeTableMed(Set<Card> cardSet) {
        Set<Show> shows = new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessonsMed) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
//                        for (Class aClass : classesMed) {// guruh
                        Show show = new Show();
                        Set<Class> collect = classes.stream().filter(aClass -> aClass.getId().equals(classId)).collect(Collectors.toSet());
                        show.setGroups(collect.stream().map(Class::getName).collect(Collectors.toList()));
                        List<String> teachers1 = new ArrayList<>();
                        List<Teacher> teachers2 = new ArrayList<>();
                        for (String classroomId : card.getClassroomIds()) {
                            for (ClassRoom room : classRoomsMed) {// xona
                                if (room.getId().equals(classroomId)) {
                                    show.setRoom(room.getName());
                                    break;
                                }
                            }
                        }
                        for (Period period : periodsMed) {
                            if (period.getName().equals(card.getPeriod())) {
                                show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                show.setHourNumber(period.getPeriod());
                                break;
                            }
                        }
                        for (DaysDef daysDef : daysDefsMed) {
                            if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                System.out.println(daysDef.getDays().get(0) + " < --------------day number--------------");
                                if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                    show.setDayNumber(1);
                                if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                    show.setDayNumber(2);
                                if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                    show.setDayNumber(3);
                                if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                    show.setDayNumber(4);
                                if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                    show.setDayNumber(5);
                                if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                    show.setDayNumber(6);
                                show.setDaysName(daysDef.getShortName());
                                break;
                            }
                        }
                        for (Subject subject : subjectsMed) {
                            if (subject.getId().equals(lesson.getSubjectId())) {
                                show.setLessonName(subject.getName());
                                break;
                            }
                        }
                        for (String teacherId : lesson.getTeacherIds()) {
                            for (Teacher teacher : teachersMed) {
                                if (teacher.getId().equals(teacherId)) {
                                    teachers2.add(teacher);
                                    teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
                                    break;
                                }
                            }
                        }

                        show.setTeachers(teachers2);
                        show.setTeacherName(teachers1);
                        shows.add(show);
//                        break;

//                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    public Set<Show> timeTable2(Set<Card> cardSet, Integer year, Integer week) {
        Set<Show> shows = new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
//                        for (Class aClass : classes) {// guruh
                        Show show = new Show();
                        Set<Class> collect = classes.stream().filter(aClass -> aClass.getId().equals(classId)).collect(Collectors.toSet());
                        show.setGroups(collect.stream().map(Class::getName).collect(Collectors.toList()));
                        List<String> teachers1 = new ArrayList<>();
                        List<Teacher> teachers2 = new ArrayList<>();
                        for (String classroomId : card.getClassroomIds()) {
                            for (ClassRoom room : classRooms) {// xona
                                if (room.getId().equals(classroomId)) {
                                    show.setRoom(room.getName());
                                    break;
                                }
                            }
                        }
                        for (Period period : periods) {
                            if (period.getName().equals(card.getPeriod())) {
                                show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                show.setHourNumber(period.getPeriod());
                                break;
                            }
                        }
                        for (DaysDef daysDef : daysDefs) {
                            if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                    show.setDayNumber(1);
                                if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                    show.setDayNumber(2);
                                if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                    show.setDayNumber(3);
                                if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                    show.setDayNumber(4);
                                if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                    show.setDayNumber(5);
                                if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                    show.setDayNumber(6);
                                show.setDaysName(daysDef.getShortName());
                                break;
                            }
                        }
                        for (Subject subject : subjects) {
                            if (subject.getId().equals(lesson.getSubjectId())) {
                                show.setLessonName(subject.getName());
                                break;
                            }
                        }
                        for (String teacherId : lesson.getTeacherIds()) {
                            for (Teacher teacher : teachers) {
                                if (teacher.getId().equals(teacherId)) {
                                    teachers2.add(teacher);
                                    teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
                                    break;
                                }
                            }
                        }
                        if (teachers2 != null && teachers2.size() > 0) {
                            if (year == 2023 && week < 44) {
                                show.setKafedraId(userRepository.getKafedraIdByUserPassport(teachers2.get(0).getEmail()));
                            } else {
                                show.setKafedraId(userRepository.getKafedraIdByUserPassportLogin(teachers2.get(0).getShortName()));
                            }
                        }
                        show.setTeachers(teachers2);
                        show.setTeacherName(teachers1);
                        shows.add(show);
                        break;

                    }
                }
//                break;
//                }
            }
        }
        return shows;
    }

    public Set<Show> timeTable2Med(Set<Card> cardSet, Integer year, Integer week) {
        Set<Show> shows = new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessonsMed) {// dars
                if (lesson.getId().equals(card.getLessonId())) {
                    for (String classId : lesson.getClassIds()) {
//                        for (Class aClass : classesMed) {// guruh
                        Show show = new Show();
                        Set<Class> collect = classes.stream().filter(aClass -> aClass.getId().equals(classId)).collect(Collectors.toSet());
                        show.setGroups(collect.stream().map(Class::getName).collect(Collectors.toList()));
                        List<String> teachers1 = new ArrayList<>();
                        List<Teacher> teachers2 = new ArrayList<>();
                        for (String classroomId : card.getClassroomIds()) {
                            for (ClassRoom room : classRoomsMed) {// xona
                                if (room.getId().equals(classroomId)) {
                                    show.setRoom(room.getName());
                                    break;
                                }
                            }
                        }
                        for (Period period : periodsMed) {
                            if (period.getName().equals(card.getPeriod())) {
                                show.setPeriodStartAndEndTime(period.getStartTime() + " - " + period.getEndTime());
                                show.setHourNumber(period.getPeriod());
                                break;
                            }
                        }
                        for (DaysDef daysDef : daysDefsMed) {
                            if (daysDef.getDays().get(0).equals(card.getDays().get(0))) {
                                if (daysDef.getDays().get(0).equals("1000000") || daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                    show.setDayNumber(1);
                                if (daysDef.getDays().get(0).equals("0100000") || daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                    show.setDayNumber(2);
                                if (daysDef.getDays().get(0).equals("0010000") || daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                    show.setDayNumber(3);
                                if (daysDef.getDays().get(0).equals("0001000") || daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                    show.setDayNumber(4);
                                if (daysDef.getDays().get(0).equals("0000100") || daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                    show.setDayNumber(5);
                                if (daysDef.getDays().get(0).equals("0000010") || daysDef.getDays().get(0).equals("000001"))
                                    show.setDayNumber(6);
                                show.setDaysName(daysDef.getShortName());
                                break;
                            }
                        }
                        for (Subject subject : subjectsMed) {
                            if (subject.getId().equals(lesson.getSubjectId())) {
                                show.setLessonName(subject.getName());
                                break;
                            }
                        }
                        for (String teacherId : lesson.getTeacherIds()) {
                            for (Teacher teacher : teachersMed) {
                                if (teacher.getId().equals(teacherId)) {
                                    teachers2.add(teacher);
                                    teachers1.add(teacher.getFirstName() + " " + teacher.getLastName());
                                    break;
                                }
                            }
                        }
                        if (teachers2 != null && teachers2.size() > 0) {
                            if (year == 2023 && week < 44) {
                                show.setKafedraId(userRepository.getKafedraIdByUserPassport(teachers2.get(0).getEmail()));
                            } else {
                                show.setKafedraId(userRepository.getKafedraIdByUserPassportLogin(teachers2.get(0).getShortName()));
                            }
                        }
                        show.setTeachers(teachers2);
                        show.setTeacherName(teachers1);
                        shows.add(show);
//                            break;

//                        }
                    }
                    break;
                }
            }
        }
        return shows;
    }

    //====================================  clear  ==========================================================
    public void clearTimeTable() {
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

    public void clearTimeTableMed() {
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
    public static void readPeriod(Element employeeNode) {
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

    public static void readPeriodMed(Element employeeNode) {
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
    public static void readDaysDef(Element employeeNode) {
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

    public static void readDaysDefMed(Element employeeNode) {
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
    public static void readWeeksDef(Element employeeNode) {
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

    public static void readWeeksDefMed(Element employeeNode) {
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
    public static void readTermsDefs(Element employeeNode) {
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

    public static void readTermsDefsMed(Element employeeNode) {
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
    public static void readSubject(Element employeeNode) {
        subjects.add(
                new Subject(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );

    }

    public static void readSubjectMed(Element employeeNode) {
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
    public static void readTeacher(Element employeeNode) {
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

    public static void readTeacherMed(Element employeeNode) {
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
    public static void readClassroom(Element employeeNode) {
        classRooms.add(
                new ClassRoom(
                        employeeNode.getAttributeValue("id"),
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        employeeNode.getAttributeValue("partner_id")
                )
        );
    }

    public static void readClassroomMed(Element employeeNode) {
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
    public static void readGrade(Element employeeNode) {
        grades.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }

    public static void readGradeMed(Element employeeNode) {
        gradesMed.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }

    //====================================  Class  ==========================================================
    public static void readClass(Element employeeNode) {
        classes.add(
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

    public static void readClassMed(Element employeeNode) {
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
    public static void readGroup(Element employeeNode) {
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

    public static void readGroupMed(Element employeeNode) {
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
    public static void readLesson(Element employeeNode) {
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

    public static void readLessonMed(Element employeeNode) {
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
    public static void readCard(Element employeeNode) {
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

    public static void readCardMed(Element employeeNode) {
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

    public static List<String> array(String str) {
        boolean has = true;
        List<String> arr = new ArrayList<>();

        while (has) {
            int index = str.indexOf(',');
            if (index == -1) {
                arr.add(str);
                has = false;
            } else {
                arr.add(str.substring(0, index));
                str = str.substring(index + 1);
            }
        }

        return arr;
    }


    //todo-----------------------------------
    public ApiResponseTwoObj getKafedraKunlikVaHaftalikStatistikasi(User user, String kafedraId, Integer year, Integer month, Integer day, Integer week, Integer weekday) {

        Path path = Paths.get(year + "\\" + week + ".xml");

        LocalDate localDate = LocalDate.of(year, month, day);
        Locale spanishLocale = new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE", spanishLocale));
        System.out.println(dayName);
        List<TeacherData> teacherData = new ArrayList<>();
        List<Table> tables = new ArrayList<>();

        if (Files.exists(path)) {
            getTimeTableByWeek(year, week);
            String dayId = daysDefs.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);
            Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(dayId)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIds = new HashSet<>();
            for (String id : lessonsIds) {
                LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                teachersIds.addAll(lessonXml.getTeacherIds());
            }
            Set<Teacher> teachers1 = new HashSet<>();
            for (String id : teachersIds) {
                Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id)).findFirst();
                first.ifPresent(teachers1::add);
            }

            for (Teacher teacher : teachers1) {
                System.out.println(teacher.getShortName());
                TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);

                if (teacherData1 != null) {
                    teacherData.add(teacherData1);
                    List<Show> shows = new ArrayList<>();
                    for (String id : lessonsIds) {
                        List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                        if (!lessonXmls.isEmpty()) {
                            for (LessonXml xml : lessonXmls) {
                                List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).collect(Collectors.toList());
                                for (Card card : collect) {
                                    Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                    Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                    for (String s : card.getClassroomIds()) {
                                        Optional<ClassRoom> roomOptional = classRooms.stream().filter(i -> i.getId().equals(s)).findFirst();
                                        if (roomOptional.isPresent()) {
                                            ClassRoom room = roomOptional.get();
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                    List<String> classIds = xml.getClassIds();
                                    List<String> stringList = new ArrayList<>();

                                    for (String classId : classIds) {
                                        Class aClass = classes.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                        stringList.add(aClass.getName());
                                    }

                                    show.setGroups(stringList);

                                    show.setLessonName(subjects.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                    show.setHourNumber(period.getPeriod());
                                    show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                    shows.add(show);
                                }

                            }
                        }
                    }
                    tables.add(new Table(teacherData1, shows));
                }
            }


        }

        //for med
        Path path2 = Paths.get(year + "\\" + week + "med.xml");
        if (Files.exists(path2)) {
            getTimeTableByWeekMed(year, week);
            String dayIdMed = daysDefsMed.stream().filter(item -> item.getName().equalsIgnoreCase(dayName)).findFirst().get().getDays().get(0);
            Set<String> lessonsIdsMed = cardsMed.stream().filter(item -> item.getDays().contains(dayIdMed)).map(Card::getLessonId).collect(Collectors.toSet());
            Set<String> teachersIdsMed = new HashSet<>();
            for (String id : lessonsIdsMed) {
                LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                teachersIdsMed.addAll(lessonXml.getTeacherIds());
            }
            Set<Teacher> teachers1Med = new HashSet<>();
            for (String id : teachersIdsMed) {
                Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id)).findFirst();
                first.ifPresent(teachers1Med::add);
            }


            for (Teacher teacher : teachers1Med) {
                TeacherData teacherData1 = userRepository.getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);
                if (teacherData1 != null) {
                    teacherData.add(teacherData1);
                    List<Show> shows = new ArrayList<>();
                    for (String id : lessonsIdsMed) {
                        List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                        if (!lessonXmls.isEmpty()) {
                            for (LessonXml xml : lessonXmls) {
                                List<Card> collect = cardsMed.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayIdMed)).collect(Collectors.toList());
                                for (Card card : collect) {
                                    Show show = new Show();
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(dayId)).findFirst().get();
                                    Period period = periodsMed.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                    for (String s : card.getClassroomIds()) {
                                        Optional<ClassRoom> roomOptional = classRoomsMed.stream().filter(i -> i.getId().equals(s)).findFirst();
                                        if (roomOptional.isPresent()) {
                                            ClassRoom room = roomOptional.get();
                                            show.setRoom(room.getName());
                                            break;
                                        }
                                    }
//                                LessonXml lessonXml = lessons.stream().filter(i -> i.getId().equals(card.getLessonId())).findFirst().get();
                                    List<String> classIds = xml.getClassIds();
                                    List<String> stringList = new ArrayList<>();

                                    for (String classId : classIds) {
                                        Class aClass = classesMed.stream().filter(i -> i.getId().equals(classId)).findFirst().get();
                                        stringList.add(aClass.getName());
                                    }

                                    show.setGroups(stringList);

                                    show.setLessonName(subjectsMed.stream().filter(i -> i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                    show.setHourNumber(period.getPeriod());
                                    show.setPeriodStartAndEndTime(period.getStartTime() + "-" + period.getEndTime());
                                    shows.add(show);
                                }

                            }
                        }
                    }
                    tables.add(new Table(teacherData1, shows));
                }
            }

        }

        return new ApiResponseTwoObj(true, "teachers", tables, userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(kafedraId, year, week, weekday));
    }


    public ApiResponseTwoObj getKafedraKunlikVaHaftalikStatistikasi2(
            User user, String kafedraId,
            Integer year, Integer month, Integer day,
            Integer week, Integer weekday) {

        LocalDate localDate = LocalDate.of(year, month, day);
        String dayName = localDate.format(
                DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU"))
        );

        // parallel ishlash uchun executor
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Default haftalikni asinxron bajarish
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "\\" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType2(year, week, dayName,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects, kafedraId));
            }
            return result;
        }, executor);

        // Med haftalikni asinxron bajarish
        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "\\" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType2(year, week, dayName,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed,
                        kafedraId));
            }
            return result;
        }, executor);

        // Umumiy statistikani DB dan asinxron olish
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() ->
                userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(
                        kafedraId, year, week, weekday
                ), executor
        );

        // Barcha natijalarni kutib olish
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join(); // ikkalasi tugaguncha kutadi

        List<UserForRoomStatistics2> stats = statsFuture.join();

        executor.shutdown();

        return new ApiResponseTwoObj(true, "teachers", allTables, stats);
    }


    private List<Table> buildTablesForType2(
            int year, int week, String dayName,
            List<DaysDef> daysDefs, List<Card> cards,
            List<LessonXml> lessons, List<Teacher> teachers,
            List<Period> periods, List<ClassRoom> classRooms,
            List<Class> classes, List<Subject> subjects,
            String kafedraId
    ) {
        List<Table> tables = Collections.synchronizedList(new ArrayList<>());

        // === oldingi dayId qismi o‘zgarishsiz ===
        String dayId = daysDefs.stream()
                .filter(d -> d.getName().equalsIgnoreCase(dayName))
                .findFirst()
                .map(d -> d.getDays().get(0))
                .orElse(null);
        if (dayId == null) return tables;

        Set<String> lessonIds = cards.stream()
                .filter(c -> c.getDays().contains(dayId))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        Set<Teacher> targetTeachers = teachers.stream()
                .filter(t -> lessons.stream()
                        .filter(l -> lessonIds.contains(l.getId()))
                        .anyMatch(l -> l.getTeacherIds().contains(t.getId())))
                .collect(Collectors.toSet());

        // === Parallel processing ===
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<?>> futures = new ArrayList<>();
        for (Teacher teacher : targetTeachers) {
            futures.add(pool.submit(() -> {
                TeacherData td = userRepository
                        .getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);
                if (td == null) return;

                List<Show> shows = new ArrayList<>();

                for (String lessonId : lessonIds) {
                    // lessons ni map qilib tezroq qidirish mumkin, lekin sintaksisni saqladim
                    for (LessonXml xml : lessons) {
                        if (!xml.getId().equals(lessonId) || !xml.getTeacherIds().contains(teacher.getId())) continue;
                        for (Card card : cards) {
                            if (!card.getLessonId().equals(xml.getId()) || !card.getDays().contains(dayId)) continue;

                            Period p = periods.stream()
                                    .filter(pe -> pe.getName().equals(card.getPeriod()))
                                    .findFirst().orElse(null);
                            if (p == null) continue;

                            Show s = new Show();
                            s.setRoom(card.getClassroomIds().stream()
                                    .map(cid -> classRooms.stream()
                                            .filter(r -> r.getId().equals(cid))
                                            .map(ClassRoom::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .findFirst().orElse(""));
                            s.setGroups(xml.getClassIds().stream()
                                    .map(cid -> classes.stream()
                                            .filter(c -> c.getId().equals(cid))
                                            .map(Class::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .collect(Collectors.toList()));
                            s.setLessonName(subjects.stream()
                                    .filter(sub -> sub.getId().equals(xml.getSubjectId()))
                                    .map(Subject::getName)
                                    .findFirst().orElse(""));
                            s.setHourNumber(p.getPeriod());
                            s.setPeriodStartAndEndTime(p.getStartTime() + "-" + p.getEndTime());
                            shows.add(s);
                        }
                    }
                }
                tables.add(new Table(td, shows));
            }));
        }

        // barcha vazifalar tugashini kutamiz
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();

        return tables;
    }

    public ApiResponseStats getKafedraKunlikVaHaftalikStatistikasi6(
            User user, String kafedraId,
            Integer year, Integer month, Integer day,
            Integer week, Integer weekday) {

        // Input validation
        if (kafedraId == null || kafedraId.isEmpty()) {
            throw new IllegalArgumentException("Kafedra ID cannot be null or empty");
        }
        if (year <= 0 || month < 1 || month > 12 || day < 1 || day > 31 || week <= 0 || weekday < 1 || weekday > 7) {
            throw new IllegalArgumentException("Invalid date or week parameters");
        }

        LocalDate localDate = LocalDate.of(year, month, day);
        String dayName = localDate.format(
                DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU"))
        );

        // Check if the input date is today and compute current hour
        boolean isToday = localDate.equals(LocalDate.now());
        Integer currentHour = isToday ? Math.max(0, LocalTime.now().getHour() - 8) : null;

        // Use dynamic thread pool size
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Default haftalikni asinxron bajarish
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "/" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType5(year, week, dayName,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects, kafedraId));
            }
            return result;
        }, executor);

        // Med haftalikni asinxron bajarish
        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "/" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType5(year, week, dayName,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed,
                        kafedraId));
            }
            return result;
        }, executor);

        // Umumiy statistikani DB dan asinxron olish
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() ->
                userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(
                        kafedraId, year, week, weekday
                ), executor
        );

        // Barcha natijalarni kutib olish
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join();

        List<UserForRoomStatistics2> stats = statsFuture.join();

        // Compute attendance statistics using CompletableFuture
        Map<String, UserForRoomStatistics2> statsMap = stats.stream()
                .collect(Collectors.toMap(UserForRoomStatistics2::getId, s -> s));

        List<Table> synchronizedTables = Collections.synchronizedList(allTables);
        List<CompletableFuture<Void>> tableFutures = synchronizedTables.stream()
                .map(table -> CompletableFuture.runAsync(() -> {
                    String teacherId = table.getTeacherData().getId();
                    UserForRoomStatistics2 stat = statsMap.get(teacherId);
                    int attended = 0;
                    int eligibleShows = 0;

                    if (stat != null && !table.getShows().isEmpty()) {
                        Map<String, Set<Integer>> attendedRoomSections = stat.getRooms().stream()
                                .filter(room -> !room.getTimes().isEmpty())
                                .collect(Collectors.toMap(
                                        room -> room.getRoom().substring(0, Math.min(room.getRoom().length(), 6)).trim(),
                                        room -> room.getTimes().stream()
                                                .map(TimesForRoom::getSection)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toSet()),
                                        (existing, replacement) -> existing
                                ));
                        System.out.println("---->"+attendedRoomSections);
                        // Count eligible shows and attended shows
                        long[] counts = table.getShows().parallelStream()
                                .collect(() -> new long[]{0, 0}, // [eligible, attended]
                                        (acc, show) -> {
                                            // Check if show is eligible (hourNumber <= currentHour if today)
                                            if (isToday && (show.getHourNumber() == null || currentHour == null || show.getHourNumber() > currentHour)) {
                                                return;
                                            }
                                            String roomPrefix = show.getRoom().substring(0, Math.min(show.getRoom().length(), 6)).trim();
                                            if (roomRepository.existsRoomByNameStartingWith(roomPrefix)) {
                                                acc[0]++; // Increment eligible shows
                                                System.out.println("roomPrefix:: " + roomPrefix);
                                                Set<Integer> sections = attendedRoomSections.get(roomPrefix);
                                                if (sections != null && show.getHourNumber() != null
                                                        && sections.contains(show.getHourNumber())) {
                                                    acc[1]++; // Increment attended
                                                }
                                            }
                                        },
                                        (acc1, acc2) -> {
                                            acc1[0] += acc2[0];
                                            acc1[1] += acc2[1];
                                        });

                        eligibleShows = (int) counts[0];
                        attended = (int) counts[1];
                    }

                    int notAttended = eligibleShows - attended;
                    table.setAttended(attended);
                    table.setNotAttended(notAttended < 0 ? 0 : notAttended);
                }, executor))
                .collect(Collectors.toList());

        // Wait for all attendance calculations
        CompletableFuture.allOf(tableFutures.toArray(new CompletableFuture[0])).join();

        // Sum attended and notAttended counts
        int totalAttended = synchronizedTables.stream()
                .mapToInt(Table::getAttended)
                .sum();
        int totalNotAttended = synchronizedTables.stream()
                .mapToInt(Table::getNotAttended)
                .sum();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return new ApiResponseStats(true, "teachers", totalAttended, totalNotAttended);
    }





    //todo SHERZOD akaga kafedra table uchun attendance statistikasi -----
    /*public GetLessonStatistics31 getTeacherMonthlyStatistics(String teacherId, Integer year, Integer month) {
        System.out.println("Keldi getTeacherMonthlyStatistics methodiga");

        int threads = Math.min(8, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();

        Map<Integer, CompletableFuture<ApiResponseStats>> futures =
                IntStream.rangeClosed(1, daysInMonth)
                        .boxed()
                        .collect(Collectors.toMap(
                                day -> day,
                                day -> CompletableFuture.supplyAsync(() -> {
                                    try {
                                        return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, day);
                                    } catch (Exception e) {
                                        System.err.println("❌ " + day + "-kunda xatolik: " + e.getMessage());
                                        return new ApiResponseStats(false, "Error at day " + day, 0, 0);
                                    }
                                }, executor)
                        ));

        Map<Integer, ApiResponseStats> results = futures.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().join()));

        executor.shutdown();

        // Default qiymatlar bilan 31 kunni to‘ldiramiz
        ApiResponseStats empty = new ApiResponseStats(true, "No data", 0, 0);
        return new GetLessonStatistics31() {
            @Override public ApiResponseStats get1() { return results.getOrDefault(1, empty); }
            @Override public ApiResponseStats get2() { return results.getOrDefault(2, empty); }
            @Override public ApiResponseStats get3() { return results.getOrDefault(3, empty); }
            @Override public ApiResponseStats get4() { return results.getOrDefault(4, empty); }
            @Override public ApiResponseStats get5() { return results.getOrDefault(5, empty); }
            @Override public ApiResponseStats get6() { return results.getOrDefault(6, empty); }
            @Override public ApiResponseStats get7() { return results.getOrDefault(7, empty); }
            @Override public ApiResponseStats get8() { return results.getOrDefault(8, empty); }
            @Override public ApiResponseStats get9() { return results.getOrDefault(9, empty); }
            @Override public ApiResponseStats get10() { return results.getOrDefault(10, empty); }
            @Override public ApiResponseStats get11() { return results.getOrDefault(11, empty); }
            @Override public ApiResponseStats get12() { return results.getOrDefault(12, empty); }
            @Override public ApiResponseStats get13() { return results.getOrDefault(13, empty); }
            @Override public ApiResponseStats get14() { return results.getOrDefault(14, empty); }
            @Override public ApiResponseStats get15() { return results.getOrDefault(15, empty); }
            @Override public ApiResponseStats get16() { return results.getOrDefault(16, empty); }
            @Override public ApiResponseStats get17() { return results.getOrDefault(17, empty); }
            @Override public ApiResponseStats get18() { return results.getOrDefault(18, empty); }
            @Override public ApiResponseStats get19() { return results.getOrDefault(19, empty); }
            @Override public ApiResponseStats get20() { return results.getOrDefault(20, empty); }
            @Override public ApiResponseStats get21() { return results.getOrDefault(21, empty); }
            @Override public ApiResponseStats get22() { return results.getOrDefault(22, empty); }
            @Override public ApiResponseStats get23() { return results.getOrDefault(23, empty); }
            @Override public ApiResponseStats get24() { return results.getOrDefault(24, empty); }
            @Override public ApiResponseStats get25() { return results.getOrDefault(25, empty); }
            @Override public ApiResponseStats get26() { return results.getOrDefault(26, empty); }
            @Override public ApiResponseStats get27() { return results.getOrDefault(27, empty); }
            @Override public ApiResponseStats get28() { return results.getOrDefault(28, empty); }
            @Override public ApiResponseStats get29() { return results.getOrDefault(29, empty); }
            @Override public ApiResponseStats get30() { return results.getOrDefault(30, empty); }
            @Override public ApiResponseStats get31() { return results.getOrDefault(31, empty); }
        };
    }

    public GetLessonStatistics31 getTeacherMonthlyStatistics5(String teacherId, Integer year, Integer month) {
        System.out.println("Keldi getTeacherMonthlyStatistics methodiga");

        int threads = Math.min(8, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threads);

        // 1️⃣ — Parallel barcha kunlarni hisoblaymiz
        Map<Integer, CompletableFuture<ApiResponseStats>> futures =
                IntStream.rangeClosed(1, 31)
                        .boxed()
                        .collect(Collectors.toMap(
                                day -> day,
                                day -> CompletableFuture.supplyAsync(() -> {
                                    try {
                                        System.out.println("→ Hisoblanmoqda: " + day);
                                        return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, day);
                                    } catch (Exception e) {
                                        System.err.println("❌ Xatolik " + day + "-kunda: " + e.getMessage());
                                        e.printStackTrace();
                                        return new ApiResponseStats(false, "Xatolik " + e.getMessage(), 0, 0);
                                    }
                                }, executor)

                        ));

        // 2️⃣ — Tugashini kutamiz va natijalarni mapga yig‘amiz
        Map<Integer, ApiResponseStats> results = futures.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().join()
                ));

        executor.shutdown();

        // 3️⃣ — Endi interfeysga joylashtiramiz
        return new GetLessonStatistics31() {
            @Override public ApiResponseStats get1() { return results.get(1); }
            @Override public ApiResponseStats get2() { return results.get(2); }
            @Override public ApiResponseStats get3() { return results.get(3); }
            @Override public ApiResponseStats get4() { return results.get(4); }
            @Override public ApiResponseStats get5() { return results.get(5); }
            @Override public ApiResponseStats get6() { return results.get(6); }
            @Override public ApiResponseStats get7() { return results.get(7); }
            @Override public ApiResponseStats get8() { return results.get(8); }
            @Override public ApiResponseStats get9() { return results.get(9); }
            @Override public ApiResponseStats get10() { return results.get(10); }
            @Override public ApiResponseStats get11() { return results.get(11); }
            @Override public ApiResponseStats get12() { return results.get(12); }
            @Override public ApiResponseStats get13() { return results.get(13); }
            @Override public ApiResponseStats get14() { return results.get(14); }
            @Override public ApiResponseStats get15() { return results.get(15); }
            @Override public ApiResponseStats get16() { return results.get(16); }
            @Override public ApiResponseStats get17() { return results.get(17); }
            @Override public ApiResponseStats get18() { return results.get(18); }
            @Override public ApiResponseStats get19() { return results.get(19); }
            @Override public ApiResponseStats get20() { return results.get(20); }
            @Override public ApiResponseStats get21() { return results.get(21); }
            @Override public ApiResponseStats get22() { return results.get(22); }
            @Override public ApiResponseStats get23() { return results.get(23); }
            @Override public ApiResponseStats get24() { return results.get(24); }
            @Override public ApiResponseStats get25() { return results.get(25); }
            @Override public ApiResponseStats get26() { return results.get(26); }
            @Override public ApiResponseStats get27() { return results.get(27); }
            @Override public ApiResponseStats get28() { return results.get(28); }
            @Override public ApiResponseStats get29() { return results.get(29); }
            @Override public ApiResponseStats get30() { return results.get(30); }
            @Override public ApiResponseStats get31() { return results.get(31); }
        };
    }

    public GetLessonStatistics31 getTeacherMonthlyStatistics4(String teacherId, Integer year, Integer month) {

        System.out.println("Keldi getTeacherMonthlyStatistics methodiga");

        int threadCount = Math.min(8, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Barcha 31 kun uchun parallel hisoblashni boshlaymiz
        Map<Integer, CompletableFuture<ApiResponseStats>> futures = IntStream.rangeClosed(1, 31)
                .boxed()
                .collect(Collectors.toMap(
                        day -> day,
                        day -> CompletableFuture.supplyAsync(
                                () -> getTeacherDailyOrMonthlyStatistics(teacherId, year, month, day),
                                executor)
                ));

        // Endi interface'ni qaytaramiz, lekin har safar future'dan join qilish o‘rniga, oldindan hisoblangan natijani qaytaramiz
        GetLessonStatistics31 stats = new GetLessonStatistics31() {
            @Override public ApiResponseStats get1() { return futures.get(1).join(); }
            @Override public ApiResponseStats get2() { return futures.get(2).join(); }
            @Override public ApiResponseStats get3() { return futures.get(3).join(); }
            @Override public ApiResponseStats get4() { return futures.get(4).join(); }
            @Override public ApiResponseStats get5() { return futures.get(5).join(); }
            @Override public ApiResponseStats get6() { return futures.get(6).join(); }
            @Override public ApiResponseStats get7() { return futures.get(7).join(); }
            @Override public ApiResponseStats get8() { return futures.get(8).join(); }
            @Override public ApiResponseStats get9() { return futures.get(9).join(); }
            @Override public ApiResponseStats get10() { return futures.get(10).join(); }
            @Override public ApiResponseStats get11() { return futures.get(11).join(); }
            @Override public ApiResponseStats get12() { return futures.get(12).join(); }
            @Override public ApiResponseStats get13() { return futures.get(13).join(); }
            @Override public ApiResponseStats get14() { return futures.get(14).join(); }
            @Override public ApiResponseStats get15() { return futures.get(15).join(); }
            @Override public ApiResponseStats get16() { return futures.get(16).join(); }
            @Override public ApiResponseStats get17() { return futures.get(17).join(); }
            @Override public ApiResponseStats get18() { return futures.get(18).join(); }
            @Override public ApiResponseStats get19() { return futures.get(19).join(); }
            @Override public ApiResponseStats get20() { return futures.get(20).join(); }
            @Override public ApiResponseStats get21() { return futures.get(21).join(); }
            @Override public ApiResponseStats get22() { return futures.get(22).join(); }
            @Override public ApiResponseStats get23() { return futures.get(23).join(); }
            @Override public ApiResponseStats get24() { return futures.get(24).join(); }
            @Override public ApiResponseStats get25() { return futures.get(25).join(); }
            @Override public ApiResponseStats get26() { return futures.get(26).join(); }
            @Override public ApiResponseStats get27() { return futures.get(27).join(); }
            @Override public ApiResponseStats get28() { return futures.get(28).join(); }
            @Override public ApiResponseStats get29() { return futures.get(29).join(); }
            @Override public ApiResponseStats get30() { return futures.get(30).join(); }
            @Override public ApiResponseStats get31() { return futures.get(31).join(); }
        };

        // Barcha future’larni tugashini kutamiz
        CompletableFuture.allOf(futures.values().toArray(new CompletableFuture[0])).join();
        executor.shutdown();

        return stats;
    }

    public GetLessonStatistics31 getTeacherMonthlyStatistics3(String teacherId, Integer year, Integer month) {

        System.out.println("Keldi getTeacherMonthlyStatistics methodiga");

        ExecutorService executor = Executors.newFixedThreadPool(8); // 8 thread parallel ishlaydi

        // Helper function to async execute method
//        Function<Integer, CompletableFuture<ApiResponseStats>> getAsync = day ->
//                CompletableFuture.supplyAsync(() -> getTeacherDailyOrMonthlyStatistics(teacherId, year, month, day), executor);
        Function<Integer, CompletableFuture<ApiResponseStats>> getAsync = day ->
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, day);
                    } catch (Exception e) {
                        System.err.println("Xatolik " + day + "-kunda: " + e.getMessage());
                        return new ApiResponseStats(); // fallback
                    }
                }, executor);

        GetLessonStatistics31 stats = new GetLessonStatistics31() {
            @Override public ApiResponseStats get1() { return getAsync.apply(1).join(); }
            @Override public ApiResponseStats get2() { return getAsync.apply(2).join(); }
            @Override public ApiResponseStats get3() { return getAsync.apply(3).join(); }
            @Override public ApiResponseStats get4() { return getAsync.apply(4).join(); }
            @Override public ApiResponseStats get5() { return getAsync.apply(5).join(); }
            @Override public ApiResponseStats get6() { return getAsync.apply(6).join(); }
            @Override public ApiResponseStats get7() { return getAsync.apply(7).join(); }
            @Override public ApiResponseStats get8() { return getAsync.apply(8).join(); }
            @Override public ApiResponseStats get9() { return getAsync.apply(9).join(); }
            @Override public ApiResponseStats get10() { return getAsync.apply(10).join(); }
            @Override public ApiResponseStats get11() { return getAsync.apply(11).join(); }
            @Override public ApiResponseStats get12() { return getAsync.apply(12).join(); }
            @Override public ApiResponseStats get13() { return getAsync.apply(13).join(); }
            @Override public ApiResponseStats get14() { return getAsync.apply(14).join(); }
            @Override public ApiResponseStats get15() { return getAsync.apply(15).join(); }
            @Override public ApiResponseStats get16() { return getAsync.apply(16).join(); }
            @Override public ApiResponseStats get17() { return getAsync.apply(17).join(); }
            @Override public ApiResponseStats get18() { return getAsync.apply(18).join(); }
            @Override public ApiResponseStats get19() { return getAsync.apply(19).join(); }
            @Override public ApiResponseStats get20() { return getAsync.apply(20).join(); }
            @Override public ApiResponseStats get21() { return getAsync.apply(21).join(); }
            @Override public ApiResponseStats get22() { return getAsync.apply(22).join(); }
            @Override public ApiResponseStats get23() { return getAsync.apply(23).join(); }
            @Override public ApiResponseStats get24() { return getAsync.apply(24).join(); }
            @Override public ApiResponseStats get25() { return getAsync.apply(25).join(); }
            @Override public ApiResponseStats get26() { return getAsync.apply(26).join(); }
            @Override public ApiResponseStats get27() { return getAsync.apply(27).join(); }
            @Override public ApiResponseStats get28() { return getAsync.apply(28).join(); }
            @Override public ApiResponseStats get29() { return getAsync.apply(29).join(); }
            @Override public ApiResponseStats get30() { return getAsync.apply(30).join(); }
            @Override public ApiResponseStats get31() { return getAsync.apply(31).join(); }
        };

//        executor.shutdown();
        return stats;
    }
*/
    public GetLessonStatistics31 getTeacherMonthlyStatistics(String teacherId, Integer year, Integer month) {

        GetLessonStatistics31 getLessonStatistics31 = new GetLessonStatistics31() {
            @Override
            public String getTeacherId() {
                return teacherId;
            }

            @Override
            public Integer getYear() {
                return year;
            }

            @Override
            public Integer getMonth() {
                return month;
            }

            @Override
            public ApiResponseStats3 get1() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 1);
            }

            @Override
            public ApiResponseStats3 get2() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 2);
            }

            @Override
            public ApiResponseStats3 get3() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 3);
            }

            @Override
            public ApiResponseStats3 get4() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 4);
            }

            @Override
            public ApiResponseStats3 get5() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 5);
            }

            @Override
            public ApiResponseStats3 get6() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 6);
            }

            @Override
            public ApiResponseStats3 get7() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 7);
            }

            @Override
            public ApiResponseStats3 get8() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 8);
            }

            @Override
            public ApiResponseStats3 get9() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 9);
            }

            @Override
            public ApiResponseStats3 get10() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 10);
            }

            @Override
            public ApiResponseStats3 get11() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 11);
            }

            @Override
            public ApiResponseStats3 get12() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 12);
            }

            @Override
            public ApiResponseStats3 get13() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 13);
            }

            @Override
            public ApiResponseStats3 get14() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 14);
            }

            @Override
            public ApiResponseStats3 get15() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 15);
            }

            @Override
            public ApiResponseStats3 get16() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 16);
            }

            @Override
            public ApiResponseStats3 get17() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 17);
            }

            @Override
            public ApiResponseStats3 get18() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 18);
            }

            @Override
            public ApiResponseStats3 get19() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 19);
            }

            @Override
            public ApiResponseStats3 get20() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 20);
            }

            @Override
            public ApiResponseStats3 get21() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 21);
            }

            @Override
            public ApiResponseStats3 get22() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 22);
            }

            @Override
            public ApiResponseStats3 get23() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 23);
            }

            @Override
            public ApiResponseStats3 get24() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 24);
            }

            @Override
            public ApiResponseStats3 get25() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 25);
            }

            @Override
            public ApiResponseStats3 get26() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 26);
            }

            @Override
            public ApiResponseStats3 get27() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 27);
            }

            @Override
            public ApiResponseStats3 get28() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 28);
            }

            @Override
            public ApiResponseStats3 get29() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 29);
            }

            @Override
            public ApiResponseStats3 get30() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 30);
            }

            @Override
            public ApiResponseStats3 get31() {
                return getTeacherDailyOrMonthlyStatistics(teacherId, year, month, 31);
            }
        };
        return getLessonStatistics31;
    }

    public ApiResponseStats3 getTeacherDailyOrMonthlyStatistics(
            String teacherId,
            Integer year, Integer month, Integer day
    ) {
        // === Input validation ===
        if (teacherId == null || teacherId.isEmpty()) {
            throw new IllegalArgumentException("Teacher ID cannot be null or empty");
        }
        if (year == null || month == null || day == null ||
                year <= 0 || month < 1 || month > 12 || day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid date parameters");
        }

        LocalDate date = LocalDate.of(year, month, day);
        boolean isToday = date.equals(LocalDate.now());
        Integer currentHour = isToday ? Math.max(0, LocalTime.now().getHour() - 8) : null;
        String dayName = date.format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU")));

        int week = date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR); // ✅ To‘g‘rilangan
        int weekday = date.getDayOfWeek().getValue();


        System.out.println("Calculating statistics for Teacher ID: " + teacherId +
                " on Date: " + year + "-" + month + "-" + day + "- Week: " + week + " Weekday: " + weekday);

        // === Asinxron ish uchun ExecutorService ===
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // === XML fayldan jadvalni olish (default va med) ===
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "/" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType52(year, week, dayName,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects, teacherId));
            }
            return result;
        }, executor);

        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "/" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType52(year, week, dayName,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed, teacherId));
            }
            return result;
        }, executor);

        // === Bitta o‘qituvchining DB dagi attendance ma’lumotlarini olish ===
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() ->
                userRepository.getTeacherStatisticsForDashboard(
                        teacherId, year, week, weekday
                ), executor
        );

        // === Jadval va statistikani birlashtirish ===
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join();

        List<UserForRoomStatistics2> stats = statsFuture.join();

        if (stats.isEmpty()) {
            executor.shutdown();
            return new ApiResponseStats3(true, "No data for this teacher",day,week,weekday, 0, 0);
        }

        UserForRoomStatistics2 teacherStats = stats.get(0);
        Map<String, Set<Integer>> attendedRoomSections = teacherStats.getRooms().stream()
                .filter(room -> !room.getTimes().isEmpty())
                //.filter(room -> roomRepository.existsRoomByNameStartingWith(room.getRoom().substring(0, Math.min(room.getRoom().length(), 6)).trim()))
                .collect(Collectors.toMap(
                        room -> room.getRoom().substring(0, Math.min(room.getRoom().length(), 6)).trim(),
                        room -> room.getTimes().stream()
                                .map(TimesForRoom::getSection)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toSet()),
                        (set1, set2) -> { // ⚡ Agar duplicate key bo‘lsa, merge qilamiz
                            set1.addAll(set2);
                            return set1;
                        }
                ));

        // === Shu o‘qituvchiga tegishli Table larni filter qilish ===
        List<Table> teacherTables = allTables.stream()
                .filter(t -> t.getTeacherData() != null && teacherId.equals(t.getTeacherData().getId()))
                .collect(Collectors.toList());

        int attended = 0;
        int eligibleShows = 0;

        for (Table table : teacherTables) {
            for (Show show : table.getShows()) {
                if (isToday && (show.getHourNumber() == null || currentHour == null || show.getHourNumber() > currentHour)) {
                    continue;
                }
                String roomPrefix = show.getRoom().substring(0, Math.min(show.getRoom().length(), 6)).trim();
                System.out.println("roomPrefix:: "+roomPrefix);
//                if (roomRepository.existsRoomByNameStartingWith(roomPrefix)) {
                    eligibleShows++;
                    Set<Integer> sections = attendedRoomSections.get(roomPrefix);
                    if (sections != null && show.getHourNumber() != null && sections.contains(show.getHourNumber())) {
                        attended++;
                    } else {
                        DailyTeacherMissedLesson dailyTeacherMissedLesson = new DailyTeacherMissedLesson(
                                userRepository.findById(teacherId).get(),
                                year,
                                month,
                                day,
                                week,
                                weekday,
                                show.getRoom(),
                                show.getHourNumber()
                        );
                        dailyTeacherMissedLessonRepository.save(dailyTeacherMissedLesson);
                    }
//                }
            }
        }

        int notAttended = Math.max(0, eligibleShows - attended);

        executor.shutdown();
        return new ApiResponseStats3(true, "Teacher statistics",day,week,weekday, attended, notAttended);
    }




    public List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi7(
            User user,
            Integer year, Integer month, Integer day,
            Integer week, Integer weekday) {

        // Input validation
        if (year <= 0 || month < 1 || month > 12 || day < 1 || day > 31 || week <= 0 || weekday < 1 || weekday > 7) {
            throw new IllegalArgumentException("Invalid date or week parameters");
        }

        LocalDate localDate = LocalDate.of(year, month, day);
        String dayName = localDate.format(
                DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU"))
        );

        // Check if the input date is today and compute current hour
        boolean isToday = localDate.equals(LocalDate.now());
        Integer currentHour = isToday ? Math.max(0, LocalTime.now().getHour() - 8) : null;

        // Use dynamic thread pool size
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Default haftalikni asinxron bajarish
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "/" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType6(year, week, dayName,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects));
            }
            return result;
        }, executor);

        // Med haftalikni asinxron bajarish
        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "/" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType6(year, week, dayName,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed));
            }
            return result;
        }, executor);

        // Umumiy statistikani DB dan asinxron olish
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() ->
                userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(
                        year, week, weekday
                ), executor
        );

        // Barcha natijalarni kutib olish
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join();

        List<UserForRoomStatistics2> stats = statsFuture.join();

        // Compute attendance statistics using CompletableFuture
        Map<String, UserForRoomStatistics2> statsMap = stats.stream()
                .collect(Collectors.toMap(UserForRoomStatistics2::getId, s -> s));

        List<Table> synchronizedTables = Collections.synchronizedList(allTables);
        List<CompletableFuture<Void>> tableFutures = synchronizedTables.stream()
                .map(table -> CompletableFuture.runAsync(() -> {
                    String teacherId = table.getTeacherData().getId();
                    UserForRoomStatistics2 stat = statsMap.get(teacherId);
                    int attended = 0;
                    int eligibleShows = 0;

                    if (stat != null && !table.getShows().isEmpty()) {
                        Map<String, Set<Integer>> attendedRoomSections = stat.getRooms().stream()
                                .filter(room -> !room.getTimes().isEmpty())
                                .collect(Collectors.toMap(
                                        room -> room.getRoom().substring(0, Math.min(room.getRoom().length(), 6)).trim(),
                                        room -> room.getTimes().stream()
                                                .map(TimesForRoom::getSection)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toSet()),
                                        (existing, replacement) -> existing
                                ));

                        // Count eligible shows and attended shows
                        long[] counts = table.getShows().parallelStream()
                                .collect(() -> new long[]{0, 0}, // [eligible, attended]
                                        (acc, show) -> {
                                            // Check if show is eligible (hourNumber <= currentHour if today)
                                            if (isToday && (show.getHourNumber() == null || currentHour == null || show.getHourNumber() > currentHour)) {
                                                return;
                                            }
                                            //*********
                                            String roomPrefix = show.getRoom().substring(0, Math.min(show.getRoom().length(), 6)).trim();
                                            if (roomRepository.existsRoomByNameStartingWith(roomPrefix)) {
                                                acc[0]++; // Increment eligible shows
                                                Set<Integer> sections = attendedRoomSections.get(roomPrefix);
                                                if (sections != null && show.getHourNumber() != null
                                                        && sections.contains(show.getHourNumber())) {
                                                    acc[1]++; // Increment attended
                                                }
                                            }
                                        },
                                        (acc1, acc2) -> {
                                            acc1[0] += acc2[0];
                                            acc1[1] += acc2[1];
                                        });

                        eligibleShows = (int) counts[0];
                        attended = (int) counts[1];
                    }

                    int notAttended = eligibleShows - attended;
                    table.setAttended(attended);
                    table.setNotAttended(notAttended < 0 ? 0 : notAttended);
                }, executor))
                .collect(Collectors.toList());

        // Wait for all attendance calculations
        CompletableFuture.allOf(tableFutures.toArray(new CompletableFuture[0])).join();

        // Group tables by kafedraName and compute stats
        Map<String, List<Table>> tablesByKafedra = synchronizedTables.stream()
                .filter(table -> table.getTeacherData() != null && table.getTeacherData().getKafedraName() != null)
                .collect(Collectors.groupingBy(
                        table -> table.getTeacherData().getKafedraName(),
                        Collectors.toList()
                ));

        List<ApiResponseStats2> responseStats = tablesByKafedra.entrySet().stream()
                .map(entry -> {
                    String kafedraName = entry.getKey();
                    List<Table> tables = entry.getValue();
                    int totalAttended = tables.stream()
                            .mapToInt(Table::getAttended)
                            .sum();
                    int totalNotAttended = tables.stream()
                            .mapToInt(Table::getNotAttended)
                            .sum();
                    return new ApiResponseStats2(true, "teachers", kafedraName, totalAttended, totalNotAttended);
                })
                .collect(Collectors.toList());

        // If no tables or no kafedraName, return empty list or default response
        if (responseStats.isEmpty()) {
            responseStats.add(new ApiResponseStats2(false, "No data found for any kafedra", null, 0, 0));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        responseStats.sort(Comparator.comparing(ApiResponseStats2::getKafedraId));
        return responseStats;
    }

//    @Override
//    public List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi9(
//            User user,
//            Integer year,
//            Integer month,
//            Integer day,
//            Integer week,
//            Integer weekday
//    ) {
//        // DB chaqiruvni tashqarida qilamiz
//        List<Kafedra> kafedras = kafedraRepository.findAll();
//
//
//
//        List<String> fakedrasIds = Collections.synchronizedList(kafedraRepository.findAll()
//                .stream()
//                .map(Kafedra::getId)
//                .collect(Collectors.toList()));
//
//        int poolSize = Math.min(fakedrasIds.size(), Runtime.getRuntime().availableProcessors());
//        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
//
//        try {
//            List<Future<ApiResponseStats2>> futures = fakedrasIds.stream()
//                    .map(id -> executor.submit(() -> {
//                        ApiResponseStats res = getKafedraKunlikVaHaftalikStatistikasi6(user, id, year, month, day, week, weekday);
//                        return new ApiResponseStats2(
//                                res.isSuccess(),
//                                res.getMessage(),
//                                kafedraRepository.getNameOfKafedraById(id),
//                                res.getTotalAttended(),
//                                res.getTotalNotAttended()
//                        );
//                    }))
//                    .collect(Collectors.toList());
//
//            List<ApiResponseStats2> result = new ArrayList<>();
//            for (Future<ApiResponseStats2> f : futures) {
//                result.add(f.get()); // get() bloklanadi, lekin hammasi parallel bajariladi
//            }
//            return result;
//        } catch (InterruptedException | ExecutionException e) {
//            throw new RuntimeException(e);
//        } finally {
//            executor.shutdown();
//        }
//    }


    @Override
    public List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi9(User user, Integer year, Integer month, Integer day, Integer week, Integer weekday) {
        List<String> fakedrasIds = kafedraRepository.findAll().stream().map(Kafedra::getId).collect(Collectors.toList());
        List<ApiResponseStats2> list = new ArrayList<>();
        fakedrasIds.forEach(id -> {
            ApiResponseStats res = getKafedraKunlikVaHaftalikStatistikasi6(user, id, year, month, day, week, weekday);
            ApiResponseStats2 res2 = new ApiResponseStats2(res.isSuccess(), res.getMessage(), kafedraRepository.getNameOfKafedraById(id), res.getTotalAttended(), res.getTotalNotAttended());
            list.add(res2);
        });
        return list;
    }


    public List<ApiResponseStats2> getKafedraKunlikVaHaftalikStatistikasi8(
            User user,
            Integer year, Integer month, Integer day,
            Integer week, Integer weekday) {

        // Input validation
        if (year <= 0 || month < 1 || month > 12 || day < 1 || day > 31 || week <= 0 || weekday < 1 || weekday > 7) {
            throw new IllegalArgumentException("Invalid date or week parameters");
        }

        LocalDate localDate = LocalDate.of(year, month, day);
        boolean isToday = localDate.equals(LocalDate.now());
        Integer currentHour = isToday ? Math.max(0, LocalTime.now().getHour() - 8) : null;

        // Generate list of day names from Monday to weekday
        List<String> dayNames = new ArrayList<>();
        LocalDate weekStart = localDate.minusDays(weekday - 1);
        for (int i = 1; i <= weekday; i++) {
            String dayName = weekStart.plusDays(i - 1)
                    .format(DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU")));
            dayNames.add(dayName);
        }

        // Use dynamic thread pool size
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Default timetable async
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "/" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType7(year, week, dayNames,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects));
            }
            return result;
        }, executor);

        // Medical timetable async
        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "/" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType7(year, week, dayNames,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed));
            }
            return result;
        }, executor);

        // Async fetch statistics from DB for all days up to weekday
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() -> {
            List<UserForRoomStatistics2> allStats = new ArrayList<>();
            for (int i = 1; i <= weekday; i++) {
                allStats.addAll(userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(year, week, i));
            }
            // Merge UserForRoomStatistics2 by id, combining their rooms
            Map<String, List<RoomsForStatistics2>> mergedRoomsById = new HashMap<>();
            for (UserForRoomStatistics2 stat : allStats) {
                mergedRoomsById.merge(stat.getId(), stat.getRooms(), (existing, newRooms) -> {
                    List<RoomsForStatistics2> combined = new ArrayList<>(existing);
                    combined.addAll(newRooms);
                    return combined;
                });
            }
            // Create merged UserForRoomStatistics2 objects
            List<UserForRoomStatistics2> mergedStats = new ArrayList<>();
            for (Map.Entry<String, List<RoomsForStatistics2>> entry : mergedRoomsById.entrySet()) {
                mergedStats.add(new UserForRoomStatistics2() {
                    private final String id = entry.getKey();
                    private final List<RoomsForStatistics2> rooms = entry.getValue();

                    @Override
                    public String getId() {
                        return id;
                    }

                    @Override
                    public String getFullName() {
                        return null;
                    } // Not used

                    @Override
                    public String getRFID() {
                        return null;
                    } // Not used

                    @Override
                    public String getPassport() {
                        return null;
                    } // Not used

                    @Override
                    public Integer getYear() {
                        return null;
                    } // Not used

                    @Override
                    public Integer getWeek() {
                        return null;
                    } // Not used

                    @Override
                    public Integer getWeekday() {
                        return null;
                    } // Not used

                    @Override
                    public List<RoomsForStatistics2> getRooms() {
                        return rooms;
                    }
                });
            }
            return mergedStats;
        }, executor);

        // Combine results
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join();

        List<UserForRoomStatistics2> stats = statsFuture.join();

        // Compute attendance statistics
        Map<String, UserForRoomStatistics2> statsMap = stats.stream()
                .collect(Collectors.toMap(
                        UserForRoomStatistics2::getId,
                        s -> s,
                        (existing, replacement) -> existing // Should not occur after merging
                ));

        List<Table> synchronizedTables = Collections.synchronizedList(allTables);
        List<CompletableFuture<Void>> tableFutures = synchronizedTables.stream()
                .map(table -> CompletableFuture.runAsync(() -> {
                    String teacherId = table.getTeacherData().getId();
                    UserForRoomStatistics2 stat = statsMap.get(teacherId);
                    int attended = 0;
                    int eligibleShows = 0;

                    if (stat != null && !table.getShows().isEmpty()) {
                        Map<String, Set<Integer>> attendedRoomSections = stat.getRooms().stream()
                                .filter(room -> !room.getTimes().isEmpty())
                                .collect(Collectors.toMap(
                                        room -> room.getRoom() != null ? room.getRoom().substring(0, Math.min(room.getRoom().length(), 4)) : "",
                                        room -> room.getTimes().stream()
                                                .map(TimesForRoom::getSection)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toSet()),
                                        (existing, replacement) -> {
                                            Set<Integer> merged = new HashSet<>(existing);
                                            merged.addAll(replacement);
                                            return merged;
                                        }
                                ));

                        // Count eligible shows and attended shows
                        long[] counts = table.getShows().parallelStream()
                                .collect(() -> new long[]{0, 0}, // [eligible, attended]
                                        (acc, show) -> {
                                            // Check if show is eligible (hourNumber <= currentHour if today)
                                            if (isToday && (show.getHourNumber() == null || currentHour == null || show.getHourNumber() > currentHour)) {
                                                return;
                                            }
                                            acc[0]++; // Increment eligible shows
                                            String roomPrefix = show.getRoom() != null
                                                    ? show.getRoom().substring(0, Math.min(show.getRoom().length(), 4))
                                                    : "";
                                            Set<Integer> sections = attendedRoomSections.get(roomPrefix);
                                            if (sections != null && show.getHourNumber() != null
                                                    && sections.contains(show.getHourNumber())) {
                                                acc[1]++; // Increment attended
                                            }
                                        },
                                        (acc1, acc2) -> {
                                            acc1[0] += acc2[0];
                                            acc1[1] += acc2[1];
                                        });

                        eligibleShows = (int) counts[0];
                        attended = (int) counts[1];
                    }

                    int notAttended = eligibleShows - attended;
                    table.setAttended(attended);
                    table.setNotAttended(notAttended < 0 ? 0 : notAttended);
                }, executor))
                .collect(Collectors.toList());

        // Wait for all attendance calculations
        CompletableFuture.allOf(tableFutures.toArray(new CompletableFuture[0])).join();

        // Group tables by kafedraName and compute stats
        Map<String, List<Table>> tablesByKafedra = synchronizedTables.stream()
                .filter(table -> table.getTeacherData() != null && table.getTeacherData().getKafedraName() != null)
                .collect(Collectors.groupingBy(
                        table -> table.getTeacherData().getKafedraName(),
                        Collectors.toList()
                ));

        List<ApiResponseStats2> responseStats = tablesByKafedra.entrySet().stream()
                .map(entry -> {
                    String kafedraName = entry.getKey();
                    List<Table> tables = entry.getValue();
                    int totalAttended = tables.stream()
                            .mapToInt(Table::getAttended)
                            .sum();
                    int totalNotAttended = tables.stream()
                            .mapToInt(Table::getNotAttended)
                            .sum();
                    return new ApiResponseStats2(true, "teachers", kafedraName, totalAttended, totalNotAttended);
                })
                .collect(Collectors.toList());

        // Default response if no data
        if (responseStats.isEmpty()) {
            responseStats.add(new ApiResponseStats2(false, "No data found for any kafedra", null, 0, 0));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return responseStats;
    }

    public ApiResponseTwoObj getKafedraKunlikVaHaftalikStatistikasi5(
            User user, String kafedraId,
            Integer year, Integer month, Integer day,
            Integer week, Integer weekday) {


        // Input validation
        if (kafedraId == null || kafedraId.isEmpty()) {
            throw new IllegalArgumentException("Kafedra ID cannot be null or empty");
        }
        if (year <= 0 || month < 1 || month > 12 || day < 1 || day > 31 || week <= 0 || weekday < 1 || weekday > 7) {
            throw new IllegalArgumentException("Invalid date or week parameters");
        }

        LocalDate localDate = LocalDate.of(year, month, day);
        String dayName = localDate.format(
                DateTimeFormatter.ofPattern("EEEE", new Locale("ru", "RU"))
        );

        // Use dynamic thread pool size based on available processors
        int cores = Math.max(2, Runtime.getRuntime().availableProcessors());
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        // Default haftalikni asinxron bajarish
        CompletableFuture<List<Table>> defaultFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path defPath = Paths.get(year + "/" + week + ".xml");
            if (Files.exists(defPath)) {
                getTimeTableByWeek(year, week);
                result.addAll(buildTablesForType5(year, week, dayName,
                        daysDefs, cards, lessons, teachers,
                        periods, classRooms, classes, subjects, kafedraId));
            }
            return result;
        }, executor);

        // Med haftalikni asinxron bajarish
        CompletableFuture<List<Table>> medFuture = CompletableFuture.supplyAsync(() -> {
            List<Table> result = new ArrayList<>();
            Path medPath = Paths.get(year + "/" + week + "med.xml");
            if (Files.exists(medPath)) {
                getTimeTableByWeekMed(year, week);
                result.addAll(buildTablesForType5(year, week, dayName,
                        daysDefsMed, cardsMed, lessonsMed, teachersMed,
                        periodsMed, classRoomsMed, classesMed, subjectsMed,
                        kafedraId));
            }
            return result;
        }, executor);

        // Umumiy statistikani DB dan asinxron olish
        CompletableFuture<List<UserForRoomStatistics2>> statsFuture = CompletableFuture.supplyAsync(() ->
                userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(
                        kafedraId, year, week, weekday
                ), executor
        );

        // Barcha natijalarni kutib olish
        List<Table> allTables = defaultFuture
                .thenCombine(medFuture, (d, m) -> {
                    List<Table> all = new ArrayList<>(d);
                    all.addAll(m);
                    return all;
                })
                .join(); // ikkalasi tugaguncha kutadi

        List<UserForRoomStatistics2> stats = statsFuture.join();

        // Compute attendance statistics using CompletableFuture
        Map<String, UserForRoomStatistics2> statsMap = stats.stream()
                .collect(Collectors.toMap(UserForRoomStatistics2::getId, s -> s));

        // Ensure thread-safe list for concurrent modifications
        List<Table> synchronizedTables = Collections.synchronizedList(allTables);
        List<CompletableFuture<Void>> tableFutures = synchronizedTables.stream()
                .map(table -> CompletableFuture.runAsync(() -> {
                    String teacherId = table.getTeacherData().getId();
                    UserForRoomStatistics2 stat = statsMap.get(teacherId);
                    int attended = 0;

                    if (stat != null && !table.getShows().isEmpty()) {
                        // Precompute room prefixes and sections
                        Map<String, Set<Integer>> attendedRoomSections = stat.getRooms().stream()
                                .filter(room -> !room.getTimes().isEmpty())
                                .collect(Collectors.toMap(
                                        room -> room.getRoom().substring(0, Math.min(room.getRoom().length(), 4)),
                                        room -> room.getTimes().stream()
                                                .map(TimesForRoom::getSection)
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.toSet()),
                                        (existing, replacement) -> existing // Merge for duplicate prefixes
                                ));

                        // Use parallel stream for large shows lists
                        attended = (int) table.getShows().parallelStream()
                                .filter(show -> {
                                    String roomPrefix = show.getRoom().substring(0, Math.min(show.getRoom().length(), 4));
                                    Set<Integer> sections = attendedRoomSections.get(roomPrefix);
                                    return sections != null && show.getHourNumber() != null
                                            && sections.contains(show.getHourNumber());
                                })
                                .count();
                    }

                    int notAttended = table.getShows().size() - attended;
                    table.setAttended(attended);
                    table.setNotAttended(notAttended < 0 ? 0 : notAttended);
                }, executor))
                .collect(Collectors.toList());

        // Wait for all attendance calculations to complete
        CompletableFuture.allOf(tableFutures.toArray(new CompletableFuture[0])).join();

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        allTables.sort(Comparator.comparing(
                t -> t.getTeacherData().getDegree(),
                Comparator.nullsLast(Integer::compareTo)
        ));
        return new ApiResponseTwoObj(true, "teachers", allTables, stats);
    }

     /*for (Table table : allTables) {
            String teacherId = table.getTeacherData().getId();
            UserForRoomStatistics2 stat = statsMap.get(teacherId);
            int attended = 0;

            if (stat != null && teacherId.equals(stat.getId()) && !table.getShows().isEmpty()) {
                for (Show show : table.getShows()) {
                    List<RoomsForStatistics2> collect = stat.getRooms().stream().filter(r -> r.getRoom().startsWith(show.getRoom().substring(0,4))).collect(Collectors.toList());
                    l:for (RoomsForStatistics2 roomsForStatistics2 : collect) {
                        for (TimesForRoom time : roomsForStatistics2.getTimes()) {
                            if(time.getSection().equals(show.getHourNumber())){
                                attended++;
                                break l;
                            }
                        }
                    }
                }
            }

            int notAttended = table.getShows().size() - attended;
            table.setAttended(attended);
            table.setNotAttended(notAttended < 0 ? 0 : notAttended); // Prevent negative values
        }*/

    private List<Table> buildTablesForType5(
            int year, int week, String dayName,
            List<DaysDef> daysDefs, List<Card> cards,
            List<LessonXml> lessons, List<Teacher> teachers,
            List<Period> periods, List<ClassRoom> classRooms,
            List<Class> classes, List<Subject> subjects,
            String kafedraId
    ) {
        List<Table> tables = Collections.synchronizedList(new ArrayList<>());

        // === oldingi dayId qismi o‘zgarishsiz ===
        String dayId = daysDefs.stream()
                .filter(d -> d.getName().equalsIgnoreCase(dayName))
                .findFirst()
                .map(d -> d.getDays().get(0))
                .orElse(null);
        if (dayId == null) return tables;

        Set<String> lessonIds = cards.stream()
                .filter(c -> c.getDays().contains(dayId))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        Set<Teacher> targetTeachers = teachers.stream()
                .filter(t -> lessons.stream()
                        .filter(l -> lessonIds.contains(l.getId()))
                        .anyMatch(l -> l.getTeacherIds().contains(t.getId())))
                .collect(Collectors.toSet());

        // === Parallel processing ===
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<?>> futures = new ArrayList<>();
        for (Teacher teacher : targetTeachers) {
            futures.add(pool.submit(() -> {
                TeacherData td = userRepository
                        .getTeachersForRememberWithKafedraIdLogin(teacher.getShortName(), kafedraId);
                if (td == null) return;

                List<Show> shows = new ArrayList<>();

                for (String lessonId : lessonIds) {
                    // lessons ni map qilib tezroq qidirish mumkin, lekin sintaksisni saqladim
                    for (LessonXml xml : lessons) {
                        if (!xml.getId().equals(lessonId) || !xml.getTeacherIds().contains(teacher.getId())) continue;
                        for (Card card : cards) {
                            if (!card.getLessonId().equals(xml.getId()) || !card.getDays().contains(dayId)) continue;

                            Period p = periods.stream()
                                    .filter(pe -> pe.getName().equals(card.getPeriod()))
                                    .findFirst().orElse(null);
                            if (p == null) continue;

                            Show s = new Show();
                            s.setRoom(card.getClassroomIds().stream()
                                    .map(cid -> classRooms.stream()
                                            .filter(r -> r.getId().equals(cid))
                                            .map(ClassRoom::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .findFirst().orElse(""));
                            s.setGroups(xml.getClassIds().stream()
                                    .map(cid -> classes.stream()
                                            .filter(c -> c.getId().equals(cid))
                                            .map(Class::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .collect(Collectors.toList()));
                            s.setLessonName(subjects.stream()
                                    .filter(sub -> sub.getId().equals(xml.getSubjectId()))
                                    .map(Subject::getName)
                                    .findFirst().orElse(""));
                            s.setHourNumber(p.getPeriod());
                            s.setPeriodStartAndEndTime(p.getStartTime() + "-" + p.getEndTime());
                            shows.add(s);
                        }
                    }
                }
                tables.add(new Table(td, shows, 0, 0)); // Initialize with 0
            }));
        }

        // barcha vazifalar tugashini kutamiz
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();

        return tables;
    }

    private List<Table> buildTablesForType52(
            int year, int week, String dayName,
            List<DaysDef> daysDefs, List<Card> cards,
            List<LessonXml> lessons, List<Teacher> teachers,
            List<Period> periods, List<ClassRoom> classRooms,
            List<Class> classes, List<Subject> subjects,
            String teacherId
    ) {
        List<Table> tables = Collections.synchronizedList(new ArrayList<>());

        // === oldingi dayId qismi o‘zgarishsiz ===
        String dayId = daysDefs.stream()
                .filter(d -> d.getName().equalsIgnoreCase(dayName))
                .findFirst()
                .map(d -> d.getDays().get(0))
                .orElse(null);
        if (dayId == null) return tables;

        Set<String> lessonIds = cards.stream()
                .filter(c -> c.getDays().contains(dayId))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        Set<Teacher> targetTeachers = teachers.stream()
                .filter(t -> lessons.stream()
                        .filter(l -> lessonIds.contains(l.getId()))
                        .anyMatch(l -> l.getTeacherIds().contains(t.getId())))
                .collect(Collectors.toSet());

        // === Parallel processing ===
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<?>> futures = new ArrayList<>();
        for (Teacher teacher : targetTeachers) {
            futures.add(pool.submit(() -> {
                TeacherData td = userRepository
                        .getTeachersForRememberWithTeacherIdLogin(teacher.getShortName(), teacherId);
                if (td == null) return;

                List<Show> shows = new ArrayList<>();

                for (String lessonId : lessonIds) {
                    // lessons ni map qilib tezroq qidirish mumkin, lekin sintaksisni saqladim
                    for (LessonXml xml : lessons) {
                        if (!xml.getId().equals(lessonId) || !xml.getTeacherIds().contains(teacher.getId())) continue;
                        for (Card card : cards) {
                            if (!card.getLessonId().equals(xml.getId()) || !card.getDays().contains(dayId)) continue;

                            Period p = periods.stream()
                                    .filter(pe -> pe.getName().equals(card.getPeriod()))
                                    .findFirst().orElse(null);
                            if (p == null) continue;

                            Show s = new Show();
                            s.setRoom(card.getClassroomIds().stream()
                                    .map(cid -> classRooms.stream()
                                            .filter(r -> r.getId().equals(cid))
                                            .map(ClassRoom::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .findFirst().orElse(""));
                            s.setGroups(xml.getClassIds().stream()
                                    .map(cid -> classes.stream()
                                            .filter(c -> c.getId().equals(cid))
                                            .map(Class::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .collect(Collectors.toList()));
                            s.setLessonName(subjects.stream()
                                    .filter(sub -> sub.getId().equals(xml.getSubjectId()))
                                    .map(Subject::getName)
                                    .findFirst().orElse(""));
                            s.setHourNumber(p.getPeriod());
                            s.setPeriodStartAndEndTime(p.getStartTime() + "-" + p.getEndTime());
                            shows.add(s);
                        }
                    }
                }
                tables.add(new Table(td, shows, 0, 0)); // Initialize with 0
            }));
        }

        // barcha vazifalar tugashini kutamiz
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();

        return tables;
    }

    private List<Table> buildTablesForType6(
            int year, int week, String dayName,
            List<DaysDef> daysDefs, List<Card> cards,
            List<LessonXml> lessons, List<Teacher> teachers,
            List<Period> periods, List<ClassRoom> classRooms,
            List<Class> classes, List<Subject> subjects
    ) {
        List<Table> tables = Collections.synchronizedList(new ArrayList<>());

        // === oldingi dayId qismi o‘zgarishsiz ===
        String dayId = daysDefs.stream()
                .filter(d -> d.getName().equalsIgnoreCase(dayName))
                .findFirst()
                .map(d -> d.getDays().get(0))
                .orElse(null);
        if (dayId == null) return tables;

        Set<String> lessonIds = cards.stream()
                .filter(c -> c.getDays().contains(dayId))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        Set<Teacher> targetTeachers = teachers.stream()
                .filter(t -> lessons.stream()
                        .filter(l -> lessonIds.contains(l.getId()))
                        .anyMatch(l -> l.getTeacherIds().contains(t.getId())))
                .collect(Collectors.toSet());

        // === Parallel processing ===
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<?>> futures = new ArrayList<>();
        for (Teacher teacher : targetTeachers) {
            futures.add(pool.submit(() -> {
                TeacherData td = userRepository
                        .getTeachersForRememberWithKafedraIdLogin(teacher.getShortName());
                if (td == null) return;

                List<Show> shows = new ArrayList<>();

                for (String lessonId : lessonIds) {
                    // lessons ni map qilib tezroq qidirish mumkin, lekin sintaksisni saqladim
                    for (LessonXml xml : lessons) {
                        if (!xml.getId().equals(lessonId) || !xml.getTeacherIds().contains(teacher.getId())) continue;
                        for (Card card : cards) {
                            if (!card.getLessonId().equals(xml.getId()) || !card.getDays().contains(dayId)) continue;

                            Period p = periods.stream()
                                    .filter(pe -> pe.getName().equals(card.getPeriod()))
                                    .findFirst().orElse(null);
                            if (p == null) continue;

                            Show s = new Show();
                            s.setRoom(card.getClassroomIds().stream()
                                    .map(cid -> classRooms.stream()
                                            .filter(r -> r.getId().equals(cid))
                                            .map(ClassRoom::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .findFirst().orElse(""));
                            s.setGroups(xml.getClassIds().stream()
                                    .map(cid -> classes.stream()
                                            .filter(c -> c.getId().equals(cid))
                                            .map(Class::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .collect(Collectors.toList()));
                            s.setLessonName(subjects.stream()
                                    .filter(sub -> sub.getId().equals(xml.getSubjectId()))
                                    .map(Subject::getName)
                                    .findFirst().orElse(""));
                            s.setHourNumber(p.getPeriod());
                            s.setPeriodStartAndEndTime(p.getStartTime() + "-" + p.getEndTime());
                            shows.add(s);
                        }
                    }
                }
                tables.add(new Table(td, shows, 0, 0)); // Initialize with 0
            }));
        }

        // barcha vazifalar tugashini kutamiz
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();

        return tables;
    }


    private List<Table> buildTablesForType7(
            int year, int week, List<String> dayNames,
            List<DaysDef> daysDefs, List<Card> cards,
            List<LessonXml> lessons, List<Teacher> teachers,
            List<Period> periods, List<ClassRoom> classRooms,
            List<Class> classes, List<Subject> subjects
    ) {
        List<Table> tables = Collections.synchronizedList(new ArrayList<>());

        // Get dayIds for all specified dayNames
        Set<String> dayIds = dayNames.stream()
                .map(dayName -> daysDefs.stream()
                        .filter(d -> d.getName().equalsIgnoreCase(dayName))
                        .findFirst()
                        .map(d -> d.getDays().get(0))
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (dayIds.isEmpty()) return tables;

        // Get lessonIds for all relevant days
        Set<String> lessonIds = cards.stream()
                .filter(c -> dayIds.stream().anyMatch(dayId -> c.getDays().contains(dayId)))
                .map(Card::getLessonId)
                .collect(Collectors.toSet());

        Set<Teacher> targetTeachers = teachers.stream()
                .filter(t -> lessons.stream()
                        .filter(l -> lessonIds.contains(l.getId()))
                        .anyMatch(l -> l.getTeacherIds().contains(t.getId())))
                .collect(Collectors.toSet());

        // Parallel processing
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(cores);

        List<Future<?>> futures = new ArrayList<>();
        for (Teacher teacher : targetTeachers) {
            futures.add(pool.submit(() -> {
                TeacherData td = userRepository
                        .getTeachersForRememberWithKafedraIdLogin(teacher.getShortName());
                if (td == null) return;

                List<Show> shows = new ArrayList<>();

                for (String lessonId : lessonIds) {
                    for (LessonXml xml : lessons) {
                        if (!xml.getId().equals(lessonId) || !xml.getTeacherIds().contains(teacher.getId())) continue;
                        for (Card card : cards) {
                            if (!card.getLessonId().equals(xml.getId()) || !dayIds.contains(card.getDays().get(0)))
                                continue;

                            Period p = periods.stream()
                                    .filter(pe -> pe.getName().equals(card.getPeriod()))
                                    .findFirst().orElse(null);
                            if (p == null) continue;

                            Show s = new Show();
                            s.setRoom(card.getClassroomIds().stream()
                                    .map(cid -> classRooms.stream()
                                            .filter(r -> r.getId().equals(cid))
                                            .map(ClassRoom::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .findFirst().orElse(""));
                            s.setGroups(xml.getClassIds().stream()
                                    .map(cid -> classes.stream()
                                            .filter(c -> c.getId().equals(cid))
                                            .map(Class::getName)
                                            .findFirst().orElse(""))
                                    .filter(str -> !str.isEmpty())
                                    .collect(Collectors.toList()));
                            s.setLessonName(subjects.stream()
                                    .filter(sub -> sub.getId().equals(xml.getSubjectId()))
                                    .map(Subject::getName)
                                    .findFirst().orElse(""));
                            s.setHourNumber(p.getPeriod());
                            s.setPeriodStartAndEndTime(p.getStartTime() + "-" + p.getEndTime());
                            shows.add(s);
                        }
                    }
                }
                synchronized (tables) {
                    tables.add(new Table(td, shows, 0, 0));
                }
            }));
        }

        // Wait for all tasks to complete
        for (Future<?> f : futures) {
            try {
                f.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();

        return tables;
    }
}
