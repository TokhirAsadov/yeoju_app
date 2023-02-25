package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.aspectj.weaver.ast.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uz.yeoju.yeoju_app.controller.BaseUrl;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.entity.enums.Gandername;
import uz.yeoju.yeoju_app.entity.enums.PhoneType;
import uz.yeoju.yeoju_app.entity.studentBall.StudentResult;
import uz.yeoju.yeoju_app.entity.studentBall.SubjectCredit;
import uz.yeoju.yeoju_app.repository.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/fileRead")
@RequiredArgsConstructor
public class FileReaderController {

    private final SubjectCreditRepository subjectCreditRepository;
    private final LessonRepository lessonRepository;
    private final StudentResultRepository studentResultRepository;
    private final UserRepository userRepository;
    private final AddressUserRepository addressUserRepository;
    private final StudentRepository studentRepository;
    private final PhoneNumberRepository phoneNumberRepository;
    private final GanderRepository ganderRepository;

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
                    //todo====================== RETAKE LARNI YOZIW Chat LOGIKASINI ==============================
                }
            }

            strings.add(grade);

//            tempStudent.setId((int) row.getCell(0).getNumericCellValue());
//            tempStudent.setContent(row.getCell(1).getStringCellValue());
//            tempStudentList.add(tempStudent);
        }
        return ResponseEntity.ok(strings.size()+" "+strings);
    }

    @Transactional
    @SneakyThrows
    @PostMapping("/studentData")
    public HttpEntity<?> studentDatas(@RequestParam("file") MultipartFile reapExcelDataFile) {

        XSSFWorkbook workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        List<String> strings = new ArrayList<>();


//        for(int i=1;i<10 ;i++) {
        for(int i=10;i<worksheet.getPhysicalNumberOfRows() ;i++) {

            XSSFRow row = worksheet.getRow(i);

            String fullName = row.getCell(0).toString();
            String email_phone = row.getCell(1).toString();
            String gender = row.getCell(2).toString();
            String birthDay = row.getCell(3).toString();
            String country =row.getCell(4).toString();
            String region =row.getCell(5).toString();
            String area = row.getCell(6).toString();
            String address = row.getCell(7).toString();
            String citizenship = row.getCell(8).toString();
            String nationality = row.getCell(9).toString();
            String passport = row.getCell(10).toString();
            String home_phone = row.getCell(11).toString();
            String mother_phone = row.getCell(12).toString();
            String father_phone = row.getCell(13).toString();
            String mobile_phone = row.getCell(14).toString();

            User user = userRepository.getUserByPassportNum(passport);
            if (user != null){

                // address
                AddressUser addressUser = new AddressUser(user,country,region,area,address,true);
                addressUserRepository.saveAndFlush(addressUser);


                // phones
                PhoneNumber mobilePhone = new PhoneNumber(mobile_phone,user, PhoneType.MOBILE_PHONE);
                PhoneNumber motherPhone = new PhoneNumber(mother_phone,user, PhoneType.MOTHER_PHONE);
                PhoneNumber fatherPhone = new PhoneNumber(father_phone,user, PhoneType.FATHER_PHONE);
                PhoneNumber homePhone = new PhoneNumber(home_phone,user, PhoneType.HOME_PHONE);
                phoneNumberRepository.save(mobilePhone);
                phoneNumberRepository.save(motherPhone);
                phoneNumberRepository.save(fatherPhone);
                phoneNumberRepository.save(homePhone);


                user.setGander(
                    Objects.equals(gender, "MALE")
                    ?
                    ganderRepository.getGanderByGandername(Gandername.MALE)
                    :
                    ganderRepository.getGanderByGandername(Gandername.FEMALE)
                );

                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                Date date = simpleDateFormat.parse(birthDay);
                user.setBornYear(date);
                System.out.println(date);

                user.setFullName(fullName);
                user.setCitizenship(citizenship);
                user.setNationality(nationality);
                user.setEnabled(true);
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);

                userRepository.save(user);


            }

            strings.add(passport);


        }
        return ResponseEntity.ok(strings.size()+" "+strings);
    }
}
