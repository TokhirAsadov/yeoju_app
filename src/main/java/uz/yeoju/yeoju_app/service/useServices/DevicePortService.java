package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.yeoju.yeoju_app.entity.admin.DevicePort;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.admin.DevicePortDto;
import uz.yeoju.yeoju_app.repository.DevicePortRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.DevicePortImplService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DevicePortService implements DevicePortImplService<DevicePortDto> {
    private final DevicePortRepository portRepository;

    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
                true,
                "All ports",
                portRepository.findAll().stream()
                        .map(this::generateDevicePortDto)
                        .collect(Collectors.toSet())
        );
    }

    public <R> DevicePortDto generateDevicePortDto(DevicePort port) {
        return new DevicePortDto(
                port.getId(),
                port.getPort()
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return null;
    }

    @Override
    public ApiResponse getById(String id) {
        return null;
    }

    @Override
    public ApiResponse saveOrUpdate(DevicePortDto dto) {

        if (dto.getId() == null){ //save
            if (!portRepository.existsDevicePortByPort(dto.getPort())){
                portRepository.save(generateDevicePort(dto));
                return new ApiResponse(true,"save device port successfully!..");
            }
            return new ApiResponse(false,"error, sorry already exist port of device");
        }
        else { //update
            if (!portRepository.existsDevicePortByPort(dto.getPort())){
                Optional<DevicePort> devicePortOptional = portRepository.findById(dto.getId());
                if (devicePortOptional.isPresent()) {
                    DevicePort devicePort = devicePortOptional.get();
                    devicePort.setPort(dto.getPort());
                    portRepository.save(devicePort);
                    return new ApiResponse(true,"update devicePort successfully!..");
                }
                return new ApiResponse(false,"error, not fount device port");
            }
            return new ApiResponse(false,"error, sorry already exist port of device");
        }
    }

    private DevicePort generateDevicePort(DevicePortDto dto) {
        return new DevicePort(
                dto.getId(),
                dto.getPort()
        );
    }


    @Override
    public ApiResponse deleteById(String id) {
        return null;
    }
}
