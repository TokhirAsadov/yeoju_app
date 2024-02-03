package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.Position;
import uz.yeoju.yeoju_app.entity.Role;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.kafedra.Kafedra;
import uz.yeoju.yeoju_app.entity.kafedra.KafedraMudiri;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraDto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraV2Dto;
import uz.yeoju.yeoju_app.payload.kafedra.KafedraV3Dto;
import uz.yeoju.yeoju_app.payload.OwnerDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeCountTodayStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.ComeStatistics;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.KafedraResDto;
import uz.yeoju.yeoju_app.payload.resDto.kafedra.NoComeStatistics;
import uz.yeoju.yeoju_app.repository.*;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraMudirRepository;
import uz.yeoju.yeoju_app.repository.kafedra.KafedraRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.KafedraImplService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KafedraService implements KafedraImplService<KafedraDto> {
    public final KafedraRepository kafedraRepository;
    public final UserRepository userRepository;
    public final KafedraMudirRepository kafedraMudirRepository;
    public final RoleRepository roleRepository;
    public final PositionRepository positionRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "List of all kafedra",
//                kafedraRepository.findAll().stream().map(this::generateKafedraDto).collect(Collectors.toSet())
                kafedraRepository.findAll().stream().map(this::generateKafedraV3Dto).collect(Collectors.toSet())
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

    @Deprecated
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
    @Deprecated
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

    public ApiResponse saveOrUpdateV2(KafedraV2Dto dto) {
        if (dto.getId() == null) {
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse save(KafedraV2Dto dto){
        if (!kafedraRepository.existsKafedraByNameEn(dto.getNameEn())){
            Optional<User> userOptional = userRepository.findById(dto.getOwnerId());
            if (userOptional.isPresent()) {
                Optional<KafedraMudiri> mudiriOptional = kafedraMudirRepository.findKafedraMudiriByUserId(dto.getOwnerId());
                if (mudiriOptional.isPresent()) {

                    Kafedra kafedra = generateKafedra(dto);
                    kafedra.setOwner(userOptional.get());
                    kafedra.setRoom(dto.getRoom());
                    kafedra.setPhone(dto.getPhone());
                    kafedraRepository.saveAndFlush(kafedra);

                    KafedraMudiri kafedraMudiri = mudiriOptional.get();
                    kafedraMudiri.setKafedra(kafedra);

                    return new ApiResponse(true, dto.getNameEn() + " saved successfully!...");
                }
                else {
                    User user = userOptional.get();
                    Kafedra kafedra = generateKafedra(dto);
                    kafedra.setOwner(user);
                    kafedra.setRoom(dto.getRoom());
                    kafedra.setPhone(dto.getPhone());
                    kafedraRepository.saveAndFlush(kafedra);

                    Set<Role> roles = user.getRoles();
                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_KAFEDRA");
                    Set<Role> roleSet = roles.stream().filter(i -> !Objects.equals(i.getRoleName(), "ROLE_USER")).collect(Collectors.toSet());
                    roleSet.add(roleOptional.get());
                    user.setRoles(roleSet);
                    userRepository.save(user);

                    KafedraMudiri kafedraMudiri = new KafedraMudiri();
                    kafedraMudiri.setKafedra(kafedra);
                    kafedraMudiri.setUser(user);
                    kafedraMudirRepository.save(kafedraMudiri);

                    return new ApiResponse(true, dto.getNameEn() + " saved successfully!...");
                }
            }
            else {
                return new ApiResponse(false,"not fount owner");
            }
        }
        else {
            return new ApiResponse(
                    false,
                    "error! not saved Kafedra! Please, enter other Kafedra!"
            );
        }
    }


    public Kafedra generateKafedra(KafedraV2Dto dto) {
        Set<Role> roleSet = new HashSet<>();
        Set<Position> positionSet = new HashSet<>();
        dto.getRoles().forEach(role -> {
            if(roleRepository.findRoleByRoleName(role).isPresent()) roleSet.add(roleRepository.findRoleByRoleName(role).get());
        });
        dto.getPositions().forEach(position -> {
            if(positionRepository.findRoleByUserPositionName(position).isPresent()) positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
        });
        return new Kafedra(dto.getNameUz(),dto.getNameRu(),dto.getNameEn(),roleSet,positionSet);
    }

    public ApiResponse update(KafedraV2Dto dto){

        Optional<Kafedra> optional = kafedraRepository.findById(dto.getId());
        if (optional.isPresent()){

            Kafedra kafedra = optional.get();
            Kafedra kafedraByName = kafedraRepository.getKafedraByNameEn(dto.getNameEn());
            if (kafedraByName !=null) {
                if (
                        Objects.equals(kafedraByName.getId(), kafedra.getId())
                                ||
                                !kafedraRepository.existsKafedraByNameEn(dto.getNameEn())
                ) {
                    Optional<User> userOptional = userRepository.findById(dto.getOwnerId());
                    if (userOptional.isPresent()) {

                        Optional<KafedraMudiri> mudiriOptional = kafedraMudirRepository.findKafedraMudiriByKafedraId(kafedra.getId());
                        if (mudiriOptional.isPresent()) {
                            KafedraMudiri oldMudir = mudiriOptional.get();
                            if (Objects.equals(oldMudir.getUser().getId(), dto.getOwnerId())) {
                                Optional<KafedraMudiri> mudiriOptional1 = kafedraMudirRepository.findKafedraMudiriByUserId(dto.getOwnerId());
                                if (oldMudir.getId().equals(mudiriOptional1.get().getId())){
                                    kafedra.setNameUz(dto.getNameUz());
                                    kafedra.setNameRu(dto.getNameRu());
                                    kafedra.setNameEn(dto.getNameEn());

                                    Set<Role> roleSet = new HashSet<>();
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        if(roleRepository.findRoleByRoleName(role).isPresent()) roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                    });
                                    dto.getPositions().forEach(position -> {
                                        if(positionRepository.findRoleByUserPositionName(position).isPresent()) positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                    });

                                    kafedra.setRoles(roleSet);
                                    kafedra.setPositions(positionSet);
                                    kafedra.setOwner(userOptional.get());
                                    kafedra.setRoom(dto.getRoom());
                                    kafedra.setPhone(dto.getPhone());

                                    kafedraRepository.save(kafedra);
                                    return new ApiResponse(true, dto.getNameEn()+" kafedra updated successfully!..");
                                }
                                else {
                                    KafedraMudiri mudiri = mudiriOptional1.get();
                                    kafedraMudirRepository.deleteById(mudiri.getId());

                                    kafedra.setNameUz(dto.getNameUz());
                                    kafedra.setNameRu(dto.getNameRu());
                                    kafedra.setNameEn(dto.getNameEn());

                                    Set<Role> roleSet = new HashSet<>();
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        if(roleRepository.findRoleByRoleName(role).isPresent()) roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                    });
                                    dto.getPositions().forEach(position -> {
                                        if(positionRepository.findRoleByUserPositionName(position).isPresent()) positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                    });

                                    kafedra.setRoles(roleSet);
                                    kafedra.setPositions(positionSet);
                                    kafedra.setOwner(userOptional.get());
                                    kafedra.setRoom(dto.getRoom());
                                    kafedra.setPhone(dto.getPhone());

                                    User user = userOptional.get();
                                    oldMudir.setUser(user);
                                    kafedraMudirRepository.save(oldMudir);

                                    kafedraRepository.save(kafedra);
                                    return new ApiResponse(true, dto.getNameEn()+" kafedra updated successfully!..");
                                }
                            }
                            else {
                                Optional<KafedraMudiri> mudiriOptional1 = kafedraMudirRepository.findKafedraMudiriByUserId(dto.getOwnerId());
                                if (mudiriOptional1.isPresent()) {

                                        User oldMudirUser = oldMudir.getUser();
                                        Set<Role> collect = oldMudirUser.getRoles().stream().filter(i -> !Objects.equals(i.getRoleName(), "ROLE_KAFEDRA")).collect(Collectors.toSet());
                                        oldMudirUser.setRoles(collect);
                                        userRepository.save(oldMudirUser);
                                        kafedraMudirRepository.deleteById(mudiriOptional1.get().getId());

                                        User user = userOptional.get();
                                        oldMudir.setUser(user);
                                        kafedraMudirRepository.save(oldMudir);

                                        kafedra.setNameUz(dto.getNameUz());
                                        kafedra.setNameRu(dto.getNameRu());
                                        kafedra.setNameEn(dto.getNameEn());

                                        Set<Role> roleSet = new HashSet<>();
                                        Set<Position> positionSet = new HashSet<>();
                                        dto.getRoles().forEach(role -> {
                                            if (roleRepository.findRoleByRoleName(role).isPresent())
                                                roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                        });
                                        dto.getPositions().forEach(position -> {
                                            if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                                positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                        });

                                        kafedra.setRoles(roleSet);
                                        kafedra.setPositions(positionSet);
                                        kafedra.setOwner(userOptional.get());
                                        kafedra.setRoom(dto.getRoom());
                                        kafedra.setPhone(dto.getPhone());

                                        kafedraRepository.save(kafedra);
                                        return new ApiResponse(true, dto.getNameEn() + " kafedra updated successfully!..");

                                }
                                else {
                                    User oldMudirUser = oldMudir.getUser();
                                    Set<Role> collect = oldMudirUser.getRoles().stream().filter(i -> !Objects.equals(i.getRoleName(), "ROLE_KAFEDRA")).collect(Collectors.toSet());
                                    oldMudirUser.setRoles(collect);
                                    userRepository.save(oldMudirUser);
                                    kafedraMudirRepository.deleteById(mudiriOptional1.get().getId());

                                    User user = userOptional.get();
                                    Set<Role> roles = user.getRoles();
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_KAFEDRA");
                                    Set<Role> roleSet1 = roles.stream().filter(i -> !Objects.equals(i.getRoleName(), "ROLE_USER")).collect(Collectors.toSet());
                                    roleSet1.add(roleOptional.get());
                                    user.setRoles(roleSet1);
                                    userRepository.saveAndFlush(user);

                                    oldMudir.setUser(user);
                                    kafedraMudirRepository.save(oldMudir);

                                    kafedra.setNameUz(dto.getNameUz());
                                    kafedra.setNameRu(dto.getNameRu());
                                    kafedra.setNameEn(dto.getNameEn());

                                    Set<Role> roleSet = new HashSet<>();
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        if (roleRepository.findRoleByRoleName(role).isPresent())
                                            roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                    });
                                    dto.getPositions().forEach(position -> {
                                        if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                            positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                    });

                                    kafedra.setRoles(roleSet);
                                    kafedra.setPositions(positionSet);
                                    kafedra.setOwner(userOptional.get());
                                    kafedra.setRoom(dto.getRoom());
                                    kafedra.setPhone(dto.getPhone());

                                    kafedraRepository.save(kafedra);
                                    return new ApiResponse(true, dto.getNameEn() + " kafedra updated successfully!..");
                                }
                            }
                        }
                        else {
                            Optional<KafedraMudiri> mudiriOptional1 = kafedraMudirRepository.findKafedraMudiriByUserId(dto.getOwnerId());
                            if (mudiriOptional1.isPresent()) {

                                KafedraMudiri kafedraMudiri = mudiriOptional1.get();
                                kafedraMudiri.setKafedra(kafedra);
                                kafedraMudirRepository.save(kafedraMudiri);

                                kafedra.setNameUz(dto.getNameUz());
                                kafedra.setNameRu(dto.getNameRu());
                                kafedra.setNameEn(dto.getNameEn());

                                Set<Role> roleSet = new HashSet<>();
                                Set<Position> positionSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    if (roleRepository.findRoleByRoleName(role).isPresent())
                                        roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                });
                                dto.getPositions().forEach(position -> {
                                    if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                        positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                });

                                kafedra.setRoles(roleSet);
                                kafedra.setPositions(positionSet);
                                kafedra.setOwner(userOptional.get());
                                kafedra.setRoom(dto.getRoom());
                                kafedra.setPhone(dto.getPhone());

                                kafedraRepository.save(kafedra);
                                return new ApiResponse(true, dto.getNameEn() + " kafedra updated successfully!..");

                            }
                            else {
                                User user = userOptional.get();
                                Set<Role> roles = user.getRoles();
                                Optional<Role> roleOptional = roleRepository.findRoleByRoleName("ROLE_KAFEDRA");
                                Set<Role> roleSet1 = roles.stream().filter(i -> !Objects.equals(i.getRoleName(), "ROLE_USER")).collect(Collectors.toSet());
                                roleSet1.add(roleOptional.get());
                                user.setRoles(roleSet1);
                                userRepository.saveAndFlush(user);

                                KafedraMudiri kafedraMudiri = new KafedraMudiri();
                                kafedraMudiri.setUser(user);
                                kafedraMudiri.setKafedra(kafedra);
                                kafedraMudirRepository.save(kafedraMudiri);

                                kafedra.setNameUz(dto.getNameUz());
                                kafedra.setNameRu(dto.getNameRu());
                                kafedra.setNameEn(dto.getNameEn());

                                Set<Role> roleSet = new HashSet<>();
                                Set<Position> positionSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    if (roleRepository.findRoleByRoleName(role).isPresent())
                                        roleSet.add(roleRepository.findRoleByRoleName(role).get());
                                });
                                dto.getPositions().forEach(position -> {
                                    if (positionRepository.findRoleByUserPositionName(position).isPresent())
                                        positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                                });

                                kafedra.setRoles(roleSet);
                                kafedra.setPositions(positionSet);
                                kafedra.setOwner(userOptional.get());
                                kafedra.setRoom(dto.getRoom());
                                kafedra.setPhone(dto.getPhone());

                                kafedraRepository.save(kafedra);
                                return new ApiResponse(true, dto.getNameEn() + " kafedra updated successfully!..");
                            }
                        }

                    }
                    else {
                        return new ApiResponse(false,"not fount owner");
                    }
                } else {
                    return new ApiResponse(
                            false,
                            "error! not saved kafedra! Please, enter other kafedra name!.."
                    );
                }
            }
            else {
                if (!kafedraRepository.existsKafedraByNameEn(dto.getNameEn())){
                    Optional<User> userOptional = userRepository.findById(dto.getOwnerId());
                    if (userOptional.isPresent()) {
                        kafedra.setNameUz(dto.getNameUz());
                        kafedra.setNameRu(dto.getNameRu());
                        kafedra.setNameEn(dto.getNameEn());

                        Set<Role> roleSet = new HashSet<>();
                        Set<Position> positionSet = new HashSet<>();
                        dto.getRoles().forEach(role -> {
                            if(roleRepository.findRoleByRoleName(role).isPresent()) roleSet.add(roleRepository.findRoleByRoleName(role).get());
                        });
                        dto.getPositions().forEach(position -> {
                            if(positionRepository.findRoleByUserPositionName(position).isPresent()) positionSet.add(positionRepository.findRoleByUserPositionName(position).get());
                        });

                        kafedra.setRoles(roleSet);
                        kafedra.setPositions(positionSet);
                        kafedra.setOwner(userOptional.get());
                        kafedra.setRoom(dto.getRoom());
                        kafedra.setPhone(dto.getPhone());

                        kafedraRepository.save(kafedra);
                        return new ApiResponse(true, dto.getNameEn()+" kafedra updated successfully!..");
                    }
                    else {
                        return new ApiResponse(false,"not fount owner");
                    }
                }
                else {
                    return new ApiResponse(
                            false,
                            "error! not saved kafedra! Please, enter other kafedra name!.."
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

    public ApiResponse getKafedraByIdV2(String id) {
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(id);
        return kafedraOptional.map(kafedra -> new ApiResponse(true, "find by id", generateKafedraV2Dto(kafedra))).orElseGet(() -> new ApiResponse(false, "not fount kafedra"));
    }

    public KafedraV2Dto generateKafedraV2Dto(Kafedra kafedra) {
        Set<String> roleSet = new HashSet<>();
        Set<String> positionSet = new HashSet<>();
        kafedra.getRoles().forEach(role -> roleSet.add(role.getRoleName()));
        kafedra.getPositions().forEach(position -> positionSet.add(position.getUserPositionName()));
        return new KafedraV2Dto(kafedra.getId(), kafedra.getNameUz(),kafedra.getNameRu(),kafedra.getNameEn(),roleSet,positionSet,kafedra.getOwner().getId(),kafedra.getRoom(),kafedra.getPhone());
    }

    public ApiResponse getKafedraV3ById(String id) {
        Optional<Kafedra> kafedraOptional = kafedraRepository.findById(id);
        return kafedraOptional.map(kafedra -> new ApiResponse(true, "find by id", generateKafedraV3Dto(kafedra))).orElseGet(() -> new ApiResponse(false, "not fount kafedra"));
    }

    public KafedraV3Dto generateKafedraV3Dto(Kafedra kafedra) {
        Set<String> roleSet = new HashSet<>();
        Set<String> positionSet = new HashSet<>();
        kafedra.getRoles().forEach(role -> roleSet.add(role.getRoleName()));
        kafedra.getPositions().forEach(position -> positionSet.add(position.getUserPositionName()));
        return new KafedraV3Dto(
                kafedra.getId(),
                kafedra.getNameUz(),
                kafedra.getNameRu(),
                kafedra.getNameEn(),
                roleSet,positionSet,
                new OwnerDto(kafedra.getOwner().getId(),kafedra.getOwner().getFullName()),
                kafedra.getRoom(),
                kafedra.getPhone()
        );
    }

    public ApiResponse getTeachersForTableByKafedraId(String kafedraId) {
        return new ApiResponse(true,"all teachers of kafedras",kafedraRepository.getTeachersForTableByKafedraId(kafedraId));
    }
}
