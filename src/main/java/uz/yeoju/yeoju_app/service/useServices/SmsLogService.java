package uz.yeoju.yeoju_app.service.useServices;

import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import uz.yeoju.yeoju_app.entity.enums.SmsStatus;
import uz.yeoju.yeoju_app.entity.enums.SmsType;
import uz.yeoju.yeoju_app.entity.sms.SmsLog;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.sms.*;
import uz.yeoju.yeoju_app.repository.PhoneNumberRepository;
import uz.yeoju.yeoju_app.repository.SmsLogRepository;
import uz.yeoju.yeoju_app.service.serviceInterfaces.implService.SmsLogImplService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SmsLogService implements SmsLogImplService<SmsLogDto> {
    public final SmsLogRepository smsLogRepository;
    public final PhoneNumberRepository numberRepository;
    private final RestTemplate smsClient;

    @Transactional
    public void sendSmsByScheduler(){
        SmsLog smsLog = smsLogRepository.getSmsByStatusUseSending(SmsStatus.SENDING.name());
        System.out.println(smsLog);
        System.out.println(SmsStatus.SENDING.name());


        if (smsLog != null){


            // sms text
            SmsSendContentDTO smsSendContentDTO = new SmsSendContentDTO(smsLog.getMessageBody());

            // sms body
            SmsSendBodyDTO smsSendBodyDTO = new SmsSendBodyDTO(smsSendContentDTO);

            // phone numbers list
            List<SmsSendMessagesDTO> messagesDTOList = new ArrayList<>();

            // phone number of students from database
            List<String> phoneNumbers = new ArrayList<>();


            if (smsLog.getSmsType().equals(SmsType.ALL)){ // All student
                System.out.println("All -------------------------- ");
                List<String> allNumber = numberRepository.getPhoneNumberForSendSmsToAllByDekanId(smsLog.getCreatedBy());
                phoneNumbers.addAll(allNumber);
            }

            if (smsLog.getSmsType().equals(SmsType.COURSE)){ // anyone courses
                System.out.println("course -------------------------- ");
                List<String> courseNumber = numberRepository.getPhoneNumberForSendSmsToCourseByDekanId(smsLog.getCreatedBy(), smsLog.getCourse());
                phoneNumbers.addAll(courseNumber);
            }

            if (smsLog.getSmsType().equals(SmsType.GROUP)){ // anyone groups
                System.out.println("group -------------------------- ");
                List<String> groupNumber = numberRepository.getPhoneNumberForSendSmsToGroupByDekanId(smsLog.getCreatedBy(), smsLog.getGroupName());
                phoneNumbers.addAll(groupNumber);
            }

            if (smsLog.getSmsType().equals(SmsType.STUDENT)){ // single student
                System.out.println("student -------------------------- ");
                List<String> groupNumber = numberRepository.getPhoneNumberForSendSmsToSingleStudentByDekanId(smsLog.getCreatedBy(), smsLog.getUserId());
                System.out.println(groupNumber);
                phoneNumbers.addAll(groupNumber);
            }


            if (phoneNumbers.size() != 0) {
                // messages connect phone numbers
                phoneNumbers.forEach((item) -> messagesDTOList.add(new SmsSendMessagesDTO(item, item)));
                System.out.println(messagesDTOList);

                // request
                SmsSendRequestDTO request = new SmsSendRequestDTO(smsSendBodyDTO, messagesDTOList);

                var responseEntity = smsClient.postForEntity("/send", request, String.class);
                SmsResponseDTO responseDTO = new SmsResponseDTO(responseEntity.getStatusCodeValue(), responseEntity.getBody());

                SmsStatus status;
                System.out.println(responseDTO + " <---------------");
                if (responseDTO.getStatusCode() == 200) {
                    status = SmsStatus.SEND;
                } else {
                    status = SmsStatus.FAILED;
                }
                smsLog.setStatus(status);
                smsLog.setAttempt(smsLog.getAttempt() + 1);
                smsLogRepository.save(smsLog);

            }
        }






    }





    @Override
    public ApiResponse findAll() {
        return new ApiResponse(
            true,
            "List of all sms",
            smsLogRepository.findAll().stream().map(this::generateSmsLogDto).collect(Collectors.toSet())
        );
    }

    @Override
    public ApiResponse findById(String id) {
        return smsLogRepository
                .findById(id)
                .map(educationLanguage -> new ApiResponse(true, "Fount sms by id", educationLanguage))
                .orElseGet(() -> new ApiResponse(false, "Not fount sms by id"));
    }

    @Override
    public ApiResponse getById(String id) {
        SmsLog smsLog = smsLogRepository.getById(id);
        return new ApiResponse(true, "Fount smsLog by id", smsLog);
    }

    @Override
    public ApiResponse saveOrUpdate(SmsLogDto dto) {
        if (dto.getId()==null){
            return save(dto);
        }
        else {
            return update(dto);
        }
    }

    public ApiResponse update(SmsLogDto dto){
        Optional<SmsLog> optional = smsLogRepository.findById(dto.getId());
        if (optional.isPresent()) {
            SmsLog sms = optional.get();
            smsLogRepository.save(sms);
            return new ApiResponse(true, "sms updated successfully!..");
        }
        else{
            return new ApiResponse(
                    false,
                    "error... not fount sms"
            );
        }
    }

    public ApiResponse save(SmsLogDto dto){
        SmsLog sms = generateSmsLog(dto);
        System.out.println(sms+" <--------------------------------");
        smsLogRepository.save(sms);
        return new ApiResponse(true,"new sms saved successfully!...");
    }

    public SmsLog generateSmsLog(SmsLogDto dto) {
        SmsLog smsLog = new SmsLog();
        smsLog.setId(dto.getId());
        smsLog.setSmsType(dto.getSmsType());
        smsLog.setCourse(dto.getCourse());
        smsLog.setAttempt(dto.getAttempt());
        smsLog.setGroupName(dto.getGroupName());
        smsLog.setMessageBody(dto.getMessageBody());
        smsLog.setUserId(dto.getUserId());
        smsLog.setStatus(dto.getStatus());
        return smsLog;
    }
    public SmsLogDto generateSmsLogDto(SmsLog smsLog) {
        return SmsLogDto.builder()
                .id(smsLog.getId())
                .messageBody(smsLog.getMessageBody())
                .course(smsLog.getCourse())
                .groupName(smsLog.getGroupName())
                .userId(smsLog.getUserId())
//                .recipientsPhoneNumber(smsLog.getRecipientsPhoneNumber())
                .status(smsLog.getStatus())
                .smsType(smsLog.getSmsType())
                .build();
    }


    @Override
    public ApiResponse deleteById(String id) {
        if (smsLogRepository.findById(id).isPresent()) {
            smsLogRepository.deleteById(id);
            return new ApiResponse(true,"language deleted successfully!..");
        }
        else {
            return new ApiResponse(false,"error... not fount education language!");
        }
    }
}
