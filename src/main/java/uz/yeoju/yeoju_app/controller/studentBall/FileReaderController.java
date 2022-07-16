package uz.yeoju.yeoju_app.controller.studentBall;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(BaseUrl.BASE_URL+"/fileRead")
@RequiredArgsConstructor
public class FileReaderController {

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

        for(int i=1;i<worksheet.getPhysicalNumberOfRows() ;i++) {
//            Test tempStudent = new Test();

            XSSFRow row = worksheet.getRow(i);
            System.out.println(row);
            strings.add(row.getCell(1).toString());

//            tempStudent.setId((int) row.getCell(0).getNumericCellValue());
//            tempStudent.setContent(row.getCell(1).getStringCellValue());
//            tempStudentList.add(tempStudent);
        }
        return ResponseEntity.ok(strings);
    }
}
