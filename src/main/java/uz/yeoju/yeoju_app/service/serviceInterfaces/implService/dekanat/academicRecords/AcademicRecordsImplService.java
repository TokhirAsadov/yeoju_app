package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.academicRecords;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.AcademicRecords;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.AcademicRecordsRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class AcademicRecordsImplService implements AcademicRecordsService{
    private final AcademicRecordsRepository academicRecordsRepository;
    private final UserRepository userRepository;

    static {
        IOUtils.setByteArrayMaxOverride(200 * 1024 * 1024); // 200 MB limit
    }

    public AcademicRecordsImplService(AcademicRecordsRepository academicRecordsRepository, UserRepository userRepository) {
        this.academicRecordsRepository = academicRecordsRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ApiResponse saveRecords(MultipartHttpServletRequest request) {
        System.out.println(" ----------------------------- !!! ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcel(file);
            }
        }
        return null;
    }

    @Override
    public ApiResponse getRecordsByUserId(String userId) {
        if (userRepository.existsById(userId)) {
            User user = userRepository.getById(userId);
            List<AcademicRecords> records = academicRecordsRepository.findAllByQaydRaqami(user.getLogin());
            if (records.isEmpty()) {
                return new ApiResponse(false, "No records found for user ID: " + userId);
            }
            return new ApiResponse(true, "Records found", records);
        }
        return new ApiResponse(false, "User not found with ID: " + userId);
    }

    @Override
    public ApiResponse getRecordsByQaytRaqami(String qaydRaqami) {
        List<AcademicRecords> records = academicRecordsRepository.findAllByQaydRaqami(qaydRaqami);
        if (records.isEmpty()) {
            return new ApiResponse(false, "No records found for qayd raqam: " + qaydRaqami);
        }
        return new ApiResponse(true, "Records found", records);
    }

    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        String seeker = "";
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // skip header

            List<Row> rowList = new ArrayList<>();
            while (rows.hasNext()) {
                rowList.add(rows.next());
            }

            int threadCount = Runtime.getRuntime().availableProcessors(); // yoki Runtime.getRuntime().availableProcessors();
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);

            List<Future<List<AcademicRecords>>> futures = new ArrayList<>();
            int chunkSize = rowList.size() / threadCount + 1;

            for (int i = 0; i < rowList.size(); i += chunkSize) {
                int from = i;
                int to = Math.min(i + chunkSize, rowList.size());

                Callable<List<AcademicRecords>> task = () -> {
                    List<AcademicRecords> records = new ArrayList<>();
                    for (int j = from; j < to; j++) {
                        Row row = rowList.get(j);
                        AcademicRecords record = new AcademicRecords(
                                getCellStringValue(row.getCell(0)),
                                getCellStringValue(row.getCell(1)),
                                getCellStringValue(row.getCell(2)),
                                getCellStringValue(row.getCell(3)),
                                getCellStringValue(row.getCell(4)),
                                getCellStringValue(row.getCell(5)),
                                getCellStringValue(row.getCell(6)),
                                getCellStringValue(row.getCell(7)),
                                getCellStringValue(row.getCell(8)),
                                getCellStringValue(row.getCell(9)),
                                getCellStringValue(row.getCell(10)),
                                getCellIntegerValue(row.getCell(11)),
                                getCellIntegerValue(row.getCell(12)),
                                getCellIntegerValue(row.getCell(13)),
                                getCellIntegerValue(row.getCell(14)),
                                getCellIntegerValue(row.getCell(15))
                        );
                        records.add(record);
                    }
                    return records;
                };

                futures.add(executor.submit(task));
            }

            List<AcademicRecords> allRecords = new ArrayList<>();
            for (Future<List<AcademicRecords>> future : futures) {
                allRecords.addAll(future.get());
            }

            executor.shutdown();

            academicRecordsRepository.saveAll(allRecords);
            return new ApiResponse(true, "Multithreaded load complete. Total: " + allRecords.size());

        } catch (Exception e) {
            return new ApiResponse(false, "Xatolik: " + e.getMessage() + " --> " + seeker);
        }
    }

   /* @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        String seeker = "";
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // skip header

            List<AcademicRecords> recordsList = new ArrayList<>();

            while (rows.hasNext()) {
                Row row = rows.next();

                String qaydRaqami = getCellStringValue(row.getCell(0));
                seeker = qaydRaqami; // for error tracking
                String berilganSana = getCellStringValue(row.getCell(1));
                String fish = getCellStringValue(row.getCell(2));
                String tugilganSana = getCellStringValue(row.getCell(3));
                String akademikDaraja = getCellStringValue(row.getCell(4));
                String fakultet = getCellStringValue(row.getCell(5));
                String yunalish = getCellStringValue(row.getCell(6));
                String uqishgaQabulQilinganSana = getCellStringValue(row.getCell(7));
                String talimOluvchiningMaqomi = getCellStringValue(row.getCell(8));
                String uqishniTamomlaganSana = getCellStringValue(row.getCell(9));
                String fanNomi = getCellStringValue(row.getCell(10));
                Integer semestr = getCellIntegerValue(row.getCell(11));
                Integer yuklama = getCellIntegerValue(row.getCell(12));
                Integer kredit = getCellIntegerValue(row.getCell(13));
                Integer ball = getCellIntegerValue(row.getCell(14));
                Integer baho = getCellIntegerValue(row.getCell(15));

                AcademicRecords record = new AcademicRecords(
                        qaydRaqami,
                        berilganSana,
                        fish,
                        tugilganSana,
                        akademikDaraja,
                        fakultet,
                        yunalish,
                        uqishgaQabulQilinganSana,
                        talimOluvchiningMaqomi,
                        uqishniTamomlaganSana,
                        fanNomi,
                        semestr,
                        yuklama,
                        kredit,
                        ball,
                        baho
                );

                recordsList.add(record);
            }

            academicRecordsRepository.saveAll(recordsList);
            return new ApiResponse(true, "All records saved successfully");

        } catch (Exception e) {
            return new ApiResponse(false, "Xatolik: " + e.getMessage() + " --> " + seeker);
        }
    }*/



    private String getCellStringValue(Cell cell) {
        if (cell == null) return null;
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((long) cell.getNumericCellValue()); // for IDs or dates as strings
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return null;
        }
    }

    private Integer getCellIntegerValue(Cell cell) {
        if (cell == null) return null;
        try {
            switch (cell.getCellType()) {
                case NUMERIC:
                    return (int) cell.getNumericCellValue();
                case STRING:
                    String str = cell.getStringCellValue().trim();
                    if (str.isEmpty()) return null;
                    return Integer.parseInt(str);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null; // yoki log yozing
        }
    }

    private Workbook getWorkBook(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Fayl bo‘sh bo‘lmasligi kerak");
        }

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (extension == null) {
            throw new IllegalArgumentException("Fayl kengaytmasi aniqlanmadi");
        }

        try {
            if (extension.equalsIgnoreCase("xlsx")) {
                return new XSSFWorkbook(file.getInputStream());
            } else if (extension.equalsIgnoreCase("xls")) {
                return new HSSFWorkbook(file.getInputStream());
            } else {
                throw new IllegalArgumentException("Noto‘g‘ri fayl formati: ." + extension);
            }
        } catch (Exception e) {
            throw new RuntimeException("Excel faylni ochishda xatolik: " + e.getMessage(), e);
        }
    }
}
/*public ApiResponse readDataFromExcel(MultipartFile file) {
        System.out.println(" ----------------------------- 4 4 4  ------------------------ --");
        String seeker = "";
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            int totalColumns = headerRow.getPhysicalNumberOfCells();
            Iterator<Row> rows = sheet.iterator();
            rows.next();

            while (rows.hasNext()){
                Row row = rows.next();

                AcademicRecords academicRecord = new AcademicRecords();

                for (int i = 0; i < totalColumns; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (i) {
                        case 0:
                            academicRecord.setQaydRaqami(cell.getStringCellValue());
                            break;
                        case 1:
                            academicRecord.setBerilganSana(cell.getStringCellValue());
                            break;
                        case 2:
                            academicRecord.setFish(cell.getStringCellValue());
                            break;
                        case 3:
                            academicRecord.setTugilganSana(cell.getStringCellValue());
                            break;
                        case 4:
                            academicRecord.setAkademikDaraja(cell.getStringCellValue());
                            break;
                        case 5:
                            academicRecord.setFakultet(cell.getStringCellValue());
                            break;
                        case 6:
                            academicRecord.setYunalish(cell.getStringCellValue());
                            break;
                        case 7:
                            academicRecord.setUqishgaQabulQilinganSana(cell.getStringCellValue());
                            break;
                        case 8:
                            academicRecord.setTalimOluvchiningMaqomi(cell.getStringCellValue());
                            break;
                        case 9:
                            academicRecord.setUqishniTamomlaganSana(cell.getStringCellValue());
                            break;
                        case 10:
                            academicRecord.setFanNomi(cell.getStringCellValue());
                            break;
                        case 11:
                            academicRecord.setSemestr((int) cell.getNumericCellValue());
                            break;
                        case 12:
                            academicRecord.setYuklama((int) cell.getNumericCellValue());
                            break;
                        case 13:
                            academicRecord.setKredit((int) cell.getNumericCellValue());
                            break;
                        case 14:
                            academicRecord.setBall((int) cell.getNumericCellValue());
                            break;
                        case 15:
                            academicRecord.setBaho((int) cell.getNumericCellValue());
                            break;
                        default:
                            // Ignore or handle additional columns if needed
                            break;
                    }
                }

                // Saqlash uchun repository ga saqlash
                academicRecordsRepository.save(academicRecord);
            }

            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,e+" --> error saved students -> "+seeker);
        }
    }*/