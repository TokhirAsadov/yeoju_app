package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeCountTodayStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.KafedraResDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.repository.KafedraRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.KafedraImplService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafedraService implements KafedraImplService<KafedraDto> {
    public final KafedraRepository kafedraRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all kafedra",
                kafedraRepository.findAll().stream().map(this::generateKafedraDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return kafedraRepository
                .findById(id)
                .map(faculty -> new ApiResponse(true, "Fount Kafedra by id", faculty))
                .orElseGet(() -> new ApiResponse(false, "Not fount Kafedra by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        Kafedra kafedra = kafedraRepository.getById(id);
        return new ApiResponse(true, "Fount Kafedra by id", kafedra);
    }

    @Override
    public ApiResponse saveOrUpdate(KafedraDto dto) {
        if (dto.getId()==null){
            System.out.println("+++++++++++++++++++++++++++");
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(KafedraDto dto){
        System.out.println("\n\nenter\n\n");
        Optional<Kafedra> optional = kafedraRepository.findById(dto.getId());
        if (optional.isPresent()){
            System.out.println("\n\nenter if\n\n");
            Kafedra kafedra = optional.get();
            Kafedra kafedraByName = kafedraRepository.getKafedraByNameEn(dto.getNameEn());
            if (kafedraByName !=null) {
                if (
                        Objects.equals(kafedraByName.getId(), kafedra.getId())
                                ||
                                !kafedraRepository.existsKafedraByNameEn(dto.getNameEn())
                ) {
                    System.out.println("9999999999999999999999999");
                    kafedra.setNameUz(dto.getNameUz());
                    kafedra.setNameRu(dto.getNameRu());
                    kafedra.setNameEn(dto.getNameEn());
                    kafedraRepository.save(kafedra);
                    return new ApiResponse(true, "kafedra updated successfully!..");
                } else {
                    return new ApiResponse(
                            false,
                            "error! nor saved kafedra! Please, enter other kafedra userPositionName!.."
                    );
                }
            }
            else {
                if (!kafedraRepository.existsKafedraByNameEn(dto.getNameEn())){
                    kafedra.setNameUz(dto.getNameUz());
                    kafedra.setNameRu(dto.getNameRu());
                    kafedra.setNameEn(dto.getNameEn());
                    kafedraRepository.save(kafedra);
                    return new ApiResponse(true,"kafedra updated successfully!..");
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! nor saved kafedra! Please, enter other kafedra userPositionName!.."
                    );
                }
            }
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount Kafedra"
            );
        }
    }

    public ApiResponse save(KafedraDto dto){
        if (!kafedraRepository.existsKafedraByNameEn(dto.getNameEn())){
            Kafedra kafedra = generateKafedra(dto);
            kafedraRepository.saveAndFlush(kafedra);
            return new ApiResponse(true,"new Kafedra saved successfully!...");
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved Kafedra! Please, enter other Kafedra!"
            );
        }
    }

    public Kafedra generateKafedra(KafedraDto dto) {
        return new Kafedra(dto.getNameUz(),dto.getNameRu(),dto.getNameEn());
    }
    public KafedraDto generateKafedraDto(Kafedra kafedra) {
        return new KafedraDto(kafedra.getId(), kafedra.getNameUz(),kafedra.getNameRu(),kafedra.getNameEn());
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (kafedraRepository.findById(id).isPresent()) {
            kafedraRepository.deleteById(id);
            return new ApiResponse(true,"Kafedra deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount Kafedra!");
        }
    }

    public ApiResponse getKafedraNameByUserId(String userId) {

        KafedraResDto kafedraResDto = kafedraRepository.getKafedraNameByUserId(userId);

        return new ApiResponse(true,"send kafedra", kafedraResDto);
    }

    public ApiResponse getComeCountTodayStatistics(String id) {

        System.out.println("method ga kirdi");
        ComeCountTodayStatistics statistics = kafedraRepository.getComeCountTodayStatistics(id);
        System.out.println(statistics.toString());

        return new ApiResponse(true,"statistics",statistics);
    }

    public ApiResponse getStatisticsForKafedraDashboard(Integer index, String kafedraId) {

        if (index!=0){
            List<NoComeStatistics> noComeStatisticsList = kafedraRepository.getStatisticsForKafedraDashboardNoCome(kafedraId);
            return new ApiResponse(true,"no come",noComeStatisticsList);
        }
        else {
            List<ComeStatistics> comeStatistics = kafedraRepository.getStatisticsForKafedraDashboardCome(kafedraId);
            return new ApiResponse(true,"come",comeStatistics);
        }

    }

    public ApiResponse getKafedrasForSelect() {
        return new ApiResponse(true,"all kafedras",kafedraRepository.getKafedrasForSelect());
    }

    public ApiResponse getTeachersForSelectByKafedraId(String kafedraId) {
        return new ApiResponse(true,"all teachers of kafedras",kafedraRepository.getTeachersForSelectByKafedraId(kafedraId));
    }
}
