package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.Faculty;
import uz.yeoju.yeoju_app.entity.dekanat.Dekanat;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DekanatSaveDto;
import uz.yeoju.yeoju_app.payload.resDto.user.UserForTeacherSaveItem;
import uz.yeoju.yeoju_app.repository.DekanatRepository;
import uz.yeoju.yeoju_app.repository.FacultyRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DekanatImplService implements DekanatService{

    private final DekanatRepository dekanatRepository;
    private final FacultyRepository facultyRepository;

    @Override
    public ApiResponse findAllDekanats() {

//        Dekanat dekanat = new Dekanat();

        return new ApiResponse(true,"all dekanat", dekanatRepository.findAll());
    }

    @Override
    @Transactional
    public ApiResponse saveDekanat(DekanatSaveDto dto) {

        if (dekanatRepository.existsDekanatByName(dto.getName())) {
            return new ApiResponse(false,"Already exists dekanat");
        }

        Dekanat dekanat = new Dekanat();
        dekanat.setName(dto.getName());

        Set<Faculty> faculties = new HashSet<>();

        for (String id : dto.getIdsOfFaculties()) {
            Optional<Faculty> facultyOptional = facultyRepository.findById(id);
            if (facultyOptional.isPresent()){
              faculties.add(facultyOptional.get());
            }
            else {
               return new ApiResponse(false,"not fount faculty");
            }
        }

        dekanat.setFaculties(faculties);
        dekanatRepository.save(dekanat);

        return new ApiResponse(true,"saved dekanat successfully...");
    }


    @Override
    public ApiResponse getDekansForSaved() {
        return new ApiResponse(true,"datas",dekanatRepository.getDekansForSaved());
    }

    @Override
    public ApiResponse getDekansSavedDatas() {
        return new ApiResponse(true,"datas",dekanatRepository.getForDekanRoleSettings());
    }

    @Override
    public ApiResponse getKafedrasSavedDatas() {
        return new ApiResponse(true,"datas",dekanatRepository.getForKafedraRoleSettings());
    }


}
