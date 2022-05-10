package uz.yeoju.yeoju_app.service.serviceInterfaces;

import uz.yeoju.yeoju_app.payload.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MainService<T> {

//    Page<T> findAll(Pageable pageable,String searchText);
//
//    Page<T> findAll(Pageable pageable);

    ApiResponse findAll();

    ApiResponse findById(UUID id);

    ApiResponse getById(UUID id);

    ApiResponse saveOrUpdate(T t);

    ApiResponse deleteById(UUID id);
}
