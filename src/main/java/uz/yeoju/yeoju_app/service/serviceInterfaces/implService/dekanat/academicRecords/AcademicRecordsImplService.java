package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.dekanat.academicRecords;

import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.repository.AcademicRecordsRepository;

@Service
public class AcademicRecordsImplService implements AcademicRecordsService{
    private final AcademicRecordsRepository academicRecordsRepository;

    public AcademicRecordsImplService(AcademicRecordsRepository academicRecordsRepository) {
        this.academicRecordsRepository = academicRecordsRepository;
    }
}
