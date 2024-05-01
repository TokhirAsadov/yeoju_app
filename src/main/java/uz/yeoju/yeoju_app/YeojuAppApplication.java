package uz.yeoju.yeoju_app;

import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.*;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.Class;
import uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable;

import java.util.ArrayList;
import java.util.List;

import static uz.yeoju.yeoju_app.payload.forTimeTableFromXmlFile.db.DataBaseForTimeTable.getSAXParsedDocument;


@SpringBootApplication
@EnableFeignClients
public class YeojuAppApplication {

	@Bean
	public WebClient webClient() {
//		return WebClient.create("http://localhost:6060/api/v1/desktop");
		return WebClient.create("http://172.16.7.237:6060/api/v1/desktop");
	}

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(YeojuAppApplication.class, args);



		String xmlFile = "yeoju.xml";
		Document document = getSAXParsedDocument(xmlFile);
		Element rootNode = document.getRootElement();
		rootNode.getChild("periods").getChildren("period").forEach(YeojuAppApplication::readPeriod);
		rootNode.getChild("daysdefs").getChildren("daysdef").forEach(YeojuAppApplication::readDaysDef);
		rootNode.getChild("weeksdefs").getChildren("weeksdef").forEach(YeojuAppApplication::readWeeksDef);
		rootNode.getChild("termsdefs").getChildren("termsdef").forEach(YeojuAppApplication::readTermsDefs);
		rootNode.getChild("subjects").getChildren("subject").forEach(YeojuAppApplication::readSubject);
		rootNode.getChild("teachers").getChildren("teacher").forEach(YeojuAppApplication::readTeacher);
		rootNode.getChild("classrooms").getChildren("classroom").forEach(YeojuAppApplication::readClassroom);
		rootNode.getChild("grades").getChildren("grade").forEach(YeojuAppApplication::readGrade);
		rootNode.getChild("classes").getChildren("class").forEach(YeojuAppApplication::readClass);
		rootNode.getChild("groups").getChildren("group").forEach(YeojuAppApplication::readGroup);
		rootNode.getChild("lessons").getChildren("lesson").forEach(YeojuAppApplication::readLesson);
		rootNode.getChild("cards").getChildren("card").forEach(YeojuAppApplication::readCard);


	}


	//====================================  Period  ==========================================================
	public static void readPeriod(Element employeeNode)
	{
		DataBaseForTimeTable.periods.add(
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
		DataBaseForTimeTable.daysDefs.add(
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
		DataBaseForTimeTable.weeksDefs.add(
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
		DataBaseForTimeTable.termsDefs.add(
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
		DataBaseForTimeTable.subjects.add(
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
		DataBaseForTimeTable.teachers.add(
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
		DataBaseForTimeTable.classRooms.add(
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
		DataBaseForTimeTable.grades.add(
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
		DataBaseForTimeTable.classes.add(
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
	//====================================  Group  ==========================================================
	public static void readGroup(Element employeeNode)
	{
		DataBaseForTimeTable.groups.add(
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
		DataBaseForTimeTable.lessons.add(
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
		DataBaseForTimeTable.cards.add(
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
