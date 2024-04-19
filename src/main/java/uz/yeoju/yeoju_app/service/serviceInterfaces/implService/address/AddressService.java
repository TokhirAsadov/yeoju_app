package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.address;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.address.Address2Dto;
import uz.yeoju.yeoju_app.payload.address.AddressDto;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.address.SelectAddressDto;

public interface AddressService {

    ApiResponse findAll();
    ApiResponse findById(String id);
    ApiResponse save(AddressDto dto);
    ApiResponse saveForUser(User user,AddressDto dto);

    ApiResponse saveSecond(User user, SelectAddressDto dto);

    ApiResponse save(User user, Address2Dto dto);

    ApiResponse getRegions();

    ApiResponse getDistrictsByRegionId(Long regionId);

    ApiResponse getVillagesByDistrictId(Long districtId);

    ApiResponse saveFromAttachment(MultipartHttpServletRequest request);
    ApiResponse saveFromAttachmentWithLogin(MultipartHttpServletRequest request);

    ApiResponse getMapStatistics();

    ApiResponse getDekanatsForStatistics();
    ApiResponse getFacultiesForStatistics();
    ApiResponse getFacultiesByDekanatId(String dekanatId);

    ApiResponse getDekanatStatistics(String dekanatId);

    ApiResponse getDekanatStatisticsByFacultyId(String facultyId);

    ApiResponse getDekanatStatisticsByFacultyAndEduType(String facultyId, String eduType);

    ApiResponse getDekanatStatisticsByFacultyAndEduTypeAndEduLang(String facultyId, String eduType, String eduLang);

    ApiResponse getStatisticsByEduTypeAndLevelAndFacultyIdAndEduLang(String eduType, Integer level, String facultyId, String eduLang);

    ApiResponse getStatisticsByEduTypeAndLevelAndFacultyId(String eduType, Integer level, String facultyId);

    ApiResponse getStatisticsByEduTypeAndLevelAndEduLang(String eduType, Integer level, String eduLang);

    ApiResponse getStatisticsByEduTypeAndLevel(String eduType, Integer level);

    ApiResponse getStatisticsByEduTypeAndFacultyIdAndEduLang(String eduType, String facultyId, String eduLang);

    ApiResponse getStatisticsByEduTypeAndEduLang(String eduType, String eduLang);

    ApiResponse getStatisticsByEduType(String eduType);

    ApiResponse getStatisticsByLevelAndFacultyIdAndEduLang(Integer level, String facultyId, String eduLang);

    ApiResponse getStatisticsByLevelAndFacultyId(Integer level, String facultyId);

    ApiResponse getStatisticsByLevelAndEduLang(Integer level, String eduLang);

    ApiResponse getStatisticsByLevel(Integer level);

    ApiResponse getStatisticsByFacultyIdAndEduLang(String facultyId, String eduLang);

    ApiResponse getStatisticsByEduLang(String eduLang);

    ApiResponse getStatisticsByEduTypeAndFacultyId(String eduType, String facultyId);
}
