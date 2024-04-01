package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.yeoju.yeoju_app.entity.*;
import uz.yeoju.yeoju_app.entity.address.AddressUser;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.ForUserSaveDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.GetUserForUpdate;
import uz.yeoju.yeoju_app.payload.resDto.dekan.StudentAddress;
import uz.yeoju.yeoju_app.repository.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AdminImplService implements AdminService{

    private final UserRepository userRepository;
    private final UserInfoRepo userInfoRepo;
    private final AccMonitoringLogRepo accMonitoringLogRepo;
    private final AddressUserRepository addressUserRepository;
    private final RoleRepository roleRepository;
    private final PositionRepository positionRepository;
    private final GanderRepository ganderRepository;
    private final PasswordEncoder encoder;


    @Override
    public ApiResponse getUserForUpdate(String param) {
        List<GetUserForUpdate> userForUpdate = userRepository.getUserForUpdate("%"+param+"%");
        return new ApiResponse(true,"users",userForUpdate);
    }

    @Override
    public ApiResponse getUserForUpdateById(String id) {
        return new ApiResponse(true,"user fields",userRepository.getUserForUpdateById(id));
    }

    @Override
    public ApiResponse getInformationAboutCountOfUsers() {
        return new ApiResponse(true,"Information of count of users",userInfoRepo.getInformationAboutCountsOfUsers());
    }

    @Override
    public ApiResponse saveOrUpdateUser(ForUserSaveDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else return update(dto);
    }


    private ApiResponse update(ForUserSaveDto dto) {
        Optional<User> userOptional = userRepository.findById(dto.getId());
        if (userOptional.isPresent()){
            User userByRFID = userRepository.findUserByRFID(dto.getRfid());
            if (userByRFID!=null){
                if (userByRFID.getId()==dto.getId()) {
                    USERINFO userinfoByCardNoForSaving = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getRfid());
                    if (userinfoByCardNoForSaving != null) {
                        Optional<User> userByPassportNum = userRepository.findUserByPassportNum(dto.getPassport());
                        if (userByPassportNum.isPresent()) {
                            User userPN = userByPassportNum.get();
                            if (userPN.getId() == dto.getId()) {
                                User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                                if (userByLogin != null) {
                                    if (userByLogin.getId() == dto.getId()) {
                                        User user = userOptional.get();

                                        List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                        accMonitorLogList.forEach(i -> {
                                            i.setCard_no(dto.getRfid());
                                            accMonitoringLogRepo.save(i);
                                        });
                                        USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                        userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                        userInfoRepo.save(userinfoByCardNoForSaving1);

                                        user.setFirstName(dto.getFirstName());
                                        user.setLastName(dto.getLastName());
                                        user.setMiddleName(dto.getMiddleName());
                                        user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                        user.setLogin(dto.getLogin());
                                        user.setRFID(dto.getRfid());
                                        user.setPassportNum(dto.getPassport());
                                        user.setPassword(encoder.encode(dto.getPassword()));
                                        user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                        Set<Role> roleSet = new HashSet<>();
                                        dto.getRoles().forEach(role -> {
                                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                            roleOptional.ifPresent(roleSet::add);
                                        });
                                        user.setRoles(roleSet);
                                        Set<Position> positionSet = new HashSet<>();
                                        dto.getPositions().forEach(position -> {
                                            Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                            positionOptional.ifPresent(positionSet::add);
                                        });
                                        user.setPositions(positionSet);
                                        userRepository.saveAndFlush(user); //save user

                                        Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                        if (addressUserByUserId.isPresent()) {
                                            AddressUser addressUser = addressUserByUserId.get();
                                            addressUser.setUser(user);
                                            addressUser.setRegion(dto.getRegion());
                                            addressUser.setDistrict(dto.getDistrict());
                                            addressUser.setAddress(dto.getAddress());
                                            addressUserRepository.save(addressUser); //save user address
                                        } else {
                                            AddressUser addressUser = new AddressUser();
                                            addressUser.setUser(user);
                                            addressUser.setRegion(dto.getRegion());
                                            addressUser.setDistrict(dto.getDistrict());
                                            addressUser.setAddress(dto.getAddress());
                                            addressUserRepository.save(addressUser); //save user address

                                        }


                                        return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                    }
                                    else {
                                        return new ApiResponse(false, "Error... Already exist other user by login -> " + dto.getLogin());
                                    }
                                }
                                else {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    } else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                }
                            }
                            else {
                                return new ApiResponse(false, "Error... Already exist other user by passport -> " + dto.getPassport());
                            }
                        }
                        else {
                            User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                            if (userByLogin != null) {
                                if (userByLogin.getId() == dto.getId()) {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    }
                                    else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                }
                                else {
                                    return new ApiResponse(false, "Error... Already exist other user by login -> " + dto.getLogin());
                                }
                            }
                            else {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                if (userinfoByCardNoForSaving1!=null) {
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                    userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);
                                }
                                else {
                                    /**============== save user info ==============**/
                                    USERINFO userinfo = new USERINFO();
                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                    userinfo.setCardNo(dto.getRfid());
                                    userinfo.setLastname(dto.getLastName());
                                    userinfo.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfo);    // save userinfo
                                }

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            }
                        }
                    }
                    else {
                        Optional<User> userByPassportNum = userRepository.findUserByPassportNum(dto.getPassport());
                        if (userByPassportNum.isPresent()) {
                            User userPN = userByPassportNum.get();
                            if (userPN.getId() == dto.getId()) {
                                User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                                if (userByLogin != null) {
                                    if (userByLogin.getId() == dto.getId()) {
                                        User user = userOptional.get();

                                        List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                        accMonitorLogList.forEach(i -> {
                                            i.setCard_no(dto.getRfid());
                                            accMonitoringLogRepo.save(i);
                                        });
                                        USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                        if (userinfoByCardNoForSaving1!=null) {
                                            userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                            userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                            userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                            userInfoRepo.save(userinfoByCardNoForSaving1);
                                        }
                                        else {
                                            /**============== save user info ==============**/
                                            USERINFO userinfo = new USERINFO();
                                            userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                            userinfo.setCardNo(dto.getRfid());
                                            userinfo.setLastname(dto.getLastName());
                                            userinfo.setName(dto.getFirstName());
                                            userInfoRepo.save(userinfo);    // save userinfo
                                        }

                                        user.setFirstName(dto.getFirstName());
                                        user.setLastName(dto.getLastName());
                                        user.setMiddleName(dto.getMiddleName());
                                        user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                        user.setLogin(dto.getLogin());
                                        user.setRFID(dto.getRfid());
                                        user.setPassportNum(dto.getPassport());
                                        user.setPassword(encoder.encode(dto.getPassword()));
                                        user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                        Set<Role> roleSet = new HashSet<>();
                                        dto.getRoles().forEach(role -> {
                                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                            roleOptional.ifPresent(roleSet::add);
                                        });
                                        user.setRoles(roleSet);
                                        Set<Position> positionSet = new HashSet<>();
                                        dto.getPositions().forEach(position -> {
                                            Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                            positionOptional.ifPresent(positionSet::add);
                                        });
                                        user.setPositions(positionSet);
                                        userRepository.saveAndFlush(user); //save user

                                        /**============== save user info ==============**/
//                                        USERINFO userinfo = new USERINFO();
//                                        userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
//                                        userinfo.setCardNo(dto.getRfid());
//                                        userinfo.setLastname(dto.getLastName());
//                                        userinfo.setName(dto.getFirstName());
//                                        userInfoRepo.save(userinfo);    // save userinfo

                                        Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                        if (addressUserByUserId.isPresent()) {
                                            AddressUser addressUser = addressUserByUserId.get();
                                            addressUser.setUser(user);
                                            addressUser.setRegion(dto.getRegion());
                                            addressUser.setDistrict(dto.getDistrict());
                                            addressUser.setAddress(dto.getAddress());
                                            addressUserRepository.save(addressUser); //save user address
                                        } else {
                                            AddressUser addressUser = new AddressUser();
                                            addressUser.setUser(user);
                                            addressUser.setRegion(dto.getRegion());
                                            addressUser.setDistrict(dto.getDistrict());
                                            addressUser.setAddress(dto.getAddress());
                                            addressUserRepository.save(addressUser); //save user address

                                        }


                                        return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                    }
                                    else {
                                        return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                                    }
                                }
                                else {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    if (userinfoByCardNoForSaving1!=null) {
                                        userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                        userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                        userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfoByCardNoForSaving1);
                                    }
                                    else {
                                        /**============== save user info ==============**/
                                        USERINFO userinfo = new USERINFO();
                                        userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                        userinfo.setCardNo(dto.getRfid());
                                        userinfo.setLastname(dto.getLastName());
                                        userinfo.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfo);    // save userinfo
                                    }

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    /**============== save user info ==============**/
//                                    USERINFO userinfo = new USERINFO();
//                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
//                                    userinfo.setCardNo(dto.getRfid());
//                                    userinfo.setLastname(dto.getLastName());
//                                    userinfo.setName(dto.getFirstName());
//                                    userInfoRepo.save(userinfo);    // save userinfo

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    } else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                }
                            }
                            else {
                                return new ApiResponse(false, "Error... Already exist user by passport -> " + dto.getPassport());
                            }
                        }
                        else {
                            User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                            if (userByLogin != null) {
                                if (userByLogin.getId() == dto.getId()) {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    if (userinfoByCardNoForSaving1!=null) {
                                        userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                        userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                        userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfoByCardNoForSaving1);
                                    }
                                    else {
                                        /**============== save user info ==============**/
                                        USERINFO userinfo = new USERINFO();
                                        userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                        userinfo.setCardNo(dto.getRfid());
                                        userinfo.setLastname(dto.getLastName());
                                        userinfo.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfo);    // save userinfo
                                    }

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    } else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                } else {
                                    return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                                }
                            } else {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                if (userinfoByCardNoForSaving1!=null) {
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                    userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);
                                }
                                else {
                                    /**============== save user info ==============**/
                                    USERINFO userinfo = new USERINFO();
                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                    userinfo.setCardNo(dto.getRfid());
                                    userinfo.setLastname(dto.getLastName());
                                    userinfo.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfo);    // save userinfo
                                }

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            }
                        }
                    }
                }
                else {
                    return new ApiResponse(false,"Error... Already exist other user by rfid -> "+ dto.getRfid());
                }

            }
            else {
                USERINFO userinfoByCardNoForSaving = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getRfid());
                if (userinfoByCardNoForSaving != null) {
                    Optional<User> userByPassportNum = userRepository.findUserByPassportNum(dto.getPassport());
                    if (userByPassportNum.isPresent()) {
                        User userPN = userByPassportNum.get();
                        if (userPN.getId() == dto.getId()) {
                            User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                            if (userByLogin != null) {
                                if (userByLogin.getId() == dto.getId()) {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                    userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    } else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                } else {
                                    return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                                }
                            } else {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                userInfoRepo.save(userinfoByCardNoForSaving1);

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            }
                        } else {
                            return new ApiResponse(false, "Error... Already exist user by passport -> " + dto.getPassport());
                        }
                    }
                    else {
                        User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                        if (userByLogin != null) {
                            if (userByLogin.getId() == dto.getId()) {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                userInfoRepo.save(userinfoByCardNoForSaving1);

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            } else {
                                return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                            }
                        } else {
                            User user = userOptional.get();

                            List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                            accMonitorLogList.forEach(i -> {
                                i.setCard_no(dto.getRfid());
                                accMonitoringLogRepo.save(i);
                            });
                            USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                            userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                            userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                            userinfoByCardNoForSaving1.setName(dto.getFirstName());
                            userInfoRepo.save(userinfoByCardNoForSaving1);

                            user.setFirstName(dto.getFirstName());
                            user.setLastName(dto.getLastName());
                            user.setMiddleName(dto.getMiddleName());
                            user.setLogin(dto.getLogin());
                            user.setRFID(dto.getRfid());
                            user.setPassportNum(dto.getPassport());
                            user.setPassword(encoder.encode(dto.getPassword()));
                            user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                            Set<Role> roleSet = new HashSet<>();
                            dto.getRoles().forEach(role -> {
                                Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                roleOptional.ifPresent(roleSet::add);
                            });
                            user.setRoles(roleSet);
                            Set<Position> positionSet = new HashSet<>();
                            dto.getPositions().forEach(position -> {
                                Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                positionOptional.ifPresent(positionSet::add);
                            });
                            user.setPositions(positionSet);
                            userRepository.saveAndFlush(user); //save user

                            Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                            if (addressUserByUserId.isPresent()) {
                                AddressUser addressUser = addressUserByUserId.get();
                                addressUser.setUser(user);
                                addressUser.setRegion(dto.getRegion());
                                addressUser.setDistrict(dto.getDistrict());
                                addressUser.setAddress(dto.getAddress());
                                addressUserRepository.save(addressUser); //save user address
                            } else {
                                AddressUser addressUser = new AddressUser();
                                addressUser.setUser(user);
                                addressUser.setRegion(dto.getRegion());
                                addressUser.setDistrict(dto.getDistrict());
                                addressUser.setAddress(dto.getAddress());
                                addressUserRepository.save(addressUser); //save user address

                            }


                            return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                        }
                    }
                }
                else {
                    Optional<User> userByPassportNum = userRepository.findUserByPassportNum(dto.getPassport());
                    if (userByPassportNum.isPresent()) {
                        User userPN = userByPassportNum.get();
                        if (userPN.getId() == dto.getId()) {
                            User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                            if (userByLogin != null) {
                                if (userByLogin.getId() == dto.getId()) {
                                    User user = userOptional.get();

                                    List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                    accMonitorLogList.forEach(i -> {
                                        i.setCard_no(dto.getRfid());
                                        accMonitoringLogRepo.save(i);
                                    });
                                    USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                    if (userinfoByCardNoForSaving1!=null) {
                                        userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                        userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                        userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfoByCardNoForSaving1);
                                    }
                                    else {
                                        /**============== save user info ==============**/
                                        USERINFO userinfo = new USERINFO();
                                        userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                        userinfo.setCardNo(dto.getRfid());
                                        userinfo.setLastname(dto.getLastName());
                                        userinfo.setName(dto.getFirstName());
                                        userInfoRepo.save(userinfo);    // save userinfo
                                    }

                                    user.setFirstName(dto.getFirstName());
                                    user.setLastName(dto.getLastName());
                                    user.setMiddleName(dto.getMiddleName());
                                    user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                    user.setLogin(dto.getLogin());
                                    user.setRFID(dto.getRfid());
                                    user.setPassportNum(dto.getPassport());
                                    user.setPassword(encoder.encode(dto.getPassword()));
                                    user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                    Set<Role> roleSet = new HashSet<>();
                                    dto.getRoles().forEach(role -> {
                                        Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                        roleOptional.ifPresent(roleSet::add);
                                    });
                                    user.setRoles(roleSet);
                                    Set<Position> positionSet = new HashSet<>();
                                    dto.getPositions().forEach(position -> {
                                        Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                        positionOptional.ifPresent(positionSet::add);
                                    });
                                    user.setPositions(positionSet);
                                    userRepository.saveAndFlush(user); //save user

                                    /**============== save user info ==============**/
//                                    USERINFO userinfo = new USERINFO();
//                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
//                                    userinfo.setCardNo(dto.getRfid());
//                                    userinfo.setLastname(dto.getLastName());
//                                    userinfo.setName(dto.getFirstName());
//                                    userInfoRepo.save(userinfo);    // save userinfo

                                    Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                    if (addressUserByUserId.isPresent()) {
                                        AddressUser addressUser = addressUserByUserId.get();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address
                                    } else {
                                        AddressUser addressUser = new AddressUser();
                                        addressUser.setUser(user);
                                        addressUser.setRegion(dto.getRegion());
                                        addressUser.setDistrict(dto.getDistrict());
                                        addressUser.setAddress(dto.getAddress());
                                        addressUserRepository.save(addressUser); //save user address

                                    }


                                    return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                                } else {
                                    return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                                }
                            } else {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                if (userinfoByCardNoForSaving1!=null) {
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                    userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);
                                }
                                else {
                                    /**============== save user info ==============**/
                                    USERINFO userinfo = new USERINFO();
                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                    userinfo.setCardNo(dto.getRfid());
                                    userinfo.setLastname(dto.getLastName());
                                    userinfo.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfo);    // save userinfo
                                }

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                /**============== save user info ==============**/
//                                USERINFO userinfo = new USERINFO();
//                                userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
//                                userinfo.setCardNo(dto.getRfid());
//                                userinfo.setLastname(dto.getLastName());
//                                userinfo.setName(dto.getFirstName());
//                                userInfoRepo.save(userinfo);    // save userinfo

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            }
                        } else {
                            return new ApiResponse(false, "Error... Already exist user by passport -> " + dto.getPassport());
                        }
                    }
                    else {
                        User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                        if (userByLogin != null) {
                            if (userByLogin.getId() == dto.getId()) {
                                User user = userOptional.get();

                                List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                                accMonitorLogList.forEach(i -> {
                                    i.setCard_no(dto.getRfid());
                                    accMonitoringLogRepo.save(i);
                                });
                                USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                                if (userinfoByCardNoForSaving1!=null) {
                                    userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                    userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                    userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfoByCardNoForSaving1);
                                }
                                else {
                                    /**============== save user info ==============**/
                                    USERINFO userinfo = new USERINFO();
                                    userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                    userinfo.setCardNo(dto.getRfid());
                                    userinfo.setLastname(dto.getLastName());
                                    userinfo.setName(dto.getFirstName());
                                    userInfoRepo.save(userinfo);    // save userinfo
                                }

                                user.setFirstName(dto.getFirstName());
                                user.setLastName(dto.getLastName());
                                user.setMiddleName(dto.getMiddleName());
                                user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                                user.setLogin(dto.getLogin());
                                user.setRFID(dto.getRfid());
                                user.setPassportNum(dto.getPassport());
                                user.setPassword(encoder.encode(dto.getPassword()));
                                user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                                Set<Role> roleSet = new HashSet<>();
                                dto.getRoles().forEach(role -> {
                                    Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                    roleOptional.ifPresent(roleSet::add);
                                });
                                user.setRoles(roleSet);
                                Set<Position> positionSet = new HashSet<>();
                                dto.getPositions().forEach(position -> {
                                    Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                    positionOptional.ifPresent(positionSet::add);
                                });
                                user.setPositions(positionSet);
                                userRepository.saveAndFlush(user); //save user

                                Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                                if (addressUserByUserId.isPresent()) {
                                    AddressUser addressUser = addressUserByUserId.get();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address
                                } else {
                                    AddressUser addressUser = new AddressUser();
                                    addressUser.setUser(user);
                                    addressUser.setRegion(dto.getRegion());
                                    addressUser.setDistrict(dto.getDistrict());
                                    addressUser.setAddress(dto.getAddress());
                                    addressUserRepository.save(addressUser); //save user address

                                }


                                return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                            } else {
                                return new ApiResponse(false, "Error... Already exist user by login -> " + dto.getLogin());
                            }
                        } else {
                            User user = userOptional.get();

                            List<AccMonitorLog> accMonitorLogList = accMonitoringLogRepo.findAccMonitorLogsByCard_no(user.getRFID());
                            accMonitorLogList.forEach(i -> {
                                i.setCard_no(dto.getRfid());
                                accMonitoringLogRepo.save(i);
                            });
                            USERINFO userinfoByCardNoForSaving1 = userInfoRepo.getUSERINFOByCardNoForSaving(user.getRFID());
                            if (userinfoByCardNoForSaving1!=null) {
                                userinfoByCardNoForSaving1.setCardNo(dto.getRfid());
                                userinfoByCardNoForSaving1.setLastname(dto.getLastName());
                                userinfoByCardNoForSaving1.setName(dto.getFirstName());
                                userInfoRepo.save(userinfoByCardNoForSaving1);
                            }
                            else {
                                /**============== save user info ==============**/
                                USERINFO userinfo = new USERINFO();
                                userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                                userinfo.setCardNo(dto.getRfid());
                                userinfo.setLastname(dto.getLastName());
                                userinfo.setName(dto.getFirstName());
                                userInfoRepo.save(userinfo);    // save userinfo
                            }

                            user.setFirstName(dto.getFirstName());
                            user.setLastName(dto.getLastName());
                            user.setMiddleName(dto.getMiddleName());
                            user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                            user.setLogin(dto.getLogin());
                            user.setRFID(dto.getRfid());
                            user.setPassportNum(dto.getPassport());
                            user.setPassword(encoder.encode(dto.getPassword()));
                            user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                            Set<Role> roleSet = new HashSet<>();
                            dto.getRoles().forEach(role -> {
                                Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                                roleOptional.ifPresent(roleSet::add);
                            });
                            user.setRoles(roleSet);
                            Set<Position> positionSet = new HashSet<>();
                            dto.getPositions().forEach(position -> {
                                Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                                positionOptional.ifPresent(positionSet::add);
                            });
                            user.setPositions(positionSet);
                            userRepository.saveAndFlush(user); //save user

                            Optional<AddressUser> addressUserByUserId = addressUserRepository.findAddressUserByUserId(dto.getId());
                            if (addressUserByUserId.isPresent()) {
                                AddressUser addressUser = addressUserByUserId.get();
                                addressUser.setUser(user);
                                addressUser.setRegion(dto.getRegion());
                                addressUser.setDistrict(dto.getDistrict());
                                addressUser.setAddress(dto.getAddress());
                                addressUserRepository.save(addressUser); //save user address
                            } else {
                                AddressUser addressUser = new AddressUser();
                                addressUser.setUser(user);
                                addressUser.setRegion(dto.getRegion());
                                addressUser.setDistrict(dto.getDistrict());
                                addressUser.setAddress(dto.getAddress());
                                addressUserRepository.save(addressUser); //save user address

                            }


                            return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " updated user successfully.");
                        }
                    }
                }
            }
        }
        else {
            return new ApiResponse(false,"Error... Not fount user by id -> "+ dto.getId());
        }
    }

    @Transactional
    public ApiResponse save(ForUserSaveDto dto) {
        USERINFO userinfoByCardNoForSaving = userInfoRepo.getUSERINFOByCardNoForSaving(dto.getRfid());
        if (userinfoByCardNoForSaving==null){
            User userByRFID = userRepository.findUserByRFID(dto.getRfid());
            if (userByRFID==null){
                User userByLogin = userRepository.getUserByLogin(dto.getLogin());
                if (userByLogin==null){
                    Optional<User> userByPassportNum = userRepository.findUserByPassportNum(dto.getPassport());
                    if (!userByPassportNum.isPresent()) {
                        User user = new User();
                        user.setFirstName(dto.getFirstName());
                        user.setLastName(dto.getLastName());
                        user.setMiddleName(dto.getMiddleName());
                        user.setFullName(dto.getLastName()+" "+dto.getFirstName()+" "+dto.getMiddleName());
                        user.setLogin(dto.getLogin());
                        user.setRFID(dto.getRfid());
                        user.setPassportNum(dto.getPassport());
                        user.setPassword(encoder.encode(dto.getPassword()));
                        user.setGander(ganderRepository.getGanderByGandername(dto.getGander()));
                        Set<Role> roleSet = new HashSet<>();
                        dto.getRoles().forEach(role -> {
                            Optional<Role> roleOptional = roleRepository.findRoleByRoleName(role);
                            roleOptional.ifPresent(roleSet::add);
                        });
                        user.setRoles(roleSet);
                        Set<Position> positionSet = new HashSet<>();
                        dto.getPositions().forEach(position -> {
                            Optional<Position> positionOptional = positionRepository.findRoleByUserPositionName(position);
                            positionOptional.ifPresent(positionSet::add);
                        });
                        user.setPositions(positionSet);
                        userRepository.saveAndFlush(user); //save user

                        /**============== save user info ==============**/
                        USERINFO userinfo = new USERINFO();
                        userinfo.setBadgenumber(userInfoRepo.getBadgenumber());
                        userinfo.setCardNo(dto.getRfid());
                        userinfo.setLastname(dto.getLastName());
                        userinfo.setName(dto.getFirstName());
                        userInfoRepo.save(userinfo);    // save userinfo

                        AddressUser addressUser = new AddressUser();
                        addressUser.setUser(user);
                        addressUser.setRegion(dto.getRegion());
                        addressUser.setDistrict(dto.getDistrict());
                        addressUser.setAddress(dto.getAddress());
                        addressUserRepository.save(addressUser); //save user address

                        return new ApiResponse(true, dto.getLastName() + " " + dto.getFirstName() + " saved user successfully.");
                    }
                    else {
                        return new ApiResponse(false,"Error... Already exist passport -> "+ dto.getPassport());
                    }
                }
                else {
                    return new ApiResponse(false,"Error... Already exist login(user id) -> "+ dto.getLogin());
                }
            }
            else {
                return new ApiResponse(false,"Error... Already exist user -> "+ dto.getRfid());
            }
        }
        else {
            return new ApiResponse(false,"Error... Already exist RFID -> "+ dto.getRfid());
        }
    }
}
