package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat;

import uz.yeoju.yeoju_app.entity.User;
import uz.yeoju.yeoju_app.payload.ApiResponse;
import uz.yeoju.yeoju_app.payload.chat.ChatDto;

public interface ChatService {
    ApiResponse findAll();
    ApiResponse save(User user,String receiverId);
    ApiResponse findUserChats(String userId);
    ApiResponse findChat(String firstId, String secondId);

    ApiResponse findById(String id);
}
