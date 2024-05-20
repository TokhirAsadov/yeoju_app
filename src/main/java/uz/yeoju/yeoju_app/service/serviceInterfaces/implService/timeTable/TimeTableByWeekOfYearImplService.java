package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.ApiResponseTwoObj;
import uz.yeoju.yeoju_app.payload.educationYear.GroupsLessonCount;
import uz.yeoju.yeoju_app.payload.educationYear.WeekRestDtoForDean;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import org.jdom2.Document;
import org.jdom2.Element;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.resDto.dekan.dashboard.StudentStatisticsWithWeekOfEduYear;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.*;
import uz.yeoju.yeoju_app.repository.DekanRepository;
import uz.yeoju.yeoju_app.repository.GroupRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.EducationYearRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@Service
public class TimeTableByWeekOfYearImplService implements TimeTableByWeekOfYearService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EducationYearRepository educationYearRepository;

    @Autowired
    DekanRepository dekanRepository;

    @Autowired
    GroupRepository groupRepository;


    public static final List<Period> periods = new ArrayList<>();
    public static final List<DaysDef> daysDefs = new ArrayList<>();
    public static final List<WeeksDef> weeksDefs = new ArrayList<>();
    public static final List<TermsDef> termsDefs = new ArrayList<>();
    public static final List<Subject> subjects = new ArrayList<>();
    public static final List<Teacher> teachers = new ArrayList<>();
    public static final List<ClassRoom> classRooms = new ArrayList<>();
    public static final List<Grade> grades = new ArrayList<>();
    public static final List<uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class> classes = new ArrayList<>();
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
    public void getTimeTableByWeek(Integer week) {

        clearTimeTable();

        String xmlFile = week+".xml";
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

        String xmlFile = year+"/"+week+".xml";
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
    public void getTimeTableByWeekMed(Integer year, Integer week) {
        clearTimeTableMed();

        String xmlFile = year+"/"+week+"med.xml";
        Document document = getSAXParsedDocument(xmlFile);
        Element rootNode = document.getRootElement();
        rootNode.getChild("periods").getChildren("period").forEach(TimeTableByWeekOfYearImplService::readPeriodMed);
        rootNode.getChild("daysdefs").getChildren("daysdef").forEach(TimeTableByWeekOfYearImplService::readDaysDefMed);
        rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(TimeTableByWeekOfYearImplService::readWeeksDefMed);
        rootNode.getChild("termsdefs").getChildren("termsdef").forEach(TimeTableByWeekOfYearImplService::readTermsDefsMed);
        rootNode.getChild("subjects").getChildren("subject").forEach(TimeTableByWeekOfYearImplService::readSubjectMed);
        rootNode.getChild("teachers").getChildren("teacher").forEach(TimeTableByWeekOfYearImplService::readTeacherMed);
        rootNode.getChild("classrooms").getChildren("classroom").forEach(TimeTableByWeekOfYearImplService::readClassroomMed);
        rootNode.getChild("grades").getChildren("grade").forEach(TimeTableByWeekOfYearImplService::readGradeMed);
        rootNode.getChild("classes").getChildren("class").forEach(TimeTableByWeekOfYearImplService::readClassMed);
        rootNode.getChild("groups").getChildren("group").forEach(TimeTableByWeekOfYearImplService::readGroupMed);
        rootNode.getChild("lessons").getChildren("lesson").forEach(TimeTableByWeekOfYearImplService::readLessonMed);
        rootNode.getChild("cards").getChildren("card").forEach(TimeTableByWeekOfYearImplService::readCardMed);
    }

    @Override
    public ApiResponseTwoObj getStatisticsForDeanDashboard(String educationYearId, String eduType,String eduTypeId, String facultyId, String facultyShortName) {
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

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml ->{
                        cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).forEach(card ->{
                            for (DaysDef daysDef : daysDefs) {
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
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

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml ->{
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
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

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml ->{
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
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

                    lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml ->{
                        Optional<Card> first = cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).findFirst();
                        if (first.isPresent()) {
                            for (DaysDef daysDef : daysDefs) {
                                Card card = first.get();
                                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                    if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                        daysS.add(1);
                                    if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                        daysS.add(2);
                                    if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                        daysS.add(3);
                                    if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                        daysS.add(4);
                                    if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                        daysS.add(5);
                                    if (daysDef.getDays().get(0).equals("000001"))
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

            return new ApiResponseTwoObj(true, "statistics", years,lessonsS);
        }
        else{

            weeks.forEach(w -> {
                System.out.println((w.getStart().getYear() + 1900) + "------------------------------------");
                getTimeTableByWeek(w.getStart().getYear() + 1900, w.getSortNumber());


                classes.stream().filter(c -> c.getShortName().startsWith(facultyShortName)).forEach(c -> {
                    Group groupByName = groupRepository.findGroupByName(c.getName());
                    if (groupByName!=null) {

                        if(groupByName.getLevel()==1) get1.add(c.getName());
                        if(groupByName.getLevel()==2) get2.add(c.getName());
                        if(groupByName.getLevel()==3) get3.add(c.getName());
                        if(groupByName.getLevel()==4) get4.add(c.getName());

                        GroupsLessonCount groupsLessonCount = new GroupsLessonCount();
                        groupsLessonCount.setWeek(w.getSortNumber());
                        groupsLessonCount.setGroupName(c.getName());

                        List<Integer> daysS = new ArrayList<>();

                        lessons.stream().filter(lessonXml -> lessonXml.getClassIds().contains(c.getId())).forEach(lessonXml ->{
                            cards.stream().filter(card -> Objects.equals(card.getLessonId(), lessonXml.getId())).forEach(card ->{
                                for (DaysDef daysDef : daysDefs) {
                                    if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                                        if (daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                                            daysS.add(1);
                                        if (daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                                            daysS.add(2);
                                        if (daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                                            daysS.add(3);
                                        if (daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                                            daysS.add(4);
                                        if (daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                                            daysS.add(5);
                                        if (daysDef.getDays().get(0).equals("000001"))
                                            daysS.add(6);
                                        break;
                                    }
                                }
                            });
                        });

                        groupsLessonCount.setCount(daysS);


                        if(groupByName.getLevel()==1) get11.add(groupsLessonCount);
                        if(groupByName.getLevel()==2) get22.add(groupsLessonCount);
                        if(groupByName.getLevel()==3) get33.add(groupsLessonCount);
                        if(groupByName.getLevel()==4) get44.add(groupsLessonCount);

                    }
                });

            });


            List<StudentStatisticsWithWeekOfEduYear> years1 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get1.toString().length() > 0 ? get1.toString().substring(1, get1.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years2 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get2.toString().length() > 0 ? get2.toString().substring(1, get2.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years3 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get3.toString().length() > 0 ? get3.toString().substring(1, get3.toString().length() - 1).replaceAll("\\s", "") : "");
            List<StudentStatisticsWithWeekOfEduYear> years4 = dekanRepository.getStudentStatisticsWithWeekOfEdu(educationYearId, facultyId, "KUNDUZGI_KECHKI", eduTypeId, get4.toString().length() > 0 ? get4.toString().substring(1, get4.toString().length() - 1).replaceAll("\\s", "") : "");

            List<List<StudentStatisticsWithWeekOfEduYear>> years = Arrays.asList(years1, years2, years3, years4);
            List<List<GroupsLessonCount>> lessonsS = Arrays.asList(get11, get22, get33, get44);

            return new ApiResponseTwoObj(true, "statistics", years,lessonsS);
//            return new ApiResponseTwoObj(false, "statistics");
        }
    }


    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user,String groupName, Integer week, Integer day, Boolean s) {

        getTimeTableByWeek(week);

        List<Show> shows = timeTable(groupName);
        if (day==0) {
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

                return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
            }
            else {

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
                return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists);
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true,"Time Table of Today",today);
        }
    }

    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(User user, String groupName, Integer year, Integer week, Integer day, Boolean s) {
        getTimeTableByWeek(year,week);

        List<Show> shows = timeTable(groupName);
        if (day==0) {
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


            if(year ==2023 && week<44){
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

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
                }
                else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists);
                }
            }
            else {
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

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
                }
                else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists);
                }
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true,"Time Table of Today",today);
        }
    }

    @Override
    public ApiResponseTwoObj getStudentTimeTableAPIByWeekOfYear(String userId, String groupName, Integer year, Integer week, Integer day, Boolean s) {
        getTimeTableByWeek(year,week);

        List<Show> shows = timeTable(groupName);
        if (day==0) {
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

            if(year ==2023 && week<44){
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

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
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
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,slists);
                }
            }
            else {
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

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
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
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,slists);
                }
            }




//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true,"Time Table of Today",today);
        }
    }


    @Override
    public ApiResponseTwoObj getTimesForRoomStatisticsByUserIdAndWeek(User user, String groupName, Integer year, Integer week, Integer day, Boolean s) {
       getTimeTableByWeek(year,week);

        List<Show> shows = timeTable(groupName);
        if (day==0) {
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


            if(year ==2023 && week<44){
                if (s) {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
                }
                else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists);
                }
            }
            else {
                if (s) {
                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportAndWeekLogin(teacher.getShortName(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });

                    return  new ApiResponseTwoObj(true, "Time Table of Week", showWeekNumberFields,lists);
                }
                else {

                    showWeekNumberFields.getGet1().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        teacherList.addAll(i.getTeachers());
                        for (Teacher teacher : i.getTeachers()) {
                            List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByUserIdAndWeek(user.getId(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    return new ApiResponseTwoObj(true, "Time Table of Week2", showWeekNumberFields,lists);
                }
            }


//            return ResponseEntity.ok(new ApiResponse(true, "Time Table of Week", showWeekNumberFields));
        }
        else {
            List<Show> today = shows.stream().filter(item -> Objects.equals(item.getDayNumber(), day)).collect(Collectors.toList());
            return new ApiResponseTwoObj(true,"Time Table of Today",today);
        }
    }

    @Override
    public ApiResponse getTeacherTimeTable(User user,Integer week,Integer year){

        getTimeTableByWeek(year,week);

        System.out.println(user+" ----------------- user");
        System.out.println("================================= +++++++++++++++++++++++");
        System.out.println("------------------- "+daysDefs.toString()+" ----------------");

        List<String> daysList = daysDefs
                .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                .collect(Collectors.toSet()).stream().map(i-> i.getDays().get(0)).collect(Collectors.toList());
        Collections.sort(daysList, Collections.reverseOrder());
        List<Table> tables = new ArrayList<>();

        if(year ==2023 && week<44){
            for (String s : daysList) {
                Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                System.out.println(teachersIds+"-------------------------- teacherIds");
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum())).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();

                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        System.out.println(card+"------------------------------------- *****************************************************************************");
                                        if (card.getClassroomIds().get(0)!=null && card.getClassroomIds().get(0)!="") {
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }


                                        show.setLessonName(subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);

                                        shows.add(show);

                                    }




                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }
        else {
            for (String s : daysList) {
                Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                System.out.println(teachersIds+"-------------------------- teacherIds");
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();

                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        System.out.println(card+"------------------------------------- *****************************************************************************");
                                        if (card.getClassroomIds().get(0)!=null && card.getClassroomIds().get(0)!="") {
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }

                                        show.setLessonName(subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }

        getTimeTableByWeekMed(year,week);
        List<String> daysListMed = daysDefsMed
                .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                .collect(Collectors.toSet()).stream().map(i-> i.getDays().get(0)).collect(Collectors.toList());
        Collections.sort(daysListMed, Collections.reverseOrder());

        if(year ==2023 && week<44){
            for (String s : daysListMed) {
                Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum()) ).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//                TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
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

                                        show.setLessonName(subjectsMed.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);

                                        shows.add(show);

                                    }




                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }
        else{
            for (String s : daysListMed) {
                Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin()) ).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
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
                                        show.setLessonName(subjectsMed.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
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
                                        System.out.println(show.toString()+" <- show "+s);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }


        return new ApiResponse(false,"ishlayapdi ok",tables);
    }

    @Override
    public ApiResponse getTeacherTimeTableToday(User user, Integer week, Integer year) {
        getTimeTableByWeek(year,week);

        System.out.println(user+" ----------------- user");
        System.out.println("================================= +++++++++++++++++++++++");
        System.out.println("------------------- "+daysDefs.toString()+" ----------------");

        LocalDate localDate= LocalDate.now();
        Locale spanishLocale=new Locale("ru", "RU");
        String dayName = localDate.format(DateTimeFormatter.ofPattern("EEEE",spanishLocale));

        List<String> daysList = daysDefs
                .stream().filter(item -> item.getName().equalsIgnoreCase(dayName) && !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                .collect(Collectors.toSet()).stream().map(i-> i.getDays().get(0)).collect(Collectors.toList());
        Collections.sort(daysList, Collections.reverseOrder());
        List<Table> tables = new ArrayList<>();

        if(year ==2023 && week<44){
            for (String s : daysList) {
                Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                System.out.println(teachersIds+"-------------------------- teacherIds");
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum())).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();

                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        System.out.println(card+"------------------------------------- *****************************************************************************");
                                        if (card.getClassroomIds().get(0)!=null && card.getClassroomIds().get(0)!="") {
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }


                                        show.setLessonName(subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);

                                        shows.add(show);

                                    }




                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }
        else {
            for (String s : daysList) {
                Set<String> lessonsIds = cards.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessons.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                System.out.println(teachersIds+"-------------------------- teacherIds");
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachers.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin())).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessons.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
                                //                        lists.add(lessonXmls);
                                for (LessonXml xml : lessonXmls) {
                                    List<Card> collect = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).collect(Collectors.toList());
//                                Card card = cards.stream().filter(i -> i.getLessonId().equals(xml.getId()) && i.getDays().contains(s)).findFirst().get();

                                    for (Card card : collect) {
                                        Show show = new Show();

                                        Period period = periods.stream().filter(i -> i.getName().equals(card.getPeriod())).findFirst().get();
                                        System.out.println(card+"------------------------------------- *****************************************************************************");
                                        if (card.getClassroomIds().get(0)!=null && card.getClassroomIds().get(0)!="") {
                                            for (String s2 : card.getClassroomIds()) {
                                                ClassRoom room = classRooms.stream().filter(i -> i.getId().equals(s2)).findFirst().get();
                                                show.setRoom(room.getName());
                                                break;
                                            }
                                        }

                                        show.setLessonName(subjects.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }

        getTimeTableByWeekMed(year,week);
        List<String> daysListMed = daysDefsMed
                .stream().filter(item -> !item.getName().equalsIgnoreCase("В любой день") && !item.getName().equalsIgnoreCase("Каждый день"))
                .collect(Collectors.toSet()).stream().map(i-> i.getDays().get(0)).collect(Collectors.toList());
        Collections.sort(daysListMed, Collections.reverseOrder());

        if(year ==2023 && week<44){
            for (String s : daysListMed) {
                Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getEmail().equalsIgnoreCase(user.getPassportNum()) ).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3(teacher.getEmail());
//                TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getEmail());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
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

                                        show.setLessonName(subjectsMed.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());

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
                                        System.out.println(show.toString()+" <- show "+s);

                                        shows.add(show);

                                    }




                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }
        else{
            for (String s : daysListMed) {
                Set<String> lessonsIds = cardsMed.stream().filter(item -> item.getDays().contains(s)).map(Card::getLessonId).collect(Collectors.toSet());
                Set<String> teachersIds = new HashSet<>();
                for (String id : lessonsIds) {
                    LessonXml lessonXml = lessonsMed.stream().filter(item -> item.getId().equals(id)).findFirst().get();
                    teachersIds.addAll(lessonXml.getTeacherIds());
                }
                Set<Teacher> teachers1 = new HashSet<>();
                for (String id : teachersIds) {
                    Optional<Teacher> first = teachersMed.stream().filter(item -> item.getId().equals(id) && item.getShortName().equalsIgnoreCase(user.getLogin()) ).findFirst();
                    first.ifPresent(teachers1::add);
                }
                List<TeacherData> teacherData = new ArrayList<>();

                System.out.println("teachers1 -> "+ teachers1);
                for (Teacher teacher : teachers1) {
                    TeacherData teacherData1 = userRepository.getTeachersForRemember3Login(teacher.getShortName());
                    if (teacherData1!=null) {
                        teacherData.add(teacherData1);
                        List<Show> shows = new ArrayList<>();
                        for (String id : lessonsIds) {
                            List<LessonXml> lessonXmls = lessonsMed.stream().filter(item -> item.getId().equals(id) && item.getTeacherIds().contains(teacher.getId())).collect(Collectors.toList());
                            if (lessonXmls.size()!=0) {
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
                                        show.setLessonName(subjectsMed.stream().filter(i->i.getId().equals(xml.getSubjectId())).findFirst().get().getName());
                                        show.setHourNumber(period.getPeriod());
                                        show.setPeriodStartAndEndTime(period.getStartTime()+"-"+period.getEndTime());
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
                                        System.out.println(show.toString()+" <- show "+s);
                                        shows.add(show);
                                    }
                                }
                            }
                        }
                        tables.add(new Table(teacherData1,shows));
                    }
                }
            }
        }


        return new ApiResponse(false,"ishlayapdi ok",tables);
    }

    @Override
    public ApiResponseTwoObj getTeacherTimeTableAndStatisticsForKafedra(User user, String kafedraId, Integer year,Integer month, Integer day,Integer week, Integer weekday) {

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
            }
            else {
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

        return new ApiResponseTwoObj(true,"teachers", tables, userRepository.getTeachersStatisticsForKafedraDashboardWithYearMonthDay(kafedraId,year,week,weekday));
    }

    @Override
    public ApiResponseTwoObj getTimeTableByRoomAndWeek(User user, String room, Integer weekday, Integer week, Integer year) {
        if(!room.startsWith("M")) {
            getTimeTableByWeek(year, week);
            getTimeTableByWeekMed(year, week);

            Optional<ClassRoom> first = classRooms.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            ClassRoom classRoom = first.get();

            //med
            Optional<ClassRoom> firstMed = classRoomsMed.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            System.out.println(firstMed + " ---------------------------------------------------------------------------------------");

            ClassRoom classRoomMed = firstMed.orElse(null);

            Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
            Set<Show> shows = timeTable(cardSet);

            //med
            Set<Card> cardSetMed = classRoomMed != null ? cardsMed.stream().filter(i -> i.getClassroomIds().contains(classRoomMed.getId())).collect(Collectors.toSet()) : null;
            Set<Show> showsMed = cardSetMed != null ? timeTableMed(cardSetMed) : null;


            Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet());

            //med
            Set<Show> showSetMed = showsMed != null ? showsMed.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet()) : new HashSet<>();

            System.out.println(showsMed + " +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

            ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

            if (showSetMed!=null) {
                System.out.println("888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888");
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
                System.out.println(
                        showSetMed.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()) + " =========================================================================="
                );
                System.out.println(Stream.of(
                        showSet.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList()),
                        showSetMed.stream().filter(item -> item.getHourNumber() == 6).collect(Collectors.toList())
                ).flatMap(Collection::stream).collect(Collectors.toList()) + " ==========================================================================");
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
            } else {
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

            System.out.println(showWeekNumberFields.getGet6() + " ===---------------------------------------======================================  121212");

            List<TeacherStatisticsOfWeekday> lists = new ArrayList<>();

            if(year ==2023 && week<44){
                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
            }
            else {
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
            }

            return new ApiResponseTwoObj(true, "time table room",

                    showSetMed.size() > 0 ?
                            Stream.of(
                                    showSet,
                                    showSetMed
                            ).flatMap(Collection::stream).collect(Collectors.toSet()) :
                            showSet
                    , lists);
        }
        else{
            getTimeTableByWeekMed(year, week);

            Optional<ClassRoom> first = classRoomsMed.stream().filter(i -> i.getName().startsWith(room.lastIndexOf("-") == 5 ? room.substring(0, 5) : room)).findFirst();
            ClassRoom classRoom = first.get();

            Set<Card> cardSet = cardsMed.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
            Set<Show> shows = timeTableMed(cardSet);

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

            if(year ==2023 && week<44){
                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
            }
            else {
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
            }

            return new ApiResponseTwoObj(true, "time table room",
                            showSet
                    , lists);

        }
    }

    @Override
    public ApiResponseTwoObj getTimeTableByAllRoomAndWeek(User user,String building, Integer weekday, Integer week, Integer year,Boolean t) {
        Set<List<TeacherStatisticsOfWeekday2>> listsFather = new HashSet<>();
        Set<Set<Show>> showSetFather = new HashSet<>();

        if(!building.startsWith("M")) {

            getTimeTableByWeek(year, week);

            classRooms.stream().filter(i -> i.getName().startsWith(building)).sorted(Comparator.comparing(ClassRoom::getName)).collect(Collectors.toCollection(LinkedHashSet::new)).forEach(classRoom -> {
                Set<Card> cardSet = cards.stream().filter(i -> i.getClassroomIds().contains(classRoom.getId())).collect(Collectors.toSet());
                Set<Show> shows = t ? timeTable(cardSet) : timeTable2(cardSet,year,week);

//                System.out.println(shows+"hello--------------------------------2------");
//                System.out.println(weekday+"hello--------------------------------3------");
//                shows.forEach(s -> {
//                    if (s.getDayNumber()==weekday) {
//                        System.out.println(s);
//                    }
//                });
                Set<Show> showSet = shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet());
//                System.out.println(shows.stream().filter(i -> i.getDayNumber()==weekday).collect(Collectors.toSet())+"--------------333---------hello---------------------------------");
                System.out.println("hello--------------------------------2------");

                ShowWeekNumberFields showWeekNumberFields = new ShowWeekNumberFields();

                System.out.println("hello--------------------------------------");

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

                if(year ==2023 && week<44){
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                }
                else {
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
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
                }

                listsFather.add(lists);
                showSetFather.add(showSet);
            });


            getTimeTableByWeekMed(year, week);
            classRoomsMed.stream().filter(i -> i.getName().startsWith(building)).sorted(Comparator.comparing(ClassRoom::getName)).collect(Collectors.toCollection(LinkedHashSet::new)).forEach(classRoom -> {
                System.out.println(classRoom);
//            Optional<ClassRoom> first = classRooms.stream().filter(i -> i.getName().equals(room)).findFirst();
//            ClassRoom classRoom = first.get();

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

                if(year ==2023 && week<44){
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                }
                else {
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getEmail(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                }

                listsFather.add(lists);
                showSetFather.add(showSet);
            });

//        Set<Set<Show>> collect = showSetFather.stream().map(i -> i.stream().sorted(Comparator.comparing(Show::getRoom)).collect(Collectors.toSet())).collect(Collectors.toSet());
        }
        else {
            getTimeTableByWeekMed(year, week);
            classRoomsMed.stream().filter(i -> i.getName().startsWith(building)).sorted(Comparator.comparing(ClassRoom::getName)).collect(Collectors.toCollection(LinkedHashSet::new)).forEach(classRoom -> {
                System.out.println(classRoom);
//            Optional<ClassRoom> first = classRooms.stream().filter(i -> i.getName().equals(room)).findFirst();
//            ClassRoom classRoom = first.get();

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

                if(year ==2023 && week<44){
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet3().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet4().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet5().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet6().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet7().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet8().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet9().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet10().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet11().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet12().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4) + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2(teacher.getEmail(), i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                }
                else {
                    showWeekNumberFields.getGet1().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
                            List<TeacherStatisticsOfWeekday2> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek2Login(teacher.getShortName(), Objects.equals(i.getRoom(), "A-612") ? "A-612%" : i.getRoom().length() == 7 ? i.getRoom() : i.getRoom().substring(0, i.getRoom().indexOf("-") + 4), i.getDayNumber(), week, year, i.getHourNumber());
                            lists.addAll(statisticsOfWeekdayList);
                        }
                    });
                    showWeekNumberFields.getGet2().forEach(i -> {
                        for (Teacher teacher : i.getTeachers()) {
                            System.out.println(i.getRoom() + " <-------------------------------------------");
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
                }


                listsFather.add(lists);
                showSetFather.add(showSet);
            });

        }

        return new ApiResponseTwoObj(true,"time table room of father",showSetFather,listsFather);
    }

    @Override
    public ApiResponseTwoObj getTimeTableByAllRoomAndWeek2(User user, Integer weekday, Integer week, Integer year, Boolean t) {
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

            if(year ==2023 && week<44){
                showWeekNumberFields.getGet1().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet2().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet3().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet4().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet5().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet6().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet7().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet8().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet9().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet10().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet11().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
                showWeekNumberFields.getGet12().forEach(i -> {
                    for (Teacher teacher : i.getTeachers()) {
                        List<TeacherStatisticsOfWeekday> statisticsOfWeekdayList = userRepository.getTimesForRoomStatisticsByPassportByWeek(teacher.getEmail(), i.getRoom(), i.getDayNumber(),week,year, i.getHourNumber());
                        lists.addAll(statisticsOfWeekdayList);
                    }
                });
            }
            else {
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
            }

            listsFather.add(lists);
            showSetFather.add(showSet);
        });

//        Set<Set<Show>> collect = showSetFather.stream().map(i -> i.stream().sorted(Comparator.comparing(Show::getRoom)).collect(Collectors.toSet())).collect(Collectors.toSet());


        return new ApiResponseTwoObj(true,"time table room of father",showSetFather,listsFather);
    }


    public List<Show> timeTable(String groupName){
        List<Show> shows= new ArrayList<>();
        for (Card card : cards) {// jadvalning bitta kuni
            for (LessonXml lesson : lessons) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class aClass : classes) {// guruh
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
    public Set<Show> timeTableMed(Set<Card> cardSet){
        Set<Show> shows= new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessonsMed) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : classesMed) {// guruh
                            Show show = new Show();
                            List<String> teachers1 = new ArrayList<>();
                            List<Teacher> teachers2 = new ArrayList<>();
                            for (String classroomId : card.getClassroomIds()) {
                                for (ClassRoom room : classRoomsMed) {// xona
                                    if (room.getId().equals(classroomId)){
                                        show.setRoom(room.getName());
                                        break;
                                    }
                                }
                            }
                            for (Period period : periodsMed) {
                                if (period.getName().equals(card.getPeriod())){
                                    show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                    show.setHourNumber(period.getPeriod());
                                    break;
                                }
                            }
                            for (DaysDef daysDef : daysDefsMed) {
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
                            for (Subject subject : subjectsMed) {
                                if (subject.getId().equals(lesson.getSubjectId())){
                                    show.setLessonName(subject.getName());
                                    break;
                                }
                            }
                            for (String teacherId : lesson.getTeacherIds()) {
                                for (Teacher teacher : teachersMed) {
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
    public Set<Show> timeTable2Med(Set<Card> cardSet,Integer year,Integer week){
        Set<Show> shows= new HashSet<>();
        for (Card card : cardSet) {// jadvalning bitta kuni
            for (LessonXml lesson : lessonsMed) {// dars
                if (lesson.getId().equals(card.getLessonId())){
                    for (String classId : lesson.getClassIds()) {
                        for (Class aClass : classesMed) {// guruh
                            Show show = new Show();
                            List<String> teachers1 = new ArrayList<>();
                            List<Teacher> teachers2 = new ArrayList<>();
                            for (String classroomId : card.getClassroomIds()) {
                                for (ClassRoom room : classRoomsMed) {// xona
                                    if (room.getId().equals(classroomId)){
                                        show.setRoom(room.getName());
                                        break;
                                    }
                                }
                            }
                            for (Period period : periodsMed) {
                                if (period.getName().equals(card.getPeriod())){
                                    show.setPeriodStartAndEndTime(period.getStartTime()+" - "+period.getEndTime());
                                    show.setHourNumber(period.getPeriod());
                                    break;
                                }
                            }
                            for (DaysDef daysDef : daysDefsMed) {
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
                            for (Subject subject : subjectsMed) {
                                if (subject.getId().equals(lesson.getSubjectId())){
                                    show.setLessonName(subject.getName());
                                    break;
                                }
                            }
                            for (String teacherId : lesson.getTeacherIds()) {
                                for (Teacher teacher : teachersMed) {
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
