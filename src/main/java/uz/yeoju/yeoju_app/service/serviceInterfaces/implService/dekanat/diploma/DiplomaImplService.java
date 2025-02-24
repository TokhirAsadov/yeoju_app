package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.diploma;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.DiplomaRepository;

@Service
public class DiplomaImplService implements DiplomaService{
    private final DiplomaRepository diplomaRepository;

    public DiplomaImplService(DiplomaRepository diplomaRepository) {
        this.diplomaRepository = diplomaRepository;
    }
}
