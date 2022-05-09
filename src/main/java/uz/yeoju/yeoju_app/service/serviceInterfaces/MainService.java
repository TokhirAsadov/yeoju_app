package uz.yeoju.yeoju_app.service.serviceInterfaces;

import java.util.List;
import java.util.Optional;

public interface MainService<T> {

//    Page<T> findAll(Pageable pageable,String searchText);
//
//    Page<T> findAll(Pageable pageable);

    List<T> findAll();

    Optional<T> findById(Long id);

    T getById(Long id);

    T saveOrUpdate(T t);

    void deleteById(Long id);
}
