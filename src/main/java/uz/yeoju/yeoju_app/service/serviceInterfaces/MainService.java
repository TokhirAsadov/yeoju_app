package uz.yeoju.yeoju_app.service.serviceInterfaces;

import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface MainService<T> {

//    Page<T> findAll(Pageable pageable,String searchText);
//
//    Page<T> findAll(Pageable pageable);

    ApiResponse findAll();

    ApiResponse findById(String id);

    ApiResponse getById(String id);

    ApiResponse saveOrUpdate(T t);

    ApiResponse deleteById(String id);
}
