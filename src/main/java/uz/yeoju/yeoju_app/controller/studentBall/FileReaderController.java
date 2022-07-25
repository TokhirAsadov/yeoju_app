package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Lesson;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.studentBall.StudentResult;
import uz.yeoju.yeoju_app.entity.studentBall.SubjectCredit;
import uz.yeoju.yeoju_app.repository.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/fileRead")
@RequiredArgsConstructor
public class FileReaderController {

    private final SubjectCreditRepository subjectCreditRepository;
    private final LessonRepository lessonRepository;
    private final StudentResultRepository studentResultRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @SneakyThrows
    @PostMapping("/import")
    public HttpEntity<?> mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile) {

        List<Test> tempStudentList = new ArrayList<Test>();
        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        List<String> strings = new ArrayList<>();

        //TODO-------------------------------------- FrontEnd ---------------------------------
        //   <form th:action="@{/import}" method="post" enctype="multipart/form-data">
        //        <input type="file" th:name="file">
        //    <input th:type="submit" value="Import" />

        for(int i=100;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            XSSFRow row = worksheet.getRow(i);

            String student_id = row.getCell(0).toString();
            String year = row.getCell(1).toString();
            String semester = row.getCell(2).toString();
            String subject = row.getCell(3).toString();
            String credit =row.getCell(4).toString();
            String score =row.getCell(5).toString();
            String grade = row.getCell(6).toString();

            SubjectCredit subjectCredit = new SubjectCredit();
            Student studentByUserLogin = studentRepository.findStudentByUserLogin(student_id);

            if (studentByUserLogin!=null) {// student borligi tekshirilyapdi
                Group group = studentByUserLogin.getGroup();
                if (!subject.contains("RETAKE")) { // retake emasligi tekshirilyapdi
                    if (!subjectCreditRepository.existsSubjectCreditByLessonNameAndGroupName(subject, group.getName())) { // bir xil lesson hamda group nameli subject credit borligi tekshirilyapdi

                        Lesson lessonByName = lessonRepository.getLessonByName(subject);
                        subjectCredit.setLesson(lessonByName);
                        subjectCredit.setCredit(credit);
                        subjectCredit.setYear(year);
                        subjectCredit.setSemester(semester);
                        SubjectCredit save = subjectCreditRepository.save(subjectCredit);

                        StudentResult studentResult = new StudentResult();
                        User userByLogin = userRepository.getUserByLogin(student_id);
                        studentResult.setScore(score);
                        studentResult.setUser(userByLogin);
                        studentResult.setSubjectCredit(save);
                        studentResultRepository.saveAndFlush(studentResult);
                    }
                    else {
                        StudentResult studentResult = new StudentResult();
                        SubjectCredit subjectCreditByLessonNameAndGroupName = subjectCreditRepository.getSubjectCreditByLessonNameAndGroupName(subject, group.getName());
                        studentResult.setSubjectCredit(subjectCreditByLessonNameAndGroupName);
                        User userByLogin = userRepository.getUserByLogin(student_id);
                        studentResult.setUser(userByLogin);
                        studentResult.setScore(score);
                        studentResultRepository.save(studentResult);
                    }
                }
                else {
                    //todo====================== RETAKE LARNI YOZIW KK LOGIKASINI ==============================
                }
            }

            strings.add(grade);

//            tempStudent.setId((int) row.getCell(0).getNumericCellValue());
//            tempStudent.setContent(row.getCell(1).getStringCellValue());
//            tempStudentList.add(tempStudent);
        }
        return ResponseEntity.ok(strings.size()+" "+strings);
    }
}
