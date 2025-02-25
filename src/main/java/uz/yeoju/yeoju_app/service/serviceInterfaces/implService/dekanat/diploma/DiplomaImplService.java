package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.Student;
import uz.yeoju.yeoju_app.entity.User;
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

    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();
                Boolean existsed = diplomaRepository.existsDiplomaByLogin(row.getCell(0).getStringCellValue());
                if(!existsed){
                    if (!diplomaRepository.existsDiplomaByDiplomId(row.getCell(1).getStringCellValue())) {
                        if (!diplomaRepository.existsDiplomaByDiplomRaqami(row.getCell(3).getStringCellValue())){
                            Diploma diploma = Diploma.builder()
                                    .login(row.getCell(0).getStringCellValue())
                                    .diplomId(row.getCell(1).getStringCellValue())
                                    .diplomSeriya(row.getCell(2).getStringCellValue())
                                    .diplomRaqami(row.getCell(3).getStringCellValue())
                                    .lName(row.getCell(4).getStringCellValue())
                                    .fName(row.getCell(5).getStringCellValue())
                                    .mName(row.getCell(6).getStringCellValue())
                                    .lNameEng(row.getCell(7).getStringCellValue())
                                    .fNameEng(row.getCell(8).getStringCellValue())
                                    .yonalishQisqa(row.getCell(9).getStringCellValue())
                                    .yonalishUzb(row.getCell(10).getStringCellValue())
                                    .yonalishEng(row.getCell(11).getStringCellValue())
                                    .maktab(row.getCell(12).getStringCellValue())
                                    .bachelorOf(row.getCell(13).getStringCellValue())
                                    .build();

                            if (row.getCell(14)!=null && !Objects.equals(row.getCell(14).getStringCellValue(), "")){
                                diploma.setImtiyoz(row.getCell(14).getStringCellValue());
                            }
                            if (row.getCell(15)!=null && !Objects.equals(row.getCell(15).getStringCellValue(), "")){
                                diploma.setImtiyozEng(row.getCell(15).getStringCellValue());
                            }

                            diplomaRepository.save(diploma);
                        }
                        else {
                            throw new IllegalArgumentException("The diploma is already with raqam: "+row.getCell(3).getStringCellValue());
                        }
                    }
                    else {
                        throw new IllegalArgumentException("The diploma is already with ID: "+row.getCell(1).getStringCellValue());
                    }
                }
                else {
                    throw new IllegalArgumentException("The diploma is already with login: "+row.getCell(0).getStringCellValue());
                }
            }

            return new ApiResponse(true,"Diplomas are saved.");
        }catch (Exception e){
            return new ApiResponse(false,"Error is occurred while saving diplomas");
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
