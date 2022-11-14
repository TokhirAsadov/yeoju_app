package uz.yeoju.yeoju_app.service.serviceInterfaces.implService.chat;

import uz.yeoju.yeoju_app.controller.chat.MessageBuild;
import uz.yeoju.yeoju_app.payload.ApiResponse;

public interface MessageService {

    ApiResponse findMessagesByChatId(String id);

    ApiResponse saveOrUpdate(MessageBuild messageDto);
}
