package uz.yeoju.yeoju_app.service.serviceInterfaces;

import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.util.List;
import java.util.Optional;

public interface MainService<T> {

//    Page<T> findAll(Pageable pageable,String searchText);
//
//    Page<T> findAll(Pageable pageable);

    ApiResponse findAll();

    ApiResponse findById(Long id);

    ApiResponse getById(Long id);

    ApiResponse saveOrUpdate(T t);

    ApiResponse deleteById(Long id);
}
