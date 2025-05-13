package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.Group;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Diploma;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaCreator;
import uz.yeoju.yeoju_app.repository.DiplomaRepository;
import uz.yeoju.yeoju_app.repository.StudentRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.Iterator;
import java.util.Optional;

@Service
public class DiplomaImplService implements DiplomaService{
    private final DiplomaRepository diplomaRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public DiplomaImplService(DiplomaRepository diplomaRepository, UserRepository userRepository, StudentRepository studentRepository) {
        this.diplomaRepository = diplomaRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public ApiResponse createDiploma(DiplomaCreator creator) {
        Optional<User> userOptional = userRepository.findById(creator.studentId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            boolean existsStudentByUserId = studentRepository.existsStudentByUserId(creator.studentId);
            if (existsStudentByUserId) {
                Student student = studentRepository.findStudentByUserId(creator.studentId);
                Group group = student.getGroup();
                Faculty faculty = group.getFaculty();

                Boolean existsed = diplomaRepository.existsDiplomaByLogin(user.getLogin());
                if (!existsed) {
                    if (!diplomaRepository.existsDiplomaByDiplomId(creator.getDiplomId())) {
                        if (!diplomaRepository.existsDiplomaByDiplomRaqami(creator.diplomRaqami)) {
                            Diploma diploma = Diploma.builder()
                                    .diplomId(creator.getDiplomId())
                                    .diplomSeriya(creator.getDiplomSeriya())
                                    .diplomRaqami(creator.getDiplomRaqami())
                                    .fName(user.getFirstName())
                                    .lName(user.getLastName())
                                    .mName(user.getMiddleName())
                                    .fNameEng(user.getFirstNameEn())
                                    .lNameEng(user.getLastNameEn())
                                    .yonalishQisqa(faculty.getShortName())
                                    .yonalishEng(faculty.getNameEn())
                                    .yonalishUzb(faculty.getName())
                                    .maktab(faculty.getSchool())
                                    .bachelorOf(creator.getBachelorOf())
                                    .imtiyoz(creator.getImtiyoz())
                                    .imtiyozEng(creator.getImtiyozEng())
                                    .login(user.getLogin())
                                    .build();
                            diplomaRepository.save(diploma);
                            return new ApiResponse(true, user.getLogin() + " diploma is saved");
                        } else {
                            throw new IllegalArgumentException("The diploma is already with raqam: " + creator.getDiplomRaqami());
                        }
                    } else {
                        throw new IllegalArgumentException("The diploma is already with ID: " + creator.getDiplomId());
                    }
                } else {
                    throw new IllegalArgumentException("The diploma is already with login: " + (user.getLogin()));
                }
            }
            else {
                throw new IllegalArgumentException("The student not found with userId: " + (creator.studentId));
            }
        }
        else {
            throw new IllegalArgumentException("The student not found: " + (creator.studentId));
        }
    }

    @Override
    public ApiResponse updateDiploma(DiplomaCreator creator) {
        if (diplomaRepository.existsById(creator.id)){
            Diploma byId = diplomaRepository.getById(creator.id);
            User user = userRepository.getById(creator.studentId);
            Student student = studentRepository.findStudentByUserId(creator.studentId);
            Group group = student.getGroup();
            Faculty faculty = group.getFaculty();
            Boolean existsed = diplomaRepository.existsDiplomaByLogin(user.getLogin());
            Boolean existsed2 = diplomaRepository.existsDiplomaByLoginAndId(user.getLogin(),creator.id);
            if(!existsed || existsed2){
                if (!diplomaRepository.existsDiplomaByDiplomId(creator.getDiplomId())
                || diplomaRepository.existsDiplomaByDiplomIdAndId(creator.getDiplomId(), creator.id)
                ) {
                    if (!diplomaRepository.existsDiplomaByDiplomRaqami(creator.diplomRaqami)
                    || diplomaRepository.existsDiplomaByDiplomRaqamiAndId(creator.diplomRaqami,creator.id)
                    ){
                        Diploma diploma = diplomaRepository.findById(creator.id).get();
                        diploma.setDiplomId(creator.getDiplomId());
                        diploma.setDiplomSeriya(creator.getDiplomSeriya());
                        diploma.setDiplomRaqami(creator.getDiplomRaqami());
                        diploma.setFName(user.getFirstName());
                        diploma.setLName(user.getLastName());
                        diploma.setMName(user.getMiddleName());
                        diploma.setFNameEng(user.getFirstNameEn());
                        diploma.setLNameEng(user.getLastNameEn());
                        diploma.setYonalishQisqa(faculty.getShortName());
                        diploma.setYonalishEng(faculty.getNameEn());
                        diploma.setYonalishUzb(faculty.getName());
                        diploma.setMaktab(faculty.getSchool());
                        diploma.setBachelorOf(creator.getBachelorOf());
                        diploma.setImtiyoz(creator.getImtiyoz());
                        diploma.setImtiyozEng(creator.getImtiyozEng());
                        diplomaRepository.save(diploma);
                        return new ApiResponse(true,user.getLogin()+" diploma is updated");
                    }
                    else {
                        throw new IllegalArgumentException("The diploma is already with raqam: "+creator.getDiplomRaqami());
                    }
                }
                else {
                    throw new IllegalArgumentException("The diploma is already with ID: "+creator.getDiplomId());
                }
            }
            else {
                throw new IllegalArgumentException("The diploma is already with login: "+(user.getLogin()));
            }
        }
        else {
            throw new IllegalArgumentException("The diploma is not found with ID: "+creator.id);
        }
    }

    @Override
    @Transactional
    public ApiResponse uploadDiploma(MultipartHttpServletRequest request) {
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
    public ApiResponse getStudentsWithDiploma(String groupId) {
        return new ApiResponse(true,"Students with diploma are listed",diplomaRepository.findStudentsWithDiploma(groupId));
    }

    @Override
    public ApiResponse deleteDiploma(String id) {
        try {
            diplomaRepository.deleteById(id);
            return new ApiResponse(true,"Diploma is deleted");
        }
        catch (Exception e){
            return new ApiResponse(false,"Error is occurred while deleting diploma");
        }
    }

    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        try {
            System.out.println("------------------------------------ 1 -----------------------");
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // Skip header row
            System.out.println("------------------------------------ 2 -----------------------");

            while (rows.hasNext()) {
                System.out.println("------------------------------------ 3 -----------------------");
                Row row = rows.next();

                // Safely get cell values with type handling
                String login = getCellValueAsString(row.getCell(0));
                String diplomId = getCellValueAsString(row.getCell(1)); // Assuming diplomId should be String
                String diplomSeriya = getCellValueAsString(row.getCell(2));
                String diplomRaqami = getCellValueAsString(row.getCell(3));

                Boolean existsed = diplomaRepository.existsDiplomaByLogin(login);
                if (!existsed) {
                    System.out.println("------------------------------------ 4 -----------------------");
                    if (!diplomaRepository.existsDiplomaByDiplomId(diplomId)) {
                        System.out.println("------------------------------------ 5 -----------------------");
                        if (!diplomaRepository.existsDiplomaByDiplomRaqami(diplomRaqami)) {
                            System.out.println("------------------------------------ 6 -----------------------");
                            Diploma diploma = Diploma.builder()
                                    .login(login)
                                    .diplomId(diplomId)
                                    .diplomSeriya(diplomSeriya)
                                    .diplomRaqami(diplomRaqami)
                                    .lName(getCellValueAsString(row.getCell(4)))
                                    .fName(getCellValueAsString(row.getCell(5)))
                                    .mName(getCellValueAsString(row.getCell(6)))
                                    .lNameEng(getCellValueAsString(row.getCell(7)))
                                    .fNameEng(getCellValueAsString(row.getCell(8)))
                                    .yonalishQisqa(getCellValueAsString(row.getCell(9)))
                                    .yonalishUzb(getCellValueAsString(row.getCell(10)))
                                    .yonalishEng(getCellValueAsString(row.getCell(11)))
                                    .maktab(getCellValueAsString(row.getCell(12)))
                                    .bachelorOf(getCellValueAsString(row.getCell(13)))
                                    .build();

                            // Optional fields with null and empty checks
                            String imtiyoz = getCellValueAsString(row.getCell(14));
                            if (imtiyoz != null && !imtiyoz.isEmpty()) {
                                diploma.setImtiyoz(imtiyoz);
                            }
                            String imtiyozEng = getCellValueAsString(row.getCell(15));
                            if (imtiyozEng != null && !imtiyozEng.isEmpty()) {
                                diploma.setImtiyozEng(imtiyozEng);
                            }

                            diplomaRepository.save(diploma);
                            System.out.println("///////////////////////////////////////////////////");
                        } else {
                            throw new IllegalArgumentException("The diploma is already with raqam: " + diplomRaqami);
                        }
                    } else {
                        throw new IllegalArgumentException("The diploma is already with ID: " + diplomId);
                    }
                } else {
                    throw new IllegalArgumentException("The diploma is already with login: " + login);
                }
            }

            return new ApiResponse(true, "Diplomas are saved.");
        } catch (Exception e) {
            return new ApiResponse(false, "Error occurred while saving diplomas: " + e.getMessage());
        }
    }

    // Helper method to get cell value as String regardless of type
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Handle dates if needed
                } else {
                    // Convert numeric to string, removing ".0" if it's a whole number
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == Math.floor(numericValue)) {
                        return String.valueOf((long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Or evaluate the formula if needed
            case BLANK:
                return "";
            default:
                return null;
        }
    }

    private Workbook getWorkBook(MultipartFile file) {
        Workbook workbook = null;
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            assert extension != null;
            if (extension.equalsIgnoreCase("xlsx")){
                workbook = new XSSFWorkbook(file.getInputStream());

            } else if (extension.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return workbook;
    }


}
