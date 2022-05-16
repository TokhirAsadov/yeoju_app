package uz.yeoju.yeoju_app.service.serviceInterfaces.implService;

import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.service.serviceInterfaces.MainService;

public interface GroupImplService<T> extends MainService<T> {
    ApiResponse findGroupByName(String name);
    ApiResponse findGroupsByLevel(Integer level);
    ApiResponse findGroupsByFacultyId(String faculty_id);
}
