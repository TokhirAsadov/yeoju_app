package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.dekanat.Diploma;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.dekanat.DiplomaCreator;
import uz.yeoju.yeoju_app.repository.DiplomaRepository;

@Service
public class DiplomaImplService implements DiplomaService{
    private final DiplomaRepository diplomaRepository;

    public DiplomaImplService(DiplomaRepository diplomaRepository) {
        this.diplomaRepository = diplomaRepository;
    }

    @Override
    public ApiResponse createDiploma(DiplomaCreator creator) {
        Boolean existsed = diplomaRepository.existsDiplomaByLogin(creator.getLogin());
        if(!existsed){
            if (!diplomaRepository.existsDiplomaByDiplomId(creator.getDiplomId())) {
                if (!diplomaRepository.existsDiplomaByDiplomRaqami(creator.diplomRaqami)){
                    Diploma diploma = Diploma.builder()
                            .diplomId(creator.getDiplomId())
                            .diplomSeriya(creator.getDiplomSeriya())
                            .diplomRaqami(creator.getDiplomRaqami())
                            .fName(creator.getFName())
                            .lName(creator.getLName())
                            .mName(creator.getMName())
                            .fNameEng(creator.getFNameEng())
                            .lNameEng(creator.getLNameEng())
                            .yonalishQisqa(creator.getYonalishQisqa())
                            .yonalishEng(creator.getYonalishEng())
                            .yonalishUzb(creator.getYonalishUzb())
                            .maktab(creator.getMaktab())
                            .bachelorOf(creator.getBachelorOf())
                            .imtiyoz(creator.getImtiyoz())
                            .imtiyozEng(creator.getImtiyozEng())
                            .login(creator.getLogin())
                            .build();
                    diplomaRepository.save(diploma);
                    return new ApiResponse(true,creator.getLogin()+" diploma is saved");
                }
                else {
                    throw new IllegalArgumentException("The diploma is already with raqam: "+creator.getDiplomRaqami());
                }
            }
            else {
                throw new IllegalArgumentException("The diploma is already with ID: "+creator.getDiplomId());
            }
        }
        else {
            throw new IllegalArgumentException("The diploma is already with login: "+(creator.getLogin()));
        }
    }

    @Override
    public ApiResponse updateDiploma(DiplomaCreator creator) {
        if (diplomaRepository.existsById(creator.id)){
            Boolean existsed = diplomaRepository.existsDiplomaByLogin(creator.getLogin());
            Boolean existsed2 = diplomaRepository.existsDiplomaByLoginAndId(creator.getLogin(),creator.id);
            if(!existsed || existsed2){
                if (!diplomaRepository.existsDiplomaByDiplomId(creator.getDiplomId())
                || diplomaRepository.existsDiplomaByDiplomIdAndId(creator.getDiplomId(), creator.id)
                ) {
                    if (!diplomaRepository.existsDiplomaByDiplomRaqami(creator.diplomRaqami)
                    || diplomaRepository.existsDiplomaByDiplomRaqamiAndId(creator.diplomRaqami,creator.id)
                    ){
                        Diploma diploma = Diploma.builder()
                                .diplomId(creator.getDiplomId())
                                .diplomSeriya(creator.getDiplomSeriya())
                                .diplomRaqami(creator.getDiplomRaqami())
                                .fName(creator.getFName())
                                .lName(creator.getLName())
                                .mName(creator.getMName())
                                .fNameEng(creator.getFNameEng())
                                .lNameEng(creator.getLNameEng())
                                .yonalishQisqa(creator.getYonalishQisqa())
                                .yonalishEng(creator.getYonalishEng())
                                .yonalishUzb(creator.getYonalishUzb())
                                .maktab(creator.getMaktab())
                                .bachelorOf(creator.getBachelorOf())
                                .imtiyoz(creator.getImtiyoz())
                                .imtiyozEng(creator.getImtiyozEng())
                                .login(creator.getLogin())
                                .build();
                        diplomaRepository.save(diploma);
                        return new ApiResponse(true,creator.getLogin()+" diploma is updated");
                    }
                    else {
                        throw new IllegalArgumentException("The diploma is already with raqam: "+creator.getDiplomRaqami());
                    }
                }
                else {
                    throw new IllegalArgumentException("The diploma is already with ID: "+creator.getDiplomId());
                }
            }
            else {
                throw new IllegalArgumentException("The diploma is already with login: "+(creator.getLogin()));
            }
        }
        else {
            throw new IllegalArgumentException("The diploma is not found with ID: "+creator.id);
        }
    }
}
