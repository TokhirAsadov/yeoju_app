package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.reference;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.permissionPost.PReference;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.permissionDto.ChangeStatusDto;
import uz.yeoju.yeoju_app.payload.permissionDto.CreateReferenceDto;
import uz.yeoju.yeoju_app.payload.permissionDto.PPermissionDto;
import uz.yeoju.yeoju_app.payload.permissionDto.PReferenceDto;

import java.util.List;

public interface ReferenceService {
    PReference getReferenceById(String id);

    List<PReferenceDto> getAll();
    List<PReferenceDto> getAllById(String userId);
    List<PReferenceDto> getAllByIdForDean(String userId);
    List<PReferenceDto> getHistoryForDean(String userId);
    ApiResponse create(User user, CreateReferenceDto dto);
    ApiResponse changeStatusOfReference(User user, ChangeStatusDto dto);
    Flux<ServerSentEvent<List<PReferenceDto>>> streamReferences(String userId,Boolean bool);

    ApiResponse getTypesOfReference();
    ApiResponse checkPreference(String studentId);
}
