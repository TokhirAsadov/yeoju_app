package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.kafedra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.dekanat.Dekan;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanSave;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraMudiriSaving;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra31;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra28;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra29;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.month.GetTeachersOfKafedra30;
import uz.yeoju.yeoju_app.repository.KafedraMudirRepository;
import uz.yeoju.yeoju_app.repository.KafedraRepository;
import uz.yeoju.yeoju_app.repository.UserRepository;

import java.util.*;

@Service
public class KafedraMudiriImplService implements KafedraMudiriService{

    @Autowired
    private KafedraMudirRepository kafedraMudirRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafedraRepository kafedraRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(true,"Barcha mudirlar ro`yxati",kafedraMudirRepository.findAll());
    }

    @Override
    @Transactional
    public ApiResponse saveKafedra(DekanSave dto) {
        Optional<User> userOptional = userRepository.findById(dto.getUserId());

        if (userOptional.isPresent()){
            Optional<Kafedra> dekanatOptional = kafedraRepository.findById(dto.getDekanatId());
            User user = userOptional.get();
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            user.setLogin(user.getFullName());
            user.setPassword(passwordEncoder.encode("root123"));
//            user.setPassportNum("");
            userRepository.save(user);

            if (dekanatOptional.isPresent()){
                KafedraMudiri kafedraMudiri = new KafedraMudiri();
                kafedraMudiri.setKafedra(dekanatOptional.get());
                kafedraMudiri.setUser(userOptional.get());
                kafedraMudirRepository.saveAndFlush(kafedraMudiri);
                return new ApiResponse(true,"saved", kafedraMudiri);
            }
            else {
                return new ApiResponse(false,"not fount kafedra");
            }
        }
        else {
            return new ApiResponse(false,"not fount user");
        }
    }

    @Override
    public ApiResponse positionEdit(String id) {
        return new ApiResponse(true,"position change",kafedraMudirRepository.getPositionEdit(id));
    }

    @Override
    public ApiResponse save(KafedraMudiriSaving saving) {

        Optional<User> userOptional = userRepository.findById(saving.getUserId());
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(saving.getKafedraId());

        if (userOptional.isPresent() && kafedraOptional.isPresent()){

            KafedraMudiri save = kafedraMudirRepository.save(new KafedraMudiri(userOptional.get(), kafedraOptional.get()));

            return new ApiResponse(true,"Saving successfully",save);
        }

        return new ApiResponse(false,"Error not fount user or kafedra");
    }

    @Override
    public ApiResponse getStatistics(User user) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = kafedraRepository.getTeachersOfKafedra31(user.getId());
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = kafedraRepository.getTeachersOfKafedra30(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = kafedraRepository.getTeachersOfKafedra29(user.getId());
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = kafedraRepository.getTeachersOfKafedra28(user.getId());
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }

    @Override
    public ApiResponse getStatistics(User user,String userId ,Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<???",kafedraRepository.getDate31(date,userId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<???",kafedraRepository.getDate30(date,userId));
        }else if (maxDay==29){
            return new ApiResponse(true,"<???",kafedraRepository.getDate29(date,userId));
        }else {
            return new ApiResponse(true,"<???",kafedraRepository.getDate28(date,userId));
        }

    }

    @Override
    public ApiResponse getStatisticsForRektor(String kafedraId ,Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(Calendar.getInstance().getMaximum(Calendar.DATE)+"-----------------------------------");
//        System.out.println(date+" /// ");

        if (maxDay==31){
            return new ApiResponse(true,"<??? 31",kafedraRepository.getDateForRektor31(date,kafedraId));
        }
        else if (maxDay==30){
            return new ApiResponse(true,"<??? 30",kafedraRepository.getDateForRektor30(date,kafedraId));
        }else if (maxDay==29){
            return new ApiResponse(true,"<??? 29",kafedraRepository.getDateForRektor29(date,kafedraId));
        }else {
            return new ApiResponse(true,"<??? 28",kafedraRepository.getDateForRektor28(date,kafedraId));
        }

    }

    @Override
    public ApiResponse getStatisticsForRektorTeacherPage(String id) {

        int maxDay = Calendar.getInstance().getMaximum(Calendar.DATE);
        Calendar calendar = Calendar.getInstance();
        int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (days==31){
            System.out.println( "31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            List<GetTeachersOfKafedra31> teachersOfKafedra = kafedraRepository.getTeachersOfKafedra31(id);
            System.out.println( teachersOfKafedra+"31 <---===========================================================================================================================================-----------------------------------------------------------------------------------------------------------------------------------------");
            return new ApiResponse(true,"show", teachersOfKafedra);
        }
        else if (days==30){
            List<GetTeachersOfKafedra30> teachersOfKafedra30 = kafedraRepository.getTeachersOfKafedra30(id);
            return new ApiResponse(true,"show", teachersOfKafedra30);
        }else if (maxDay==29){
            List<GetTeachersOfKafedra29> teachersOfKafedra29 = kafedraRepository.getTeachersOfKafedra29(id);
            return new ApiResponse(true,"show",teachersOfKafedra29 );
        }else {
            List<GetTeachersOfKafedra28> teachersOfKafedra28 = kafedraRepository.getTeachersOfKafedra28(id);
            return new ApiResponse(true,"show", teachersOfKafedra28);
        }

    }
}
