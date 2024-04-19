package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.entity.address.Villages;
import uz.yeoju.yeoju_app.payload.address.Address2Dto;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.address.SelectAddressDto;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.address.DistrictsRepository;
import uz.yeoju.yeoju_app.repository.address.RegionsRepository;
import uz.yeoju.yeoju_app.repository.address.VillagesRepository;
import uz.yeoju.yeoju_app.service.useServices.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AddressImplService implements AddressService{

    @Autowired
    private AddressUserRepository addressUserRepository;

    @Autowired
    private VillagesRepository villagesRepository;

    @Autowired
    private RegionsRepository regionsRepository;

    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private GanderRepository ganderRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"all addresses",addressUserRepository.findAll().stream().map(this::generateAddressDto).collect(Collectors.toList()));
    }

    @Override
    public ApiResponse findById(String id) {
        if (addressUserRepository.findById(id).isPresent()) {
            return new ApiResponse(true,"address",generateAddressDto(addressUserRepository.findById(id).get()));
        }
        else {
            return new ApiResponse(false,"not fount address");
        }
    }

    @Override
    public ApiResponse save(AddressDto dto) {
        addressUserRepository.save(generateAddress(dto));
        if (dto.getId()==null)
            return new ApiResponse(true,"saved");
        return new ApiResponse(true,"updated");
    }

    @Override
    public ApiResponse saveForUser(User user, AddressDto dto) {
        ApiResponse response = userService.findById(user.getId());
        if (response.isSuccess()){
            return save(
                            new AddressDto(
                                dto.getId(),
                                userService.generateUserDto((User) response.getObj()),
                                dto.getCountry(),
                                dto.getRegion(),
                                dto.getArea(),
                                dto.getAddress(),
                                dto.getConstant()
                            )
                        );
        }
        return new ApiResponse(false,"not fount user");
    }

    @Override
    public ApiResponse saveSecond(User user, SelectAddressDto dto) {
        return null;
    }

    @Override
    public ApiResponse save(User user, Address2Dto dto ) {

        if (dto.getId()==null){
            return save2(user,dto);
        }
        else {
            return update2(user,dto );
        }

    }

    @Override
    public ApiResponse getRegions() {
        return new ApiResponse(true,"regions", regionsRepository.findAll());
    }

    @Override
    public ApiResponse getDistrictsByRegionId(Long regionId) {
        return new ApiResponse(true,"districts by id "+regionId,districtsRepository.findDistrictsByRegionId(regionId));
    }

    @Override
    public ApiResponse getVillagesByDistrictId(Long districtId) {
        return new ApiResponse(true,"villages by id "+districtId,villagesRepository.findVillagesByDistrictId(districtId));
    }

    @Transactional
    @Override
    public ApiResponse saveFromAttachment(MultipartHttpServletRequest request) {
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
    public ApiResponse saveFromAttachmentWithLogin(MultipartHttpServletRequest request) {
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcelWithLogin(file);
            }
        }
        return null;
    }

    @Override
    public ApiResponse getMapStatistics() {
        return new ApiResponse(true," get Map Statistics ",addressUserRepository.getMapStatistics());
    }

    @Override
    public ApiResponse getDekanatsForStatistics() {
        return new ApiResponse(true,"dekanats",addressUserRepository.getDekanatsForStatistics());
    }

    @Override
    public ApiResponse getFacultiesForStatistics() {
        return new ApiResponse(true,"faculties",addressUserRepository.getFacultiesForStatistics());
    }

    @Override
    public ApiResponse getFacultiesByDekanatId(String dekanatId) {
        return new ApiResponse(true,"faculties by id: "+dekanatId,addressUserRepository.getFacultiesByDekanatId(dekanatId));
    }

    @Override
    public ApiResponse getDekanatStatistics(String dekanatId) {
        return new ApiResponse(true,"dekanat statistics",addressUserRepository.getDekanatStatistics(dekanatId));
    }

    @Override
    public ApiResponse getDekanatStatisticsByFacultyId(String facultyId) {
        return new ApiResponse(true,"statistics by faculty id",addressUserRepository.getDekanatStatisticsByFacultyId(facultyId));
    }

    @Override
    public ApiResponse getDekanatStatisticsByFacultyAndEduType(String facultyId, String eduType) {
        return new ApiResponse(true,"statistics by faculty id and education type",addressUserRepository.getDekanatStatisticsByFacultyAndEduType(facultyId,eduType));
    }

    @Override
    public ApiResponse getDekanatStatisticsByFacultyAndEduTypeAndEduLang(String facultyId, String eduType, String eduLang) {
        return new ApiResponse(true,"statistics by faculty id and education type and education language",addressUserRepository.getDekanatStatisticsByFacultyAndEduTypeAndEduLang(facultyId, eduType, eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndLevelAndFacultyIdAndEduLang(String eduType, Integer level, String facultyId, String eduLang) {
        return new ApiResponse(true,"statistics by education type and level and faculty id and education language",addressUserRepository.getStatisticsByEduTypeAndLevelAndFacultyIdAndEduLang(eduType,level,facultyId,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndLevelAndFacultyId(String eduType, Integer level, String facultyId) {
        return new ApiResponse(true,"statistics by education type and level and faculty id ",addressUserRepository.getStatisticsByEduTypeAndLevelAndFacultyId(eduType,level,facultyId));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndLevelAndEduLang(String eduType, Integer level, String eduLang) {
        return new ApiResponse(true,"statistics by education type and level and education language",addressUserRepository.getStatisticsByEduTypeAndLevelAndEduLang(eduType,level,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndLevel(String eduType, Integer level) {
        return new ApiResponse(true,"statistics by education type and level",addressUserRepository.getStatisticsByEduTypeAndLevel(eduType,level));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndFacultyIdAndEduLang(String eduType, String facultyId, String eduLang) {
        return new ApiResponse(true,"statistics by education type and faculty id and education language",addressUserRepository.getStatisticsByEduTypeAndFacultyIdAndEduLang(eduType,facultyId,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndEduLang(String eduType, String eduLang) {
        return new ApiResponse(true,"statistics by education type and education language",addressUserRepository.getStatisticsByEduTypeAndEduLang(eduType,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduType(String eduType) {
        return new ApiResponse(true,"statistics by education type",addressUserRepository.getStatisticsByEduType(eduType));
    }

    @Override
    public ApiResponse getStatisticsByLevelAndFacultyIdAndEduLang(Integer level, String facultyId, String eduLang) {
        return  new ApiResponse(true,"statistics by level and faculty id and education language",addressUserRepository.getStatisticsByLevelAndFacultyIdAndEduLang(level,facultyId,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByLevelAndFacultyId(Integer level, String facultyId) {
        return new ApiResponse(true,"statistics by level and faculty id",addressUserRepository.getStatisticsByLevelAndFacultyId(level,facultyId));
    }

    @Override
    public ApiResponse getStatisticsByLevelAndEduLang(Integer level, String eduLang) {
        return new ApiResponse(true,"statistics by level and education language",addressUserRepository.getStatisticsByLevelAndEduLang(level,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByLevel(Integer level) {
        return new ApiResponse(true,"statistics by level",addressUserRepository.getStatisticsByLevel(level));
    }

    @Override
    public ApiResponse getStatisticsByFacultyIdAndEduLang(String facultyId, String eduLang) {
        return new ApiResponse(true,"statistics by faculty id and education language",addressUserRepository.getStatisticsByFacultyIdAndEduLang(facultyId,eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduLang(String eduLang) {
        return new ApiResponse(true,"statistics by education language",addressUserRepository.getStatisticsByEduLang(eduLang));
    }

    @Override
    public ApiResponse getStatisticsByEduTypeAndFacultyId(String eduType, String facultyId) {
        return new ApiResponse(true,"statistics by education type and faculty id",addressUserRepository.getStatisticsByEduTypeAndFacultyId(eduType,facultyId));
    }

    @Transactional
    public ApiResponse readDataFromExcelWithLogin(MultipartFile file) {
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            System.out.println(rows);
            System.out.println(rows.hasNext());
            while (rows.hasNext()){
                Row row = rows.next();
//                row.getCell(3).getStringCellValue()

                System.out.println(row.getCell(0).getStringCellValue()+" <- user ID");
//                System.out.println(row.getCell(0).toString()+" <- user ID");
                System.out.println(row.getCell(1).getStringCellValue()+" <- region");
                System.out.println(row.getCell(2).getStringCellValue()+" <- district");
//                System.out.println(row.getCell(19).getStringCellValue()+" <- ismi");


                User user  = userRepository.getUserByLogin(row.getCell(0).getStringCellValue());
                if (user!=null) {
                    System.out.println(user + "+ <- user");

                    AddressUser addressUser = new AddressUser();
                    addressUser.setUser(user);
                    addressUser.setRegion(row.getCell(1).getStringCellValue());
                    addressUser.setArea(row.getCell(2).getStringCellValue());

                    System.out.println(addressUser+" <- address");

                    addressUserRepository.save(addressUser);

                }

            }

            return new ApiResponse(true,"Addresses were saved successfully");
        }catch (Exception e){
            return new ApiResponse(false,"Error occured while reading data from Excel");
        }
    }

    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            System.out.println(rows);
            System.out.println(rows.hasNext());
            while (rows.hasNext()){
                Row row = rows.next();
//                row.getCell(3).getStringCellValue()

                System.out.println(row.getCell(0).getStringCellValue()+" <- user ID");
//                System.out.println(row.getCell(0).toString()+" <- user ID");
                System.out.println(row.getCell(17).getStringCellValue()+" <- rfid");
                System.out.println(row.getCell(18).getStringCellValue()+" <- familiyasi");
                System.out.println(row.getCell(19).getStringCellValue()+" <- ismi");
                System.out.println(row.getCell(20).getStringCellValue()+" <- sharifi");
                System.out.println(row.getCell(7).getNumericCellValue()+" <- jinsi");
                System.out.println(row.getCell(5).getStringCellValue()+" <- group");
                System.out.println(row.getCell(9).getStringCellValue()+" <- passport");
                System.out.println(row.getCell(11).getStringCellValue()+" <- viloyat");
                System.out.println(row.getCell(12).getStringCellValue()+" <- tumani");

                User user  = userRepository.getUserByLogin(row.getCell(0).getStringCellValue());
                if (user!=null) {
                    System.out.println(user + "+ <- user");
                    user.setLastName(row.getCell(18).getStringCellValue());
                    user.setFirstName(row.getCell(19).getStringCellValue());
                    user.setMiddleName(row.getCell(20).getStringCellValue());
                    Optional<Gander> ganderOptional = ganderRepository.findById((long) row.getCell(7).getNumericCellValue());
                    ganderOptional.ifPresent(user::setGander);

//                    if (user.getUserId()==null) {
//                        if (row.getCell(0).getCellType()== CellType.NUMERIC) {
//                            System.out.println("--------------------------------- numiric ------------------------------------------------------");
//                            System.out.println(String.valueOf(row.getCell(0).getNumericCellValue())+"--------------------------------- numiric ------------------------------------------------------");
//                        }
//                        user.setUserId(row.getCell(0).getCellType()== CellType.NUMERIC ?  String.valueOf(row.getCell(0)) : row.getCell(0).toString());
//                    }

                    userRepository.saveAndFlush(user);

                    Student studentByUserId = studentRepository.findStudentByUserId(user.getId());
                    if (studentByUserId ==null){
                        Student student = new Student();
                        student.setUser(user);
                        Group group = groupRepository.findGroupByName(row.getCell(5).getStringCellValue());
                        student.setGroup(group);
                        studentRepository.save(student);
                    }


                    AddressUser addressUser = new AddressUser();
                    addressUser.setUser(user);
                    addressUser.setRegion(row.getCell(11).getStringCellValue());
                    addressUser.setArea(row.getCell(12).getStringCellValue());

                    System.out.println(addressUser+" <- address");

                    addressUserRepository.save(addressUser);

                }
                else {
                    User newUser = new User();

                    newUser.setFullName(row.getCell(1).getStringCellValue());
                    newUser.setLastName(row.getCell(18).getStringCellValue());
                    newUser.setFirstName(row.getCell(19).getStringCellValue());
                    newUser.setMiddleName(row.getCell(20).getStringCellValue());
                    Optional<Gander> ganderOptional = ganderRepository.findById((long) row.getCell(7).getNumericCellValue());
                    ganderOptional.ifPresent(newUser::setGander);
                    newUser.setRFID(row.getCell(17).getStringCellValue());
                    newUser.setLogin(row.getCell(0).getStringCellValue());
                    newUser.setPassportNum(row.getCell(9).getStringCellValue());
                    newUser.setPassword(passwordEncoder.encode(row.getCell(9).getStringCellValue()));

                    Optional<Role> role_student = roleRepository.findRoleByRoleName("ROLE_STUDENT");
                    Set<Role> roles = new HashSet<>();
                    roles.add(role_student.get());
                    newUser.setRoles(roles);

                    User saveAndFlush = userRepository.save(newUser);


                    Student student = new Student();
                    student.setUser(saveAndFlush);
                    Group group = groupRepository.findGroupByName(row.getCell(5).getStringCellValue());
                    student.setGroup(group);
                    studentRepository.save(student);


                    AddressUser addressUser = new AddressUser();
                    addressUser.setUser(saveAndFlush);
                    addressUser.setRegion(row.getCell(11).getStringCellValue());
                    addressUser.setArea(row.getCell(12).getStringCellValue());

                    System.out.println(addressUser+" <- address");

                    addressUserRepository.save(addressUser);

                }


//                userRepository.saveOrUpdate(user);



            }


            return new ApiResponse(true,"saved users");
        }catch (Exception e){
            return new ApiResponse(false,"error saved users");
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

    private ApiResponse update2(User user, Address2Dto dto ) {

        Optional<AddressUser> addressUserOptional = addressUserRepository.findById(dto.getId());
        if (addressUserOptional.isPresent()) {
            AddressUser addressUser = addressUserOptional.get();
            Optional<Villages> villagesOptional = villagesRepository.findById(dto.getVillageId());
            addressUser.setUser(user);
            addressUser.setVillage(villagesOptional.get());
            addressUser.setAddress(dto.getAddress());
            addressUser.setArea(dto.getArea());
            addressUser.setCountry(dto.getCountry());
            addressUser.setRegion(dto.getRegion());
            addressUser.setIsConstant(dto.getConstant());
            addressUser.setFromUzbekistan(dto.getFromUzbekistan());
            addressUserRepository.saveAndFlush(addressUser);
            return new ApiResponse(true,"update address successfully.", addressUser);
        }
        else {
            return new ApiResponse(false, "not fount address");
        }

    }

    private ApiResponse save2(User user, Address2Dto dto) {

        AddressUser addressUser = new AddressUser();
        Optional<Villages> villagesOptional = villagesRepository.findById(dto.getVillageId());

        addressUser.setUser(user);
        addressUser.setVillage(villagesOptional.get());

        addressUser.setAddress(dto.getAddress());
        addressUser.setArea(dto.getArea());
        addressUser.setCountry(dto.getCountry());
        addressUser.setRegion(dto.getRegion());

        addressUser.setIsConstant(dto.getConstant());
        addressUser.setFromUzbekistan(dto.getFromUzbekistan());

        addressUserRepository.saveAndFlush(addressUser);

        return new ApiResponse(true,"save address", addressUser);
    }


    public AddressUser generateAddress(AddressDto dto){
        return new AddressUser(
                dto.getId(),
                userService.generateUser(dto.getUserDto()),
                dto.getCountry(),
                dto.getRegion(),
                dto.getArea(),
                dto.getAddress(),
                dto.getConstant()
        );
    }

    public AddressDto generateAddressDto(AddressUser address){
        return new AddressDto(
                address.getId(),
                userService.generateUserDto(address.getUser()),
                address.getCountry(),
                address.getRegion(),
                address.getArea(),
                address.getAddress(),
                address.getIsConstant()
        );
    }
}
