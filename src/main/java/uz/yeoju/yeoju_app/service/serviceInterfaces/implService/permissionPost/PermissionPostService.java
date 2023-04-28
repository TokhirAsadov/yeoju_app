package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.permissionPost;


import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.entity.permissionPost.PermissionPost;
import uz.yeoju.yeoju_app.payload.permissionDto.ChangeStatusDto;
import uz.yeoju.yeoju_app.payload.permissionDto.CommentRequest;
import uz.yeoju.yeoju_app.payload.permissionDto.PPermissionDto;

import java.util.List;

public interface PermissionPostService {

    Flux<ServerSentEvent<List<PPermissionDto>>> streamPosts(String userId);

    PermissionPost getPostByID(String postID);

    List<PPermissionDto> getAll();
    List<PPermissionDto> getAllById(String userId);

   PermissionPost create(User user, PermissionPost post);

    PermissionPost createCommit(User user, CommentRequest request);

    PermissionPost deletePCommit(User user, String postId, String pcommitId);

    String deletePermissionPost(User user, String postId);

    PermissionPost changeStatusOfPost(User user, ChangeStatusDto dto);
}
