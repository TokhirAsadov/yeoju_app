package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.AccMonitorLog;
import uz.yeoju.yeoju_app.entity.USERINFO;
import uz.yeoju.yeoju_app.exceptions.PageSizeException;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.repository.UserInfoRepo;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.AccMonitoringLogImplService;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.USERINFOImplService;
import uz.yeoju.yeoju_app.utills.CommandUtills;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class USERINFOService implements USERINFOImplService<USERINFO> {
    private final UserInfoRepo userInfoRepo;



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

}
