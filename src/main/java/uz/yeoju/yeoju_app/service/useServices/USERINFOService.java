package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jaxen.util.SingletonList;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.enums.Gandername;
import uz.yeoju.yeoju_app.exceptions.PageSizeException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.UserInfoAndUserAndStudentDto;
import uz.yeoju.yeoju_app.repository.RoleRepository;
import uz.yeoju.yeoju_app.repository.UserInfoRepo;
import uz.yeoju.yeoju_app.repository.UserRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.USERINFOImplService;
import uz.yeoju.yeoju_app.utills.CommandUtills;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class
USERINFOService implements USERINFOImplService<USERINFO> {
    private final UserInfoRepo userInfoRepo;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;



    public ApiResponse byPageable(Integer page, Integer size ) throws PageSizeException {
        List<USERINFO> userinfoList=new ArrayList<>();
        long totalElements=0;

            Page<USERINFO> userinfoPage = userInfoRepo.findAll(CommandUtills.descOrAscByCreatedAtPageable(page,size,false));
            totalElements=userinfoPage.getTotalElements();


        return new ApiResponse(true,"All By Pageable",userinfoPage,totalElements);
    }

    @Override
    public ApiResponse findAll() {


        return null;
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(USERINFO userinfo) {
        return null;
    }

    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }

    @Transactional
    public ApiResponse createUserInfo(UserInfoAndUserAndStudentDto dto) {
        Long badgenumber = userInfoRepo.getBadgenumber();

        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getCardNo());
        System.out.println(dto.toString());
        if (byCardNo==null){
            USERINFO userinfo = new USERINFO();
            userinfo.setBadgenumber(badgenumber+1);
            userinfo.setName(dto.getName());
            userinfo.setLastname(dto.getLastname());
            userinfo.setCardNo(dto.getCardNo());
            System.out.println(userinfo.toString());
            userInfoRepo.save(userinfo);

            User user = new User();
            user.setFullName(dto.getName()+" "+dto.getLastname());
            user.setLogin(dto.getLogin());
            user.setRFID(dto.getCardNo());
            user.setPassportNum(dto.getPassport());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
            if (roleOptional.isPresent()){
                user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
            }
            else {
                user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
            }
            userRepository.save(user);
            return new ApiResponse(true,"saved user successfully.");
        }
        else {

            User userByRFID = userRepository.findUserByRFID(dto.getCardNo());
            if (userByRFID==null){
                User user = new User();
                user.setFullName(dto.getName()+" "+dto.getLastname());
                user.setLogin(dto.getLogin());
                user.setRFID(dto.getCardNo());
                user.setPassportNum(dto.getPassport());
                user.setPassword(passwordEncoder.encode(dto.getPassword()));
                user.setAccountNonExpired(true);
                user.setAccountNonLocked(true);
                user.setCredentialsNonExpired(true);
                user.setEnabled(true);

                Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
                if (roleOptional.isPresent()){
                    user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                }
                else {
                    user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                }

                userRepository.save(user);

                return new ApiResponse(true,"saved user successfully.");
            }

            return new ApiResponse(false,"Bunday card_no li user mavjud. Boshqa card_no kiriting");
        }
    }





    @Transactional
    public ApiResponse saving(MultipartHttpServletRequest request) throws IOException {
        System.out.println(" ----------------------------- 2 2 2 ------------------------ --");
        Iterator<String> fileNames = request.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile file = request.getFile(fileNames.next());
            if (file != null) {
                return readDataFromExcel(file);
            }
        }
        return null;
    }


    @Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();

//                System.out.println(row.getCell(0).getStringCellValue());
////                System.out.println( (long)row.getCell(1).getNumericCellValue());
//                System.out.println(row.getCell(2).getStringCellValue());
//                System.out.println(row.getCell(3).getNumericCellValue());
//                System.out.println(row.getCell(4).getStringCellValue());
//                System.out.println(row.getCell(5).getStringCellValue());
//                System.out.println(row.getCell(6).getStringCellValue());

//                Long badgenumber = userInfoRepo.getBadgenumber();
//
                USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(3).getStringCellValue());

                if (byCardNo==null){
                    USERINFO userinfo = new USERINFO();
                    userinfo.setBadgenumber((long) row.getCell(3).getNumericCellValue());
                    userinfo.setName(row.getCell(1).getStringCellValue());
                    userinfo.setLastname(row.getCell(0).getStringCellValue());
                    userinfo.setCardNo(row.getCell(5).getStringCellValue());
                    System.out.println(userinfo.toString());
                    userInfoRepo.save(userinfo);
                    //todo---------------------


                    User userByRFID = userRepository.findUserByRFID(row.getCell(5).getStringCellValue());
                    if (userByRFID==null){
                        User user = new User();

                        user.setFullName(row.getCell(0).getStringCellValue()+" "+ row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());

                        user.setLogin(row.getCell(4).getStringCellValue());
                        user.setRFID(row.getCell(5).getStringCellValue());
                        user.setPassportNum(row.getCell(6).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(user);
                    }
                    else {
                        userByRFID.setFullName(row.getCell(0).getStringCellValue()+" "+ row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());

                        userByRFID.setLogin(row.getCell(4).getStringCellValue());
                        userByRFID.setRFID(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(6).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);
                        userByRFID.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(userByRFID);
//                        return new ApiResponse(false, row.getCell(5).getStringCellValue() + " <- bunday card_no (rfid) li user mavjud. Boshqa card_no kiriting");
                    }







                    //todo------------------
/*
                    User user = new User();
                    user.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                    user.setLogin(row.getCell(4).getStringCellValue());
                    user.setRFID(row.getCell(5).getStringCellValue());
                    user.setPassportNum(row.getCell(6).getStringCellValue());
                    user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);

                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                    if (roleOptional.isPresent()){
                        user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                    }
                    else {
                        user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                    }
                    userRepository.save(user);*/
                }
                else {
                    byCardNo.setBadgenumber((long) row.getCell(3).getNumericCellValue());
                    byCardNo.setName(row.getCell(1).getStringCellValue());
                    byCardNo.setLastname(row.getCell(0).getStringCellValue());
                    byCardNo.setCardNo(row.getCell(5).getStringCellValue());
                    System.out.println(byCardNo.toString());
                    userInfoRepo.save(byCardNo);

                    User userByRFID = userRepository.findUserByRFID(row.getCell(5).getStringCellValue());
                    if (userByRFID==null){
                        User user = new User();

                        user.setFullName(row.getCell(0).getStringCellValue()+" "+ row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());

                        user.setLogin(row.getCell(4).getStringCellValue());
                        user.setRFID(row.getCell(5).getStringCellValue());
                        user.setPassportNum(row.getCell(6).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(user);
                    }
                    else {
                        userByRFID.setFullName(row.getCell(0).getStringCellValue()+" "+ row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());

                        userByRFID.setLogin(row.getCell(4).getStringCellValue());
                        userByRFID.setRFID(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(6).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);
                        userByRFID.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(userByRFID);
//                        return new ApiResponse(false, row.getCell(5).getStringCellValue() + " <- bunday card_no (rfid) li user mavjud. Boshqa card_no kiriting");
                    }
                }
            }
            return new ApiResponse(true,"saved users");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }


    /*@Transactional
    public ApiResponse readDataFromExcel(MultipartFile file) {
        System.out.println(" ----------------------------- 3 3 3 ------------------------ --");
        try {
            Workbook workbook = getWorkBook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next();
            while (rows.hasNext()){
                Row row = rows.next();

                System.out.println(row.getCell(0).getStringCellValue());
                System.out.println( row.getCell(1).getStringCellValue());
                System.out.println(row.getCell(2).getStringCellValue());
                System.out.println((long)row.getCell(3).getNumericCellValue());
                System.out.println(row.getCell(4).getStringCellValue());
                System.out.println(row.getCell(5).getStringCellValue());
                System.out.println(row.getCell(6).getStringCellValue());
                System.out.println(row.getCell(7).getStringCellValue());
                System.out.println(row.getCell(8).getStringCellValue());

                Long badgenumber = userInfoRepo.getBadgenumber();

                USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(row.getCell(5).getStringCellValue());

                if (byCardNo==null){
                    USERINFO userinfo = new USERINFO();
                    userinfo.setBadgenumber((long) row.getCell(3).getNumericCellValue());
                    userinfo.setName(row.getCell(1).getStringCellValue());
                    userinfo.setLastname(row.getCell(0).getStringCellValue());
                    userinfo.setCardNo(row.getCell(5).getStringCellValue());
                    System.out.println(userinfo.toString());
                    userInfoRepo.save(userinfo);

                    User user = new User();
                    user.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                    user.setLogin(row.getCell(4).getStringCellValue());
                    user.setRFID(row.getCell(5).getStringCellValue());
                    user.setPassportNum(row.getCell(6).getStringCellValue());
                    user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                    user.setAccountNonExpired(true);
                    user.setAccountNonLocked(true);
                    user.setCredentialsNonExpired(true);
                    user.setEnabled(true);

                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                    if (roleOptional.isPresent()){
                        user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                    }
                    else {
                        user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                    }
                    userRepository.save(user);
                }
                else {

                    User userByRFID = userRepository.findUserByRFID(row.getCell(5).getStringCellValue());
                    if (userByRFID==null){
                        User user = new User();
                        user.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                        user.setLogin(row.getCell(4).getStringCellValue());
                        user.setRFID(row.getCell(5).getStringCellValue());
                        user.setPassportNum(row.getCell(6).getStringCellValue());
                        user.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        user.setAccountNonExpired(true);
                        user.setAccountNonLocked(true);
                        user.setCredentialsNonExpired(true);
                        user.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(user);
                    }
                    else {
                        userByRFID.setFullName(row.getCell(0).getStringCellValue()+" "+row.getCell(1).getStringCellValue()+" "+row.getCell(2).getStringCellValue());
                        userByRFID.setLogin(row.getCell(4).getStringCellValue());
                        userByRFID.setRFID(row.getCell(5).getStringCellValue());
                        userByRFID.setPassportNum(row.getCell(6).getStringCellValue());
                        userByRFID.setPassword(passwordEncoder.encode(row.getCell(7).getStringCellValue()));
                        userByRFID.setAccountNonExpired(true);
                        userByRFID.setAccountNonLocked(true);
                        userByRFID.setCredentialsNonExpired(true);
                        userByRFID.setEnabled(true);

                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(row.getCell(8).getStringCellValue());
                        if (roleOptional.isPresent()){
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
                        }
                        else {
                            userByRFID.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
                        }
                        userRepository.save(userByRFID);
//                        return new ApiResponse(false, row.getCell(5).getStringCellValue() + " <- bunday card_no (rfid) li user mavjud. Boshqa card_no kiriting");
                    }
                }
            }
            return new ApiResponse(true,"saved students");
        }catch (Exception e){
            return new ApiResponse(false,"error saved students");
        }
    }*/


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






    /*@Transactional
    public ApiResponse createUserInfo(UserInfoAndUserAndStudentDto dto) {
        USERINFO byCardNo = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getCardNo());
        System.out.println(dto.toString());
        if (byCardNo==null){
            USERINFO userinfo = new USERINFO();
            userinfo.setName(dto.getName());
            userinfo.setLastname(dto.getLastname());
            userinfo.setCardNo(dto.getCardNo());
            System.out.println(userinfo.toString());
            userInfoRepo.save(userinfo);

            User user = new User();
            user.setFullName(dto.getName()+" "+dto.getLastname());
            user.setLogin(dto.getLogin());
            user.setRFID(dto.getCardNo());
            user.setPassportNum(dto.getPassport());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);

            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(dto.getRoleName());
            if (roleOptional.isPresent()){
                user.setRoles(new HashSet<>(new SingletonList(roleOptional.get())));
            }
            else {
                user.setRoles(new HashSet<>(new SingletonList(roleRepository.findRoleByRoleName("ROLE_USER").get())));
            }


            userRepository.save(user);

            return new ApiResponse(true,"saved user successfully.");

        }
        else {
            return new ApiResponse(false,"Bunday card_no li user mavjud. Boshqa card_no kiriting");
        }
    }*/
}
