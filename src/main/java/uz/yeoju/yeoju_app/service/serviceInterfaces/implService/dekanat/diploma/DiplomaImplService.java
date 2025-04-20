package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.dekanat.Diploma;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaCreator;
import uz.yeoju.yeoju_app.repository.DiplomaRepository;

import java.util.Iterator;
import java.util.Objects;

@Service
public class DiplomaImplService implements DiplomaService{
    private final DiplomaRepository diplomaRepository;

    public DiplomaImplService(DiplomaRepository diplomaRepository) {
        this.diplomaRepository = diplomaRepository;
    }

    @Override
    public ApiResponse createDiploma(DiplomaCreator creator) {
        Boolean existsed = diplomaRepository.existsDiplomaByLogin(creator.getLogin());
        if(!existsed){
            if (!diplomaRepository.existsDiplomaByDiplomId(creator.getDiplomId())) {
                if (!diplomaRepository.existsDiplomaByDiplomRaqami(creator.diplomRaqami)){
                    Diploma diploma = Diploma.builder()
                            .diplomId(creator.getDiplomId())
                            .diplomSeriya(creator.getDiplomSeriya())
                            .diplomRaqami(creator.getDiplomRaqami())
                            .fName(creator.getFName())
                            .lName(creator.getLName())
                            .mName(creator.getMName())
                            .fNameEng(creator.getFNameEng())
                            .lNameEng(creator.getLNameEng())
                            .yonalishQisqa(creator.getYonalishQisqa())
                            .yonalishEng(creator.getYonalishEng())
                            .yonalishUzb(creator.getYonalishUzb())
                            .maktab(creator.getMaktab())
                            .bachelorOf(creator.getBachelorOf())
                            .imtiyoz(creator.getImtiyoz())
                            .imtiyozEng(creator.getImtiyozEng())
                            .login(creator.getLogin())
                            .build();
                    diplomaRepository.save(diploma);
                    return new ApiResponse(true,creator.getLogin()+" diploma is saved");
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
            throw new IllegalArgumentException("The diploma is already with login: "+(creator.getLogin()));
        }
    }

    @Override
    public ApiResponse updateDiploma(DiplomaCreator creator) {
        if (diplomaRepository.existsById(creator.id)){
            Boolean existsed = diplomaRepository.existsDiplomaByLogin(creator.getLogin());
            Boolean existsed2 = diplomaRepository.existsDiplomaByLoginAndId(creator.getLogin(),creator.id);
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
                        diploma.setFName(creator.getFName());
                        diploma.setLName(creator.getLName());
                        diploma.setMName(creator.getMName());
                        diploma.setFNameEng(creator.getFNameEng());
                        diploma.setLNameEng(creator.getLNameEng());
                        diploma.setYonalishQisqa(creator.getYonalishQisqa());
                        diploma.setYonalishEng(creator.getYonalishEng());
                        diploma.setYonalishUzb(creator.getYonalishUzb());
                        diploma.setMaktab(creator.getMaktab());
                        diploma.setBachelorOf(creator.getBachelorOf());
                        diploma.setImtiyoz(creator.getImtiyoz());
                        diploma.setImtiyozEng(creator.getImtiyozEng());
                        diplomaRepository.save(diploma);
                        return new ApiResponse(true,creator.getLogin()+" diploma is updated");
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
                throw new IllegalArgumentException("The diploma is already with login: "+(creator.getLogin()));
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
