package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.admin.AccDoor;
import uz.yeoju.yeoju_app.entity.admin.Machines;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.AccDoorDto;
import uz.yeoju.yeoju_app.payload.resDto.admin.DeviceList;
import uz.yeoju.yeoju_app.repository.AccDoorRepository;
import uz.yeoju.yeoju_app.repository.MachinesRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccDoorImplService implements AccDoorService{

    @Autowired
    private AccDoorRepository accDoorRepository;

    @Autowired
    private MachinesRepository machinesRepository;

    @Override
    public ApiResponse findAll() {
        List<AccDoorDto> doorDtoList = accDoorRepository.findAll().stream().map(this::generateAccDoorDto).collect(Collectors.toList());
        return new ApiResponse(true,"all doors",doorDtoList);
    }

    @Override
    public ApiResponse findById(Long id) {
        Optional<AccDoor> optional = accDoorRepository.findById(id);
        return optional.map(accDoor -> new ApiResponse(true, "find door by id", generateAccDoorDto(accDoor))).orElseGet(() -> new ApiResponse(false, "not fount door by id"));
    }

    @Override
    public ApiResponse save(AccDoorDto dto) {
        AccDoor doorForSave = accDoorRepository.getDoorForSave(dto.getDeviceId(), dto.getDoorNo());
        doorForSave.setDoorName(dto.getDoorName());
        AccDoor save = accDoorRepository.save(doorForSave);
//        System.out.println(save.getId()+" ---> "+save.getDoorName());
//        System.out.println(accDoorRepository.findById(save.getId()).get()+" <-- saved");
//        System.out.println("-> "+doorForSave);
        return new ApiResponse(true,"saved successfully...");
    }

    @Override
    public ApiResponse deleteById(Long id) {
//        System.out.println(id+" --->");
        Optional<AccDoor> accDoorOptional = accDoorRepository.findById(id);
        if (accDoorOptional.isPresent()) {
            AccDoor accDoor = accDoorOptional.get();
            Optional<Machines> machinesOptional = machinesRepository.findById(accDoor.getDeviceId());
            Machines machines = machinesOptional.get();
//            System.out.println("m -> "+machines);
            accDoor.setDoorName(machines.getIp()+"-"+accDoor.getDoorNo());
            AccDoor save = accDoorRepository.save(accDoor);
            return new ApiResponse(true,"deleted successfully...",save);
        }
        else {
            return new ApiResponse(false,"not fount door...");
        }


    }

    @Override
    public ApiResponse findByDeviceId(Long deviceId) {
        List<AccDoor> doorList = accDoorRepository.findAllByDeviceId(deviceId);
        return new ApiResponse(true,"list doors by device id",doorList.stream().map(this::generateAccDoorDto));
    }

    @Override
    public ApiResponse getPortsByDeviceId(Long deviceId) {
        return new ApiResponse(true,"ports",accDoorRepository.getPortsByDeviceId(deviceId));
    }

    @Override
    public ApiResponse getDevicesList() {
        List<DeviceList> devicesList = accDoorRepository.getDevicesList();
        return new ApiResponse(true,"list of devices",devicesList);
    }

    public AccDoorDto generateAccDoorDto(AccDoor accDoor){
        AccDoorDto dto = new AccDoorDto();
        dto.setId(accDoor.getId());
        dto.setDoorNo(accDoor.getDoorNo());
        dto.setDeviceId(accDoor.getDeviceId());
        dto.setDoorName(accDoor.getDoorName());
        return dto;
    }
}
