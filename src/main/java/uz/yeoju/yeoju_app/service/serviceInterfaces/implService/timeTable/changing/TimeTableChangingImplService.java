package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.changing;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.glassfish.grizzly.utils.ArraySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.educationYear.WeekOfEducationYear;
import uz.yeoju.yeoju_app.entity.educationYear.WeekType;
import uz.yeoju.yeoju_app.entity.timetableDB.CardDB;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingRoomOfLessonDetailsDto;
import uz.yeoju.yeoju_app.payload.timetableChanging.ChangingTeacherDetailsDto;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.repository.educationYear.WeekOfEducationYearRepository;
import uz.yeoju.yeoju_app.repository.timetableDB.CardDBRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.timeTable.TimeTableByWeekOfYearImplService;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;

@Service
public class TimeTableChangingImplService implements TimeTableChangingService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CardDBRepository cardDBRepository;


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
    public ApiResponse changingTeacherData(ChangingTeacherDetailsDto dto) {



        try {
            // Load the XML file
            Path path = dto.type.equals(WeekType.DEFAULT) ? Paths.get(dto.year + "\\" + dto.week + ".xml"): Paths.get(dto.year + "\\" + dto.week + "med.xml");
            File xmlFile = new File(String.valueOf(path));
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Get all <teacher> elements
            NodeList teacherList = doc.getElementsByTagName("teacher");

            // Iterate through each <teacher> element and modify the 'short' attribute
            for (int i = 0; i < teacherList.getLength(); i++) {
                Node teacherNode = teacherList.item(i);
                if (teacherNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element teacherElement = (Element) teacherNode;

                    if (teacherElement.getAttribute("id").equals(dto.id)){
                        // Get the current 'short' attribute value
                        String currentShort = teacherElement.getAttribute("short");
                        System.out.println("Current 'short' value: " + currentShort);

                        // Set a new 'short' attribute value (e.g., updating it)
                        teacherElement.setAttribute("short", dto.shortName); // Replace "NEW_VALUE_" as needed
                    }
                }
            }

            // Save the changes back to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(String.valueOf(path)));
            transformer.transform(source, result);

            System.out.println("Updated 'short' attributes in the XML file.");

        } catch (Exception e) {
            e.printStackTrace();
        }




        return null;
    }

    @Override
    public ApiResponse changingRoomOfLesson(ChangingRoomOfLessonDetailsDto dto) {
        try {
            Path path = dto.type.equals(WeekType.DEFAULT) ?
                    Paths.get(dto.year + "\\" + dto.week + ".xml")
                    : Paths.get(dto.year + "\\" + dto.week + "med.xml");
            File xmlFile = path.toFile();

            if (!xmlFile.exists()) {
                return new ApiResponse(false, "The XML file does not exist at: " + path);
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            String expression = String.format(
                    "//card[@lessonid='%s' and @period='%s' and @days='%s']",
                    dto.lessonId, dto.period, dto.dayCode
            );
            NodeList cardList = (NodeList) xPath.evaluate(expression, doc, XPathConstants.NODESET);

            for (int i = 0; i < cardList.getLength(); i++) {
                Element cardElement = (Element) cardList.item(i);
//                Optional<ClassRoom> roomOptional = classRooms.stream()
//                        .filter(room -> room.getId().equals(dto.roomId))
//                        .findFirst();
//
//                if (roomOptional.isPresent()) {
                    String classroomids = cardElement.getAttribute("classroomids");
                    if (!classroomids.equals(dto.roomId)) {
                        cardElement.setAttribute("classroomids", dto.roomId);
                    }
//                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

//060D83E303B2043C
            User teacher = userRepository.getById(dto.teacherId);
            String id = cardDBRepository.getCardId(
                    dto.day,
                    dto.period,
                    new HashSet<>(Arrays.asList(teacher)),
                    dto.week,
                    dto.year
            );
            CardDB cardDB = cardDBRepository.getById(id);
//            CardDB cardDB = new CardDB();

            System.out.println("card db -> "+cardDB);
/*            if(card.getClassroomIds().get(0).length()!=0){
                ClassRoom room = classRooms.stream().filter(classRoom -> classRoom.getId().equals(card.getClassroomIds().get(0))).findFirst().get();
                cardDB.setClassroom(room.getName());
            }
            for (DaysDef daysDef : daysDefs) {
                if (daysDef.getDays().get(0).equals(card.getDays().get(0))){
                    if (daysDef.getDays().get(0).equals("1000000") ||daysDef.getDays().get(0).equals("100000") || daysDef.getDays().get(0).equals("10000"))
                        cardDB.setDay(1);
                    if (daysDef.getDays().get(0).equals("0100000") ||daysDef.getDays().get(0).equals("010000") || daysDef.getDays().get(0).equals("01000"))
                        cardDB.setDay(2);
                    if (daysDef.getDays().get(0).equals("0010000") ||daysDef.getDays().get(0).equals("001000") || daysDef.getDays().get(0).equals("00100"))
                        cardDB.setDay(3);
                    if (daysDef.getDays().get(0).equals("0001000") ||daysDef.getDays().get(0).equals("000100") || daysDef.getDays().get(0).equals("00010"))
                        cardDB.setDay(4);
                    if (daysDef.getDays().get(0).equals("0000100") ||daysDef.getDays().get(0).equals("000010") || daysDef.getDays().get(0).equals("00001"))
                        cardDB.setDay(5);
                    if (daysDef.getDays().get(0).equals("0000010")||daysDef.getDays().get(0).equals("000001"))
                        cardDB.setDay(6);
                    cardDB.setDayName(daysDef.getName()+"-"+daysDef.getShortName());
                    break;
                }
            }
            cardDBRepository.save(cardDB);*/

            return new ApiResponse(true, "Classroom updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(false, "An error occurred: " + e.getMessage());
        }
    }


    @Override
    public ApiResponse getDataOfTeachers(WeekType weekType,Integer year, Integer week) {
        if (weekType==WeekType.DEFAULT){
            getTimeTableByWeek(year,week);
            return new ApiResponse(true,"all teachers year: "+year+", week: "+week,teachers);
        }
        else {
            getTimeTableByWeekMed(year,week);
            return new ApiResponse(true,"all med teachers year: "+year+", week: "+week,teachersMed);
        }
    }


    public void getTimeTableByWeek(Integer year, Integer week) {
        clearTimeTable();

        String xmlFile = year+"/"+week+".xml";
        org.jdom2.Document document = getSAXParsedDocument(xmlFile);
        org.jdom2.Element rootNode = document.getRootElement();
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


    public void getTimeTableByWeekMed(Integer year, Integer week) {
        clearTimeTableMed();

        String xmlFile = year+"/"+week+"med.xml";
        org.jdom2.Document document = getSAXParsedDocument(xmlFile);
        org.jdom2.Element rootNode = document.getRootElement();
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
    public static void readPeriod(org.jdom2.Element employeeNode)
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
    public static void readPeriodMed(org.jdom2.Element employeeNode)
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
    public static void readDaysDef(org.jdom2.Element employeeNode)
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

    public static void readDaysDefMed(org.jdom2.Element employeeNode)
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
    public static void readWeeksDef(org.jdom2.Element employeeNode)
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
    public static void readWeeksDefMed(org.jdom2.Element employeeNode)
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
    public static void readTermsDefs(org.jdom2.Element employeeNode)
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
    public static void readTermsDefsMed(org.jdom2.Element employeeNode)
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
    public static void readSubject(org.jdom2.Element employeeNode)
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
    public static void readSubjectMed(org.jdom2.Element employeeNode)
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
    public static void readTeacher(org.jdom2.Element employeeNode)
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
    public static void readTeacherMed(org.jdom2.Element employeeNode)
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
    public static void readClassroom(org.jdom2.Element employeeNode)
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
    public static void readClassroomMed(org.jdom2.Element employeeNode)
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
    public static void readGrade(org.jdom2.Element employeeNode)
    {
        grades.add(
                new Grade(
                        employeeNode.getAttributeValue("name"),
                        employeeNode.getAttributeValue("short"),
                        Integer.valueOf(employeeNode.getAttributeValue("grade"))
                )
        );
    }
    public static void readGradeMed(org.jdom2.Element employeeNode)
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
    public static void readClass(org.jdom2.Element employeeNode)
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
    public static void readClassMed(org.jdom2.Element employeeNode)
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
    public static void readGroup(org.jdom2.Element employeeNode)
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
    public static void readGroupMed(org.jdom2.Element employeeNode)
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
    public static void readLesson(org.jdom2.Element employeeNode)
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
    public static void readLessonMed(org.jdom2.Element employeeNode)
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
    public static void readCard(org.jdom2.Element employeeNode)
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
    public static void readCardMed(org.jdom2.Element employeeNode)
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
